package org.example.mappers;

import org.example.DTOs.ItemSubclassDto;
import org.example.model.ItemSubclass;

public class ItemSubclassMapper implements IMapEntities<ItemSubclassDto, ItemSubclass> {
    @Override
    public ItemSubclass map(ItemSubclassDto itemSubclassDto) {
        return map(itemSubclassDto, new ItemSubclass());
    }

    @Override
    public ItemSubclass map(ItemSubclassDto itemSubclassDto, ItemSubclass itemSubclass) {
        itemSubclass.setSubclassName(itemSubclassDto.getName());
        return itemSubclass;
    }
}
