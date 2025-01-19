package org.example;

import org.example.exeptions.*;
import org.example.model.*;
import org.example.repositories.*;
import org.example.services.MyRestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class MyRestServiceTest {

    @Mock
    private ICatalogData db;
    @Mock
    private ItemRepository itemRepository;

    @Mock
    private ItemClassRepository itemClassRepository;

    @Mock
    private ItemSubclassRepository itemSubclassRepository;

    @Mock
    private ItemSetRepository itemSetRepository;

    @Mock
    private ItemSpellsRepository itemSpellsRepository;

    @Mock
    private ItemStatsRepository itemStatsRepository;

    private MyRestService myRestService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        myRestService = new MyRestService(db);
        Mockito.when(db.getItems()).thenReturn(itemRepository);
        Mockito.when(db.getItemClasses()).thenReturn(itemClassRepository);
        Mockito.when(db.getItemSubclasses()).thenReturn(itemSubclassRepository);
        Mockito.when(db.getItemSets()).thenReturn(itemSetRepository);
        Mockito.when(db.getItemSpells()).thenReturn(itemSpellsRepository);
        Mockito.when(db.getItemStats()).thenReturn(itemStatsRepository);
    }

    @Test
    void getAllItems_shouldReturnPagedItems() {
        List<Item> mockItems = List.of(new Item(), new Item());
        Page<Item> mockPage = new PageImpl<>(mockItems);
        Pageable pageable = PageRequest.of(0, 2);
        when(db.getItems().findAll(pageable)).thenReturn(mockPage);

        List<Item> result = myRestService.getAllItems(0, 2);

        assertThat(result).hasSize(2);
        verify(db.getItems(), times(1)).findAll(pageable);
    }

    @Test
    void getItemsCreatedByUser_shouldReturnItemsCreatedByUser() {

        List<Item> mockItems = List.of(new Item(), new Item());
        when(db.getItems().findByisCreatedByUser(true)).thenReturn(mockItems);

        List<Item> result = myRestService.getItemsCreatedByUser();

        assertThat(result).isEqualTo(mockItems);
        verify(db.getItems(), times(1)).findByisCreatedByUser(true);
    }

    @Test
    void getItemClasses_shouldReturnItemClasses() {

        List<ItemClass> mockItemClasses = List.of(new ItemClass(), new ItemClass());
        when(db.getItemClasses().findAll()).thenReturn(mockItemClasses);
        List<ItemClass> result = myRestService.getAllItemClasses();

        assertThat(result).isEqualTo(mockItemClasses);
        verify(db.getItemClasses(), times(1)).findAll();
    }

    @Test
    void getItemSubclasses_shouldReturnItemSubclasses() {

        List<ItemSubclass> mockItemSubclasses = List.of(new ItemSubclass(), new ItemSubclass());
        when(db.getItemSubclasses().findAll()).thenReturn(mockItemSubclasses);
        List<ItemSubclass> result = myRestService.getAllItemSubclasses();

        assertThat(result).isEqualTo(mockItemSubclasses);
        verify(db.getItemSubclasses(), times(1)).findAll();
    }

    @Test
    void getItemSets_shouldReturnItemSets() {

        List<ItemSet> mockItemSet = List.of(new ItemSet(), new ItemSet());
        when(db.getItemSets().findAll()).thenReturn(mockItemSet);
        List<ItemSet> result = myRestService.getAllItemSets();

        assertThat(result).isEqualTo(mockItemSet);
        verify(db.getItemSets(), times(1)).findAll();
    }

    @Test
    void getSelectedItemSets_shouldReturnPagedItemSets() {
        List<ItemSet> mockItemSet = List.of(new ItemSet(), new ItemSet());
        Page<ItemSet> mockPage = new PageImpl<>(mockItemSet);
        Pageable pageable = PageRequest.of(0, 2);
        when(db.getItemSets().findAll(pageable)).thenReturn(mockPage);

        List<ItemSet> result = myRestService.getSelectedItemSets(0, 2);

        assertThat(result).hasSize(2);
        verify(db.getItemSets(), times(1)).findAll(pageable);
    }

    @Test
    void getItemSpellById_shouldReturnItemSpellWhenExists() {

        ItemSpells itemSpell = new ItemSpells();
        itemSpell.setId(1L);
        when(db.getItemSpells().findById(1L)).thenReturn(Optional.of(itemSpell));


        ItemSpells result = myRestService.getItemSpellById(1L);


        assertThat(result).isEqualTo(itemSpell);
        verify(db.getItemSpells(), times(1)).findById(1L);
    }

    @Test
    void getItemSpellById_shouldThrowExceptionWhenNotFound() {

        when(db.getItemSpells().findById(1L)).thenReturn(Optional.empty());


        assertThatThrownBy(() -> myRestService.getItemSpellById(1L))
                .isInstanceOf(ResourceNotExistException.class);
        verify(db.getItemSpells(), times(1)).findById(1L);
    }

    @Test
    void getAllItemSpells_shouldReturnItemSpells() {

        List<ItemSpells> mockItemSpells = List.of(new ItemSpells(), new ItemSpells());
        when(db.getItemSpells().findAll()).thenReturn(mockItemSpells);
        List<ItemSpells> result = myRestService.getAllItemSpells();

        assertThat(result).isEqualTo(mockItemSpells);
        verify(db.getItemSpells(), times(1)).findAll();
    }

    @Test
    void getSelectedItemSpells_shouldReturnPagedItemSpells() {
        List<ItemSpells> mockItemSpells = List.of(new ItemSpells(), new ItemSpells());
        Page<ItemSpells> mockPage = new PageImpl<>(mockItemSpells);
        Pageable pageable = PageRequest.of(0, 2);
        when(db.getItemSpells().findAll(pageable)).thenReturn(mockPage);

        List<ItemSpells> result = myRestService.getSelectedItemSpells(0, 2);

        assertThat(result).hasSize(2);
        verify(db.getItemSpells(), times(1)).findAll(pageable);
    }

    @Test
    void addItem_shouldAddItemSuccessfullyWithNoSpells() {
        Item item = new Item();
        item.setName("Item1");
        ItemClass itemClass = new ItemClass();
        itemClass.setClassName("Class1");
        item.setItemClass(itemClass);
        ItemSubclass itemSubclass = new ItemSubclass();
        itemSubclass.setSubclassName("Subclass1");
        item.setItemSubclass(itemSubclass);
        ItemSet itemSet = new ItemSet();
        itemSet.setSetName("Set1");
        item.setItemSet(itemSet);
        item.setItemStats(new ItemStats());

        when(db.getItems().findFirstByName("Item1")).thenReturn(Optional.empty());
        Mockito.when(db.getItemClasses().findByClassName("Class1")).thenReturn(Optional.of(itemClass));
        when(db.getItemSubclasses().findBySubclassName("Subclass1")).thenReturn(Optional.of(itemSubclass));
        when(db.getItemSets().findBySetName("Set1")).thenReturn(Optional.of(itemSet));


        myRestService.addItem(item);


        verify(db.getItems(), times(1)).save(item);
    }

    @Test
    void addItem_shouldThrowExceptionWhenNameAlreadyExists() {
        Item item = new Item();
        item.setName("Item1");
        ItemClass itemClass = new ItemClass();
        itemClass.setClassName("Class1");
        item.setItemClass(itemClass);
        ItemSubclass itemSubclass = new ItemSubclass();
        itemSubclass.setSubclassName("Subclass1");
        item.setItemSubclass(itemSubclass);
        ItemSet itemSet = new ItemSet();
        itemSet.setSetName("Set1");
        item.setItemSet(itemSet);
        item.setItemStats(new ItemStats());


        when(db.getItems().findFirstByName("Item1")).thenReturn(Optional.of(new Item()));

        assertThatThrownBy(() -> myRestService.addItem(item))
                .isInstanceOf(ResourceAlreadyExistException.class);
        verify(db.getItems(), never()).save(any());
    }

    @Test
    void addItem_shouldThrowExceptionWhenFieldsAreNull() {
        Item item = new Item();

        assertThatThrownBy(() -> myRestService.addItem(item))
                .isInstanceOf(ResourceCantHaveAllNullValuesExceptions.class);
    }

    @Test
    void addItem_ThrowsResourceNotExistException_WhenItemClassNotFound() {
        Item item = new Item();
        item.setName("Item1");


        Mockito.when(db.getItems().findFirstByName("Item1")).thenReturn(Optional.empty());
        Mockito.when(db.getItemClasses().findByClassName("Weapon")).thenReturn(Optional.empty());



        assertThatThrownBy(() -> myRestService.addItem(item))
                .isInstanceOf(ResourceCantHaveAllNullValuesExceptions.class);
        verify(db.getItems(), never()).save(any());
    }

    @Test
    void addItem_ThrowsResourceNotExistException_WhenItemSublassNotFound() {
        Item item = new Item();
        item.setName("Item1");
        ItemClass itemClass = new ItemClass();
        itemClass.setClassName("Class1");
        item.setItemClass(itemClass);
        Mockito.when(db.getItems().findFirstByName("Item1")).thenReturn(Optional.empty());
        Mockito.when(db.getItemClasses().findByClassName("Class1")).thenReturn(Optional.of(itemClass));
        Mockito.when(db.getItemSubclasses().findBySubclassName("Subclass1")).thenReturn(Optional.empty());


        assertThatThrownBy(() -> myRestService.addItem(item))
                .isInstanceOf(ResourceCantHaveAllNullValuesExceptions.class);
        verify(db.getItems(), never()).save(any());
    }

    @Test
    void addItem_ThrowsResourceNotExistException_WhenItemSetNotFound() {
        Item item = new Item();
        item.setName("Item1");
        ItemClass itemClass = new ItemClass();
        itemClass.setClassName("Class1");
        item.setItemClass(itemClass);
        ItemSubclass itemSubclass = new ItemSubclass();
        itemSubclass.setSubclassName("Subclass1");
        item.setItemSubclass(itemSubclass);

        Mockito.when(db.getItems().findFirstByName("Item1")).thenReturn(Optional.empty());
        Mockito.when(db.getItemClasses().findByClassName("Class1")).thenReturn(Optional.of(itemClass));
        Mockito.when(db.getItemSubclasses().findBySubclassName("Subclass1")).thenReturn(Optional.of(itemSubclass));
        Mockito.when(db.getItemSets().findBySetName("Set1")).thenReturn(Optional.empty());


        assertThatThrownBy(() -> myRestService.addItem(item))
                .isInstanceOf(ResourceCantHaveAllNullValuesExceptions.class);
        verify(db.getItems(), never()).save(any());
    }

    @Test
    void addItem_WorksWithSpellListWithSpells() {
        Item item = new Item();
        item.setName("Item1");
        ItemClass itemClass = new ItemClass();
        itemClass.setClassName("Class1");
        item.setItemClass(itemClass);
        ItemSubclass itemSubclass = new ItemSubclass();
        itemSubclass.setSubclassName("Subclass1");
        item.setItemSubclass(itemSubclass);
        ItemSet itemSet = new ItemSet();
        itemSet.setSetName("Set1");
        item.setItemSet(itemSet);
        item.setItemStats(new ItemStats());
        List<ItemSpells> itemSpells = new ArrayList<>();
        ItemSpells itemSpell = new ItemSpells();
        itemSpell.setName("Spell1");
        itemSpells.add(itemSpell);
        item.setItemSpells(itemSpells);


        when(db.getItems().findFirstByName("Item1")).thenReturn(Optional.empty());
        Mockito.when(db.getItemClasses().findByClassName("Class1")).thenReturn(Optional.of(itemClass));
        when(db.getItemSubclasses().findBySubclassName("Subclass1")).thenReturn(Optional.of(itemSubclass));
        when(db.getItemSets().findBySetName("Set1")).thenReturn(Optional.of(itemSet));
        when(db.getItemSpells().findByName("Spell1")).thenReturn(Optional.of(itemSpell));


        myRestService.addItem(item);


        verify(db.getItems(), times(1)).save(item);
    }


    @Test
    void addItem_DontWorksWithSpellListWithSpells() {
        Item item = new Item();
        item.setName("Item1");
        ItemClass itemClass = new ItemClass();
        itemClass.setClassName("Class1");
        item.setItemClass(itemClass);
        ItemSubclass itemSubclass = new ItemSubclass();
        itemSubclass.setSubclassName("Subclass1");
        item.setItemSubclass(itemSubclass);
        ItemSet itemSet = new ItemSet();
        itemSet.setSetName("Set1");
        item.setItemSet(itemSet);
        item.setItemStats(new ItemStats());
        List<ItemSpells> itemSpells = new ArrayList<>();
        ItemSpells itemSpell = new ItemSpells();
        itemSpell.setName("Spell1");
        itemSpells.add(itemSpell);
        item.setItemSpells(itemSpells);

        when(db.getItems().findFirstByName("Item1")).thenReturn(Optional.empty());
        Mockito.when(db.getItemClasses().findByClassName("Class1")).thenReturn(Optional.of(itemClass));
        when(db.getItemSubclasses().findBySubclassName("Subclass1")).thenReturn(Optional.of(itemSubclass));
        when(db.getItemSets().findBySetName("Set1")).thenReturn(Optional.of(itemSet));
        when(db.getItemSpells().findByName("Spell1")).thenReturn(Optional.empty());


        assertThatThrownBy(() -> myRestService.addItem(item))
                .isInstanceOf(ResourceNotExistException.class);
        verify(db.getItems(), never()).save(any());
    }

    @Test
    void updateItem_shouldUpdateItemSuccessfully() {
        Item item2 = new Item();
        item2.setId(1L);
        item2.setName("Updated Item");
        ItemClass itemClass = new ItemClass();
        itemClass.setClassName("Updated Class");
        item2.setItemClass(itemClass);
        ItemSubclass itemSubclass = new ItemSubclass();
        itemSubclass.setSubclassName("Updated Subclass");
        item2.setItemSubclass(itemSubclass);
        ItemSet itemSet = new ItemSet();
        itemSet.setSetName("Updated Set");
        item2.setItemSet(itemSet);
        item2.setItemStats(new ItemStats());
        List<ItemSpells> itemSpells = new ArrayList<>();
        ItemSpells itemSpell = new ItemSpells();
        itemSpell.setName("Updated Spell");
        itemSpells.add(itemSpell);
        item2.setItemSpells(itemSpells);

        Item item = new Item();
        item.setId(1L);

        when(db.getItems().findById(1L)).thenReturn(Optional.of(item));
        when(db.getItems().findFirstByName("Updated Item")).thenReturn(Optional.empty());
        when(db.getItemClasses().findByClassName("Updated Class")).thenReturn(Optional.of(itemClass));
        when(db.getItemSubclasses().findBySubclassName("Updated Subclass")).thenReturn(Optional.of(itemSubclass));
        when(db.getItemSets().findBySetName("Updated Set")).thenReturn(Optional.of(itemSet));
        when(db.getItemSpells().findByName("Updated Spell")).thenReturn(Optional.of(itemSpell));

        myRestService.updateItem(item2);

        verify(db.getItems(), times(1)).save(item);
    }

    @Test
    void updateItem_shouldThrowExceptionWhenItemNotFound() {
        Item item2 = new Item();
        item2.setId(1L);

        when(db.getItems().findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> myRestService.updateItem(item2))
                .isInstanceOf(ResourceNotExistException.class);
    }

    @Test
    void updateItem_shouldThrowExceptionWhenNameAlreadyExists() {
        Item item2 = new Item();
        item2.setId(1L);
        item2.setName("Existing Item");

        Item existingItem = new Item();
        existingItem.setId(2L);

        when(db.getItems().findById(1L)).thenReturn(Optional.of(new Item()));
        when(db.getItems().findFirstByName("Existing Item")).thenReturn(Optional.of(existingItem));

        assertThatThrownBy(() -> myRestService.updateItem(item2))
                .isInstanceOf(ResourceAlreadyExistException.class);
    }

    @Test
    void updateItem_shouldThrowExceptionWhenItemClassNotFound() {
        Item item2 = new Item();
        item2.setId(1L);
        ItemClass itemClass = new ItemClass();
        itemClass.setClassName("Nonexistent Class");
        item2.setItemClass(itemClass);

        when(db.getItems().findById(1L)).thenReturn(Optional.of(new Item()));
        when(db.getItemClasses().findByClassName("Nonexistent Class")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> myRestService.updateItem(item2))
                .isInstanceOf(ResourceNotExistException.class);
    }

    @Test
    void updateItem_shouldThrowExceptionWhenItemSubclassNotFound() {
        Item item2 = new Item();
        item2.setId(1L);
        ItemSubclass itemSubclass = new ItemSubclass();
        itemSubclass.setSubclassName("Nonexistent Subclass");
        item2.setItemSubclass(itemSubclass);

        when(db.getItems().findById(1L)).thenReturn(Optional.of(new Item()));
        when(db.getItemSubclasses().findBySubclassName("Nonexistent Subclass")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> myRestService.updateItem(item2))
                .isInstanceOf(ResourceNotExistException.class);
    }

    @Test
    void updateItem_shouldThrowExceptionWhenItemSetNotFound() {
        Item item2 = new Item();
        item2.setId(1L);
        ItemSet itemSet = new ItemSet();
        itemSet.setSetName("Nonexistent Set");
        item2.setItemSet(itemSet);

        when(db.getItems().findById(1L)).thenReturn(Optional.of(new Item()));
        when(db.getItemSets().findBySetName("Nonexistent Set")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> myRestService.updateItem(item2))
                .isInstanceOf(ResourceNotExistException.class);
    }

    @Test
    void updateItem_shouldThrowExceptionWhenItemSpellNotFound() {
        Item item2 = new Item();
        item2.setId(1L);
        List<ItemSpells> itemSpells = new ArrayList<>();
        ItemSpells itemSpell = new ItemSpells();
        itemSpell.setName("Nonexistent Spell");
        itemSpells.add(itemSpell);
        item2.setItemSpells(itemSpells);

        when(db.getItems().findById(1L)).thenReturn(Optional.of(new Item()));
        when(db.getItemSpells().findByName("Nonexistent Spell")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> myRestService.updateItem(item2))
                .isInstanceOf(ResourceNotExistException.class);
    }

    @Test
    void updateItemClass_shouldUpdateClassName() {
        ItemClass itemClass2 = new ItemClass();
        itemClass2.setId(1L);
        itemClass2.setClassName("Updated Class");

        ItemClass itemClass = new ItemClass();
        itemClass.setId(1L);

        when(db.getItemClasses().findById(1L)).thenReturn(Optional.of(itemClass));

        myRestService.updateItemClass(itemClass2);

        assertThat(itemClass.getClassName()).isEqualTo("Updated Class");
        verify(db.getItemClasses(), times(1)).save(itemClass);
    }

    @Test
    void updateItemClass_shouldThrowExceptionWhenItemClassNotFound() {
        ItemClass itemClass2 = new ItemClass();
        itemClass2.setId(1L);

        when(db.getItemClasses().findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> myRestService.updateItemClass(itemClass2))
                .isInstanceOf(ResourceNotExistException.class);
    }

    @Test
    void updateItemClass_shouldThrowExceptionWhenClassNameIsNull() {
        ItemClass itemClass2 = new ItemClass();
        itemClass2.setId(1L);

        ItemClass itemClass = new ItemClass();
        itemClass.setId(1L);

        when(db.getItemClasses().findById(1L)).thenReturn(Optional.of(itemClass));

        assertThatThrownBy(() -> myRestService.updateItemClass(itemClass2))
                .isInstanceOf(ResourceCantHaveAllNullValuesExceptions.class);
    }

    @Test
    void updateItemClass_shouldThrowExceptionWhenClassNameAlreadyExists() {
        ItemClass itemClass2 = new ItemClass();
        itemClass2.setId(1L);
        itemClass2.setClassName("Existing Class");

        ItemClass existingClass = new ItemClass();
        existingClass.setId(2L);

        when(db.getItemClasses().findById(1L)).thenReturn(Optional.of(new ItemClass()));
        when(db.getItemClasses().findByClassName("Existing Class")).thenReturn(Optional.of(existingClass));

        assertThatThrownBy(() -> myRestService.updateItemClass(itemClass2))
                .isInstanceOf(ResourceAlreadyExistException.class);
    }

    @Test
    void updateItemSubclass_shouldUpdateSubclassNameAndClass() {
        ItemSubclass itemSubclass2 = new ItemSubclass();
        itemSubclass2.setId(1L);
        itemSubclass2.setSubclassName("Updated Subclass");
        ItemClass itemClass = new ItemClass();
        itemClass.setClassName("Updated Class");
        itemSubclass2.setItemClass(itemClass);

        ItemSubclass itemSubclass = new ItemSubclass();
        itemSubclass.setId(1L);

        when(db.getItemSubclasses().findById(1L)).thenReturn(Optional.of(itemSubclass));
        when(db.getItemClasses().findByClassName("Updated Class")).thenReturn(Optional.of(itemClass));

        myRestService.updateItemSubclass(itemSubclass2);

        assertThat(itemSubclass.getSubclassName()).isEqualTo("Updated Subclass");
        assertThat(itemSubclass.getItemClass()).isEqualTo(itemClass);
        verify(db.getItemSubclasses(), times(1)).save(itemSubclass);
    }

    @Test
    void updateItemSubclass_shouldUpdateClassWhenSubclassNameIsNull() {
        ItemSubclass itemSubclass2 = new ItemSubclass();
        itemSubclass2.setId(1L);
        ItemClass itemClass = new ItemClass();
        itemClass.setClassName("Updated Class");
        itemSubclass2.setItemClass(itemClass);

        ItemSubclass itemSubclass = new ItemSubclass();
        itemSubclass.setId(1L);

        when(db.getItemSubclasses().findById(1L)).thenReturn(Optional.of(itemSubclass));
        when(db.getItemClasses().findByClassName("Updated Class")).thenReturn(Optional.of(itemClass));

        myRestService.updateItemSubclass(itemSubclass2);

        assertThat(itemSubclass.getItemClass()).isEqualTo(itemClass);
        verify(db.getItemSubclasses(), times(1)).save(itemSubclass);
    }

    @Test
    void updateItemSubclass_shouldThrowExceptionWhenItemSubclassNotFound() {
        ItemSubclass itemSubclass2 = new ItemSubclass();
        itemSubclass2.setId(1L);

        when(db.getItemSubclasses().findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> myRestService.updateItemSubclass(itemSubclass2))
                .isInstanceOf(ResourceNotExistException.class);
    }

    @Test
    void updateItemSubclass_shouldThrowExceptionWhenItemClassNotFound() {
        ItemSubclass itemSubclass2 = new ItemSubclass();
        itemSubclass2.setSubclassName("Updated Subclass");
        itemSubclass2.setId(1L);
        ItemClass itemClass = new ItemClass();
        itemClass.setClassName("Nonexistent Class");
        itemSubclass2.setItemClass(itemClass);

        when(db.getItemSubclasses().findById(1L)).thenReturn(Optional.of(new ItemSubclass()));
        when(db.getItemClasses().findByClassName("Nonexistent Class")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> myRestService.updateItemSubclass(itemSubclass2))
                .isInstanceOf(ResourceNotExistException.class);
    }

    @Test
    void updateItemSubclass_shouldThrowExceptionWhenSubclassNameAlreadyExists() {
        ItemSubclass itemSubclass2 = new ItemSubclass();
        itemSubclass2.setId(1L);
        itemSubclass2.setSubclassName("Existing Subclass");

        ItemSubclass existingSubclass = new ItemSubclass();
        existingSubclass.setId(2L);

        when(db.getItemSubclasses().findById(1L)).thenReturn(Optional.of(new ItemSubclass()));
        when(db.getItemSubclasses().findBySubclassName("Existing Subclass")).thenReturn(Optional.of(existingSubclass));

        assertThatThrownBy(() -> myRestService.updateItemSubclass(itemSubclass2))
                .isInstanceOf(ResourceAlreadyExistException.class);
    }

    @Test
    void updateItemSubclass_shouldThrowExceptionWhenBothSubclassNameAndItemClassAreNull() {
        ItemSubclass itemSubclass2 = new ItemSubclass();
        itemSubclass2.setId(1L);

        ItemSubclass itemSubclass = new ItemSubclass();
        itemSubclass.setId(1L);

        when(db.getItemSubclasses().findById(1L)).thenReturn(Optional.of(itemSubclass));

        assertThatThrownBy(() -> myRestService.updateItemSubclass(itemSubclass2))
                .isInstanceOf(ResourceCantHaveAllNullValuesExceptions.class);
    }

    @Test
    void updateItemSet_shouldUpdateSetNameAndEffects() {
        ItemSet itemSet2 = new ItemSet();
        itemSet2.setId(1L);
        itemSet2.setSetName("Updated Set");
        List<String> effects = List.of("Effect1", "Effect2");
        itemSet2.setEffects(effects);

        ItemSet itemSet = new ItemSet();
        itemSet.setId(1L);

        when(db.getItemSets().findById(1L)).thenReturn(Optional.of(itemSet));

        myRestService.updateItemSet(itemSet2);

        assertThat(itemSet.getSetName()).isEqualTo("Updated Set");
        assertThat(itemSet.getEffects()).isEqualTo(effects);
        verify(db.getItemSets(), times(1)).save(itemSet);
    }

    @Test
    void updateItemSet_shouldThrowExceptionWhenItemSetNotFound() {
        ItemSet itemSet2 = new ItemSet();
        itemSet2.setId(1L);

        when(db.getItemSets().findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> myRestService.updateItemSet(itemSet2))
                .isInstanceOf(ResourceNotExistException.class);
    }

    @Test
    void updateItemSet_shouldThrowExceptionWhenSetNameAlreadyExists() {
        ItemSet itemSet2 = new ItemSet();
        itemSet2.setId(1L);
        itemSet2.setSetName("Existing Set");

        ItemSet existingSet = new ItemSet();
        existingSet.setId(2L);

        when(db.getItemSets().findById(1L)).thenReturn(Optional.of(new ItemSet()));
        when(db.getItemSets().findBySetName("Existing Set")).thenReturn(Optional.of(existingSet));

        assertThatThrownBy(() -> myRestService.updateItemSet(itemSet2))
                .isInstanceOf(ResourceAlreadyExistException.class);
    }

    @Test
    void updateItemSpell_shouldUpdateSpellNameAndDescription() {
        ItemSpells itemSpells2 = new ItemSpells();
        itemSpells2.setId(1L);
        itemSpells2.setName("Updated Spell");
        itemSpells2.setDescription("Updated Description");

        ItemSpells itemSpells = new ItemSpells();
        itemSpells.setId(1L);

        when(db.getItemSpells().findById(1L)).thenReturn(Optional.of(itemSpells));
        when(db.getItemSpells().findByName("Updated Spell")).thenReturn(Optional.empty());

        myRestService.updateItemSpell(itemSpells2);

        assertThat(itemSpells.getName()).isEqualTo("Updated Spell");
        assertThat(itemSpells.getDescription()).isEqualTo("Updated Description");
        verify(db.getItemSpells(), times(1)).save(itemSpells);
    }

    @Test
    void updateItemSet_shouldUpdateEffectsWhenSetNameIsEmpty() {
        ItemSet itemSet2 = new ItemSet();
        itemSet2.setId(1L);
        List<String> effects = List.of("Effect1", "Effect2");
        itemSet2.setEffects(effects);

        ItemSet itemSet = new ItemSet();
        itemSet.setId(1L);

        when(db.getItemSets().findById(1L)).thenReturn(Optional.of(itemSet));

        myRestService.updateItemSet(itemSet2);

        assertThat(itemSet.getEffects()).isEqualTo(effects);
        verify(db.getItemSets(), times(1)).save(itemSet);
    }

    @Test
    void updateItemSpell_shouldThrowExceptionWhenItemSpellNotFound() {
        ItemSpells itemSpells2 = new ItemSpells();
        itemSpells2.setId(1L);

        when(db.getItemSpells().findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> myRestService.updateItemSpell(itemSpells2))
                .isInstanceOf(ResourceNotExistException.class);
    }

    @Test
    void updateItemSpell_shouldThrowExceptionWhenNameAlreadyExists() {
        ItemSpells itemSpells2 = new ItemSpells();
        itemSpells2.setId(1L);
        itemSpells2.setName("Existing Spell");

        ItemSpells existingSpell = new ItemSpells();
        existingSpell.setId(2L);

        when(db.getItemSpells().findById(1L)).thenReturn(Optional.of(new ItemSpells()));
        when(db.getItemSpells().findByName("Existing Spell")).thenReturn(Optional.of(existingSpell));

        assertThatThrownBy(() -> myRestService.updateItemSpell(itemSpells2))
                .isInstanceOf(ResourceAlreadyExistException.class);
    }

    @Test
    void deleteItem_shouldDeleteItemSuccessfully() {

        Item item = new Item();
        item.setId(1L);
        item.setItemStats(new ItemStats());
        when(db.getItems().findById(1L)).thenReturn(Optional.of(item));


        myRestService.deleteItem(1L);


        verify(db.getItems(), times(1)).delete(item);
    }

    @Test
    void deleteItem_shouldThrowExceptionWhenItemNotFound() {

        when(db.getItems().findById(1L)).thenReturn(Optional.empty());


        assertThatThrownBy(() -> myRestService.deleteItem(1L))
                .isInstanceOf(ResourceNotExistException.class);
        verify(db.getItems(), never()).delete(any());
    }

    @Test
    void deleteItemClass_shouldDeleteItemClassSuccessfully() {
        ItemClass itemClass = new ItemClass();
        itemClass.setId(1L);
        itemClass.setClassName("Class1");

        when(db.getItemClasses().findById(1L)).thenReturn(Optional.of(itemClass));
        when(db.getItems().findByItemClassClassName("Class1")).thenReturn(List.of());
        when(db.getItemSubclasses().findByItemClassId(1L)).thenReturn(Optional.empty());

        myRestService.deleteItemClass(1L);

        verify(db.getItemClasses(), times(1)).delete(itemClass);
    }

    @Test
    void deleteItemClass_shouldThrowExceptionWhenItemClassNotFound() {
        when(db.getItemClasses().findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> myRestService.deleteItemClass(1L))
                .isInstanceOf(ResourceNotExistException.class);
    }

    @Test
    void deleteItemClass_shouldThrowExceptionWhenItemClassStillHasObjects() {
        ItemClass itemClass = new ItemClass();
        itemClass.setId(1L);
        itemClass.setClassName("Class1");

        when(db.getItemClasses().findById(1L)).thenReturn(Optional.of(itemClass));
        when(db.getItems().findByItemClassClassName("Class1")).thenReturn(List.of(new Item()));

        assertThatThrownBy(() -> myRestService.deleteItemClass(1L))
                .isInstanceOf(ResourceStillHaveObjectsException.class);
    }

    @Test
    void deleteItemSubclass_shouldDeleteItemSubclassSuccessfully() {
        ItemSubclass itemSubclass = new ItemSubclass();
        itemSubclass.setId(1L);
        itemSubclass.setSubclassName("Subclass1");

        when(db.getItemSubclasses().findById(1L)).thenReturn(Optional.of(itemSubclass));
        when(db.getItems().findByItemSubclassSubclassName("Subclass1")).thenReturn(List.of());

        myRestService.deleteItemSubclass(1L);

        verify(db.getItemSubclasses(), times(1)).delete(itemSubclass);
    }

    @Test
    void deleteItemSubclass_shouldThrowExceptionWhenItemSubclassNotFound() {
        when(db.getItemSubclasses().findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> myRestService.deleteItemSubclass(1L))
                .isInstanceOf(ResourceNotExistException.class);
    }

    @Test
    void deleteItemSubclass_shouldThrowExceptionWhenItemSubclassStillHasObjects() {
        ItemSubclass itemSubclass = new ItemSubclass();
        itemSubclass.setId(1L);
        itemSubclass.setSubclassName("Subclass1");

        when(db.getItemSubclasses().findById(1L)).thenReturn(Optional.of(itemSubclass));
        when(db.getItems().findByItemSubclassSubclassName("Subclass1")).thenReturn(List.of(new Item()));

        assertThatThrownBy(() -> myRestService.deleteItemSubclass(1L))
                .isInstanceOf(ResourceStillHaveObjectsException.class);
    }

    @Test
    void deleteItemSet_shouldDeleteItemSetSuccessfully() {
        ItemSet itemSet = new ItemSet();
        itemSet.setId(1L);
        itemSet.setSetName("Set1");

        when(db.getItemSets().findById(1L)).thenReturn(Optional.of(itemSet));
        when(db.getItems().findByItemSetSetName("Set1")).thenReturn(List.of());

        myRestService.deleteItemSet(1L);

        verify(db.getItemSets(), times(1)).delete(itemSet);
    }

    @Test
    void deleteItemSet_shouldThrowExceptionWhenItemSetNotFound() {
        when(db.getItemSets().findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> myRestService.deleteItemSet(1L))
                .isInstanceOf(ResourceNotExistException.class);
    }

    @Test
    void deleteItemSet_shouldThrowExceptionWhenItemSetStillHasObjects() {
        ItemSet itemSet = new ItemSet();
        itemSet.setId(1L);
        itemSet.setSetName("Set1");

        when(db.getItemSets().findById(1L)).thenReturn(Optional.of(itemSet));
        when(db.getItems().findByItemSetSetName("Set1")).thenReturn(List.of(new Item()));

        assertThatThrownBy(() -> myRestService.deleteItemSet(1L))
                .isInstanceOf(ResourceStillHaveObjectsException.class);
    }

    @Test
    void deleteItemSpell_shouldDeleteItemSpellSuccessfully() {
        ItemSpells itemSpells = new ItemSpells();
        itemSpells.setId(1L);
        itemSpells.setName("Spell1");

        when(db.getItemSpells().findById(1L)).thenReturn(Optional.of(itemSpells));
        when(db.getItems().findByItemSpellsName("Spell1")).thenReturn(List.of());

        myRestService.deleteItemSpell(1L);

        verify(db.getItemSpells(), times(1)).delete(itemSpells);
    }

    @Test
    void deleteItemSpell_shouldThrowExceptionWhenItemSpellNotFound() {
        when(db.getItemSpells().findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> myRestService.deleteItemSpell(1L))
                .isInstanceOf(ResourceNotExistException.class);
    }

    @Test
    void deleteItemSpell_shouldThrowExceptionWhenItemSpellStillHasObjects() {
        ItemSpells itemSpells = new ItemSpells();
        itemSpells.setId(1L);
        itemSpells.setName("Spell1");

        when(db.getItemSpells().findById(1L)).thenReturn(Optional.of(itemSpells));
        when(db.getItems().findByItemSpellsName("Spell1")).thenReturn(List.of(new Item()));

        assertThatThrownBy(() -> myRestService.deleteItemSpell(1L))
                .isInstanceOf(ResourceStillHaveObjectsException.class);
    }
}
