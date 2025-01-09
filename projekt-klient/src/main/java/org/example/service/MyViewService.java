package org.example.service;

import org.example.model.*;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class MyViewService {
    private RestClient restClient;

    public MyViewService(RestClient restClient) {
        this.restClient = restClient;
    }

    public List<Item> findSelectedItems(Long id) {
        List<Item> itemList = restClient.get()
                .uri("http://localhost:8082/getselecteditems?start="+id+"&limit=10")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});

        return itemList;
    }

    public List<ItemClass> findItemClasses() {
        List<ItemClass> itemClassList = restClient.get()
                .uri("http://localhost:8082/getallitemclasses")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});

        return itemClassList;
    }

    public List<Item> findItemsCreatedByUser() {
        List<Item> itemList = restClient.get()
                .uri("http://localhost:8082/getitemscreatedbyuser")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});

        return itemList;
    }

    public List<ItemSubclass> findItemSubclasses() {
        List<ItemSubclass> itemSubclassList = restClient.get()
                .uri("http://localhost:8082/getallitemsubclasses")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});

        return itemSubclassList;
    }

    public List<ItemSpells> findSelectedItemSpells(Long id) {
        List<ItemSpells> itemSpellsList = restClient.get()
                .uri("http://localhost:8082/getselecteditemspells?start="+id+"&limit=10")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});

        return itemSpellsList;
    }

    public List<ItemSet> findSelectedItemSets(Long id) {
        List<ItemSet> itemSetList = restClient.get()
                .uri("http://localhost:8082/getselecteditemsets?start="+id+"&limit=10")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});

        return itemSetList;
    }

    public List<ItemSet> findAllItemSets() {
        List<ItemSet> itemSetList = restClient.get()
                .uri("http://localhost:8082/getallitemsets")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});

        return itemSetList;
    }

    public List<ItemSpells> findAllItemSpells() {
        List<ItemSpells> itemSpellsList = restClient.get()
                .uri("http://localhost:8082/getallitemspells")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});

        return itemSpellsList;
    }

    public void addItem(Item item) {
        restClient.post()
                .uri("http://localhost:8082/additem")
                .body(item)
                .retrieve()
                .toBodilessEntity();
    }

    public ItemSpells findItemSpellById(Long id) {
        ItemSpells itemSpells = restClient.get()
                .uri("http://localhost:8082/getitemspellbyid/"+id)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});

        return itemSpells;
    }

    public void editItem(Item item) {
        restClient.patch()
                .uri("http://localhost:8082/updateitem")
                .body(item)
                .retrieve()
                .toBodilessEntity();
    }

    public void deleteItem(Long id) {
        restClient.post()
                .uri("http://localhost:8082/deleteitem/"+id)
                .retrieve()
                .toBodilessEntity();
    }
}
