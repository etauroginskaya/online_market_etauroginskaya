package com.gmail.etauroginskaya.online_market.service.impl;

import com.gmail.etauroginskaya.online_market.repository.ItemRepository;
import com.gmail.etauroginskaya.online_market.repository.model.Item;
import com.gmail.etauroginskaya.online_market.service.ItemService;
import com.gmail.etauroginskaya.online_market.service.converter.ItemConverter;
import com.gmail.etauroginskaya.online_market.service.model.ItemDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final ItemConverter itemConverter;

    public ItemServiceImpl(ItemRepository itemRepository, ItemConverter itemConverter) {
        this.itemRepository = itemRepository;
        this.itemConverter = itemConverter;
    }

    @Override
    @Transactional
    public Page<ItemDTO> getItemPageByNameAsc(int pageSize, int currentPage) {
        currentPage--;
        int startItem = currentPage * pageSize;
        int quantityEntity = itemRepository.getCountOfEntities();
        List<ItemDTO> dtos;
        if (quantityEntity < startItem) {
            dtos = Collections.emptyList();
        } else {
            List<Item> items = itemRepository.getItemsByNameAsc(startItem, pageSize);
            dtos = items.stream()
                    .map(itemConverter::toDTO)
                    .collect(Collectors.toList());
        }
        return new PageImpl<>(dtos, PageRequest.of(currentPage, pageSize), quantityEntity);
    }

    @Override
    @Transactional
    public ItemDTO getItemById(Long id) {
        Item item = itemRepository.getById(id);
        if (item.isDeleted()) {
            return null;
        }
        return itemConverter.toDTO(item);
    }

    @Override
    @Transactional
    public ItemDTO deleteItemById(Long id) {
        Item item = itemRepository.getById(id);
        if (item.isDeleted() == true) {
            return null;
        }
        itemRepository.remove(item);
        return itemConverter.toDTO(item);
    }

    @Override
    @Transactional
    public void addItem(ItemDTO itemDTO) {
        Item item = itemConverter.toEntity(itemDTO);
        itemRepository.persist(item);
    }

    @Override
    @Transactional
    public List<ItemDTO> getAllItems() {
        return itemRepository.getAll().stream()
                .map(itemConverter::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public int addItems(List<ItemDTO> dtos) {
        int countAddedItems = 0;
        for (ItemDTO dto : dtos) {
            dto.setUniqueNumber(UUID.randomUUID().toString());
        }
        List<Item> items = dtos.stream()
                .map(itemConverter::toEntity)
                .collect(Collectors.toList());
        for (Item item : items) {
            itemRepository.persist(item);
            countAddedItems++;
        }
        return countAddedItems;
    }
}