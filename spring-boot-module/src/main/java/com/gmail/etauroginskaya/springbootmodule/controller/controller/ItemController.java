package com.gmail.etauroginskaya.springbootmodule.controller.controller;

import com.gmail.etauroginskaya.online_market.service.ItemService;
import com.gmail.etauroginskaya.online_market.service.model.ItemDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.gmail.etauroginskaya.springbootmodule.controller.constant.PageConstants.ITEMS_PAGE;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.PageConstants.ITEM_NEW_PAGE;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.PageConstants.ITEM_PAGE;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.ParameterConstants.ADD_SUCCESSFULLY;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.ParameterConstants.DELETE_SUCCESSFULLY;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.UrlConstants.ITEMS_URL;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.UrlConstants.ITEM_ADD_URL;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.UrlConstants.ITEM_COPY_URL;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.UrlConstants.ITEM_DELETE_URL;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.UrlConstants.ITEM_URL;

@Controller
@RequestMapping()
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping(ITEMS_URL)
    public String getViewItems(Model model,
                               @RequestParam(value = "page", defaultValue = "1") Integer currentPage,
                               @RequestParam(value = "size", defaultValue = "10") Integer pageSize) {
        Page<ItemDTO> itemPage = itemService.getItemPageByNameAsc(pageSize, currentPage - 1);
        model.addAttribute("itemPage", itemPage);
        if (itemPage.getTotalPages() > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, itemPage.getTotalPages())
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return ITEMS_PAGE;
    }

    @GetMapping(ITEM_URL)
    public String getItem(Model model,
                          @PathVariable("id") Long id) {
        ItemDTO itemDTO = itemService.getItemById(id);
        model.addAttribute("item", itemDTO);
        return ITEM_PAGE;
    }

    @PostMapping(ITEM_DELETE_URL)
    public String deleteItem(@PathVariable("id") Long id) {
        itemService.deleteItemById(id);
        return "redirect:".concat(ITEMS_URL).concat(DELETE_SUCCESSFULLY);
    }

    @PostMapping(ITEM_COPY_URL)
    public String getCopyItemForm(Model model,
                                  @PathVariable("id") Long id) {
        ItemDTO itemDTO = itemService.getItemById(id);
        itemDTO.setUniqueNumber(UUID.randomUUID().toString());
        model.addAttribute("item", itemDTO);
        return ITEM_NEW_PAGE;
    }

    @PostMapping(ITEM_ADD_URL)
    public String addItem(@Valid @ModelAttribute("item") ItemDTO itemDTO,
                          BindingResult bindingResult,
                          Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("item", itemDTO);
            return ITEM_NEW_PAGE;
        }
        itemService.addItem(itemDTO);
        return "redirect:".concat(ITEMS_URL).concat(ADD_SUCCESSFULLY);
    }
}