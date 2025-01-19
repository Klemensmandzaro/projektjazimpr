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

    public List<Item> findItems(int page) {
        List<Item> itemList = restClient.get()
                .uri("http://localhost:8082/getallitems?page="+page+"&limit=50")
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

    public List<ItemSpells> findSelectedItemSpells(int page) {
        List<ItemSpells> itemSpellsList = restClient.get()
                .uri("http://localhost:8082/getselecteditemspells?page="+page+"&limit=50")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});

        return itemSpellsList;
    }

    public List<ItemSet> findSelectedItemSets(int page) {
        List<ItemSet> itemSetList = restClient.get()
                .uri("http://localhost:8082/getselecteditemsets?page="+page+"&limit=50")
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

    public void addItemClass(ItemClass itemClass) {
        restClient.post()
                .uri("http://localhost:8082/additemclass")
                .body(itemClass)
                .retrieve()
                .toBodilessEntity();
    }

    public void editItemClass(ItemClass itemClass) {
        restClient.patch()
                .uri("http://localhost:8082/updateitemclass")
                .body(itemClass)
                .retrieve()
                .toBodilessEntity();
    }

    public void deleteItemClass(Long id) {
        restClient.post()
                .uri("http://localhost:8082/deleteitemclass/"+id)
                .retrieve()
                .toBodilessEntity();
    }

    public void addItemSubclass(ItemSubclass itemSubclass){
        restClient.post()
                .uri("http://localhost:8082/additemsubclass")
                .body(itemSubclass)
                .retrieve()
                .toBodilessEntity();
    }

    public void editItemSubclass(ItemSubclass itemSubclass){
        restClient.post()
                .uri("http://localhost:8082/updateitemsubclass")
                .body(itemSubclass)
                .retrieve()
                .toBodilessEntity();
    }

    public void deleteItemSubclass(Long id){
        restClient.post()
                .uri("http://localhost:8082/deleteitemsubclass/"+id)
                .retrieve()
                .toBodilessEntity();
    }

    public void addItemSet(ItemSet itemSet){
        restClient.post()
                .uri("http://localhost:8082/additemset")
                .body(itemSet)
                .retrieve()
                .toBodilessEntity();
    }

    public void editItemSet(ItemSet itemSet){
        restClient.post()
                .uri("http://localhost:8082/updateitemset")
                .body(itemSet)
                .retrieve()
                .toBodilessEntity();
    }

    public void deleteItemSet(Long id){
        restClient.post()
                .uri("http://localhost:8082/deleteitemset/"+id)
                .retrieve()
                .toBodilessEntity();
    }

    public void addItemSpell(ItemSpells itemSpells){
        restClient.post()
                .uri("http://localhost:8082/additemspell")
                .body(itemSpells)
                .retrieve()
                .toBodilessEntity();
    }

    public void editItemSpell(ItemSpells itemSpells){
        restClient.post()
                .uri("http://localhost:8082/updateitemspell")
                .body(itemSpells)
                .retrieve()
                .toBodilessEntity();
    }

    public void deleteItemSpell(Long id){
        restClient.post()
                .uri("http://localhost:8082/deleteitemspell/"+id)
                .retrieve()
                .toBodilessEntity();
    }
}
