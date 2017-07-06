package sk.kadlecek.productsemantizer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import sk.kadlecek.productsemantizer.bean.KnowledgeBase;
import sk.kadlecek.productsemantizer.exception.DatabaseException;
import sk.kadlecek.productsemantizer.service.KnowledgeBaseService;

import java.util.List;

@RestController
@RequestMapping("/knowledge-bases")
public class KnowledgeBaseController extends AbstractController {

    private final KnowledgeBaseService knowledgeBaseService;

    @Autowired
    public KnowledgeBaseController(KnowledgeBaseService knowledgeBaseService) {
        Assert.notNull(knowledgeBaseService, "KnowledgeBaseService must not be null!");
        this.knowledgeBaseService = knowledgeBaseService;
    }

    @GetMapping("/active")
    public @ResponseBody List<KnowledgeBase> findAllActive() throws DatabaseException {
        return knowledgeBaseService.findAllActive();
    }

}
