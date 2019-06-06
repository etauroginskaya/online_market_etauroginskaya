package com.gmail.etauroginskaya.online_market.service.parser;

import com.gmail.etauroginskaya.online_market.service.model.ItemDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ParseService {

    List<ItemDTO> parseInListItem(MultipartFile xml);
}
