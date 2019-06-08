package com.gmail.etauroginskaya.springbootmodule.controller;

import com.gmail.etauroginskaya.online_market.service.ReviewService;
import com.gmail.etauroginskaya.springbootmodule.controller.controller.ReviewController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.gmail.etauroginskaya.springbootmodule.controller.constant.ParameterConstants.*;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.UrlConstants.REVIEWS_URL;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class ReviewControllerTest {

    @Mock
    private ReviewService reviewService;

    private ReviewController reviewController;
    private MockMvc mockMvc;

    @Before
    public void init() {
        reviewController = new ReviewController(reviewService);
        mockMvc = MockMvcBuilders.standaloneSetup(reviewController).build();
    }

    @Test
    public void shouldRedirectToReviewsPageWithSuccessfullyParameterAfterSuccessfullyUpdatedReviewShow() {
        when(reviewService.updateShowReview(1L, true)).thenReturn(1);
        String resultUrl = reviewController.updateShowReview(1L, true);
        assertThat(resultUrl, equalTo("redirect:".concat(REVIEWS_URL).concat(UPDATE_SUCCESSFULLY)));
    }

    @Test
    public void shouldRedirectToReviewsPageWithNotSuccessfullyParameterAfterInvalidUpdatedReviewShow() {
        when(reviewService.updateShowReview(1L, true)).thenReturn(0);
        String resultUrl = reviewController.updateShowReview(1L, true);
        assertThat(resultUrl, equalTo("redirect:".concat(REVIEWS_URL).concat(UPDATE_NOT_SUCCESSFULLY)));
    }

    @Test
    public void shouldRedirectToReviewsPageWithSuccessfullyParameterAfterSuccessfullyDeletedReview() {
        when(reviewService.deleteReviewById(1L)).thenReturn(1);
        String resultUrl = reviewController.deleteReview(1L);
        assertThat(resultUrl, equalTo("redirect:".concat(REVIEWS_URL).concat(DELETE_SUCCESSFULLY)));
    }

    @Test
    public void shouldRedirectToReviewsPageWithNotSuccessfullyParameterAfterInvalidDeletedReview() {
        when(reviewService.deleteReviewById(1L)).thenReturn(0);
        String resultUrl = reviewController.deleteReview(1L);
        assertThat(resultUrl, equalTo("redirect:".concat(REVIEWS_URL).concat(DELETE_NOT_SUCCESSFULLY)));
    }
}