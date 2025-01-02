package org.example.mappers;

import org.example.DTOs.*;
import org.example.model.*;

public interface ICatalogMappers {
    IMapEntities<ItemDto, Item> forItem();

    IMapEntities<ItemClassDto, ItemClass> forItemClass();

    IMapEntities<ItemMediaDto, ItemMedia> forItemMedia();

    IMapEntities<ItemSubclassDto, ItemSubclass> forItemSubclass();

    IMapEntities<ItemSetDto, ItemSet> forItemSet();

    IMapEntities<ItemStatsDto, ItemStats> forItemStats();

    IMapEntities<ItemSpellsDto, ItemSpells> forItemSpells();
}
