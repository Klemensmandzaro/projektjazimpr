package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.DTOs.*;
import org.example.model.*;
import org.example.services.MyRestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MyRestController {
    private final MyRestService myRestService;

    public MyRestController(MyRestService myRestService) {
        this.myRestService = myRestService;
    }


//    @GetMapping("/getallitems")
//    public ResponseEntity<List<Item>> getAllItems(){
//        return new ResponseEntity<>(myRestService.getAllItems(), HttpStatus.OK);
//    }

    @GetMapping("/getselecteditems")
    public ResponseEntity<List<Item>> getSelectedItems(@RequestParam Long start, @RequestParam int limit){
        return new ResponseEntity<>(myRestService.getSelectedItems(start, limit), HttpStatus.OK);
    }

    @GetMapping("/getitemscreatedbyuser")
    public ResponseEntity<List<Item>> getItemsCreatedByUser(){
        return new ResponseEntity<>(myRestService.getItemsCreatedByUser(), HttpStatus.OK);
    }

//    @GetMapping("/getitemsbetween")
//    public ResponseEntity<List<Item>> getItemsBetween(@RequestParam Long start,@RequestParam Long end){
//        return new ResponseEntity<>(myRestService.getItemsBetween(start, end), HttpStatus.OK);
//    }

    @GetMapping("/getallitemclasses")
    public ResponseEntity<List<ItemClass>> getAllItemClasses(){
        return new ResponseEntity<>(myRestService.getAllItemClasses(), HttpStatus.OK);
    }

    @GetMapping("/getallitemsubclasses")
    public ResponseEntity<List<ItemSubclass>> getAllItemSubclasses(){
        return new ResponseEntity<>(myRestService.getAllItemSubclasses(), HttpStatus.OK);
    }

//    @GetMapping("/getitemsetsbetween")
//    public ResponseEntity<List<ItemSet>> getAllItemSets(@RequestParam Long start, @RequestParam Long end){
//        return new ResponseEntity<>(myRestService.getBetweenItemSets(start, end), HttpStatus.OK);
//    }

    @GetMapping("/getallitemsets")
    public ResponseEntity<List<ItemSet>> getAllItemSets(){
        return new ResponseEntity<>(myRestService.getAllItemSets(), HttpStatus.OK);
    }

    @GetMapping("/getselecteditemsets")
    public ResponseEntity<List<ItemSet>> getSelectedItemSets(@RequestParam int page, @RequestParam int limit){
        return new ResponseEntity<>(myRestService.getSelectedItemSets(page, limit), HttpStatus.OK);
    }

    @GetMapping("/getallitemspells")
    public ResponseEntity<List<ItemSpells>> getAllItemSpells(){
        return new ResponseEntity<>(myRestService.getAllItemSpells(), HttpStatus.OK);
    }

//    @GetMapping("/getitemspellsbetween")
//    public ResponseEntity<List<ItemSpells>> getBetweenItemSpells(@RequestParam Long start, @RequestParam Long end){
//        return new ResponseEntity<>(myRestService.getBetweenItemSpells(start, end), HttpStatus.OK);
//    }

    @GetMapping("/getselecteditemspells")
    public ResponseEntity<List<ItemSpells>> getSelectedSpells(@RequestParam int page, @RequestParam int limit){
        return new ResponseEntity<>(myRestService.getSelectedItemSpells(page, limit), HttpStatus.OK);
    }

    @GetMapping("/getitemspellbyid/{id}")
    public ResponseEntity<ItemSpells> getItemSpellById(@PathVariable Long id){
        return new ResponseEntity<>(myRestService.getItemSpellById(id), HttpStatus.OK);
    }

    @PostMapping("/additem")
    public ResponseEntity<Void> addItem(@RequestBody Item item){
        myRestService.addItem(item);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/additemclass")
    public ResponseEntity<Void> addItemClass(@RequestBody ItemClass itemClass){
        myRestService.addItemClass(itemClass);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/additemsubclass")
    public ResponseEntity<Void> addItemSubclass(@RequestBody ItemSubclass itemSubclass){
        myRestService.addItemSubclass(itemSubclass);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/additemset")
    public ResponseEntity<Void> addItemSet(@RequestBody ItemSet itemSet){
        myRestService.addItemSet(itemSet);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/additemspell")
    public ResponseEntity<Void> addItemSpell(@RequestBody ItemSpells itemSpells){
        myRestService.addItemSpell(itemSpells);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/updateitem")
    public ResponseEntity<Void> updateItem(@RequestBody Item item){
        myRestService.updateItem(item);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PatchMapping("/updateitemclass")
    public ResponseEntity<Void> updateItemClass(@RequestBody ItemClass itemClass){
        myRestService.updateItemClass(itemClass);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping("/updateitemsubclass")
    public ResponseEntity<Void> updateItemSubclass(@RequestBody ItemSubclass itemSubclass){
        myRestService.updateItemSubclass(itemSubclass);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping("/updateitemset")
    public ResponseEntity<Void> updateItemSet(@RequestBody ItemSet itemSet){
        myRestService.updateItemSet(itemSet);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping("/updateitemspell")
    public ResponseEntity<Void> updateItemSpell(@RequestBody ItemSpells itemSpells){
        myRestService.updateItemSpell(itemSpells);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping("/deleteitem/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id){
        myRestService.deleteItem(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping("/deleteitemclass/{id}")
    public ResponseEntity<Void> deleteItemClass(@PathVariable Long id){
        myRestService.deleteItemClass(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping("/deleteitemsubclass/{id}")
    public ResponseEntity<Void> deleteItemSubclass(@PathVariable Long id){
        myRestService.deleteItemSubclass(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping("/deleteitemset/{id}")
    public ResponseEntity<Void> deleteItemSet(@PathVariable Long id){
        myRestService.deleteItemSet(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping("/deleteitemspell/{id}")
    public ResponseEntity<Void> deleteItemSpell(@PathVariable Long id){
        myRestService.deleteItemSpell(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
