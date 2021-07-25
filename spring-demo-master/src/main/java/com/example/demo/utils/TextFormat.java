
package com.example.demo.utils;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class TextFormat {

    public static String formatText(String text) {
        final AtomicInteger counter = new AtomicInteger(0);
        return text.chars().mapToObj(c -> (char) c).collect(Collectors.groupingBy(s -> counter.getAndIncrement()/17)).values()
                .stream().map(characters ->
                        characters.stream().map(String::valueOf).collect(Collectors.joining())+"\n")
                .collect(Collectors.joining());
    }
    
}
