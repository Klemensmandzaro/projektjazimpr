package org.example.mappers;

import org.example.DTOs.ItemSpellsDto;
import org.example.model.ItemSpells;

public class ItemSpellsMapper implements IMapEntities<ItemSpellsDto, ItemSpells> {
    @Override
    public ItemSpells map(ItemSpellsDto itemSpellsDto) {
        return map(itemSpellsDto, new ItemSpells());
    }

    @Override
    public ItemSpells map(ItemSpellsDto itemSpellsDto, ItemSpells itemSpells) {
        itemSpells.setName(itemSpellsDto.getName());
        itemSpells.setDescription(itemSpellsDto.getDescription());
        return itemSpells;
    }
}
