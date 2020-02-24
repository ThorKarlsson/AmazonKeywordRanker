package com.sellics.keywordranker.service;

import com.sellics.keywordranker.gateway.AmazonSuggestionAPI;
import com.sellics.keywordranker.model.Suggestions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@Slf4j
public class KeywordEstimateService {

    private AmazonSuggestionAPI amazonSuggester;
    private static final int NO_OF_SUGGESTIONS = 10;
    private String[] nextChar = {" ", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};

    @Autowired
    public KeywordEstimateService(AmazonSuggestionAPI amazonSuggester) {
        this.amazonSuggester = amazonSuggester;
    }

    public int estimateValueOfKeyword(String keyword) {
        log.info("Estimating value of {}", keyword);
        String prefix = "";
        int distance = keyword.length();
        for(char letter : keyword.toCharArray()) {
            prefix += letter;
            if(isHit(amazonSuggester.getSuggestions(prefix), keyword)) {
                break;
            }
            distance--;
        }
        int score = ((distance * 100) / keyword.length());
        int depthBonus = measureDepth(prefix, keyword);
        int breadthBonus = measureBreadth(prefix);
        int finalScore = (((depthBonus + breadthBonus * 4) / 5) + score) / 2;

        log.info("Initial score: {}", score);
        log.info("Depth bonus: {}", depthBonus);
        log.info("Breadth bonus: {}", breadthBonus);
        log.info("Final score: {}", finalScore);

        return Math.min(finalScore, 100);
    }

    private int measureBreadth(String prefix) {
        int breadth = Arrays.stream(nextChar)
                .parallel()
                .mapToInt(x -> (int) amazonSuggester.getSuggestions(prefix + x).getSuggestions().stream().filter(s -> !s.isSpellCorrected()).count())
                .sum();
        return Math.min((breadth * 100) / (nextChar.length * NO_OF_SUGGESTIONS), 100);
    }

    private int measureDepth(String prefix, String keyword) {
        //Handle the case where there is no suffix because only the keyword itself had a match e.g. "tv"
        if(prefix.equals(keyword)) {
            return Math.min((int) ((amazonSuggester.getSuggestions(keyword).getSuggestions().stream().filter(s -> !s.isSpellCorrected()).count() * 100) / 10), 100);
        }

        String suffix = keyword.substring(prefix.length());
        int depth = 0;
        for(char x : suffix.toCharArray()) {
            depth += amazonSuggester.getSuggestions(prefix).getSuggestions().stream().filter(s -> !s.isSpellCorrected()).count();
            prefix += x;
        }
        return Math.min((depth * 100) / (suffix.length() * 11), 100);
    }

    private boolean isHit(Suggestions suggestions, String keyword) {
        return suggestions.getSuggestions().stream()
                .anyMatch(suggestion -> suggestion.getValue().equals(keyword));
    }
}
