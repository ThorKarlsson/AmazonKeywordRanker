package com.sellics.keywordranker.controllers;

import com.sellics.keywordranker.model.Result;
import com.sellics.keywordranker.service.KeywordEstimateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KeywordEstimateController {


    private final KeywordEstimateService keywordEstimateService;

    @Autowired
    public KeywordEstimateController(KeywordEstimateService keywordEstimateService) {
        this.keywordEstimateService = keywordEstimateService;
    }

    @GetMapping("/estimate")
    public Result getEstimate(@RequestParam String keyword) {
        return Result.builder()
                .keyword(keyword)
                .score(keywordEstimateService.estimateValueOfKeyword(keyword))
                .build();
    }
}
