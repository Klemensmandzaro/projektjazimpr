package org.example.repositories;

import org.springframework.stereotype.Repository;

@Repository
public class ItemDataCatalog implements ICatalogData {
    private final ItemRepository items;
    private final ItemSetRepository itemSets;
    private final ItemClassRepository itemClasses;
    private final ItemSubclassRepository itemSubclasses;
    private final ItemStatsRepository itemStats;
    private final ItemMediaRepository itemMedia;
    private final ItemSpellsRepository itemSpells;

    public ItemDataCatalog(ItemRepository items, ItemSetRepository itemSets, ItemClassRepository itemClasses, ItemSubclassRepository itemSubclasses, ItemStatsRepository itemStats, ItemMediaRepository itemMedia, ItemSpellsRepository itemSpells) {
        this.items = items;
        this.itemSets = itemSets;
        this.itemClasses = itemClasses;
        this.itemSubclasses = itemSubclasses;
        this.itemStats = itemStats;
        this.itemMedia = itemMedia;
        this.itemSpells = itemSpells;
    }

    @Override
    public ItemRepository getItems() {
        return items;
    }

    @Override
    public ItemSetRepository getItemSets() {
        return itemSets;
    }

    @Override
    public ItemClassRepository getItemClasses() {
        return itemClasses;
    }

    @Override
    public ItemSubclassRepository getItemSubclasses() {
        return itemSubclasses;
    }

    @Override
    public ItemStatsRepository getItemStats() {
        return itemStats;
    }

    @Override
    public ItemMediaRepository getItemMedia() {
        return itemMedia;
    }

    @Override
    public ItemSpellsRepository getItemSpells() {
        return itemSpells;
    }
}
