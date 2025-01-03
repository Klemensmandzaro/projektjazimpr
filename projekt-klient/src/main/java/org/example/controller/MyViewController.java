package org.example.controller;

import org.example.Main;
import org.example.model.Item;
import org.example.service.MyViewService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

import java.util.List;

@Controller
public class MyViewController {
    private MyViewService myViewService;

    public MyViewController(MyViewService myViewService) {
        this.myViewService = myViewService;
    }

    @GetMapping("view/selected")
    public String viewSelected(Model model) {
        List<Item> itemList = myViewService.findSelectedItems();
        model.addAttribute("itemList", itemList);
        return "viewSelected";
    }
}
