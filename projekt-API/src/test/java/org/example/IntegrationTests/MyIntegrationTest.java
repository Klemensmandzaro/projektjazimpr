package org.example.IntegrationTests;

import io.restassured.RestAssured;
import org.example.API;
import org.example.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;


import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.is;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = API.class)
public class MyIntegrationTest {

    @LocalServerPort
    private int port = 8082;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    public void getSelectedItems(){
        get(basePath + "/getselecteditems?start=25&limit=10")
                .then()
                .statusCode(200)
                .body("$.size()", is(10));
    }

    @Test
    public void getItemsCreatedByUser(){
        get(basePath + "/getitemscreatedbyuser")
                .then()
                .statusCode(200)
                .body("$.size()", is(1));
    }

    @Test
    public void postAddItem(){
        Item item = new Item();
        item.setName("Test Item");
        ItemClass itemClass =new ItemClass();
        itemClass.setClassName("Weapon");
        item.setItemClass(itemClass);
        ItemSubclass itemSubclass = new ItemSubclass();
        itemSubclass.setSubclassName("Maze");
        item.setItemSubclass(itemSubclass);
        ItemSet set = new ItemSet();
        set.setSetName("Neutral");
        item.setItemSet(set);
        item.setItemStats(new ItemStats());
        List<ItemSpells> itemSpells = new ArrayList<>();
        item.setItemSpells(itemSpells);


        with()
                .body(item)
                .header("Content-Type", "application/json")
                .when()
                .post(basePath+ "/additem")
                .then()
                .statusCode(201);
    }
}

