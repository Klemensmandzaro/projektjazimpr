package org.example;

import org.example.controller.MyViewController;
import org.example.model.*;
import org.example.service.MyViewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MyViewControllerTest {

    @Mock
    private MyViewService myViewService;

    @Mock
    private Model model;

    @InjectMocks
    private MyViewController myViewController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void viewSelected_ShouldReturnViewItems() {
        List<Item> items = Arrays.asList(new Item(), new Item());
        when(myViewService.findItems(1)).thenReturn(items);

        String viewName = myViewController.viewSelected(model, 1);

        assertEquals("viewItems", viewName);
        verify(model).addAttribute("itemList", items);
        verify(model).addAttribute("page", 1);
    }

    @Test
    public void viewItemsCreatedByUser_ShouldReturnViewItems() {
        List<Item> items = Arrays.asList(new Item(), new Item());
        when(myViewService.findItemsCreatedByUser()).thenReturn(items);

        String viewName = myViewController.viewItemsCreatedByUser(model);

        assertEquals("viewItems", viewName);
        verify(model).addAttribute("itemList", items);
    }

    @Test
    public void viewClasses_ShouldReturnViewClasses() {
        List<ItemClass> itemClasses = Arrays.asList(new ItemClass(), new ItemClass());
        when(myViewService.findItemClasses()).thenReturn(itemClasses);

        String viewName = myViewController.viewClasses(model);

        assertEquals("viewClasses", viewName);
        verify(model).addAttribute("itemClassList", itemClasses);
    }

    @Test
    public void viewSubclasses_ShouldReturnViewSubclasses() {
        List<ItemSubclass> itemSubclasses = Arrays.asList(new ItemSubclass(), new ItemSubclass());
        when(myViewService.findItemSubclasses()).thenReturn(itemSubclasses);

        String viewName = myViewController.viewSubclasses(model);

        assertEquals("viewSubclasses", viewName);
        verify(model).addAttribute("itemSubclassList", itemSubclasses);
    }

    @Test
    public void viewSelectedSpells_ShouldReturnViewSpells() {
        List<ItemSpells> itemSpells = Arrays.asList(new ItemSpells(), new ItemSpells());
        when(myViewService.findSelectedItemSpells(1)).thenReturn(itemSpells);

        String viewName = myViewController.viewSelectedSpells(model, 1);

        assertEquals("viewSpells", viewName);
        verify(model).addAttribute("itemSpellsList", itemSpells);
        verify(model).addAttribute("page", 1);
    }

    @Test
    public void viewSelectedSets_ShouldReturnViewSets() {
        List<ItemSet> itemSets = Arrays.asList(new ItemSet(), new ItemSet());
        when(myViewService.findSelectedItemSets(1)).thenReturn(itemSets);

        String viewName = myViewController.viewSelectedSets(model, 1);

        assertEquals("viewSets", viewName);
        verify(model).addAttribute("itemSetList", itemSets);
        verify(model).addAttribute("page", 1);
    }

    @Test
    public void viewItemsForm_ShouldReturnViewItemAdd() {
        String viewName = myViewController.viewItemsForm(model);

        assertEquals("viewItemAdd", viewName);
        verify(model).addAttribute(eq("item"), any(Item.class));
        verify(model).addAttribute(eq("itemClassList"), anyList());
        verify(model).addAttribute(eq("itemSubclassList"), anyList());
        verify(model).addAttribute(eq("itemSetList"), anyList());
        verify(model).addAttribute(eq("itemSpellsList"), anyList());
    }

    @Test
    public void addItem_ShouldRedirectToViewItemsCreatedByUser() throws Exception {
        Item item = new Item();
        String selectedSpells = "1,2,3";
        when(myViewService.findItemSpellById(anyLong())).thenReturn(new ItemSpells());

        String viewName = myViewController.addItem(item, selectedSpells);

        assertEquals("redirect:/view/itemscreatedbyuser", viewName);
        verify(myViewService).addItem(item);
    }

    @Test
    public void addItem_ShouldThrowExceptionWhenErrorOccurs() {
        Item item = new Item();
        String selectedSpells = "1,2,3";
        when(myViewService.findItemSpellById(anyLong())).thenThrow(new RuntimeException("Error"));

        Exception exception = assertThrows(Exception.class, () -> {
            myViewController.addItem(item, selectedSpells);
        });

        assertEquals("Error adding item: Error", exception.getMessage());
    }

    @Test
    public void editItem_ShouldRedirectToViewItemsCreatedByUser() throws Exception {
        Item item = new Item();
        String selectedSpells = "1,2,3";
        String name = "TestName";
        when(myViewService.findItemSpellById(anyLong())).thenReturn(new ItemSpells());

        String viewName = myViewController.editItem(item, selectedSpells, name);

        assertEquals("redirect:/view/itemscreatedbyuser", viewName);
        verify(myViewService).editItem(item);
    }

    @Test
    public void editItem_ShouldThrowExceptionWhenErrorOccurs() {
        Item item = new Item();
        String selectedSpells = "1,2,3";
        String name = "TestName";
        when(myViewService.findItemSpellById(anyLong())).thenThrow(new RuntimeException("Error"));

        Exception exception = assertThrows(Exception.class, () -> {
            myViewController.editItem(item, selectedSpells, name);
        });

        assertEquals("Error adding item: Error", exception.getMessage());
    }

    @Test
    public void deleteItem_ShouldRedirectToViewItemsCreatedByUser() {
        Long id = 1L;

        String viewName = myViewController.deleteItem(id);

        assertEquals("redirect:/view/itemscreatedbyuser", viewName);
        verify(myViewService).deleteItem(id);
    }

    @Test
    public void viewItemClassForm_ShouldReturnViewItemClassAdd() {
        String viewName = myViewController.viewItemClassForm(model);

        assertEquals("viewItemClassAdd", viewName);
        verify(model).addAttribute(eq("itemClass"), any(ItemClass.class));
    }

    @Test
    public void addItemClass_ShouldRedirectToViewClasses() throws Exception {
        ItemClass itemClass = new ItemClass();

        String viewName = myViewController.addItemClass(itemClass);

        assertEquals("redirect:/view/classes", viewName);
        verify(myViewService).addItemClass(itemClass);
    }

    @Test
    public void addItemClass_ShouldThrowExceptionWhenErrorOccurs() {
        ItemClass itemClass = new ItemClass();
        doThrow(new RuntimeException("Error")).when(myViewService).addItemClass(any(ItemClass.class));

        Exception exception = assertThrows(Exception.class, () -> {
            myViewController.addItemClass(itemClass);
        });

        assertEquals("Error adding itemClass: Error", exception.getMessage());
    }

    @Test
    public void editItemClass_ShouldRedirectToViewClasses() throws Exception {
        ItemClass itemClass = new ItemClass();

        String viewName = myViewController.editItemClass(itemClass);

        assertEquals("redirect:/view/classes", viewName);
        verify(myViewService).editItemClass(itemClass);
    }

    @Test
    public void editItemClass_ShouldThrowExceptionWhenErrorOccurs() {
        ItemClass itemClass = new ItemClass();
        doThrow(new RuntimeException("Error")).when(myViewService).editItemClass(any(ItemClass.class));

        Exception exception = assertThrows(Exception.class, () -> {
            myViewController.editItemClass(itemClass);
        });

        assertEquals("Error adding item: Error", exception.getMessage());
    }

    @Test
    public void deleteItemClass_ShouldRedirectToViewClasses() {
        Long id = 1L;

        String viewName = myViewController.deleteItemClass(id);

        assertEquals("redirect:/view/classes", viewName);
        verify(myViewService).deleteItemClass(id);
    }

    @Test
    public void viewItemSubclassForm_ShouldReturnViewItemSubclassAdd() {
        String viewName = myViewController.viewItemSubclassForm(model);

        assertEquals("viewItemSubclassAdd", viewName);
        verify(model).addAttribute(eq("itemSubclass"), any(ItemSubclass.class));
        verify(model).addAttribute(eq("itemClassList"), anyList());
    }

    @Test
    public void addItemSubclass_ShouldRedirectToViewSubclasses() throws Exception {
        ItemSubclass itemSubclass = new ItemSubclass();

        String viewName = myViewController.addItemSubclass(itemSubclass);

        assertEquals("redirect:/view/subclasses", viewName);
        verify(myViewService).addItemSubclass(itemSubclass);
    }

    @Test
    public void addItemSubclass_ShouldThrowExceptionWhenErrorOccurs() {
        ItemSubclass itemSubclass = new ItemSubclass();
        doThrow(new RuntimeException("Error")).when(myViewService).addItemSubclass(any(ItemSubclass.class));

        Exception exception = assertThrows(Exception.class, () -> {
            myViewController.addItemSubclass(itemSubclass);
        });

        assertEquals("Error adding item: Error", exception.getMessage());
    }

    @Test
    public void editItemSubclass_ShouldRedirectToViewSubclasses() throws Exception {
        ItemSubclass itemSubclass = new ItemSubclass();

        String viewName = myViewController.editItemSubclass(itemSubclass);

        assertEquals("redirect:/view/subclasses", viewName);
        verify(myViewService).editItemSubclass(itemSubclass);
    }

    @Test
    public void editItemSubclass_ShouldThrowExceptionWhenErrorOccurs() {
        ItemSubclass itemSubclass = new ItemSubclass();
        doThrow(new RuntimeException("Error")).when(myViewService).editItemSubclass(any(ItemSubclass.class));

        Exception exception = assertThrows(Exception.class, () -> {
            myViewController.editItemSubclass(itemSubclass);
        });

        assertEquals("Error adding item: Error", exception.getMessage());
    }

    @Test
    public void deleteItemSubclass_ShouldRedirectToViewSubclasses() {
        Long id = 1L;

        String viewName = myViewController.deleteItemSubclass(id);

        assertEquals("redirect:/view/subclasses", viewName);
        verify(myViewService).deleteItemSubclass(id);
    }

    @Test
    public void viewItemSetForm_ShouldReturnViewItemSetAdd() {
        String viewName = myViewController.viewItemSetForm(model);

        assertEquals("viewItemSetAdd", viewName);
        verify(model).addAttribute(eq("itemSet"), any(ItemSet.class));
    }

    @Test
    public void addItemSet_ShouldRedirectToViewSets() throws Exception {
        ItemSet itemSet = new ItemSet();

        String viewName = myViewController.addItemSet(itemSet);

        assertEquals("redirect:/view/sets?page=0", viewName);
        verify(myViewService).addItemSet(itemSet);
    }

    @Test
    public void addItemSet_ShouldThrowExceptionWhenErrorOccurs() {
        ItemSet itemSet = new ItemSet();
        doThrow(new RuntimeException("Error")).when(myViewService).addItemSet(any(ItemSet.class));

        Exception exception = assertThrows(Exception.class, () -> {
            myViewController.addItemSet(itemSet);
        });

        assertEquals("Error adding item: Error", exception.getMessage());
    }

    @Test
    public void editItemSet_ShouldRedirectToViewSets() throws Exception {
        ItemSet itemSet = new ItemSet();

        String viewName = myViewController.editItemSet(itemSet);

        assertEquals("redirect:/view/sets?page=0", viewName);
        verify(myViewService).editItemSet(itemSet);
    }

    @Test
    public void editItemSet_ShouldThrowExceptionWhenErrorOccurs() {
        ItemSet itemSet = new ItemSet();
        doThrow(new RuntimeException("Error")).when(myViewService).editItemSet(any(ItemSet.class));

        Exception exception = assertThrows(Exception.class, () -> {
            myViewController.editItemSet(itemSet);
        });

        assertEquals("Error adding item: Error", exception.getMessage());
    }

    @Test
    public void deleteItemSet_ShouldRedirectToViewSets() {
        Long id = 1L;

        String viewName = myViewController.deleteItemSet(id);

        assertEquals("redirect:/view/sets?page=0", viewName);
        verify(myViewService).deleteItemSet(id);
    }

    @Test
    public void viewItemSpellForm_ShouldReturnViewItemSpellAdd() {
        String viewName = myViewController.viewItemSpellForm(model);

        assertEquals("viewItemSpellAdd", viewName);
        verify(model).addAttribute(eq("itemSpell"), any(ItemSpells.class));
    }

    @Test
    public void addItemSpell_ShouldRedirectToViewSpells() throws Exception {
        ItemSpells itemSpells = new ItemSpells();

        String viewName = myViewController.addItemSpell(itemSpells);

        assertEquals("redirect:/view/spells?page=0", viewName);
        verify(myViewService).addItemSpell(itemSpells);
    }

    @Test
    public void addItemSpell_ShouldThrowExceptionWhenErrorOccurs() {
        ItemSpells itemSpells = new ItemSpells();
        doThrow(new RuntimeException("Error")).when(myViewService).addItemSpell(any(ItemSpells.class));

        Exception exception = assertThrows(Exception.class, () -> {
            myViewController.addItemSpell(itemSpells);
        });

        assertEquals("Error adding item: Error", exception.getMessage());
    }

    @Test
    public void editItemSpell_ShouldRedirectToViewSpells() throws Exception {
        ItemSpells itemSpells = new ItemSpells();

        String viewName = myViewController.editItemSpell(itemSpells);

        assertEquals("redirect:/view/spells?page=0", viewName);
        verify(myViewService).editItemSpell(itemSpells);
    }

    @Test
    public void editItemSpell_ShouldThrowExceptionWhenErrorOccurs() {
        ItemSpells itemSpells = new ItemSpells();
        doThrow(new RuntimeException("Error")).when(myViewService).editItemSpell(any(ItemSpells.class));

        Exception exception = assertThrows(Exception.class, () -> {
            myViewController.editItemSpell(itemSpells);
        });

        assertEquals("Error adding item: Error", exception.getMessage());
    }

    @Test
    public void deleteItemSpell_ShouldRedirectToViewSpells() {
        Long id = 1L;

        String viewName = myViewController.deleteItemSpell(id);

        assertEquals("redirect:/view/spells?page=0", viewName);
        verify(myViewService).deleteItemSpell(id);
    }
}