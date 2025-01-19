package org.example.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.DTOs.*;
import org.example.mappers.ItemMapper;
import org.example.model.Item;
import org.example.model.ItemClass;
import org.example.model.ItemSubclass;
import org.example.repositories.ItemClassRepository;
import org.example.repositories.ItemRepository;
import org.example.repositories.ItemSetRepository;
import org.example.repositories.ItemSubclassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


@Service
public class ItemService {

    private final WebClient webClient;
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;


    public ItemService(WebClient.Builder webClientBuilder, ItemRepository itemRepository, ItemMapper itemMapper, ItemClassRepository itemClassRepository, ItemSubclassRepository itemSubclassRepository, ItemSetRepository itemSetRepository) {
        this.webClient = webClientBuilder.baseUrl("https://us.api.blizzard.com")
                .codecs(configurer -> configurer.defaultCodecs()
                    .maxInMemorySize(1024 * 1024 * 10 * 2))
                .build();
        this.itemRepository = itemRepository;
        this.itemMapper = itemMapper;
    }



    public void fetchAndSaveItems(String accessToken) {

        Long lastItem = getHighestIdFromBlizzard(accessToken);

        Long itemId = 25L;
        if (itemRepository.findTopByOrderByBlizzardIdDesc().isPresent())
        {
            itemId=itemRepository.findTopByOrderByBlizzardIdDesc().get().getBlizzardId();
        }

        while (itemId < lastItem) {

            int page = 1;

            boolean hasMorePages = true;

            while (hasMorePages) {
                int finalPage = page;
                Long finalItemId = itemId;
                var response = webClient.get()
                        .uri(uriBuilder -> uriBuilder
                                .path("/data/wow/search/item")
                                .queryParam("namespace", "static-us")
                                .queryParam("locale", "en_US")
                                .queryParam("orderby", "id")
                                .queryParam("_page", finalPage)
                                .queryParam("_pageSize", 100)
                                .queryParam("id", "[" + finalItemId + ",]")
                                .build())
                        .header("Authorization", "Bearer " + accessToken)
                        .retrieve()
                        .bodyToMono(String.class)
                        .block();


                hasMorePages = processResponse(response, accessToken);
                page++;
            }
            if (itemRepository.findTopByOrderByBlizzardIdDesc().isPresent())
            {
                itemId=itemRepository.findTopByOrderByBlizzardIdDesc().get().getBlizzardId();
            }
            else
            {
                itemId++;
            }
        }
    }

    public boolean processResponse(String response, String accessToken) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            JsonNode rootNode = objectMapper.readTree(response);
            JsonNode items = rootNode.path("results");

            for (JsonNode itemNode : items) {
                String key = itemNode.path("key").path("href").asText();
                var itemResponse = webClient.get()
                        .uri(key)
                        .header("Authorization", "Bearer " + accessToken)
                        .retrieve()
                        .bodyToMono(String.class)
                        .block();

                JsonNode itemNodeReal = objectMapper.readTree(itemResponse);

                String keyToMedia = itemNodeReal.path("media").path("key").path("href").asText();
                var itemMediaResponse = webClient.get()
                        .uri(keyToMedia)
                        .header("Authorization", "Bearer " + accessToken)
                        .retrieve()
                        .bodyToMono(String.class)
                        .block();
                JsonNode itemMediaNode = objectMapper.readTree(itemMediaResponse);
                String mediaLink = "";
                if (!itemMediaNode.path("assets").isEmpty())
                {
                    mediaLink = itemMediaNode.path("assets").get(0).path("value").asText();
                }



                ItemDto itemDto = mapToDto(itemNodeReal, mediaLink);

                Item item = itemMapper.map(itemDto);
                if (itemRepository.findByBlizzardId(item.getBlizzardId()).isEmpty())
                {
                    try {
                        itemRepository.save(item);
                    } catch (Exception e) {
                        System.out.println("this item lagging bro");
                    }
                }
                else {
                    System.out.println("this item already exists in database");
                }

            }


            return !(rootNode.path("pageCount").asInt() == rootNode.path("page").asInt());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private ItemDto mapToDto(JsonNode itemNode, String mediaLink) {
        ItemDto itemDto = new ItemDto();
        itemDto.setCreatedByUser(false);
        itemDto.setBlizzardId(itemNode.path("id").asLong());
        itemDto.setName(itemNode.path("name").path("en_US").asText());
        itemDto.setDescription(itemNode.path("preview_item").path("description").asText());


        ItemClassDto itemClassDto = new ItemClassDto();
        itemClassDto.setName(itemNode.path("item_class").path("name").path("en_US").asText());
        itemDto.setItemClass(itemClassDto);

        ItemSubclassDto itemSubclassDto = new ItemSubclassDto();
        itemSubclassDto.setItemClassDto(itemClassDto);
        itemSubclassDto.setName(itemNode.path("item_subclass").path("name").path("en_US").asText());
        itemDto.setItemSubclass(itemSubclassDto);


        ItemStatsDto itemStatsDto = new ItemStatsDto();
        JsonNode statsNode = itemNode.path("preview_item").path("stats");
        if (statsNode.isArray()) {
            for (JsonNode stat : statsNode) {
                String statType = stat.path("type").path("type").asText();
                int statValue = stat.path("value").asInt();

                switch (statType.toLowerCase()) {
                    case "strength":
                        itemStatsDto.setStrength(statValue);
                        break;
                    case "agility":
                        itemStatsDto.setAgility(statValue);
                        break;
                    case "intellect":
                        itemStatsDto.setIntellect(statValue);
                        break;
                    case "stamina":
                        itemStatsDto.setStamina(statValue);
                        break;
                    case "crit_rating":
                        itemStatsDto.setCriticalStrike(statValue);
                        break;
                    case "mastery_rating":
                        itemStatsDto.setMastery(statValue);
                        break;
                    case "versatility":
                        itemStatsDto.setVersatility(statValue);
                        break;
                    case "haste_rating":
                        itemStatsDto.setHaste(statValue);
                        break;
                    case "health_regen":
                        itemStatsDto.setHealthRegeneration(statValue);
                        break;
                    case "mana_regeneration":
                        itemStatsDto.setManaRegeneration(statValue);
                        break;
                    case "dodge_rating":
                        itemStatsDto.setDodge(statValue);
                        break;
                    case "arcane_resistance":
                        itemStatsDto.setArcane_resistance(statValue);
                        break;
                    case "fire_resistance":
                        itemStatsDto.setFire_resistance(statValue);
                        break;
                    case "frost_resistance":
                        itemStatsDto.setFrost_resistance(statValue);
                        break;
                    case "nature_resistance":
                        itemStatsDto.setNature_resistance(statValue);
                        break;
                    case "shadow_resistance":
                        itemStatsDto.setShadow_resistance(statValue);
                        break;
                    case "spell_power":
                        itemStatsDto.setSpell_power(statValue);
                        break;
                    case "spirit":
                        itemStatsDto.setSpirit(statValue);
                        break;
                    case "parry_rating":
                        itemStatsDto.setParry(statValue);
                        break;
                    case "crit_ranged_rating":
                        itemStatsDto.setCrit_ranged(statValue);
                        break;
                    case "attack_power":
                        itemStatsDto.setAttack_power(statValue);
                        break;
                    case "ranged_attack_power":
                        itemStatsDto.setRanged_attack_power(statValue);
                        break;
                    case "bonus_stat_profession_crafting_speed":
                        itemStatsDto.setCrafting_speed(statValue);
                        break;
                    case "bonus_stat_profession_deftness":
                        itemStatsDto.setDeftness(statValue);
                        break;
                    case "bonus_stat_profession_finesse":
                        itemStatsDto.setFinesse(statValue);
                        break;
                    case "bonus_stat_profession_ingenuity":
                        itemStatsDto.setIngenuity(statValue);
                        break;
                    case "bonus_stat_profession_multicraft":
                        itemStatsDto.setMulticraft(statValue);
                        break;
                    case "bonus_stat_profession_perception":
                        itemStatsDto.setPerception(statValue);
                        break;
                    case "bonus_stat_profession_resourcefulness":
                        itemStatsDto.setResourcefulness(statValue);
                        break;
                    case "combat_rating_avoidance":
                        itemStatsDto.setAvoidance(statValue);
                        break;
                    case "combat_rating_lifesteal":
                        itemStatsDto.setLifesteal(statValue);
                        break;
                    case "combat_rating_speed":
                        itemStatsDto.setSpeed(statValue);
                        break;
                    case "combat_rating_sturdiness":
                        itemStatsDto.setSturdiness(statValue);
                        break;
                    case "corruption_resistance":
                        itemStatsDto.setCorruption_resistance(statValue);
                        break;
                    case "extra_armor":
                        itemStatsDto.setExtra_armor(statValue);
                        break;
                    case "mana":
                        itemStatsDto.setMana(statValue);
                        break;
                    default:
                        itemStatsDto.setOtherType(statType);
                        break;
                }
            }
        }


        itemStatsDto.setArmor(itemNode.path("preview_item").path("armor").path("value").asInt()); // to dziala
        itemStatsDto.setBlock(itemNode.path("preview_item").path("shield_block").path("value").asInt()); // to tez
        itemStatsDto.setDamage_min(itemNode.path("preview_item").path("weapon").path("damage").path("min_value").asInt());
        itemStatsDto.setDamage_max(itemNode.path("preview_item").path("weapon").path("damage").path("max_value").asInt());
        itemStatsDto.setAttack_speed(itemNode.path("preview_item").path("weapon").path("attack_speed").path("value").asInt());
        itemStatsDto.setDPS(itemNode.path("preview_item").path("weapon").path("dps").path("value").asDouble());

        //dolozyc  bo nie działa, mana regen

        itemDto.setItemStats(itemStatsDto);


        ItemMediaDto itemMediaDto = new ItemMediaDto();
        itemMediaDto.setMediaUrl(mediaLink);
        itemDto.setItemMedia(itemMediaDto);


        ItemSetDto itemSetDto = new ItemSetDto();
        if (itemNode.path("preview_item").has("set")) {
            itemSetDto.setName(itemNode.path("preview_item").path("set").path("item_set").path("name").path("en_US").asText());
            JsonNode effectsNode = itemNode.path("preview_item").path("set").path("effects");
            if (effectsNode.isArray() && !effectsNode.isEmpty()) {
                for (JsonNode effect : effectsNode) {
                    itemSetDto.getEffects().add(effect.path("display_string").path("en_US").asText());
                }
            }
        }
        else
        {
            itemSetDto.setName("Neutral");
        }
        itemDto.setItemSet(itemSetDto);


        // Mapuj zaklęcia (zobaczymy czy en_US działa)
        JsonNode spellsNode = itemNode.path("preview_item").path("spells");
        if (spellsNode.isArray() && !spellsNode.isEmpty()) {
            for (JsonNode spell : spellsNode) {
                ItemSpellsDto itemSpellsDto = new ItemSpellsDto();
                itemSpellsDto.setName(spell.path("spell").path("name").path("en_US").asText());
                itemSpellsDto.setDescription(spell.path("description").path("en_US").asText());
                itemDto.getItemSpells().add(itemSpellsDto);
            }
        }

        return itemDto;
    }

    public Long getHighestIdFromBlizzard(String accessToken) {
        var response = webClient.get()
                .uri("https://us.api.blizzard.com/data/wow/search/item?namespace=static-us&locale=en_US&orderby=id:desc&_page=1&_pageSize=1&id=[,999999]")
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            JsonNode rootNode = objectMapper.readTree(response);
            Long lastItem = rootNode.path("results").get(0).path("data").path("id").asLong();
            return lastItem;
        }catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error while fetching highest id from Blizzard API");
        }

    }
}





