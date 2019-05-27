package com.gmail.etauroginskaya.springbootmodule.controller.controller;

import com.gmail.etauroginskaya.online_market.service.ItemService;
import com.gmail.etauroginskaya.online_market.service.model.ItemDTO;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.gmail.etauroginskaya.springbootmodule.controller.constant.UrlConstants.API_ITEMS_URL;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.UrlConstants.API_ITEM_URL;

@RestController
public class ItemAPIController {

    private final ItemService itemService;

    public ItemAPIController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping(API_ITEMS_URL)
    public List<ItemDTO> getAllItems() {
        return itemService.getAllItems();
    }

    @GetMapping(API_ITEM_URL)
    public ItemDTO getItemById(@PathVariable("id") Long id) {
        return itemService.getItemById(id);
    }

    @PostMapping(API_ITEMS_URL)
    public void saveItem(@RequestBody ItemDTO itemDTO) {
        itemService.addItem(itemDTO);
    }

    @DeleteMapping(API_ITEM_URL)
    public void deleteItemById(@PathVariable("id") Long id) {
        itemService.deleteItemById(id);
    }
}
