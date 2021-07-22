package com.example.demo.dto;

import com.example.demo.enums.SearchType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SearchByPhraseDTO {
    private String searchPhrase;
    private SearchType searchType;
}
