package org.example.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


public class ItemDto {
    private Long id;

    @JsonProperty("id") private Long blizzardId;
    private String name;
    private String description;

    private ItemClassDto itemClass;
    private ItemSubclassDto itemSubclass;

    private ItemStatsDto itemStats;

    private ItemMediaDto itemMedia;

    private ItemSetDto itemSet;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}

