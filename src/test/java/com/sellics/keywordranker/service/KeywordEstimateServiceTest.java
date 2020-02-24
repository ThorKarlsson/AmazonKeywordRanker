package com.sellics.keywordranker.service;

import com.sellics.keywordranker.gateway.AmazonSuggestionAPI;
import org.junit.Test;

public class KeywordEstimateServiceTest {

    @Test
    public void estimateValueOfKeyword() {
        KeywordEstimateService estimator = new KeywordEstimateService(new AmazonSuggestionAPI());

        estimator.estimateValueOfKeyword("tv");
        estimator.estimateValueOfKeyword("pics peanut butter");
        estimator.estimateValueOfKeyword("tb12");
        estimator.estimateValueOfKeyword("airpods");
        estimator.estimateValueOfKeyword("paw paw ointment");
        estimator.estimateValueOfKeyword("rabokki");
        estimator.estimateValueOfKeyword("qwddeco");
        estimator.estimateValueOfKeyword("settlers of catan");
        estimator.estimateValueOfKeyword("animal crossing new horizons");
        estimator.estimateValueOfKeyword("animal crossing new leaf");
        estimator.estimateValueOfKeyword("alto sax reeds");

    }
}
