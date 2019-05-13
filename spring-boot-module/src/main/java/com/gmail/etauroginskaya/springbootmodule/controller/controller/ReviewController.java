package com.gmail.etauroginskaya.springbootmodule.controller.controller;

import com.gmail.etauroginskaya.online_market.service.ReviewService;
import com.gmail.etauroginskaya.online_market.service.model.ReviewDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.gmail.etauroginskaya.springbootmodule.controller.constant.PageConstants.REVIEWS_PAGE;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.UrlConstants.*;

@Controller
@RequestMapping()
public class ReviewController {

    private static final Integer DEFAULT_NUMBER_START_PAGE = 1;
    private static final Integer DEFAULT_PAGE_SIZE = 10;
    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping(REVIEWS_URL)
    public String getListReviews(Model model,
                                  @RequestParam("page") Optional<Integer> page,
                                  @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(DEFAULT_NUMBER_START_PAGE);
        int pageSize = size.orElse(DEFAULT_PAGE_SIZE);
        Page<ReviewDTO> reviewPage = reviewService.getReviewsInPage(PageRequest.of(currentPage - 1, pageSize));
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
    public String updateStatusShowReview(@PathVariable("id") Long id,
                                          @RequestParam(value = "show", defaultValue = "false") Boolean show) {
        reviewService.updateStatusReview(id, show);
        return "redirect:" + REVIEWS_URL;
    }

    @PostMapping(REVIEWS_DELETE_URL)
    public String deleteReview(@PathVariable("id") Long id) {
        reviewService.deleteReview(Long.valueOf(id));
        return "redirect:" + REVIEWS_URL;
    }
}
