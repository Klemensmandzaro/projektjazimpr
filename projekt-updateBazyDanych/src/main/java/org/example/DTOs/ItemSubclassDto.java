package org.example.DTOs;


import org.example.model.ItemClass;

public class ItemSubclassDto {
    private String name;
    private ItemClassDto itemClassDto;

    public ItemClassDto getItemClassDto() {
        return itemClassDto;
    }

    public void setItemClassDto(ItemClassDto itemClassDto) {
        this.itemClassDto = itemClassDto;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

