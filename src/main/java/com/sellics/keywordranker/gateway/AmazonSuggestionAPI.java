package com.sellics.keywordranker.gateway;

import com.google.gson.Gson;
import com.sellics.keywordranker.model.Suggestion;
import com.sellics.keywordranker.model.Suggestions;
import kong.unirest.Unirest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AmazonSuggestionAPI {

    //private final String BASE_URL = "https://completion.amazon.com/search/complete";
    private final String BASE_URL = "https://completion.amazon.com/api/2017/suggestions";

    public Suggestions getSuggestions(String prefix) {
        String json = Unirest.get(BASE_URL)
                .queryString("session-id", "144-8614407-7888854")
                .queryString("customer-id", "AEFZ6T6X987TJ")
                .queryString("request-id", "STAJ1XKWFTQW32XWY628")
                .queryString("page-type", "Gateway")
                .queryString("lop", "en_US")
                .queryString("site-variant", "desktop")
                .queryString("client-info", "amazon-search-ui")
                .queryString("mid", "ATVPDKIKX0DER")
                .queryString("alias", "aps")
                .queryString("b2b", 0)
                .queryString("fresh", 0)
                .queryString("ks", 65)
                .queryString("prefix", prefix)
                .queryString("event", "onKeyPress")
                .queryString("limit", 10)
                .queryString("fb", 1)
                .queryString("suggestion-type", "KEYWORD")
                .queryString("suggestion-type", "WIDGET")
                .queryString("_", "1582106228841")
                .asString().getBody();

        return new Gson().fromJson(json, Suggestions.class);
    }
}
