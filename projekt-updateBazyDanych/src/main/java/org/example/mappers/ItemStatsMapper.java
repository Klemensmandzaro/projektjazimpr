package org.example.mappers;

import org.example.DTOs.ItemStatsDto;
import org.example.model.ItemStats;

public class ItemStatsMapper implements IMapEntities<ItemStatsDto, ItemStats> {
    @Override
    public ItemStats map(ItemStatsDto itemStatsDto) {
        return map(itemStatsDto, new ItemStats());
    }

    @Override
    public ItemStats map(ItemStatsDto itemStatsDto, ItemStats itemStats) {

        return null;
    }
}
