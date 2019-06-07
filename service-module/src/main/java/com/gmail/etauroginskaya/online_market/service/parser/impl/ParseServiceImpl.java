package com.gmail.etauroginskaya.online_market.service.parser.impl;

import com.gmail.etauroginskaya.online_market.service.exception.ServiceException;
import com.gmail.etauroginskaya.online_market.service.model.ItemDTO;
import com.gmail.etauroginskaya.online_market.service.parser.ParseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class ParseServiceImpl implements ParseService {

    private static final Logger logger = LoggerFactory.getLogger(ParseServiceImpl.class);

    @Override
    public List<ItemDTO> parseInListItem(MultipartFile xml) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xml.getInputStream());
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("Item");
            List<ItemDTO> items = new ArrayList<>();
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    ItemDTO item = new ItemDTO();
                    item.setName(eElement.getElementsByTagName("name").item(0).getTextContent());
                    item.setDescription(eElement.getElementsByTagName("description").item(0).getTextContent());
                    item.setPrice(new BigDecimal(eElement.getElementsByTagName("price").item(0).getTextContent()));
                    items.add(item);
                }
            }
            return items;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException("Parse error document: " + xml.getOriginalFilename(), e);
        }
    }
}
