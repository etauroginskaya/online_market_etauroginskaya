package com.gmail.etauroginskaya.online_market.service.impl;

import com.gmail.etauroginskaya.online_market.repository.ItemRepository;
import com.gmail.etauroginskaya.online_market.repository.model.Item;
import com.gmail.etauroginskaya.online_market.service.ItemService;
import com.gmail.etauroginskaya.online_market.service.converter.ItemConverter;
import com.gmail.etauroginskaya.online_market.service.model.ItemDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {

    private static final Logger logger = LoggerFactory.getLogger(ItemServiceImpl.class);
    private final ItemRepository itemRepository;
    private final ItemConverter itemConverter;

    public ItemServiceImpl(ItemRepository itemRepository, ItemConverter itemConverter) {
        this.itemRepository = itemRepository;
        this.itemConverter = itemConverter;
    }

    @Override
    @Transactional
    public Page<ItemDTO> getItemPageByNameAsc(int pageSize, int currentPage) {
        int startItem = currentPage * pageSize;
        int quantityEntity = itemRepository.getCountOfEntities();
        List<ItemDTO> dtos;
        if (quantityEntity < startItem) {
            dtos = Collections.emptyList();
        } else {
            List<Item> articles = itemRepository.getItemsByNameAsc(startItem, pageSize);
            dtos = articles.stream()
                    .map(itemConverter::toDTO)
                    .collect(Collectors.toList());
        }
        Page<ItemDTO> itemPage = new PageImpl<>(dtos, PageRequest.of(currentPage, pageSize), quantityEntity);
        return itemPage;
    }

    @Override
    @Transactional
    public ItemDTO getItemById(Long id) {
        Item item = itemRepository.getById(id);
        ItemDTO itemDTO = itemConverter.toDTO(item);
        return itemDTO;
    }

    @Override
    @Transactional
    public void deleteItemById(Long id) {
        Item item = itemRepository.getById(id);
        itemRepository.remove(item);
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
        List<ItemDTO> dtos = itemRepository.getAll().stream()
                .map(itemConverter::toDTO)
                .collect(Collectors.toList());
        return dtos;
    }
}
