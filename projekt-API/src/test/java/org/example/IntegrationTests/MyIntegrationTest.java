package org.example.IntegrationTests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.example.API;
import org.example.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;


import java.util.ArrayList;


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
    public void getAllItems(){
        get(basePath + "/getallitems?page=0&limit=50")
                .then()
                .statusCode(200)
                .body("$.size()", is(50));
    }

    @Test
    public void getItemsCreatedByUser(){
        get(basePath + "/getitemscreatedbyuser")
                .then()
                .statusCode(200)
                .body("$.size()", is(1));
    }

    @Test
    public void getAllItemClasses(){
        get(basePath + "/getallitemclasses")
                .then()
                .statusCode(200)
                .body("$.size()", is(16));
    }

    @Test
    public void getAllItemSublasses(){
        get(basePath + "/getallitemsubclasses")
                .then()
                .statusCode(200)
                .body("$.size()", is(99));
    }

    @Test
    public void getAllItemSets(){
        get(basePath + "/getallitemsets")
                .then()
                .statusCode(200)
                .body("$.size()", is(560));
    }

    @Test
    public void getSelectedItemSets(){
        get(basePath + "/getselecteditemsets?page=0&limit=50")
                .then()
                .statusCode(200)
                .body("$.size()", is(50));
    }

    @Test
    public void getAllItemSpells(){
        get(basePath + "/getallitemspells")
                .then()
                .statusCode(200)
                .body("$.size()", is(5413));
    }

    @Test
    public void getSelectedItemSpells(){
        get(basePath + "/getselecteditemspells?page=0&limit=50")
                .then()
                .statusCode(200)
                .body("$.size()", is(50));
    }

    @Test
    public void getItemSpellById(){
        get(basePath + "/getitemspellbyid/5")
                .then()
                .statusCode(200)
                .body("id", is(5));
    }



    @Test
    public void postAddItem() {
        Item item = new Item();
        item.setName("Unique2 Item");

        ItemClass itemClass = new ItemClass();
        itemClass.setClassName("Weapon");
        item.setItemClass(itemClass);

        ItemSubclass itemSubclass = new ItemSubclass();
        itemSubclass.setSubclassName("Mace");
        item.setItemSubclass(itemSubclass);

        ItemSet itemSet = new ItemSet();
        itemSet.setSetName("Neutral");
        item.setItemSet(itemSet);

        item.setItemStats(new ItemStats());
        item.setItemSpells(new ArrayList<>());

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(item)
                .when()
                .post("/additem")
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .log().ifError();
    }

    @Test
    public void postAddItemClass() {
        ItemClass itemClass = new ItemClass();
        itemClass.setClassName("Unique");

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(itemClass)
                .when()
                .post("/additemclass")
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .log().ifError();
    }

    @Test
    public void postAddItemSubclass() {
        ItemSubclass itemSubclass = new ItemSubclass();
        itemSubclass.setSubclassName("Unique");
        ItemClass itemClass = new ItemClass();
        itemClass.setClassName("Unique");
        itemSubclass.setItemClass(itemClass);

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(itemSubclass)
                .when()
                .post("/additemsubclass")
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .log().ifError();
    }

    @Test
    public void postAddItemSet() {
        ItemSet itemSet = new ItemSet();
        itemSet.setSetName("Unique");

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(itemSet)
                .when()
                .post("/additemset")
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .log().ifError();
    }

    @Test
    public void postAddItemSpell() {
        ItemSpells itemSpells = new ItemSpells();
        itemSpells.setName("Unique");

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(itemSpells)
                .when()
                .post("/additemspell")
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .log().ifError();
    }

    @Test
    public void patchEditItem() {
        Item item = new Item();
        item.setId(59809L);
        item.setName("Unique Item");

        ItemClass itemClass = new ItemClass();
        itemClass.setClassName("Weapon");
        item.setItemClass(itemClass);

        ItemSubclass itemSubclass = new ItemSubclass();
        itemSubclass.setSubclassName("Mace");
        item.setItemSubclass(itemSubclass);

        ItemSet itemSet = new ItemSet();
        itemSet.setSetName("Neutral");
        item.setItemSet(itemSet);

        item.setItemStats(new ItemStats());
        item.setItemSpells(new ArrayList<>());

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(item)
                .when()
                .patch("/updateitem")
                .then()
                .statusCode(HttpStatus.SC_ACCEPTED)
                .log().ifError();
    }

    @Test
    public void patchEditItemClass() {
        ItemClass itemClass = new ItemClass();
        itemClass.setId(19L);
        itemClass.setClassName("Uniquee");

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(itemClass)
                .when()
                .patch("/updateitemclass")
                .then()
                .statusCode(HttpStatus.SC_ACCEPTED)
                .log().ifError();
    }

    @Test
    public void postEditItemSubclass() {
        ItemSubclass itemSubclass = new ItemSubclass();
        itemSubclass.setId(104L);
        itemSubclass.setSubclassName("Uniquee");
        ItemClass itemClass = new ItemClass();
        itemClass.setClassName("Uniquee");
        itemSubclass.setItemClass(itemClass);

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(itemSubclass)
                .when()
                .post("/updateitemsubclass")
                .then()
                .statusCode(HttpStatus.SC_ACCEPTED)
                .log().ifError();
    }

    @Test
    public void postEditItemSet() {
        ItemSet itemSet = new ItemSet();
        itemSet.setId(581L);
        itemSet.setSetName("Uniquee");

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(itemSet)
                .when()
                .post("/updateitemset")
                .then()
                .statusCode(HttpStatus.SC_ACCEPTED)
                .log().ifError();
    }

    @Test
    public void postEditItemSpell() {
        ItemSpells itemSpells = new ItemSpells();
        itemSpells.setId(5461L);
        itemSpells.setName("Uniquee");

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(itemSpells)
                .when()
                .post("/updateitemspell")
                .then()
                .statusCode(HttpStatus.SC_ACCEPTED)
                .log().ifError();
    }

    @Test
    public void postDeleteItem() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .post("/deleteitem/59809")
                .then()
                .statusCode(HttpStatus.SC_ACCEPTED)
                .log().ifError();
    }

    @Test
    public void postDeleteItemSubclass() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .post("/deleteitemsubclass/104")
                .then()
                .statusCode(HttpStatus.SC_ACCEPTED)
                .log().ifError();
    }

    @Test
    public void postDeleteItemClass() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .post("/deleteitemclass/19")
                .then()
                .statusCode(HttpStatus.SC_ACCEPTED)
                .log().ifError();
    }

    @Test
    public void postDeleteItemSet() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .post("/deleteitemset/581")
                .then()
                .statusCode(HttpStatus.SC_ACCEPTED)
                .log().ifError();
    }

    @Test
    public void postDeleteItemSpell() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .post("/deleteitemspell/5461")
                .then()
                .statusCode(HttpStatus.SC_ACCEPTED)
                .log().ifError();
    }


}

