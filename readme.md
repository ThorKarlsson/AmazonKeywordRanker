# Amazon Keyword Ranker

Endpoint accepts keyword and outputs the estimated popularity of the keyword from 0 - 100

```
REQUEST GET `/estimate?keyword=<keyword>` 

RESPONSE
{
    "keyword": <keyword>",
    "score": <value [0 - 100]>
}
```

# How does the algorithm work?
	
To estimate the popularity of a particular keyword I looked at 3 different things. First the number of characters, or distance, it took to get a match. The number of characters remaining in a keyword was then divided by the length of the keyword and multiplied by 100 to get an initial score. As an example, “airpods” gets a match right off the bat with the letter a. “Airpods” is a 7 letter word and as there were no misses, the initial score is 100.

Next we look at the density of keywords around where the initial match was found. To do this, we take the prefix that got a hit, and then do a search for every possible letter or symbol directly after the prefix in a breadth search. In the previous example our prefix hit was "a". So we do a search for "aa", "ab", "ac" and so on. By checking the number of suggestions for each neighboring prefix we can see the volume of possible keywords that the prefix match was contending with. Depending on how high or low the volume is, either a penalty or a bonus can be applied to the initial score. We can assume if the volume is low, that an early prefix match would be due to a low competitive space. 

Finally we look at the volume of results in a depth search starting from the prefix match, down to the entire keyword. So for airpods we would measure the volume of "ai", "air", "airp" etc.

These two numbers we call breadth bonus and depth bonus. The idea here is to get an estimate of how many keywords our particular keyword is contending with. The breadth bonus is given a higher weight than the depth bonus as it is a more exhaustive search and also because depth can be deceptive in that certain keywords can have many permutations while still relating to the same product. A good example of this would be the keyword "qwddeco". While there are virtually no other keywords in the breadth search, a depth search will yield many different versions of the same keyword inflating the score.

**Assumptions**

I've assumed that different amazon query parameters will not significantly affect the results from the suggester. This includes mobile vs desktop, changing the userid from time to time and changing the language.    


**Do you think the hint that we gave you earlier is correct and if so - why?**

Yes. Initially I thought it wasn't correct because in the response there is a parameter `shuffled: false` which I assumed would mean it is ordered by rank. However in using the API, I noticed the ordering would shuffle from letter to letter.  



**How precise do you think your outcome is and why?**

I think the outcome is precise relative to other keywords in the same letter group. However the biggest drawback I see is not being able to accurately compare across the alphabet. For example I would assume "airpods" ranks higher than "Zinc supplement" but they both score high in their own letter group and have similar scores. I think a more accurate program would do a more exhaustive search and scraping more keywords and saving results in a database. I think it would also be interesting to factor in spell checked results. My assumption there would be if a keyword is spell checked suggested, then it is more popular. However this would require some kind of fuzzy misspelling keyword predictor.      
