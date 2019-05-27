package com.gmail.etauroginskaya.springbootmodule.controller.controller;

import com.gmail.etauroginskaya.online_market.service.ReviewService;
import com.gmail.etauroginskaya.online_market.service.model.ReviewDTO;
import org.springframework.data.domain.Page;
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

import static com.gmail.etauroginskaya.springbootmodule.controller.constant.PageConstants.REVIEWS_PAGE;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.ParameterConstants.DELETE_NOT_SUCCESSFULLY;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.ParameterConstants.DELETE_SUCCESSFULLY;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.ParameterConstants.UPDATE_NOT_SUCCESSFULLY;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.ParameterConstants.UPDATE_SUCCESSFULLY;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.UrlConstants.REVIEWS_DELETE_URL;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.UrlConstants.REVIEWS_UPDATE_URL;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.UrlConstants.REVIEWS_URL;

@Controller
@RequestMapping()
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping(REVIEWS_URL)
    public String getViewReviews(Model model,
                                 @RequestParam(value = "page", defaultValue = "1") Integer currentPage,
                                 @RequestParam(value = "size", defaultValue = "10") Integer pageSize) {
        Page<ReviewDTO> reviewPage = reviewService.getReviewsPage(pageSize, currentPage - 1);
        model.addAttribute("reviewPage", reviewPage);
        int totalPages = reviewPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return REVIEWS_PAGE;
    }

    @PostMapping(REVIEWS_UPDATE_URL)
    public String updateShowReview(@PathVariable("id") Long id,
                                   @RequestParam(value = "show", defaultValue = "false") Boolean show) {
        int result = reviewService.updateShowReview(id, show);
        if (result > 0) {
            return "redirect:".concat(REVIEWS_URL).concat(UPDATE_SUCCESSFULLY);
        }
        return "redirect:".concat(REVIEWS_URL).concat(UPDATE_NOT_SUCCESSFULLY);
    }

    @PostMapping(REVIEWS_DELETE_URL)
    public String deleteReview(@PathVariable("id") Long id) {
        int result = reviewService.deleteReviewById(id);
        if (result > 0) {
            return "redirect:".concat(REVIEWS_URL).concat(DELETE_SUCCESSFULLY);
        }
        return "redirect:".concat(REVIEWS_URL).concat(DELETE_NOT_SUCCESSFULLY);
    }
}
