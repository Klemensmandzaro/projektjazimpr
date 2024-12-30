package org.example.mappers;

import org.example.DTOs.*;
import org.example.model.*;

public class EntityMapper implements ICatalogMappers{

    private final IMapEntities<ItemDto, Item> forItem;
    private final IMapEntities<ItemClassDto, ItemClass> forItemClass;
    private final IMapEntities<ItemMediaDto, ItemMedia> forItemMedia;
    private final IMapEntities<ItemSubclassDto, ItemSubclass> forItemSubclass;
    private final IMapEntities<ItemSetDto, ItemSet> forItemSet;
    private final IMapEntities<ItemStatsDto, ItemStats> forItemStats;

    public EntityMapper(IMapEntities<ItemDto, Item> forItem, IMapEntities<ItemClassDto, ItemClass> forItemClass, IMapEntities<ItemMediaDto, ItemMedia> forItemMedia, IMapEntities<ItemSubclassDto, ItemSubclass> forItemSubclass, IMapEntities<ItemSetDto, ItemSet> forItemSet, IMapEntities<ItemStatsDto, ItemStats> forItemStats) {
        this.forItem = forItem;
        this.forItemClass = forItemClass;
        this.forItemMedia = forItemMedia;
        this.forItemSubclass = forItemSubclass;
        this.forItemSet = forItemSet;
        this.forItemStats = forItemStats;
    }

    @Override
    public IMapEntities<ItemDto, Item> forItem() {
        return forItem;
    }

    @Override
    public IMapEntities<ItemClassDto, ItemClass> forItemClass() {
        return forItemClass;
    }

    @Override
    public IMapEntities<ItemMediaDto, ItemMedia> forItemMedia() {
        return forItemMedia;
    }

    @Override
    public IMapEntities<ItemSubclassDto, ItemSubclass> forItemSubclass() {
        return forItemSubclass;
    }

    @Override
    public IMapEntities<ItemSetDto, ItemSet> forItemSet() {
        return forItemSet;
    }

    @Override
    public IMapEntities<ItemStatsDto, ItemStats> forItemStats() {
        return forItemStats;
    }
}
