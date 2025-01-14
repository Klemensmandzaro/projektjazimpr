package org.example.controller;

import org.example.model.*;
import org.example.service.MyViewService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class MyViewController {
    private MyViewService myViewService;

    public MyViewController(MyViewService myViewService) {
        this.myViewService = myViewService;
    }

    @GetMapping("view/selecteditems")
    public String viewSelected(Model model, @RequestParam Long id) {
        List<Item> itemList = myViewService.findSelectedItems(id);
        model.addAttribute("itemList", itemList);
        return "viewSelectedItems";
    }

    @GetMapping("view/itemscreatedbyuser")
    public String viewItemsCreatedByUser(Model model) {
        List<Item> itemList = myViewService.findItemsCreatedByUser();
        model.addAttribute("itemList", itemList);
        return "viewSelectedItems";
    }

    @GetMapping("view/classes")
    public String viewClasses(Model model) {
        List<ItemClass> itemClassList = myViewService.findItemClasses();
        model.addAttribute("itemClassList", itemClassList);
        return "viewClasses";
    }

    @GetMapping("view/subclasses")
    public String viewSubclasses(Model model) {
        List<ItemSubclass> itemSubclassList = myViewService.findItemSubclasses();
        model.addAttribute("itemSubclassList", itemSubclassList);
        return "viewSubclasses";
    }

    @GetMapping("view/selectedspells")
    public String viewSelectedSpells(Model model, @RequestParam int page) {
        List<ItemSpells> itemSpellsList = myViewService.findSelectedItemSpells(page);
        model.addAttribute("itemSpellsList", itemSpellsList);
        model.addAttribute("page", page);
        return "viewSelectedSpells";
    }

    @GetMapping("view/selectedsets")
    public String viewSelectedSets(Model model, @RequestParam int page) {
        List<ItemSet> itemSetList = myViewService.findSelectedItemSets(page);
        model.addAttribute("page", page);
        model.addAttribute("itemSetList", itemSetList);
        return "viewSelectedSets";
    }

    @GetMapping("view/itemadd")
    public String viewItemsForm(Model model) {
        model.addAttribute("item", new Item());
        model.addAttribute("itemClassList", myViewService.findItemClasses());
        model.addAttribute("itemSubclassList", myViewService.findItemSubclasses());
        model.addAttribute("itemSetList", myViewService.findAllItemSets());
        model.addAttribute("itemSpellsList", myViewService.findAllItemSpells());
        return "viewItemAdd";
    }

    @PostMapping("/item/add")
    public String addItem(@ModelAttribute Item item, @RequestParam("selectedSpells") String selectedSpells) throws Exception {
        List<Long> spellIds = Arrays.stream(selectedSpells.split(","))
                .map(Long::valueOf)
                .collect(Collectors.toList());
        List<ItemSpells> itemSpellsList = new ArrayList<>();
        for (Long spellId : spellIds) {
            itemSpellsList.add(myViewService.findItemSpellById(spellId));
        }
        item.setItemSpells(itemSpellsList);
        myViewService.addItem(item);
        return "redirect:/view/itemscreatedbyuser";
    }

    @GetMapping("view/itemedit")
    public String viewItemEdit(Model model) {
        model.addAttribute("item", new Item());
        model.addAttribute("itemList", myViewService.findItemsCreatedByUser());
        model.addAttribute("itemClassList", myViewService.findItemClasses());
        model.addAttribute("itemSubclassList", myViewService.findItemSubclasses());
        model.addAttribute("itemSetList", myViewService.findAllItemSets());
        model.addAttribute("itemSpellsList", myViewService.findAllItemSpells());
        return "viewItemEdit";
    }

    @PostMapping("/item/edit")
    public String editItem(@ModelAttribute Item item, @RequestParam("selectedSpells") String selectedSpells, @RequestParam("selectedName") String name) throws Exception {
        List<Long> spellIds = Arrays.stream(selectedSpells.split(","))
                .map(Long::valueOf)
                .collect(Collectors.toList());
        List<ItemSpells> itemSpellsList = new ArrayList<>();
        for (Long spellId : spellIds) {
            itemSpellsList.add(myViewService.findItemSpellById(spellId));
        }
        item.setItemSpells(itemSpellsList);
        item.setName(name);
        myViewService.editItem(item);
        return "redirect:/view/itemscreatedbyuser";
    }

    @PostMapping("/item/delete")
    public String deleteItem(@RequestParam Long id) {
        myViewService.deleteItem(id);
        return "redirect:/view/itemscreatedbyuser";
    }


    @GetMapping("view/itemclassadd")
    public String viewItemClassForm(Model model) {
        model.addAttribute("itemClass", new ItemClass());
        return "viewItemClassAdd";
    }

    @PostMapping("/itemclass/add")
    public String addItemClass(@ModelAttribute ItemClass itemClass) {
        myViewService.addItemClass(itemClass);
        return "redirect:/view/classes";
    }

    @GetMapping("view/itemclassedit")
    public String viewItemClassEdit(Model model) {
        model.addAttribute("itemClass", new ItemClass());
        model.addAttribute("itemClassList", myViewService.findItemClasses());
        return "viewItemClassEdit";
    }

    @PostMapping("/itemclass/edit")
    public String editItemClass(@ModelAttribute ItemClass itemClass) {
        myViewService.editItemClass(itemClass);
        return "redirect:/view/classes";
    }

    @PostMapping("/itemclass/delete")
    public String deleteItemClass(@RequestParam Long id) {
        myViewService.deleteItemClass(id);
        return "redirect:/view/classes";
    }

    @GetMapping("view/itemsubclassadd")
    public String viewItemSubclassForm(Model model) {
        model.addAttribute("itemSubclass", new ItemSubclass());
        model.addAttribute("itemClassList", myViewService.findItemClasses());
        return "viewItemSubclassAdd";
    }

    @PostMapping("/itemsubclass/add")
    public String addItemSubclass(@ModelAttribute ItemSubclass itemSubclass) {
        myViewService.addItemSubclass(itemSubclass);
        return "redirect:/view/subclasses";
    }

    @GetMapping("view/itemsubclassedit")
    public String viewItemSubclassEdit(Model model) {
        model.addAttribute("itemSubclass", new ItemSubclass());
        model.addAttribute("itemSubclassList", myViewService.findItemSubclasses());
        model.addAttribute("itemClassList", myViewService.findItemClasses());
        return "viewItemSubclassEdit";
    }

    @PostMapping("/itemsubclass/edit")
    public String editItemSubclass(@ModelAttribute ItemSubclass itemSubclass) {
        myViewService.editItemSubclass(itemSubclass);
        return "redirect:/view/subclasses";
    }

    @PostMapping("/itemsubclass/delete")
    public String deleteItemSubclass(@RequestParam Long id) {
        myViewService.deleteItemSubclass(id);
        return "redirect:/view/subclasses";
    }

    @GetMapping("view/itemsetadd")
    public String viewItemSetForm(Model model) {
        model.addAttribute("itemSet", new ItemSet());
        return "viewItemSetAdd";
    }

    @PostMapping("/itemset/add")
    public String addItemSet(@ModelAttribute ItemSet itemSet) {
        myViewService.addItemSet(itemSet);
        return "redirect:/view/selectedsets?page=0";
    }

    @GetMapping("view/itemsetedit")
    public String viewItemSetEdit(Model model) {
        model.addAttribute("itemSet", new ItemSet());
        model.addAttribute("itemSetList", myViewService.findAllItemSets());
        return "viewItemSetEdit";
    }

    @PostMapping("/itemset/edit")
    public String editItemSet(@ModelAttribute ItemSet itemSet) {
        myViewService.editItemSet(itemSet);
        return "redirect:/view/selectedsets?page=0";
    }

    @PostMapping("/itemset/delete")
    public String deleteItemSet(@RequestParam Long id) {
        myViewService.deleteItemSet(id);
        return "redirect:/view/selectedsets?page=0";
    }

    @GetMapping("view/itemspelladd")
    public String viewItemSpellForm(Model model) {
        model.addAttribute("itemSpell", new ItemSpells());
        return "viewItemSpellAdd";
    }

    @PostMapping("/itemspell/add")
    public String addItemSpell(@ModelAttribute ItemSpells itemSpells) {
        myViewService.addItemSpell(itemSpells);
        return "redirect:/view/selectedspells?page=0";
    }

    @GetMapping("view/itemspelledit")
    public String viewItemSpellEdit(Model model) {
        model.addAttribute("itemSpell", new ItemSpells());
        model.addAttribute("itemSpellsList", myViewService.findAllItemSpells());
        return "viewItemSpellEdit";
    }

    @PostMapping("/itemspell/edit")
    public String editItemSpell(@ModelAttribute ItemSpells itemSpells) {
        myViewService.editItemSpell(itemSpells);
        return "redirect:/view/selectedspells?page=0";
    }

    @PostMapping("/itemspell/delete")
    public String deleteItemSpell(@RequestParam Long id) {
        myViewService.deleteItemSpell(id);
        return "redirect:/view/selectedspells?page=0";
    }
}
