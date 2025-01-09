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
        ItemClassMapper itemClassMapper = new ItemClassMapper();
        itemSubclass.setItemClass(itemClassMapper.map(itemSubclassDto.getItemClassDto()));
        return itemSubclass;
    }
}
