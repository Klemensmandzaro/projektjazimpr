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


    @GetMapping("/getallitems")
    public ResponseEntity<List<Item>> getAllItems(){
        return new ResponseEntity<>(myRestService.getAllItems(), HttpStatus.OK);
    }

    @GetMapping("/getselecteditems")
    public ResponseEntity<List<Item>> getSelectedItems(@RequestParam Long start, @RequestParam int limit){
        return new ResponseEntity<>(myRestService.getSelectedItems(start, limit), HttpStatus.OK);
    }

    @GetMapping("/getitemscreatedbyuser")
    public ResponseEntity<List<Item>> getItemsCreatedByUser(){
        return new ResponseEntity<>(myRestService.getItemsCreatedByUser(), HttpStatus.OK);
    }

    @GetMapping("/getitemsbetween")
    public ResponseEntity<List<Item>> getItemsBetween(@RequestParam Long start,@RequestParam Long end){
        return new ResponseEntity<>(myRestService.getItemsBetween(start, end), HttpStatus.OK);
    }

    @GetMapping("/getallitemclasses")
    public ResponseEntity<List<ItemClass>> getAllItemClasses(){
        return new ResponseEntity<>(myRestService.getAllItemClasses(), HttpStatus.OK);
    }

    @GetMapping("/getallitemsubclasses")
    public ResponseEntity<List<ItemSubclass>> getAllItemSubclasses(){
        return new ResponseEntity<>(myRestService.getAllItemSubclasses(), HttpStatus.OK);
    }

    @GetMapping("/getitemsetsbetween")
    public ResponseEntity<List<ItemSet>> getAllItemSets(@RequestParam Long start, @RequestParam Long end){
        return new ResponseEntity<>(myRestService.getBetweenItemSets(start, end), HttpStatus.OK);
    }

    @GetMapping("/getallitemsets")
    public ResponseEntity<List<ItemSet>> getAllItemSets(){
        return new ResponseEntity<>(myRestService.getAllItemSets(), HttpStatus.OK);
    }

    @GetMapping("/getselecteditemsets")
    public ResponseEntity<List<ItemSet>> getSelectedItemSets(@RequestParam Long start, @RequestParam int limit){
        return new ResponseEntity<>(myRestService.getSelectedItemSets(start, limit), HttpStatus.OK);
    }

    @GetMapping("/getallitemspells")
    public ResponseEntity<List<ItemSpells>> getAllItemSpells(){
        return new ResponseEntity<>(myRestService.getAllItemSpells(), HttpStatus.OK);
    }

    @GetMapping("/getitemspellsbetween")
    public ResponseEntity<List<ItemSpells>> getBetweenItemSpells(@RequestParam Long start, @RequestParam Long end){
        return new ResponseEntity<>(myRestService.getBetweenItemSpells(start, end), HttpStatus.OK);
    }

    @GetMapping("/getselecteditemspells")
    public ResponseEntity<List<ItemSpells>> getSelectedSpells(@RequestParam Long start, @RequestParam int limit){
        return new ResponseEntity<>(myRestService.getSelectedItemSpells(start, limit), HttpStatus.OK);
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
    public ResponseEntity<Void> addItemClass(@RequestBody ItemClassDto itemClassDto){
        myRestService.addItemClass(itemClassDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/additemsubclass")
    public ResponseEntity<Void> addItemSubclass(@RequestBody ItemSubclassDto itemSubclassDto){
        myRestService.addItemSubclass(itemSubclassDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/additemset")
    public ResponseEntity<Void> addItemSet(@RequestBody ItemSetDto itemSetDto){
        myRestService.addItemSet(itemSetDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/additemspell")
    public ResponseEntity<Void> addItemSpell(@RequestBody ItemSpellsDto itemSpellsDto){
        myRestService.addItemSpell(itemSpellsDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/updateitem")
    public ResponseEntity<Void> updateItem(@RequestBody Item item){
        myRestService.updateItem(item);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping("/updateitemclass/{name}")
    public ResponseEntity<Void> updateItemClass(@PathVariable String name){
        myRestService.updateItemClass(name);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping("/updateitemsubclass/{name}")
    public ResponseEntity<Void> updateItemSubclass(@PathVariable String name){
        myRestService.updateItemSubclass(name);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping("/updateitemset/{name}")
    public ResponseEntity<Void> updateItemSet(@PathVariable String name, @RequestBody ItemSetDto itemSetDto){
        myRestService.updateItemSet(name, itemSetDto);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping("/updateitemspell/{name}")
    public ResponseEntity<Void> updateItemSpell(@PathVariable String name, @RequestBody ItemSpellsDto itemSpellsDto){
        myRestService.updateItemSpell(name, itemSpellsDto);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping("/deleteitem/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id){
        myRestService.deleteItem(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping("/deleteitemclass/{name}")
    public ResponseEntity<Void> deleteItemClass(@PathVariable String name){
        myRestService.deleteItemClass(name);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping("/deleteitemsubclass/{name}")
    public ResponseEntity<Void> deleteItemSubclass(@PathVariable String name){
        myRestService.deleteItemSubclass(name);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping("/deleteitemset/{name}")
    public ResponseEntity<Void> deleteItemSet(@PathVariable String name){
        myRestService.deleteItemSet(name);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping("/deleteitemspell/{name}")
    public ResponseEntity<Void> deleteItemSpell(@PathVariable String name){
        myRestService.deleteItemSpell(name);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
