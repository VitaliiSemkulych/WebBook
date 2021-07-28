package com.example.demo.utils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CollectionConvector {

    public final static <T> List<T> IterableToList(Iterable<T> iterable){
        return StreamSupport
                .stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }

}
