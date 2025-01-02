package org.example.services;


import org.example.DTOs.*;
import org.example.exeptions.ResourceAlreadyExistException;
import org.example.exeptions.ResourceCantHaveAllNullValuesExceptions;
import org.example.exeptions.ResourceNotExistException;
import org.example.exeptions.ResourceStillHaveObjectsException;
import org.example.mappers.ItemStatsMapper;
import org.example.model.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.example.repositories.ICatalogData;
import org.springframework.data.domain.Pageable;


import java.util.List;


@Service

public class MyRestService {

    private final ICatalogData db;

    public MyRestService(ICatalogData db) {
        this.db = db;
    }


    public List<Item> getAllItems() {
        return db.getItems().findFirst10ByOrderByIdAsc();
    }

    public List<Item> getSelectedItems(Long start, int limit)
    {
        Pageable pageable = PageRequest.of(0, limit);
        return db.getItems().findByIdGreaterThanOrEqual(start, pageable);
    }

    public List<Item> getItemsBetween(Long start, Long end) {
        return db.getItems().findByIdBetween(start, end);
    }


    public List<ItemClass> getAllItemClasses() {
        return db.getItemClasses().findAll();
    }

    public List<ItemSubclass> getAllItemSubclasses() {
        return db.getItemSubclasses().findAll();
    }


    public List<ItemSet> getAllItemSets(Long start, Long end) {
        return db.getItemSets().findByIdBetween(start, end);
    }

    public List<ItemSpells> getBetweenItemSpells(Long start, Long end) {
        return db.getItemSpells().findByIdBetween(start, end);
    }

    public void addItem(ItemDto itemDto) {
        if (itemDto.getName() == null || itemDto.getItemClass()==null || itemDto.getItemSubclass()==null || itemDto.getItemSet()==null || itemDto.getItemStats()==null) {
            throw new ResourceCantHaveAllNullValuesExceptions();
        }
        Item item = new Item();
        item.setName(itemDto.getName());
        ItemMedia media = new ItemMedia();
        media.setIconUrl(itemDto.getItemMedia().getMediaUrl());
        item.setItemMedia(media);

        ItemStatsMapper itemStatsMapper = new ItemStatsMapper();
        ItemStats itemStats = itemStatsMapper.map(itemDto.getItemStats());
        item.setItemStats(itemStats);


        if (db.getItemClasses().findByClassName(itemDto.getItemClass().getName()).isPresent() &&
                db.getItemSubclasses().findBySubclassName(itemDto.getItemSubclass().getName()).isPresent() &&
                db.getItemSets().findBySetName(itemDto.getItemSet().getName()).isPresent())
        {
            item.setItemClass(db.getItemClasses().findByClassName(itemDto.getItemClass().getName()).get());
            item.setItemSubclass(db.getItemSubclasses().findBySubclassName(itemDto.getItemSubclass().getName()).get());
            item.setItemSet(db.getItemSets().findBySetName(itemDto.getItemSet().getName()).get());
        }
        else {
            throw new ResourceNotExistException();
        }

        if (itemDto.getItemSpells() != null)
        {
            for (ItemSpellsDto itemSpellsDto : itemDto.getItemSpells())
            {
                if (db.getItemSpells().findByName(itemSpellsDto.getName()).isPresent())
                {
                    item.getItemSpells().add(db.getItemSpells().findByName(itemSpellsDto.getName()).get());
                }
                else {
                    throw new ResourceNotExistException();
                }
            }
        }


        db.getItems().save(item);
    }

    public void addItemClass(ItemClassDto itemClassDto) {
        ItemClass itemClass = new ItemClass();
        if (itemClassDto.getName() == null) {
            throw new ResourceCantHaveAllNullValuesExceptions();
        }
        itemClass.setClassName(itemClassDto.getName());
        db.getItemClasses().save(itemClass);
    }

    public void addItemSubclass(ItemSubclassDto itemSubclassDto) {
        ItemSubclass itemSubclass = new ItemSubclass();
        if (itemSubclassDto.getName() == null) {
            throw new ResourceCantHaveAllNullValuesExceptions();
        }
        itemSubclass.setSubclassName(itemSubclassDto.getName());
        db.getItemSubclasses().save(itemSubclass);
    }

    public void addItemSet(ItemSetDto itemSetDto) {
        ItemSet itemSet = new ItemSet();
        if (itemSetDto.getName() != null)
        {
            itemSet.setSetName(itemSetDto.getName());
            itemSet.setEffects(itemSetDto.getEffects());
        }
        else
        {
            throw new ResourceCantHaveAllNullValuesExceptions();
        }
        db.getItemSets().save(itemSet);
    }

    public void addItemSpell(ItemSpellsDto itemSpellsDto) {
        ItemSpells itemSpells = new ItemSpells();
        if (itemSpellsDto.getName() == null && itemSpellsDto.getDescription() == null) {
            throw new ResourceCantHaveAllNullValuesExceptions();
        }
        itemSpells.setName(itemSpellsDto.getName());
        itemSpells.setDescription(itemSpellsDto.getDescription());
        db.getItemSpells().save(itemSpells);
    }

    public void updateItem(String name, ItemDto itemDto) {

        Item item = db.getItems().findFirstByName(name).orElseThrow(ResourceNotExistException::new);

        if (itemDto.getName() != null) {
            item.setName(itemDto.getName());
            if (db.getItems().findFirstByName(itemDto.getName()).isPresent() && !itemDto.getName().equals(name))
            {
                throw new ResourceAlreadyExistException();
            }
        }

        if (itemDto.getItemMedia() != null)
        {
            ItemMedia media = new ItemMedia();
            media.setIconUrl(itemDto.getItemMedia().getMediaUrl());
            item.setItemMedia(media);
        }

        if (itemDto.getItemStats() != null)
        {
            ItemStatsMapper itemStatsMapper = new ItemStatsMapper();
            ItemStats itemStats = itemStatsMapper.map(itemDto.getItemStats());
            item.setItemStats(itemStats);
        }

        if (itemDto.getItemClass() != null)
        {
            if (db.getItemClasses().findByClassName(itemDto.getItemClass().getName()).isPresent())
            {
                item.setItemClass(db.getItemClasses().findByClassName(itemDto.getItemClass().getName()).get());
            }
            else {
                throw new ResourceNotExistException();
            }
        }

        if (itemDto.getItemSubclass() != null)
        {
            if (db.getItemSubclasses().findBySubclassName(itemDto.getItemSubclass().getName()).isPresent())
            {
                item.setItemSubclass(db.getItemSubclasses().findBySubclassName(itemDto.getItemSubclass().getName()).get());
            }
            else {
                throw new ResourceNotExistException();
            }
        }

        if (itemDto.getItemSet() != null)
        {
            if (db.getItemSets().findBySetName(itemDto.getItemSet().getName()).isPresent())
            {
                item.setItemSet(db.getItemSets().findBySetName(itemDto.getItemSet().getName()).get());
            }
            else {
                throw new ResourceNotExistException();
            }
        }

        if (itemDto.getItemSpells() != null)
        {
            for (ItemSpellsDto itemSpellsDto : itemDto.getItemSpells())
            {
                if (db.getItemSpells().findByName(itemSpellsDto.getName()).isPresent())
                {
                    item.getItemSpells().add(db.getItemSpells().findByName(itemSpellsDto.getName()).get());
                }
                else {
                    throw new ResourceNotExistException();
                }
            }
        }

        db.getItems().save(item);
    }

    public void updateItemClass(String name) {
        ItemClass itemClass = db.getItemClasses().findByClassName(name).orElseThrow(ResourceNotExistException::new);
        db.getItemClasses().save(itemClass);
    }

    public void updateItemSubclass(String name) {
        ItemSubclass itemSubclass = db.getItemSubclasses().findBySubclassName(name).orElseThrow(ResourceNotExistException::new);
        db.getItemSubclasses().save(itemSubclass);
    }

    public void updateItemSet(String name, ItemSetDto itemSetDto) {
        ItemSet itemSet = db.getItemSets().findBySetName(name).orElseThrow(ResourceNotExistException::new);
        if (itemSetDto.getName() != null)
        {
            itemSet.setSetName(itemSetDto.getName());
        }

        if (itemSetDto.getEffects() != null)
        {
            itemSet.setEffects(itemSetDto.getEffects());
        }
        db.getItemSets().save(itemSet);
    }

    public void updateItemSpell(String name, ItemSpellsDto itemSpellsDto) {
        ItemSpells itemSpells = db.getItemSpells().findByName(name).orElseThrow(ResourceNotExistException::new);
        if (itemSpellsDto.getName() != null)
        {
            itemSpells.setName(itemSpellsDto.getName());
        }

        if (itemSpellsDto.getDescription() != null)
        {
            itemSpells.setDescription(itemSpellsDto.getDescription());
        }
        db.getItemSpells().save(itemSpells);
    }

    public void deleteItem(String name) {
        Item item = db.getItems().findFirstByName(name).orElseThrow(ResourceNotExistException::new);
        ItemMedia itemMedia = item.getItemMedia();
        ItemStats itemStats = item.getItemStats();

        db.getItemMedia().delete(itemMedia);
        db.getItemStats().delete(itemStats);
        item.setItemSubclass(null);
        item.setItemClass(null);
        item.setItemSet(null);
        item.setItemSpells(null);
        db.getItems().save(item);
        db.getItems().delete(item);

    }

    public void deleteItemClass(String name) {
        ItemClass itemClass = db.getItemClasses().findByClassName(name).orElseThrow(ResourceNotExistException::new);
        if (db.getItems().findByItemClassClassName(itemClass.getClassName()).isEmpty())
        {
            db.getItemClasses().delete(itemClass);
        }
        else
        {
            throw new ResourceStillHaveObjectsException();
        }

    }

    public void deleteItemSubclass(String name) {
        ItemSubclass itemSubclass = db.getItemSubclasses().findBySubclassName(name).orElseThrow(ResourceNotExistException::new);
        if (db.getItems().findByItemSubclassSubclassName(itemSubclass.getSubclassName()).isEmpty())
        {
            db.getItemSubclasses().delete(itemSubclass);
        }
        else
        {
            throw new ResourceStillHaveObjectsException();
        }

    }

    public void deleteItemSet(String name) {
        ItemSet itemSet = db.getItemSets().findBySetName(name).orElseThrow(ResourceNotExistException::new);
        if (db.getItems().findByItemSetSetName(itemSet.getSetName()).isEmpty())
        {
            db.getItemSets().delete(itemSet);
        }
        else
        {
            throw new ResourceStillHaveObjectsException();
        }

    }

    public void deleteItemSpell(String name) {
        ItemSpells itemSpells = db.getItemSpells().findByName(name).orElseThrow(ResourceNotExistException::new);
        if (db.getItems().findByItemSpellsName(itemSpells.getName()).isEmpty())
        {
            db.getItemSpells().delete(itemSpells);
        }
        else
        {
            throw new ResourceStillHaveObjectsException();
        }

    }
}
