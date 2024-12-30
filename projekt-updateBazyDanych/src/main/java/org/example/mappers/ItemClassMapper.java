package org.example.mappers;

import org.example.DTOs.ItemClassDto;
import org.example.model.ItemClass;

public class ItemClassMapper implements IMapEntities<ItemClassDto, ItemClass> {
    @Override
    public ItemClass map(ItemClassDto itemClassDto) {
        return map(itemClassDto, new ItemClass());
    }

    @Override
    public ItemClass map(ItemClassDto itemClassDto, ItemClass itemClass) {
        itemClass.setClassName(itemClassDto.getName());
        return itemClass;
    }
}
