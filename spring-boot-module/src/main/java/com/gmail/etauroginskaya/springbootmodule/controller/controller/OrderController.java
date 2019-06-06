package com.gmail.etauroginskaya.springbootmodule.controller.controller;

import com.gmail.etauroginskaya.online_market.service.OrderService;
import com.gmail.etauroginskaya.online_market.service.model.AppUserPrincipal;
import com.gmail.etauroginskaya.online_market.service.model.OrderDTO;
import com.gmail.etauroginskaya.online_market.service.model.OrderWithUserInfoDTO;
import org.springframework.data.domain.Page;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.gmail.etauroginskaya.springbootmodule.controller.constant.PageConstants.ORDERS_PAGE;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.PageConstants.ORDER_PAGE;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.ParameterConstants.ADD_ORDER_NOT_VALID_PARAMETER;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.ParameterConstants.ADD_SUCCESSFULLY;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.RoleConstants.SALE_ROLE_NAME;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.UrlConstants.ITEMS_URL;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.UrlConstants.ORDERS_URL;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.UrlConstants.ORDER_ADD_URL;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.UrlConstants.ORDER_UPDATE_URL;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.UrlConstants.ORDER_URL;

@Controller
@RequestMapping()
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping(ORDERS_URL)
    public String getViewOrders(Model model,
                                @AuthenticationPrincipal AppUserPrincipal user,
                                @RequestParam(value = "page", defaultValue = "1") Integer currentPage,
                                @RequestParam(value = "size", defaultValue = "10") Integer pageSize) {
        List<String> authorities = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        Page<OrderDTO> orderPage;
        if (authorities.contains(SALE_ROLE_NAME)) {
            orderPage = orderService.getOrderPageByCreatedDesc(pageSize, currentPage);
        } else {
            orderPage = orderService.getOrderPageCreatedDesc(user.getId(), pageSize, currentPage);
        }
        model.addAttribute("orderPage", orderPage);
        if (orderPage.getTotalPages() > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, orderPage.getTotalPages())
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return ORDERS_PAGE;
    }

    @GetMapping(ORDER_URL)
    public String getOrder(Model model,
                           @PathVariable("number") Long number) {
        OrderWithUserInfoDTO orderDTO = orderService.getOrderByNumber(number);
        model.addAttribute("order", orderDTO);
        return ORDER_PAGE;
    }

    @PostMapping(ORDER_UPDATE_URL)
    public String updateStatusOrder(Model model,
                                    @PathVariable("number") Long number,
                                    @RequestParam(value = "status") String newStatus) {
        orderService.updateOrderStatus(number, newStatus);
        System.out.println(newStatus);
        OrderWithUserInfoDTO orderDTO = orderService.getOrderByNumber(number);
        model.addAttribute("order", orderDTO);
        return ORDER_PAGE;
    }

    @PostMapping(ORDER_ADD_URL)
    public String addOrder(@AuthenticationPrincipal AppUserPrincipal user,
                           @RequestParam(value = "itemID") Long itemID,
                           @RequestParam(value = "quantity") String quantity) {
        quantity = quantity.substring(1);
        if (quantity.isEmpty() || quantity == null || Integer.valueOf(quantity) < 1) {
            return "redirect:".concat(ITEMS_URL).concat(ADD_ORDER_NOT_VALID_PARAMETER);
        }
        orderService.addOrder(user.getId(), itemID, Integer.valueOf(quantity));
        return "redirect:".concat(ORDERS_URL).concat(ADD_SUCCESSFULLY);
    }
}
