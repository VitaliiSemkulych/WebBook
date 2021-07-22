package com.example.demo.character;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

// create list with english alphabet letter
public class Letter {
    private static final List<Character> characterList;

    static {
        characterList = IntStream.rangeClosed('A', 'Z')
                .mapToObj(i -> (char)i)
                .collect(Collectors.toList());
    }

    public static List<Character> getLetterList() {
        return characterList;
    }
}
