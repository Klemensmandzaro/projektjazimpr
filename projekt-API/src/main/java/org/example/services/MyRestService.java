package org.example.services;


import org.example.DTOs.*;
import org.example.exeptions.ResourceAlreadyExistException;
import org.example.exeptions.ResourceCantHaveAllNullValuesExceptions;
import org.example.exeptions.ResourceNotExistException;
import org.example.exeptions.ResourceStillHaveObjectsException;
import org.example.mappers.ItemStatsMapper;
import org.example.model.*;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.example.repositories.ICatalogData;
import org.springframework.data.domain.Pageable;


import java.util.ArrayList;
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

    public List<Item> getItemsCreatedByUser() {
        return db.getItems().findByisCreatedByUser(true);
    }


    public List<ItemClass> getAllItemClasses() {
        return db.getItemClasses().findAll();
    }

    public List<ItemSubclass> getAllItemSubclasses() {
        return db.getItemSubclasses().findAll();
    }


    public List<ItemSet> getBetweenItemSets(Long start, Long end) {
        return db.getItemSets().findByIdBetween(start, end);
    }

    public List<ItemSet> getAllItemSets() {
        return db.getItemSets().findAll();
    }

    public List<ItemSet> getSelectedItemSets(Long start, int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return db.getItemSets().findByIdGreaterThanOrEqual(start, pageable);
    }

    public List<ItemSpells> getBetweenItemSpells(Long start, Long end) {
        return db.getItemSpells().findByIdBetween(start, end);
    }

    public ItemSpells getItemSpellById(Long id) {
        return db.getItemSpells().findById(id).orElseThrow(ResourceNotExistException::new);
    }

    @Cacheable("itemSpells")
    public List<ItemSpells> getAllItemSpells() {
        return db.getItemSpells().findAll();
    }

    public List<ItemSpells> getSelectedItemSpells(Long start, int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return db.getItemSpells().findByIdGreaterThanOrEqual(start, pageable);
    }

    public void addItem(Item item) {
        if (item.getName() == null || item.getItemClass()==null || item.getItemSubclass()==null || item.getItemSet()==null || item.getItemStats()==null) {
            throw new ResourceCantHaveAllNullValuesExceptions();
        }

        if (db.getItems().findFirstByName(item.getName()).isPresent())
        {
            throw new ResourceAlreadyExistException();
        }

        item.setItemClass(db.getItemClasses().findByClassName(item.getItemClass().getClassName()).orElseThrow(ResourceNotExistException::new));
        item.setItemSubclass(db.getItemSubclasses().findBySubclassName(item.getItemSubclass().getSubclassName()).orElseThrow(ResourceNotExistException::new));
        item.setItemSet(db.getItemSets().findBySetName(item.getItemSet().getSetName()).orElseThrow(ResourceNotExistException::new));
        List<ItemSpells> itemSpellsList = new ArrayList<>();
        if (item.getItemSpells() != null)
        {
            for (ItemSpells itemSpells : item.getItemSpells())
            {
                if (db.getItemSpells().findByName(itemSpells.getName()).isPresent())
                {
                    itemSpellsList.add(db.getItemSpells().findByName(itemSpells.getName()).get());
                }
                else {
                    throw new ResourceNotExistException();
                }
            }
            item.setItemSpells(itemSpellsList);
        }
        item.setCreatedByUser(true);

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

    public void updateItem(Item item2) {

        Item item = db.getItems().findById(item2.getId()).orElseThrow(ResourceNotExistException::new);

        if (item2.getName() != null) {
            item.setName(item2.getName());
            if (db.getItems().findFirstByName(item2.getName()).isPresent() && db.getItems().findFirstByName(item2.getName()).get().getId()!= item2.getId())
            {
                throw new ResourceAlreadyExistException();
            }
        }





        if (item2.getItemStats() != null)
        {
            item.setItemStats(item2.getItemStats());
        }

        if (item2.getItemClass() != null)
        {
            if (db.getItemClasses().findByClassName(item2.getItemClass().getClassName()).isPresent())
            {
                item.setItemClass(db.getItemClasses().findByClassName(item2.getItemClass().getClassName()).get());
            }
            else {
                throw new ResourceNotExistException();
            }
        }

        if (item2.getItemSubclass() != null)
        {
            if (db.getItemSubclasses().findBySubclassName(item2.getItemSubclass().getSubclassName()).isPresent())
            {
                item.setItemSubclass(db.getItemSubclasses().findBySubclassName(item2.getItemSubclass().getSubclassName()).get());
            }
            else {
                throw new ResourceNotExistException();
            }
        }

        if (item2.getItemSet() != null)
        {
            if (db.getItemSets().findBySetName(item2.getItemSet().getSetName()).isPresent())
            {
                item.setItemSet(db.getItemSets().findBySetName(item2.getItemSet().getSetName()).get());
            }
            else {
                throw new ResourceNotExistException();
            }
        }

        if (item2.getItemSpells() != null)
        {
            List<ItemSpells> itemSpellsList = new ArrayList<>();
            for (ItemSpells itemSpells : item2.getItemSpells())
            {
                if (db.getItemSpells().findByName(itemSpells.getName()).isPresent())
                {
                    itemSpellsList.add(db.getItemSpells().findByName(itemSpells.getName()).get());
                }
                else {
                    throw new ResourceNotExistException();
                }
            }
            item.setItemSpells(itemSpellsList);
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

    public void deleteItem(Long id) {
        Item item = db.getItems().findById(id).orElseThrow(ResourceNotExistException::new);

        ItemStats itemStats = item.getItemStats();


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
