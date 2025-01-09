package org.example.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


public class ItemDto {


    @JsonProperty("id") private Long blizzardId;
    private String name;
    private String description;

    private ItemClassDto itemClass;
    private ItemSubclassDto itemSubclass;

    private ItemStatsDto itemStats;

    private ItemMediaDto itemMedia;

    private ItemSetDto itemSet;

    private List<ItemSpellsDto> itemSpells = new ArrayList<>();

    private boolean isCreatedByUser;

    public boolean isCreatedByUser() {
        return isCreatedByUser;
    }

    public void setCreatedByUser(boolean createdByUser) {
        isCreatedByUser = createdByUser;
    }

    public Long getBlizzardId() {
        return blizzardId;
    }

    public void setBlizzardId(Long blizzardId) {
        this.blizzardId = blizzardId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ItemClassDto getItemClass() {
        return itemClass;
    }

    public void setItemClass(ItemClassDto itemClass) {
        this.itemClass = itemClass;
    }

    public ItemSubclassDto getItemSubclass() {
        return itemSubclass;
    }

    public void setItemSubclass(ItemSubclassDto itemSubclass) {
        this.itemSubclass = itemSubclass;
    }

    public ItemStatsDto getItemStats() {
        return itemStats;
    }

    public void setItemStats(ItemStatsDto itemStats) {
        this.itemStats = itemStats;
    }

    public ItemMediaDto getItemMedia() {
        return itemMedia;
    }

    public void setItemMedia(ItemMediaDto itemMedia) {
        this.itemMedia = itemMedia;
    }

    public ItemSetDto getItemSet() {
        return itemSet;
    }

    public void setItemSet(ItemSetDto itemSet) {
        this.itemSet = itemSet;
    }

    public List<ItemSpellsDto> getItemSpells() {
        return itemSpells;
    }

    public void setItemSpells(List<ItemSpellsDto> itemSpells) {
        this.itemSpells = itemSpells;
    }
}

