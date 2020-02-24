package com.sellics.keywordranker.model;

import lombok.Data;

@Data
public class Suggestion {
    private String value;
    private boolean spellCorrected;
}
