package org.example.mappers;

import org.example.DTOs.ItemSetDto;
import org.example.model.ItemSet;

public class ItemSetMapper implements IMapEntities<ItemSetDto, ItemSet> {
    @Override
    public ItemSet map(ItemSetDto itemSetDto) {
        return map(itemSetDto, new ItemSet());
    }

    @Override
    public ItemSet map(ItemSetDto itemSetDto, ItemSet itemSet) {
        itemSet.setSetName(itemSetDto.getName());
        itemSet.setEffects(itemSetDto.getEffects());

        return itemSet;
    }
}
