package com.mini.mini_2.openai.domain.dto;



import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true) // json에 정의되지 않은 필드는 무시 
public class ChatResponseDTO {

    private String restArea ; 
    private List<Recommend> recommends ; 

    @Builder
    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    @JsonIgnoreProperties(ignoreUnknown = true) // json에 정의되지 않은 필드는 무시 
    public static class Recommend {
        private String name ;  
        private String food ;
        private String reason ; 
    }
}
