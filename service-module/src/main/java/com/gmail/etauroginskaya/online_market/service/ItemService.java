package com.gmail.etauroginskaya.online_market.service;

import com.gmail.etauroginskaya.online_market.service.model.ItemDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ItemService {

    Page<ItemDTO> getItemPageByNameAsc(int pageSize, int currentPage);

    ItemDTO getItemById(Long id);

    ItemDTO deleteItemById(Long id);

    void addItem(ItemDTO itemDTO);

    List<ItemDTO> getAllItems();

    int addItems(List<ItemDTO> dtos);
}
