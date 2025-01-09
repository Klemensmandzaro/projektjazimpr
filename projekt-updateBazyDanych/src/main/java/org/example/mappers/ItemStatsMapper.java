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
        itemStats.setOtherType(itemStatsDto.getOtherType());
        itemStats.setArcane_resistance(itemStatsDto.getArcane_resistance());
        itemStats.setFire_resistance(itemStatsDto.getFire_resistance());
        itemStats.setFrost_resistance(itemStatsDto.getFrost_resistance());
        itemStats.setNature_resistance(itemStatsDto.getNature_resistance());
        itemStats.setShadow_resistance(itemStatsDto.getShadow_resistance());
        itemStats.setCrit_ranged(itemStatsDto.getCrit_ranged());
        itemStats.setParry(itemStatsDto.getParry());
        itemStats.setSpell_power(itemStatsDto.getSpell_power());
        itemStats.setSpirit(itemStatsDto.getSpirit());
        itemStats.setDamage_min(itemStatsDto.getDamage_min());
        itemStats.setDamage_max(itemStatsDto.getDamage_max());
        itemStats.setAttack_speed(itemStatsDto.getAttack_speed());
        itemStats.setDPS(itemStatsDto.getDPS());
        itemStats.setAttack_power(itemStatsDto.getAttack_power());
        itemStats.setRanged_attack_power(itemStatsDto.getRanged_attack_power());
        itemStats.setCrafting_speed(itemStatsDto.getCrafting_speed());
        itemStats.setDeftness(itemStatsDto.getDeftness());
        itemStats.setFinesse(itemStatsDto.getFinesse());
        itemStats.setIngenuity(itemStatsDto.getIngenuity());
        itemStats.setMulticraft(itemStatsDto.getMulticraft());
        itemStats.setPerception(itemStatsDto.getPerception());
        itemStats.setResourcefulness(itemStatsDto.getResourcefulness());
        itemStats.setAvoidance(itemStatsDto.getAvoidance());
        itemStats.setLifesteal(itemStatsDto.getLifesteal());
        itemStats.setSpeed(itemStatsDto.getSpeed());
        itemStats.setSturdiness(itemStatsDto.getSturdiness());
        itemStats.setCorruption_resistance(itemStatsDto.getCorruption_resistance());
        itemStats.setExtra_armor(itemStatsDto.getExtra_armor());
        itemStats.setMana(itemStatsDto.getMana());


        return itemStats;

    }
}
