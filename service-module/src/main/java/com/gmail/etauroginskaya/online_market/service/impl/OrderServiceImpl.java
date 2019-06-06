package com.gmail.etauroginskaya.online_market.service.impl;

import com.gmail.etauroginskaya.online_market.repository.OrderRepository;
import com.gmail.etauroginskaya.online_market.repository.model.Item;
import com.gmail.etauroginskaya.online_market.repository.model.Order;
import com.gmail.etauroginskaya.online_market.service.model.OrderStatusEnum;
import com.gmail.etauroginskaya.online_market.repository.model.User;
import com.gmail.etauroginskaya.online_market.service.EmailService;
import com.gmail.etauroginskaya.online_market.service.OrderService;
import com.gmail.etauroginskaya.online_market.service.converter.OrderConverter;
import com.gmail.etauroginskaya.online_market.service.model.OrderDTO;
import com.gmail.etauroginskaya.online_market.service.model.OrderWithUserInfoDTO;
import com.gmail.etauroginskaya.online_market.service.GeneratorService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.gmail.etauroginskaya.online_market.service.constants.FormatConstants.DATE_FORMATTER;

@Service
public class OrderServiceImpl implements OrderService {

    private static final int QUANTITY_NUMBERS_IN_ORDER_ID = 10;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER);
    private final OrderRepository orderRepository;
    private final OrderConverter orderConverter;
    private final EmailService emailService;
    private final GeneratorService generatorService;

    public OrderServiceImpl(OrderRepository orderRepository, OrderConverter orderConverter,
                            EmailService emailService, GeneratorService generatorService) {
        this.orderRepository = orderRepository;
        this.orderConverter = orderConverter;
        this.emailService = emailService;
        this.generatorService = generatorService;
    }

    @Override
    @Transactional
    public Page<OrderDTO> getOrderPageByCreatedDesc(int pageSize, int currentPage) {
        currentPage--;
        int startItem = currentPage * pageSize;
        int quantityEntity = orderRepository.getCountOfEntities();
        List<OrderDTO> dtos;
        if (quantityEntity < startItem) {
            dtos = Collections.emptyList();
        } else {
            List<Order> orders = orderRepository.getOrdersByCreatedDesc(startItem, pageSize);
            dtos = orders.stream()
                    .map(orderConverter::toDTO)
                    .collect(Collectors.toList());
        }
        return new PageImpl<>(dtos, PageRequest.of(currentPage, pageSize), quantityEntity);
    }

    @Override
    @Transactional
    public OrderWithUserInfoDTO getOrderByNumber(Long number) {
        Order order = orderRepository.getById(number);
        return orderConverter.toDTOWithUserInfo(order);
    }

    @Override
    @Transactional
    public void updateOrderStatus(Long number, String newStatus) {
        Order order = orderRepository.getById(number);
        order.setStatus(newStatus);
        orderRepository.merge(order);
        emailService.sendUpdateStatusOrderMessage(order.getUser().getId(), order.getUniqueNumber(), newStatus);
    }

    @Override
    @Transactional
    public void addOrder(Long userID, Long itemID, int quantity) {
        Order order = new Order();
        User user = new User();
        user.setId(userID);
        order.setUser(user);
        Item item = new Item();
        item.setId(itemID);
        order.setItem(item);
        order.setQuantity(quantity);
        order.setStatus(OrderStatusEnum.NEW.name());
        order.setCreated(LocalDateTime.now().format(formatter));
        long uniqueNumber = generatorService.getNumber(QUANTITY_NUMBERS_IN_ORDER_ID);
        while (orderRepository.exists(uniqueNumber)) {
            uniqueNumber = generatorService.getNumber(QUANTITY_NUMBERS_IN_ORDER_ID);
        }
        order.setUniqueNumber(uniqueNumber);
        orderRepository.persist(order);
        emailService.sendCreateOrderMessage(userID, uniqueNumber);
    }

    @Override
    @Transactional
    public List<OrderDTO> getAllOrders() {
        return orderRepository.getAll().stream()
                .map(orderConverter::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public OrderDTO getOrderById(Long id) {
        Order order = orderRepository.getById(id);
        if (order == null) {
            return null;
        }
        return orderConverter.toDTO(order);
    }

    @Override
    @Transactional
    public Page<OrderDTO> getOrderPageCreatedDesc(Long userID, int pageSize, int currentPage) {
        currentPage--;
        int startItem = currentPage * pageSize;
        int quantityEntity = orderRepository.getCountOfEntities();
        List<OrderDTO> dtos;
        if (quantityEntity < startItem) {
            dtos = Collections.emptyList();
        } else {
            List<Order> orders = orderRepository.getOrdersByUserIdCreatedDesc(userID, startItem, pageSize);
            dtos = orders.stream()
                    .map(orderConverter::toDTO)
                    .collect(Collectors.toList());
        }
        return new PageImpl<>(dtos, PageRequest.of(currentPage, pageSize), quantityEntity);
    }
}