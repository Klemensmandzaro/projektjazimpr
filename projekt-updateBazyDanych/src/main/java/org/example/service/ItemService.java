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
        this.webClient = webClientBuilder.baseUrl("https://us.api.blizzard.com").build();
        this.itemRepository = itemRepository;
        this.itemMapper = itemMapper;
    }



    public void fetchAndSaveItems(String accessToken) {
        int page = 1;
        boolean hasMorePages = true;

        while (hasMorePages) {
            int finalPage = page;
            var response = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/data/wow/search/item")
                            .queryParam("namespace", "static-us")
                            .queryParam("locale", "en_US")
                            .queryParam("orderby", "id")
                            .queryParam("_page", finalPage)
                            .build())
                    .header("Authorization", "Bearer " + accessToken)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();


            hasMorePages = processResponse(response, accessToken);
            page++;
        }
    }

    private boolean processResponse(String response, String accessToken) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            JsonNode rootNode = objectMapper.readTree(response);
            JsonNode items = rootNode.path("results");

            for (JsonNode itemNode : items) {
                // Mapuj dane JSON na DTO
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

                String mediaLink = itemMediaNode.path("assets").get(0).path("value").asText();

                ItemDto itemDto = mapToDto(itemNodeReal, mediaLink);

                // Mapuj DTO na encję i zapisz do bazy danych
                Item item = itemMapper.map(itemDto);
                if (itemRepository.findByBlizzardId(item.getBlizzardId()).isEmpty())
                {
                    try {
                        itemRepository.save(item);
                    } catch (Exception e) {
                        System.out.println("this item already exists in database");
                    }
                }

            }

            // Sprawdź, czy są kolejne strony
            return !rootNode.path("page").path("is_last_page").asBoolean();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private ItemDto mapToDto(JsonNode itemNode, String mediaLink) {
        ItemDto itemDto = new ItemDto();
        itemDto.setBlizzardId(itemNode.path("id").asLong());
        itemDto.setName(itemNode.path("name").path("en_US").asText());
        itemDto.setDescription(itemNode.path("data").path("description").path("en_US").asText());


        ItemClassDto itemClassDto = new ItemClassDto();
        itemClassDto.setName(itemNode.path("item_class").path("name").path("en_US").asText());
        itemDto.setItemClass(itemClassDto);

        ItemSubclassDto itemSubclassDto = new ItemSubclassDto();
        itemSubclassDto.setName(itemNode.path("item_subclass").path("name").path("en_US").asText());
        itemDto.setItemSubclass(itemSubclassDto);

        // Mapuj statystyki
        ItemStatsDto itemStatsDto = new ItemStatsDto();
        JsonNode statsNode = itemNode.path("preview_item").path("stats");
        if (statsNode.isArray()) {
            for (JsonNode stat : statsNode) {
                String statType = stat.path("type").path("type").asText(); // Użycie klucza "type" -> "type"
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
                    case "mana":
                        itemStatsDto.setMana(statValue);
                        break;
                    case "critical_strike":
                        itemStatsDto.setCriticalStrike(statValue);
                        break;
                    case "mastery":
                        itemStatsDto.setMastery(statValue);
                        break;
                    case "versatility":
                        itemStatsDto.setVersatility(statValue);
                        break;
                    case "haste":
                        itemStatsDto.setHaste(statValue);
                        break;
                    case "cooldown_reduction":
                        itemStatsDto.setCooldownReduction(statValue);
                        break;
                    case "health_regeneration":
                        itemStatsDto.setHealthRegeneration(statValue);
                        break;
                    case "mana_regeneration":
                        itemStatsDto.setManaRegeneration(statValue);
                        break;
                    case "healing":
                        itemStatsDto.setHealing(statValue);
                        break;
                    default:
                        // Obsłuż inne statystyki, jeśli są potrzebne
                        break;
                }
            }
        }


        itemStatsDto.setArmor(itemNode.path("preview_item").path("armor").path("value").asInt()); // to dziala
        itemStatsDto.setDodge(itemNode.path("preview_item").path("dodge").path("value").asInt()); //to mam nadzieje ale trzeba sprawdzic
        itemStatsDto.setBlock(itemNode.path("preview_item").path("block").path("value").asInt()); // to tez

        //dolozyc mastery bo nie działa, mana regen, mana, health_regen, healing, haste, dodge, critical strike, cooldown, block

        itemDto.setItemStats(itemStatsDto);

        // Mapuj media
        ItemMediaDto itemMediaDto = new ItemMediaDto();
        itemMediaDto.setMediaUrl(mediaLink);
        itemDto.setItemMedia(itemMediaDto);

        // Mapuj zestawy
        ItemSetDto itemSetDto = new ItemSetDto();
        if (itemNode.has("set")) {
            itemSetDto.setName(itemNode.path("set").path("item_set").path("name").asText());
        }
        else
        {
            itemSetDto.setName("Neutral");
        }
        itemDto.setItemSet(itemSetDto);

        return itemDto;
    }
}





