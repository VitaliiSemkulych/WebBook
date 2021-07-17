/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.utils;


import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


/**
 *
 * @author Admin
 */
// dividing text by 17 character in line for boxing in single recension area
public class TextFormat {

    public static String formatText(String text) {
        final AtomicInteger counter = new AtomicInteger(0);
        return text.chars().mapToObj(c -> (char) c).collect(Collectors.groupingBy(s -> counter.getAndIncrement()/17)).values()
                .stream().map(characters ->
                        characters.stream().map(String::valueOf).collect(Collectors.joining())+"\n")
                .collect(Collectors.joining());
    }
    
}
