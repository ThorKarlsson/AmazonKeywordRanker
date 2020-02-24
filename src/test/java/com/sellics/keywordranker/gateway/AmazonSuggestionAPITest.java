package com.sellics.keywordranker.gateway;

import org.junit.Test;


public class AmazonSuggestionAPITest {

    @Test
    public void getSuggestions() {
        var api = new AmazonSuggestionAPI();

        api.getSuggestions("animal crossing new horizon");
    }
}
