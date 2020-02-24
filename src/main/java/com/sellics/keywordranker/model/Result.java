package com.sellics.keywordranker.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Result {
    private String keyword;
    private int score;
}
