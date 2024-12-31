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
        itemStats.setStrength(itemStatsDto.getStrength());
        itemStats.setAgility(itemStatsDto.getAgility());
        itemStats.setIntellect(itemStatsDto.getIntellect());
        itemStats.setStamina(itemStatsDto.getStamina());
        itemStats.setCriticalStrike(itemStatsDto.getCriticalStrike());
        itemStats.setMastery(itemStatsDto.getMastery());
        itemStats.setVersatility(itemStatsDto.getVersatility());
        itemStats.setHaste(itemStatsDto.getHaste());
        itemStats.setArmor(itemStatsDto.getArmor());
        itemStats.setBlock(itemStatsDto.getBlock());
        itemStats.setDodge(itemStatsDto.getDodge());
        itemStats.setHealthRegeneration(itemStatsDto.getHealthRegeneration());
        itemStats.setHealthRegeneration(itemStatsDto.getHealthRegeneration());
        return itemStats;

    }
}
