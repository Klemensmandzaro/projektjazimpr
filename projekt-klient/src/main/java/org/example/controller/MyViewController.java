package org.example.controller;

import org.example.model.*;
import org.example.service.MyViewService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
public class MyViewController {
    private MyViewService myViewService;

    public MyViewController(MyViewService myViewService) {
        this.myViewService = myViewService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("view/items")
    public String viewSelected(Model model, @RequestParam int page) {
        List<Item> itemList = myViewService.findItems(page);
        model.addAttribute("itemList", itemList);
        model.addAttribute("page", page);
        log.info("Fetching all items with page: {} and limit: 50", page);
        return "viewItems";
    }

    @GetMapping("view/itemscreatedbyuser")
    public String viewItemsCreatedByUser(Model model) {
        List<Item> itemList = myViewService.findItemsCreatedByUser();
        model.addAttribute("itemList", itemList);
        log.info("Fetching all items created by user");
        return "viewItems";
    }

    @GetMapping("view/classes")
    public String viewClasses(Model model) {
        List<ItemClass> itemClassList = myViewService.findItemClasses();
        model.addAttribute("itemClassList", itemClassList);
        log.info("Fetching all classes");
        return "viewClasses";
    }

    @GetMapping("view/subclasses")
    public String viewSubclasses(Model model) {
        List<ItemSubclass> itemSubclassList = myViewService.findItemSubclasses();
        model.addAttribute("itemSubclassList", itemSubclassList);
        log.info("Fetching all subclasses");
        return "viewSubclasses";
    }

    @GetMapping("view/spells")
    public String viewSelectedSpells(Model model, @RequestParam int page) {
        List<ItemSpells> itemSpellsList = myViewService.findSelectedItemSpells(page);
        model.addAttribute("itemSpellsList", itemSpellsList);
        model.addAttribute("page", page);
        log.info("Fetching all spells with page: {} and limit: 50", page);
        return "viewSpells";
    }

    @GetMapping("view/sets")
    public String viewSelectedSets(Model model, @RequestParam int page) {
        List<ItemSet> itemSetList = myViewService.findSelectedItemSets(page);
        model.addAttribute("page", page);
        model.addAttribute("itemSetList", itemSetList);
        log.info("Fetching all sets with page: {} and limit: 50", page);
        return "viewSets";
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
        try {
            List<Long> spellIds = Arrays.stream(selectedSpells.split(","))
                    .map(Long::valueOf)
                    .collect(Collectors.toList());
            List<ItemSpells> itemSpellsList = new ArrayList<>();
            for (Long spellId : spellIds) {
                itemSpellsList.add(myViewService.findItemSpellById(spellId));
            }
            item.setItemSpells(itemSpellsList);
            myViewService.addItem(item);
            log.info("Creating new item {}", item.getName());
        } catch (Exception e) {
            throw new Exception("Error adding item: " + e.getMessage());
        }
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
        try {
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
            log.info("Editing item {}", item.getName());
        } catch (Exception e) {
            throw new Exception("Error adding item: " + e.getMessage());
        }
        return "redirect:/view/itemscreatedbyuser";
    }

    @PostMapping("/item/delete")
    public String deleteItem(@RequestParam Long id) {
        myViewService.deleteItem(id);
        log.info("Deleting item with id: {}", id);
        return "redirect:/view/itemscreatedbyuser";
    }


    @GetMapping("view/itemclassadd")
    public String viewItemClassForm(Model model) {
        model.addAttribute("itemClass", new ItemClass());
        return "viewItemClassAdd";
    }

    @PostMapping("/itemclass/add")
    public String addItemClass(@ModelAttribute ItemClass itemClass) throws Exception {
        try {
            myViewService.addItemClass(itemClass);
            log.info("Creating new itemClass {}", itemClass.getClassName());
        } catch (Exception e) {
            throw new Exception("Error adding itemClass: " + e.getMessage());
        }
        return "redirect:/view/classes";
    }

    @GetMapping("view/itemclassedit")
    public String viewItemClassEdit(Model model) {
        model.addAttribute("itemClass", new ItemClass());
        model.addAttribute("itemClassList", myViewService.findItemClasses());
        return "viewItemClassEdit";
    }

    @PostMapping("/itemclass/edit")
    public String editItemClass(@ModelAttribute ItemClass itemClass) throws Exception {
        try {
            myViewService.editItemClass(itemClass);
            log.info("Editing itemClass {}", itemClass.getClassName());
        } catch (Exception e) {
            throw new Exception("Error adding item: " + e.getMessage());
        }
        return "redirect:/view/classes";
    }

    @PostMapping("/itemclass/delete")
    public String deleteItemClass(@RequestParam Long id) {
        myViewService.deleteItemClass(id);
        log.info("Deleting itemClass with id: {}", id);
        return "redirect:/view/classes";
    }

    @GetMapping("view/itemsubclassadd")
    public String viewItemSubclassForm(Model model) {
        model.addAttribute("itemSubclass", new ItemSubclass());
        model.addAttribute("itemClassList", myViewService.findItemClasses());
        return "viewItemSubclassAdd";
    }

    @PostMapping("/itemsubclass/add")
    public String addItemSubclass(@ModelAttribute ItemSubclass itemSubclass) throws Exception {
        try {
            myViewService.addItemSubclass(itemSubclass);
            log.info("Creating new itemSubclass {}", itemSubclass.getSubclassName());
        } catch (Exception e) {
            throw new Exception("Error adding item: " + e.getMessage());
        }
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
    public String editItemSubclass(@ModelAttribute ItemSubclass itemSubclass) throws Exception {
        try {
            myViewService.editItemSubclass(itemSubclass);
            log.info("Editing itemSubclass {}", itemSubclass.getSubclassName());
        }catch (Exception e) {
            throw new Exception("Error adding item: " + e.getMessage());
        }
        return "redirect:/view/subclasses";
    }

    @PostMapping("/itemsubclass/delete")
    public String deleteItemSubclass(@RequestParam Long id) {
        myViewService.deleteItemSubclass(id);
        log.info("Deleting itemSubclass with id: {}", id);
        return "redirect:/view/subclasses";
    }

    @GetMapping("view/itemsetadd")
    public String viewItemSetForm(Model model) {
        model.addAttribute("itemSet", new ItemSet());
        return "viewItemSetAdd";
    }

    @PostMapping("/itemset/add")
    public String addItemSet(@ModelAttribute ItemSet itemSet) throws Exception {
        try {
            myViewService.addItemSet(itemSet);
            log.info("Creating new itemSet {}", itemSet.getSetName());
        } catch (Exception e) {
            throw new Exception("Error adding item: " + e.getMessage());
        }
        return "redirect:/view/sets?page=0";
    }

    @GetMapping("view/itemsetedit")
    public String viewItemSetEdit(Model model) {
        model.addAttribute("itemSet", new ItemSet());
        model.addAttribute("itemSetList", myViewService.findAllItemSets());
        return "viewItemSetEdit";
    }

    @PostMapping("/itemset/edit")
    public String editItemSet(@ModelAttribute ItemSet itemSet) throws Exception {
        try {
            myViewService.editItemSet(itemSet);
            log.info("Editing itemSet {}", itemSet.getSetName());
        }catch (Exception e) {
            throw new Exception("Error adding item: " + e.getMessage());
        }
        return "redirect:/view/sets?page=0";
    }

    @PostMapping("/itemset/delete")
    public String deleteItemSet(@RequestParam Long id) {
        myViewService.deleteItemSet(id);
        log.info("Deleting itemSet with id: {}", id);
        return "redirect:/view/sets?page=0";
    }

    @GetMapping("view/itemspelladd")
    public String viewItemSpellForm(Model model) {
        model.addAttribute("itemSpell", new ItemSpells());
        return "viewItemSpellAdd";
    }

    @PostMapping("/itemspell/add")
    public String addItemSpell(@ModelAttribute ItemSpells itemSpells) throws Exception {
        try {
            myViewService.addItemSpell(itemSpells);
            log.info("Creating new itemSpell {}", itemSpells.getName());
        } catch (Exception e) {
            throw new Exception("Error adding item: " + e.getMessage());
        }
        return "redirect:/view/spells?page=0";
    }

    @GetMapping("view/itemspelledit")
    public String viewItemSpellEdit(Model model) {
        model.addAttribute("itemSpell", new ItemSpells());
        model.addAttribute("itemSpellsList", myViewService.findAllItemSpells());
        return "viewItemSpellEdit";
    }

    @PostMapping("/itemspell/edit")
    public String editItemSpell(@ModelAttribute ItemSpells itemSpells) throws Exception {
        try {
            myViewService.editItemSpell(itemSpells);
            log.info("Editing itemSpell {}", itemSpells.getName());
        } catch (Exception e) {
            throw new Exception("Error adding item: " + e.getMessage());
        }
        return "redirect:/view/spells?page=0";
    }

    @PostMapping("/itemspell/delete")
    public String deleteItemSpell(@RequestParam Long id) {
        myViewService.deleteItemSpell(id);
        log.info("Deleting itemSpell with id: {}", id);
        return "redirect:/view/spells?page=0";
    }
}
