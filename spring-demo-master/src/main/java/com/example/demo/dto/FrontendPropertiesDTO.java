package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class FrontendPropertiesDTO {
    private boolean isSettingsPageUnderReview;
    private boolean isUserPageUnderReview;
    private boolean isAddBookPageUnderReview;
    private boolean isAddAuthorPageUnderReview;
    private boolean isAddGenrePageUnderReview;
    private boolean isAddPublisherPageUnderReview;
    private String genreName;
    private Character letter;
    private SearchByPhraseDTO searchByPhrase;

    public static FrontendPropertiesDTO getFrontendProperties(String genreName, Character character,
                                                                     SearchByPhraseDTO searchByPhrase,
                                                                     boolean isSettingsPageUnderReview,
                                                                     boolean isUserPageUnderReview,
                                                                     boolean isAddBookPageUnderReview,
                                                              boolean isAddAuthorPageUnderReview,
                                                              boolean isAddGenrePageUnderReview,
                                                              boolean isAddPublisherPageUnderReview){

        return FrontendPropertiesDTO.builder()
                .genreName(genreName)
                .letter(character)
                .searchByPhrase(searchByPhrase)
                .isSettingsPageUnderReview(isSettingsPageUnderReview)
                .isUserPageUnderReview(isUserPageUnderReview)
                .isAddBookPageUnderReview(isAddBookPageUnderReview)
                .isAddAuthorPageUnderReview(isAddAuthorPageUnderReview)
                .isAddGenrePageUnderReview(isAddGenrePageUnderReview)
                .isAddPublisherPageUnderReview(isAddPublisherPageUnderReview)
                .build();
    }
}
