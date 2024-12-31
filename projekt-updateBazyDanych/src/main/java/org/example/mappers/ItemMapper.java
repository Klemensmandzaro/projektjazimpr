package org.example.mappers;

import org.example.DTOs.ItemDto;
import org.example.model.*;
import org.example.repositories.ItemClassRepository;
import org.example.repositories.ItemRepository;
import org.example.repositories.ItemSetRepository;
import org.example.repositories.ItemSubclassRepository;
import org.springframework.stereotype.Component;

@Component
public class ItemMapper implements IMapEntities<ItemDto, Item> {
    private final ItemClassRepository itemClassRepository;
    private final ItemSubclassRepository itemSubclassRepository;
    private final ItemSetRepository itemSetRepository;


    public ItemMapper(ItemClassRepository itemClassRepository, ItemSubclassRepository itemSubclassRepository, ItemSetRepository itemSetRepository, ItemRepository itemRepository) {
        this.itemClassRepository = itemClassRepository;
        this.itemSubclassRepository = itemSubclassRepository;
        this.itemSetRepository = itemSetRepository;

    }

    @Override
    public Item map(ItemDto itemDto) {
        return map(itemDto, new Item());
    }

    @Override
    public Item map(ItemDto itemDto, Item item) {

        item.setBlizzardId(itemDto.getBlizzardId());
        item.setName(itemDto.getName());
        item.setDescription(itemDto.getDescription());
        if (itemClassRepository.findByClassName(itemDto.getItemClass().getName()).isPresent()) {
            item.setItemClass(itemClassRepository.findByClassName(itemDto.getItemClass().getName()).get());
        } else {
            ItemClass itemClass = new ItemClass();
            itemClass.setClassName(itemDto.getItemClass().getName());
            item.setItemClass(itemClass);
        }

        if (itemSubclassRepository.findBySubclassName(itemDto.getItemSubclass().getName()).isPresent()) {
            item.setItemSubclass(itemSubclassRepository.findBySubclassName(itemDto.getItemSubclass().getName()).get());
        } else {
            ItemSubclass itemSubclass = new ItemSubclass();
            itemSubclass.setSubclassName(itemDto.getItemSubclass().getName());
            item.setItemSubclass(itemSubclass);
        }

        ItemStats itemStats = new ItemStats();
        itemStats.setStrength(itemDto.getItemStats().getStrength());
        itemStats.setAgility(itemDto.getItemStats().getAgility());
        itemStats.setIntellect(itemDto.getItemStats().getIntellect());
        itemStats.setStamina(itemDto.getItemStats().getStamina());
        itemStats.setCriticalStrike(itemDto.getItemStats().getCriticalStrike());
        itemStats.setMastery(itemDto.getItemStats().getMastery());
        itemStats.setVersatility(itemDto.getItemStats().getVersatility());
        itemStats.setHaste(itemDto.getItemStats().getHaste());
        itemStats.setArmor(itemDto.getItemStats().getArmor());
        itemStats.setDodge(itemDto.getItemStats().getDodge());
        itemStats.setBlock(itemDto.getItemStats().getBlock());
        itemStats.setHealthRegeneration(itemDto.getItemStats().getHealthRegeneration());
        itemStats.setManaRegeneration(itemDto.getItemStats().getManaRegeneration());
        itemStats.setOtherType(itemDto.getItemStats().getOtherType());
        itemStats.setArcane_resistance(itemDto.getItemStats().getArcane_resistance());
        itemStats.setCrit_ranged(itemDto.getItemStats().getCrit_ranged());
        itemStats.setParry(itemDto.getItemStats().getParry());
        itemStats.setSpell_power(itemDto.getItemStats().getSpell_power());
        itemStats.setSpirit(itemDto.getItemStats().getSpirit());
        itemStats.setShadow_resistance(itemDto.getItemStats().getShadow_resistance());
        itemStats.setFire_resistance(itemDto.getItemStats().getFire_resistance());
        itemStats.setFrost_resistance(itemDto.getItemStats().getFrost_resistance());
        itemStats.setNature_resistance(itemDto.getItemStats().getNature_resistance());
        itemStats.setShadow_resistance(itemDto.getItemStats().getShadow_resistance());
        item.setItemStats(itemStats);


        ItemMedia itemMedia = new ItemMedia();
        itemMedia.setIconUrl(itemDto.getItemMedia().getMediaUrl());
        item.setItemMedia(itemMedia);

        if (itemSetRepository.findBySetName(itemDto.getItemSet().getName()).isPresent()) {
            item.setItemSet(itemSetRepository.findBySetName(itemDto.getItemSet().getName()).get());
        } else {
            ItemSet itemSet = new ItemSet();
            itemSet.setSetName(itemDto.getItemSet().getName());
            item.setItemSet(itemSet);
        }




        return item;


    }
}
