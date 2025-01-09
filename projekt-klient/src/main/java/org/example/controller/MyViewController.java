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
    public String viewSelectedSpells(Model model, @RequestParam Long id) {
        List<ItemSpells> itemSpellsList = myViewService.findSelectedItemSpells(id);
        model.addAttribute("itemSpellsList", itemSpellsList);
        return "viewSelectedSpells";
    }

    @GetMapping("view/selectedsets")
    public String viewSelectedSets(Model model, @RequestParam Long id) {
        List<ItemSet> itemSetList = myViewService.findSelectedItemSets(id);
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


}
