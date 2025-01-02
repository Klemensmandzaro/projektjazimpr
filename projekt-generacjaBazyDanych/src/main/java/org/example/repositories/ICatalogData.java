package org.example.repositories;

public interface ICatalogData {
    ItemRepository getItems();
    ItemSetRepository getItemSets();
    ItemClassRepository getItemClasses();
    ItemSubclassRepository getItemSubclasses();
    ItemStatsRepository getItemStats();
    ItemMediaRepository getItemMedia();
    ItemSpellsRepository getItemSpells();
}
