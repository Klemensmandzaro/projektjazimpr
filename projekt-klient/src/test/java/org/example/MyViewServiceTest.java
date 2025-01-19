package org.example;

import org.example.model.*;
import org.example.service.MyViewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.MockServerRestClientCustomizer;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class MyViewServiceTest {

    private MyViewService myViewService;


    MockServerRestClientCustomizer customizer = new MockServerRestClientCustomizer();
    RestClient.Builder builder = RestClient.builder();

    @BeforeEach
    void setUp() {
        customizer.customize(builder);
        myViewService = new MyViewService(builder.build());
    }

    @Test
    void findItems_shouldReturnItemList() {
        List<Item> expectedItems = List.of(new Item(), new Item());
        expectedItems.getFirst().setId(1L);
        customizer.getServer().expect(ExpectedCount.once(), MockRestRequestMatchers.requestTo("http://localhost:8082/getallitems?page=0&limit=50"))
                .andRespond(MockRestResponseCreators.withSuccess("[{\"id\":\"1\"}, {}]", MediaType.APPLICATION_JSON));

        List<Item> actualItems = myViewService.findItems(0);

        assertEquals(actualItems.getFirst().getId(), expectedItems.getFirst().getId());
        assertEquals(actualItems.getFirst().getName(), expectedItems.getFirst().getName());

    }

    @Test
    void findItems_shouldReturnEmptyList() {
        customizer.getServer().expect(ExpectedCount.once(), MockRestRequestMatchers.requestTo("http://localhost:8082/getallitems?page=0&limit=50"))
                .andRespond(MockRestResponseCreators.withSuccess("[]", MediaType.APPLICATION_JSON));

        List<Item> actualItems = myViewService.findItems(0);

        assertThat(actualItems).isEmpty();
    }

    @Test
    void findItemClasses_shouldReturnItemClassList() {
        List<ItemClass> expectedItemClasses = List.of(new ItemClass(), new ItemClass());
        customizer.getServer().expect(ExpectedCount.once(), MockRestRequestMatchers.requestTo("http://localhost:8082/getallitemclasses"))
                .andRespond(MockRestResponseCreators.withSuccess("[{\"id\":\"1\"}, {}]", MediaType.APPLICATION_JSON));
        expectedItemClasses.getFirst().setId(1L);
        List<ItemClass> actualItemClasses = myViewService.findItemClasses();

        assertEquals(actualItemClasses.getFirst().getId(), expectedItemClasses.getFirst().getId());

    }

    @Test
    void findItemClasses_shouldReturnEmptyList() {
        customizer.getServer().expect(ExpectedCount.once(), MockRestRequestMatchers.requestTo("http://localhost:8082/getallitemclasses"))
                .andRespond(MockRestResponseCreators.withSuccess("[]", MediaType.APPLICATION_JSON));

        List<ItemClass> actualItemClasses = myViewService.findItemClasses();

        assertThat(actualItemClasses).isEmpty();
    }

    @Test
    void findItemsCreatedByUser_shouldReturnItemList() {
        List<Item> expectedItems = List.of(new Item(), new Item());
        expectedItems.getFirst().setId(1L);
        customizer.getServer().expect(ExpectedCount.once(), MockRestRequestMatchers.requestTo("http://localhost:8082/getitemscreatedbyuser"))
                .andRespond(MockRestResponseCreators.withSuccess("[{\"id\":\"1\"}, {}]", MediaType.APPLICATION_JSON));

        List<Item> actualItems = myViewService.findItemsCreatedByUser();

        assertEquals(actualItems.getFirst().getId(), expectedItems.getFirst().getId());
        assertEquals(actualItems.getFirst().getName(), expectedItems.getFirst().getName());
    }

    @Test
    void findItemsCreatedByUser_shouldReturnEmptyList() {
        customizer.getServer().expect(ExpectedCount.once(), MockRestRequestMatchers.requestTo("http://localhost:8082/getitemscreatedbyuser"))
                .andRespond(MockRestResponseCreators.withSuccess("[]", MediaType.APPLICATION_JSON));

        List<Item> actualItems = myViewService.findItemsCreatedByUser();

        assertThat(actualItems).isEmpty();
    }

    @Test
    void findItemSubclasses_shouldReturnItemSubclassList() {
        List<ItemSubclass> expectedItemSubclasses = List.of(new ItemSubclass(), new ItemSubclass());
        expectedItemSubclasses.getFirst().setId(1L);
        expectedItemSubclasses.getFirst().setSubclassName("subclassName");
        customizer.getServer().expect(ExpectedCount.once(), MockRestRequestMatchers.requestTo("http://localhost:8082/getallitemsubclasses"))
                .andRespond(MockRestResponseCreators.withSuccess("[{\"id\":\"1\",\"subclassName\":\"subclassName\"}, {}]", MediaType.APPLICATION_JSON));

        List<ItemSubclass> actualItemSubclasses = myViewService.findItemSubclasses();

        assertEquals(actualItemSubclasses.getFirst().getId(), expectedItemSubclasses.getFirst().getId());
        assertEquals(actualItemSubclasses.getFirst().getSubclassName(), expectedItemSubclasses.getFirst().getSubclassName());
    }

    @Test
    void findItemSubclasses_shouldReturnEmptyList() {
        customizer.getServer().expect(ExpectedCount.once(), MockRestRequestMatchers.requestTo("http://localhost:8082/getallitemsubclasses"))
                .andRespond(MockRestResponseCreators.withSuccess("[]", MediaType.APPLICATION_JSON));

        List<ItemSubclass> actualItemSubclasses = myViewService.findItemSubclasses();

        assertThat(actualItemSubclasses).isEmpty();
    }

    @Test
    void findSelectedItemSpells_shouldReturnItemSpellsList() {
        List<ItemSpells> expectedItemSpells = List.of(new ItemSpells(), new ItemSpells());
        expectedItemSpells.getFirst().setId(1L);
        expectedItemSpells.getFirst().setName("name");
        customizer.getServer().expect(ExpectedCount.once(), MockRestRequestMatchers.requestTo("http://localhost:8082/getselecteditemspells?page=1&limit=50"))
                .andRespond(MockRestResponseCreators.withSuccess("[{\"id\":\"1\",\"name\":\"name\"}, {}]", MediaType.APPLICATION_JSON));

        List<ItemSpells> actualItemSpells = myViewService.findSelectedItemSpells(1);

        assertEquals(actualItemSpells.getFirst().getId(), expectedItemSpells.getFirst().getId());
        assertEquals(actualItemSpells.getFirst().getName(), expectedItemSpells.getFirst().getName());
    }

    @Test
    void findSelectedItemSpells_shouldReturnEmptyList() {
        customizer.getServer().expect(ExpectedCount.once(), MockRestRequestMatchers.requestTo("http://localhost:8082/getselecteditemspells?page=1&limit=50"))
                .andRespond(MockRestResponseCreators.withSuccess("[]", MediaType.APPLICATION_JSON));

        List<ItemSpells> actualItemSpells = myViewService.findSelectedItemSpells(1);

        assertThat(actualItemSpells).isEmpty();
    }

    @Test
    void findSelectedItemSets_shouldReturnItemSetList() {
        List<ItemSet> expectedItemSets = List.of(new ItemSet(), new ItemSet());
        expectedItemSets.getFirst().setId(1L);
        expectedItemSets.getFirst().setSetName("name");
        customizer.getServer().expect(ExpectedCount.once(), MockRestRequestMatchers.requestTo("http://localhost:8082/getselecteditemsets?page=1&limit=50"))
                .andRespond(MockRestResponseCreators.withSuccess("[{\"id\":\"1\",\"setName\":\"name\"}, {}]", MediaType.APPLICATION_JSON));

        List<ItemSet> actualItemSets = myViewService.findSelectedItemSets(1);

        assertEquals(actualItemSets.getFirst().getId(), expectedItemSets.getFirst().getId());
        assertEquals(actualItemSets.getFirst().getSetName(), expectedItemSets.getFirst().getSetName());
    }

    @Test
    void findSelectedItemSets_shouldReturnEmptyList() {
        customizer.getServer().expect(ExpectedCount.once(), MockRestRequestMatchers.requestTo("http://localhost:8082/getselecteditemsets?page=1&limit=50"))
                .andRespond(MockRestResponseCreators.withSuccess("[]", MediaType.APPLICATION_JSON));

        List<ItemSet> actualItemSets = myViewService.findSelectedItemSets(1);

        assertThat(actualItemSets).isEmpty();
    }

    @Test
    void findAllItemSets_shouldReturnItemSetList() {
        List<ItemSet> expectedItemSets = List.of(new ItemSet(), new ItemSet());
        expectedItemSets.getFirst().setId(1L);
        expectedItemSets.getFirst().setSetName("name");
        customizer.getServer().expect(ExpectedCount.once(), MockRestRequestMatchers.requestTo("http://localhost:8082/getallitemsets"))
                .andRespond(MockRestResponseCreators.withSuccess("[{\"id\":\"1\",\"setName\":\"name\"}, {}]", MediaType.APPLICATION_JSON));

        List<ItemSet> actualItemSets = myViewService.findAllItemSets();

        assertEquals(actualItemSets.getFirst().getId(), expectedItemSets.getFirst().getId());
        assertEquals(actualItemSets.getFirst().getSetName(), expectedItemSets.getFirst().getSetName());
    }

    @Test
    void findAllItemSets_shouldReturnEmptyList() {
        customizer.getServer().expect(ExpectedCount.once(), MockRestRequestMatchers.requestTo("http://localhost:8082/getallitemsets"))
                .andRespond(MockRestResponseCreators.withSuccess("[]", MediaType.APPLICATION_JSON));

        List<ItemSet> actualItemSets = myViewService.findAllItemSets();

        assertThat(actualItemSets).isEmpty();
    }

    @Test
    void findAllItemSpells_shouldReturnItemSpellsList() {
        List<ItemSpells> expectedItemSpells = List.of(new ItemSpells(), new ItemSpells());
        expectedItemSpells.getFirst().setId(1L);
        expectedItemSpells.getFirst().setName("name");
        customizer.getServer().expect(ExpectedCount.once(), MockRestRequestMatchers.requestTo("http://localhost:8082/getallitemspells"))
                .andRespond(MockRestResponseCreators.withSuccess("[{\"id\":\"1\",\"name\":\"name\"}, {}]", MediaType.APPLICATION_JSON));

        List<ItemSpells> actualItemSpells = myViewService.findAllItemSpells();

        assertEquals(actualItemSpells.getFirst().getId(), expectedItemSpells.getFirst().getId());
        assertEquals(actualItemSpells.getFirst().getName(), expectedItemSpells.getFirst().getName());
    }

    @Test
    void findAllItemSpells_shouldReturnEmptyList() {
        customizer.getServer().expect(ExpectedCount.once(), MockRestRequestMatchers.requestTo("http://localhost:8082/getallitemspells"))
                .andRespond(MockRestResponseCreators.withSuccess("[]", MediaType.APPLICATION_JSON));

        List<ItemSpells> actualItemSpells = myViewService.findAllItemSpells();

        assertThat(actualItemSpells).isEmpty();
    }

    @Test
    void addItem_shouldAddItemSuccessfully() {
        Item item = new Item();
        item.setName("Item1");
        ItemClass itemClass = new ItemClass();
        itemClass.setClassName("Class1");
        item.setItemClass(itemClass);
        ItemSubclass itemSubclass = new ItemSubclass();
        itemSubclass.setSubclassName("Subclass1");
        item.setItemSubclass(itemSubclass);
        ItemSet itemSet = new ItemSet();
        itemSet.setSetName("Set1");
        item.setItemSet(itemSet);
        item.setItemStats(new ItemStats());
        item.setItemSpells(new ArrayList<>());
        customizer.getServer().expect(ExpectedCount.once(), MockRestRequestMatchers.requestTo("http://localhost:8082/additem"))
                .andRespond(MockRestResponseCreators.withSuccess());

        myViewService.addItem(item);

        customizer.getServer().verify();
    }

    @Test
    void findItemSpellById_shouldReturnItemSpell() {
        ItemSpells expectedItemSpells = new ItemSpells();
        expectedItemSpells.setId(1L);
        expectedItemSpells.setName("name");
        customizer.getServer().expect(ExpectedCount.once(), MockRestRequestMatchers.requestTo("http://localhost:8082/getitemspellbyid/1"))
                .andRespond(MockRestResponseCreators.withSuccess("{\"id\":\"1\",\"name\":\"name\"}", MediaType.APPLICATION_JSON));

        ItemSpells actualItemSpells = myViewService.findItemSpellById(1L);

        assertEquals(actualItemSpells.getId(), expectedItemSpells.getId());
        assertEquals(actualItemSpells.getName(), expectedItemSpells.getName());
    }

    @Test
    void editItem_shouldEditItemSuccessfully() {
        Item item = new Item();
        item.setName("Item1");
        ItemClass itemClass = new ItemClass();
        itemClass.setClassName("Class1");
        item.setItemClass(itemClass);
        ItemSubclass itemSubclass = new ItemSubclass();
        itemSubclass.setSubclassName("Subclass1");
        item.setItemSubclass(itemSubclass);
        ItemSet itemSet = new ItemSet();
        itemSet.setSetName("Set1");
        item.setItemSet(itemSet);
        item.setItemStats(new ItemStats());
        item.setItemSpells(new ArrayList<>());
        customizer.getServer().expect(ExpectedCount.once(), MockRestRequestMatchers.requestTo("http://localhost:8082/updateitem"))
                .andRespond(MockRestResponseCreators.withSuccess());

        myViewService.editItem(item);

        customizer.getServer().verify();
    }

    @Test
    void deleteItem_shouldDeleteItemSuccessfully() {
        customizer.getServer().expect(ExpectedCount.once(), MockRestRequestMatchers.requestTo("http://localhost:8082/deleteitem/1"))
                .andRespond(MockRestResponseCreators.withSuccess());

        myViewService.deleteItem(1L);

        customizer.getServer().verify();
    }

    @Test
    void addItemClass_shouldAddItemClassSuccessfully() {
        ItemClass itemClass = new ItemClass();
        customizer.getServer().expect(ExpectedCount.once(), MockRestRequestMatchers.requestTo("http://localhost:8082/additemclass"))
                .andRespond(MockRestResponseCreators.withSuccess());

        myViewService.addItemClass(itemClass);

        customizer.getServer().verify();
    }

    @Test
    void editItemClass_shouldEditItemClassSuccessfully() {
        ItemClass itemClass = new ItemClass();
        customizer.getServer().expect(ExpectedCount.once(), MockRestRequestMatchers.requestTo("http://localhost:8082/updateitemclass"))
                .andRespond(MockRestResponseCreators.withSuccess());

        myViewService.editItemClass(itemClass);

        customizer.getServer().verify();
    }

    @Test
    void deleteItemClass_shouldDeleteItemClassSuccessfully() {
        customizer.getServer().expect(ExpectedCount.once(), MockRestRequestMatchers.requestTo("http://localhost:8082/deleteitemclass/1"))
                .andRespond(MockRestResponseCreators.withSuccess());

        myViewService.deleteItemClass(1L);

        customizer.getServer().verify();
    }

    @Test
    void addItemSubclass_shouldAddItemSubclassSuccessfully() {
        ItemSubclass itemSubclass = new ItemSubclass();
        customizer.getServer().expect(ExpectedCount.once(), MockRestRequestMatchers.requestTo("http://localhost:8082/additemsubclass"))
                .andRespond(MockRestResponseCreators.withSuccess());

        myViewService.addItemSubclass(itemSubclass);

        customizer.getServer().verify();
    }

    @Test
    void editItemSubclass_shouldEditItemSubclassSuccessfully() {
        ItemSubclass itemSubclass = new ItemSubclass();
        customizer.getServer().expect(ExpectedCount.once(), MockRestRequestMatchers.requestTo("http://localhost:8082/updateitemsubclass"))
                .andRespond(MockRestResponseCreators.withSuccess());

        myViewService.editItemSubclass(itemSubclass);

        customizer.getServer().verify();
    }

    @Test
    void deleteItemSubclass_shouldDeleteItemSubclassSuccessfully() {
        customizer.getServer().expect(ExpectedCount.once(), MockRestRequestMatchers.requestTo("http://localhost:8082/deleteitemsubclass/1"))
                .andRespond(MockRestResponseCreators.withSuccess());

        myViewService.deleteItemSubclass(1L);

        customizer.getServer().verify();
    }

    @Test
    void addItemSet_shouldAddItemSetSuccessfully() {
        ItemSet itemSet = new ItemSet();
        customizer.getServer().expect(ExpectedCount.once(), MockRestRequestMatchers.requestTo("http://localhost:8082/additemset"))
                .andRespond(MockRestResponseCreators.withSuccess());

        myViewService.addItemSet(itemSet);

        customizer.getServer().verify();
    }

    @Test
    void editItemSet_shouldEditItemSetSuccessfully() {
        ItemSet itemSet = new ItemSet();
        customizer.getServer().expect(ExpectedCount.once(), MockRestRequestMatchers.requestTo("http://localhost:8082/updateitemset"))
                .andRespond(MockRestResponseCreators.withSuccess());

        myViewService.editItemSet(itemSet);

        customizer.getServer().verify();
    }

    @Test
    void deleteItemSet_shouldDeleteItemSetSuccessfully() {
        customizer.getServer().expect(ExpectedCount.once(), MockRestRequestMatchers.requestTo("http://localhost:8082/deleteitemset/1"))
                .andRespond(MockRestResponseCreators.withSuccess());

        myViewService.deleteItemSet(1L);

        customizer.getServer().verify();
    }

    @Test
    void addItemSpell_shouldAddItemSpellSuccessfully() {
        ItemSpells itemSpells = new ItemSpells();
        customizer.getServer().expect(ExpectedCount.once(), MockRestRequestMatchers.requestTo("http://localhost:8082/additemspell"))
                .andRespond(MockRestResponseCreators.withSuccess());

        myViewService.addItemSpell(itemSpells);

        customizer.getServer().verify();
    }

    @Test
    void editItemSpell_shouldEditItemSpellSuccessfully() {
        ItemSpells itemSpells = new ItemSpells();
        customizer.getServer().expect(ExpectedCount.once(), MockRestRequestMatchers.requestTo("http://localhost:8082/updateitemspell"))
                .andRespond(MockRestResponseCreators.withSuccess());

        myViewService.editItemSpell(itemSpells);

        customizer.getServer().verify();
    }

    @Test
    void deleteItemSpell_shouldDeleteItemSpellSuccessfully() {
        customizer.getServer().expect(ExpectedCount.once(), MockRestRequestMatchers.requestTo("http://localhost:8082/deleteitemspell/1"))
                .andRespond(MockRestResponseCreators.withSuccess());

        myViewService.deleteItemSpell(1L);

        customizer.getServer().verify();
    }
}