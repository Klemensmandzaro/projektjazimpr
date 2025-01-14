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


//    public List<Item> getAllItems() {
//        return db.getItems().findFirst10ByOrderByIdAsc();
//    }

    public List<Item> getSelectedItems(Long start, int limit)
    {
        Pageable pageable = PageRequest.of(0, limit);
        return db.getItems().findByIdGreaterThanOrEqual(start, pageable);
    }

//    public List<Item> getItemsBetween(Long start, Long end) {
//        return db.getItems().findByIdBetween(start, end);
//    }

    public List<Item> getItemsCreatedByUser() {
        return db.getItems().findByisCreatedByUser(true);
    }


    public List<ItemClass> getAllItemClasses() {
        return db.getItemClasses().findAll();
    }

    public List<ItemSubclass> getAllItemSubclasses() {
        return db.getItemSubclasses().findAll();
    }


//    public List<ItemSet> getBetweenItemSets(Long start, Long end) {
//        return db.getItemSets().findByIdBetween(start, end);
//    }

    @Cacheable("itemSets")
    public List<ItemSet> getAllItemSets() {
        return db.getItemSets().findAll();
    }

    public List<ItemSet> getSelectedItemSets(int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit);
        return db.getItemSets().findAll(pageable).stream().toList();
    }
//
//    public List<ItemSpells> getBetweenItemSpells(Long start, Long end) {
//        return db.getItemSpells().findByIdBetween(start, end);
//    }

    public ItemSpells getItemSpellById(Long id) {
        return db.getItemSpells().findById(id).orElseThrow(ResourceNotExistException::new);
    }

    @Cacheable("itemSpells")
    public List<ItemSpells> getAllItemSpells() {
        return db.getItemSpells().findAll();
    }

    public List<ItemSpells> getSelectedItemSpells(int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit);
        return db.getItemSpells().findAll(pageable).stream().toList();
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

    public void addItemClass(ItemClass itemClass2) {
        ItemClass itemClass = new ItemClass();
        if (itemClass2.getClassName() == null) {
            throw new ResourceCantHaveAllNullValuesExceptions();
        } else if (db.getItemClasses().findByClassName(itemClass2.getClassName()).isPresent()) {
            throw new ResourceAlreadyExistException();
        }
        itemClass.setClassName(itemClass2.getClassName());
        db.getItemClasses().save(itemClass);
    }

    public void addItemSubclass(ItemSubclass itemSubclass2) {
        ItemSubclass itemSubclass = new ItemSubclass();
        if (itemSubclass2.getSubclassName() == null || itemSubclass2.getItemClass() == null) {
            throw new ResourceCantHaveAllNullValuesExceptions();
        }else if (db.getItemSubclasses().findBySubclassName(itemSubclass2.getSubclassName()).isPresent()) {
            throw new ResourceAlreadyExistException();
        }

        itemSubclass.setSubclassName(itemSubclass2.getSubclassName());
        itemSubclass.setItemClass(db.getItemClasses().findByClassName(itemSubclass2.getItemClass().getClassName()).orElseThrow(ResourceNotExistException::new));

        db.getItemSubclasses().save(itemSubclass);
    }

    public void addItemSet(ItemSet itemSet2) {
        ItemSet itemSet = new ItemSet();
        if (itemSet2.getSetName() != null)
        {
            itemSet.setSetName(itemSet2.getSetName());
            itemSet.setEffects(itemSet2.getEffects());
        } else if (db.getItemSets().findBySetName(itemSet2.getSetName()).isPresent())
        {
            throw new ResourceAlreadyExistException();
        } else
        {
            throw new ResourceCantHaveAllNullValuesExceptions();
        }
        db.getItemSets().save(itemSet);
    }

    public void addItemSpell(ItemSpells itemSpells2) {
        ItemSpells itemSpells = new ItemSpells();
        if (itemSpells2.getName() == null && itemSpells2.getDescription() == null) {
            throw new ResourceCantHaveAllNullValuesExceptions();
        }
        itemSpells.setName(itemSpells2.getName());
        itemSpells.setDescription(itemSpells2.getDescription());
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

    public void updateItemClass(ItemClass itemClass2) {
        ItemClass itemClass = db.getItemClasses().findById(itemClass2.getId()).orElseThrow(ResourceNotExistException::new);
        if (itemClass2.getClassName() != null)
        {
            itemClass.setClassName(itemClass2.getClassName());
        }
        db.getItemClasses().save(itemClass);
    }

    public void updateItemSubclass(ItemSubclass itemSubclass2) {
        ItemSubclass itemSubclass = db.getItemSubclasses().findById(itemSubclass2.getId()).orElseThrow(ResourceNotExistException::new);
        if (itemSubclass2.getSubclassName() != null && itemSubclass2.getItemClass() != null)
        {
            itemSubclass.setSubclassName(itemSubclass2.getSubclassName());
            itemSubclass.setItemClass(db.getItemClasses().findByClassName(itemSubclass2.getItemClass().getClassName()).orElseThrow(ResourceNotExistException::new));
        }
        else
        {
            throw new ResourceCantHaveAllNullValuesExceptions();
        }
        db.getItemSubclasses().save(itemSubclass);
    }

    public void updateItemSet(ItemSet itemSet2) {
        ItemSet itemSet = db.getItemSets().findById(itemSet2.getId()).orElseThrow(ResourceNotExistException::new);
        if (itemSet2.getSetName() != "")
        {
            itemSet.setSetName(itemSet2.getSetName());
        }

        List<String> effects = new ArrayList<>();
        if (itemSet2.getEffects() != null)
        {
            effects.addAll(itemSet2.getEffects());
        }
        itemSet.setEffects(effects);
        db.getItemSets().save(itemSet);
    }

    public void updateItemSpell(ItemSpells itemSpells2) {
        ItemSpells itemSpells = db.getItemSpells().findById(itemSpells2.getId()).orElseThrow(ResourceNotExistException::new);
        if (itemSpells2.getName() != "")
        {
            itemSpells.setName(itemSpells2.getName());
        } else if (db.getItemSpells().findByName(itemSpells2.getName()).isPresent()) {
            throw new ResourceCantHaveAllNullValuesExceptions();
        }

        if (itemSpells2.getDescription() != null)
        {
            itemSpells.setDescription(itemSpells2.getDescription());
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

    public void deleteItemClass(Long id) {
        ItemClass itemClass = db.getItemClasses().findById(id).orElseThrow(ResourceNotExistException::new);
        if (db.getItems().findByItemClassClassName(itemClass.getClassName()).isEmpty() && db.getItemSubclasses().findByItemClassId(itemClass.getId()).isEmpty())
        {
            db.getItemClasses().delete(itemClass);
        }
        else
        {
            throw new ResourceStillHaveObjectsException();
        }

    }

    public void deleteItemSubclass(Long id) {
        ItemSubclass itemSubclass = db.getItemSubclasses().findById(id).orElseThrow(ResourceNotExistException::new);
        if (db.getItems().findByItemSubclassSubclassName(itemSubclass.getSubclassName()).isEmpty())
        {
            db.getItemSubclasses().delete(itemSubclass);
        }
        else
        {
            throw new ResourceStillHaveObjectsException();
        }

    }

    public void deleteItemSet(Long id) {
        ItemSet itemSet = db.getItemSets().findById(id).orElseThrow(ResourceNotExistException::new);
        if (db.getItems().findByItemSetSetName(itemSet.getSetName()).isEmpty())
        {
            db.getItemSets().delete(itemSet);
        }
        else
        {
            throw new ResourceStillHaveObjectsException();
        }

    }

    public void deleteItemSpell(Long id) {
        ItemSpells itemSpells = db.getItemSpells().findById(id).orElseThrow(ResourceNotExistException::new);
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
