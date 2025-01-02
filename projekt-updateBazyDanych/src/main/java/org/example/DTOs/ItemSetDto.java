package org.example.DTOs;


import org.example.model.Item;

import java.util.ArrayList;
import java.util.List;


public class ItemSetDto {
    private String name;
    private List<Item> items;
    private List<String> effects = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public List<String> getEffects() {
        return effects;
    }

    public void setEffects(List<String> effects) {
        this.effects = effects;
    }
}

