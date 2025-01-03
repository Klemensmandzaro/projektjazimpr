package org.example.controller;

import org.example.service.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/api/item")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/update")
    public ResponseEntity<String> getItem() {
        itemService.fetchAndSaveItems("EUhgsxayNE98vxnhUVnyqSXAFnye6oEX07");
        return new ResponseEntity<>("Update wpada", HttpStatus.OK);


    }
}

