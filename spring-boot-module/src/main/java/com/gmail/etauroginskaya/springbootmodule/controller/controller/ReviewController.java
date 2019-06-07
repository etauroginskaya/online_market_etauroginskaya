package com.gmail.etauroginskaya.springbootmodule.controller.controller;

import com.gmail.etauroginskaya.online_market.service.ReviewService;
<<<<<<< HEAD
import com.gmail.etauroginskaya.online_market.service.model.ReviewDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
=======
import com.gmail.etauroginskaya.online_market.service.model.AppUserPrincipal;
import com.gmail.etauroginskaya.online_market.service.model.ReviewDTO;
import com.gmail.etauroginskaya.online_market.service.model.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
>>>>>>> develop
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

<<<<<<< HEAD
=======
import javax.validation.Valid;
>>>>>>> develop
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.gmail.etauroginskaya.springbootmodule.controller.constant.PageConstants.REVIEWS_PAGE;
<<<<<<< HEAD
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.ParameterConstants.*;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.UrlConstants.*;
=======
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.PageConstants.REVIEW_ADD_PAGE;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.ParameterConstants.ADD_REVIEW_SUCCESSFULLY;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.ParameterConstants.DELETE_NOT_SUCCESSFULLY;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.ParameterConstants.DELETE_SUCCESSFULLY;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.ParameterConstants.UPDATE_NOT_SUCCESSFULLY;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.ParameterConstants.UPDATE_SUCCESSFULLY;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.UrlConstants.ITEMS_URL;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.UrlConstants.REVIEW_ADD_URL;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.UrlConstants.REVIEWS_DELETE_URL;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.UrlConstants.REVIEWS_UPDATE_URL;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.UrlConstants.REVIEWS_URL;
>>>>>>> develop

@Controller
@RequestMapping()
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping(REVIEWS_URL)
    public String getViewReviews(Model model,
<<<<<<< HEAD
                                 @RequestParam(value = "page", defaultValue = "1") Integer currentPage,
                                 @RequestParam(value = "size", defaultValue = "10") Integer pageSize) {
        Page<ReviewDTO> reviewPage = reviewService.getReviewsPage(PageRequest.of(currentPage - 1, pageSize));
=======
                                 @AuthenticationPrincipal AppUserPrincipal user,
                                 @RequestParam(value = "page", defaultValue = "1") Integer currentPage,
                                 @RequestParam(value = "size", defaultValue = "10") Integer pageSize) {
        Page<ReviewDTO> reviewPage = reviewService.getReviewsPage(pageSize, currentPage);
>>>>>>> develop
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
<<<<<<< HEAD
            return "redirect:".concat(REVIEWS_URL).concat(UPDATE_REVIEW_SUCCESSFULLY);
        }
        return "redirect:".concat(REVIEWS_URL).concat(UPDATE_REVIEW_NOT_SUCCESSFULLY);
=======
            return "redirect:".concat(REVIEWS_URL).concat(UPDATE_SUCCESSFULLY);
        }
        return "redirect:".concat(REVIEWS_URL).concat(UPDATE_NOT_SUCCESSFULLY);
>>>>>>> develop
    }

    @PostMapping(REVIEWS_DELETE_URL)
    public String deleteReview(@PathVariable("id") Long id) {
<<<<<<< HEAD
        int result = reviewService.deleteReview(id);
        if (result > 0) {
            return "redirect:".concat(REVIEWS_URL).concat(DELETE_REVIEW_SUCCESSFULLY);
        }
        return "redirect:".concat(REVIEWS_URL).concat(DELETE_REVIEW_NOT_SUCCESSFULLY);
    }
}
=======
        int result = reviewService.deleteReviewById(id);
        if (result > 0) {
            return "redirect:".concat(REVIEWS_URL).concat(DELETE_SUCCESSFULLY);
        }
        return "redirect:".concat(REVIEWS_URL).concat(DELETE_NOT_SUCCESSFULLY);
    }

    @GetMapping(REVIEW_ADD_URL)
    public String getViewNewReview(Model model) {
        model.addAttribute("review", new ReviewDTO());
        return REVIEW_ADD_PAGE;
    }

    @PostMapping(REVIEW_ADD_URL)
    public String addReview(Model model,
                            @AuthenticationPrincipal AppUserPrincipal user,
                            @Valid @ModelAttribute(name = "review") ReviewDTO reviewDTO,
                            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("review", reviewDTO);
            return REVIEW_ADD_PAGE;
        }
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        reviewDTO.setUser(userDTO);
        reviewService.addReview(reviewDTO);
        return "redirect:".concat(ITEMS_URL).concat(ADD_REVIEW_SUCCESSFULLY);
    }
}
>>>>>>> develop
