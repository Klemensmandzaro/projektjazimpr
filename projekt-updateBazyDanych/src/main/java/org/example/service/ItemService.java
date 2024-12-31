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
        if (itemRepository.findTopByOrderByIdDesc().isPresent())
        {
            itemId=itemRepository.findTopByOrderByIdDesc().get().getBlizzardId();
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
            if (itemRepository.findTopByOrderByIdDesc().isPresent())
            {
                itemId=itemRepository.findTopByOrderByIdDesc().get().getBlizzardId();
            }
            else
            {
                itemId++;
            }
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
                        System.out.println("this item lagging bro");
                    }
                }
                else {
                    System.out.println("this item already exists in database");
                }

            }

            // Sprawdź, czy są kolejne strony
            return !(rootNode.path("pageCount").asInt() == rootNode.path("page").asInt());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private ItemDto mapToDto(JsonNode itemNode, String mediaLink) {
        ItemDto itemDto = new ItemDto();
        itemDto.setBlizzardId(itemNode.path("id").asLong());
        itemDto.setName(itemNode.path("name").path("en_US").asText());
        itemDto.setDescription(itemNode.path("preview_item").path("description").asText());


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
                    default:
                        // Obsłuż inne statystyki, jeśli są potrzebne
                        break;
                }
            }
        }


        itemStatsDto.setArmor(itemNode.path("preview_item").path("armor").path("value").asInt()); // to dziala
        itemStatsDto.setBlock(itemNode.path("preview_item").path("shield_block").path("value").asInt()); // to tez

        //dolozyc  bo nie działa, mana regen

        itemDto.setItemStats(itemStatsDto);

        // Mapuj media
        ItemMediaDto itemMediaDto = new ItemMediaDto();
        itemMediaDto.setMediaUrl(mediaLink);
        itemDto.setItemMedia(itemMediaDto);

        // Mapuj zestawy
        ItemSetDto itemSetDto = new ItemSetDto();
        if (itemNode.path("preview_item").has("set")) {
            itemSetDto.setName(itemNode.path("preview_item").path("set").path("item_set").path("name").path("en_US").asText());
        }
        else
        {
            itemSetDto.setName("Neutral");
        }
        itemDto.setItemSet(itemSetDto);

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





