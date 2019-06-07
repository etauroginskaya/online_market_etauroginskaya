package com.gmail.etauroginskaya.online_market.service.converter;

import com.gmail.etauroginskaya.online_market.repository.model.Item;
import com.gmail.etauroginskaya.online_market.service.model.ItemDTO;

public interface ItemConverter {

    ItemDTO toDTO(Item item);

    Item toEntity(ItemDTO itemDTO);
}
