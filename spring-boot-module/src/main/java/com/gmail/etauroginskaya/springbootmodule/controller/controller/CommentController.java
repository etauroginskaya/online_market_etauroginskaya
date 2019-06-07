package com.gmail.etauroginskaya.springbootmodule.controller.controller;

import com.gmail.etauroginskaya.online_market.service.CommentService;
import com.gmail.etauroginskaya.online_market.service.model.AppUserPrincipal;
import com.gmail.etauroginskaya.online_market.service.model.CommentDTO;
import com.gmail.etauroginskaya.online_market.service.model.UserDTO;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.gmail.etauroginskaya.springbootmodule.controller.constant.ParameterConstants.ADD_SUCCESSFULLY;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.ParameterConstants.DELETE_SUCCESSFULLY;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.UrlConstants.ARTICLES_URL;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.UrlConstants.COMMENT_ADD_URL;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.UrlConstants.COMMENT_DELETE_URL;

@Controller
@RequestMapping()
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping(COMMENT_DELETE_URL)
    public String deleteComment(@PathVariable("id") Long id) {
        long articleID = commentService.deleteCommentById(id);
        return "redirect:".concat(ARTICLES_URL).concat("/" + articleID).concat(DELETE_SUCCESSFULLY);
    }

    @PostMapping(COMMENT_ADD_URL)
    public String addComment(@AuthenticationPrincipal AppUserPrincipal user,
                             @ModelAttribute("comment") CommentDTO commentDTO) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        commentDTO.setUserDTO(userDTO);
        commentService.addComment(commentDTO);
        return "redirect:".concat(ARTICLES_URL).concat("/" + commentDTO.getArticleID()).concat(ADD_SUCCESSFULLY);
    }
}