package com.mini.mini_2.openapi.domain.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RestAreaInfoApiResponseDTO {
    @JsonProperty("list")
    private List<RestAreaInfoDTO> list;
    @JsonProperty("code")
    private String code;
    @JsonProperty("message")
    private String message;
    @JsonProperty("count")
    private String count;
    @JsonProperty("pageNo")
    private String pageNo;
    @JsonProperty("numOfRows")
    private String numOfRows;
    @JsonProperty("pageSize")
    private String pageSize;
    @Data
    public static class RestAreaInfoDTO {
        @JsonProperty("svarAddr")
        private String svarAddr;
        @JsonProperty("routeNm")
        private String routeNm;
        @JsonProperty("hdqrNm")
        private String hdqrNm;
        @JsonProperty("mtnofNm")
        private String mtnofNm;
        @JsonProperty("svarCd")
        private String svarCd;
        @JsonProperty("svarNm")
        private String svarNm;
        @JsonProperty("svarGsstClssNm")
        private String svarGsstClssNm;
        @JsonProperty("gudClssNm")
        private String gudClssNm;
        @JsonProperty("cocrPrkgTrcn")
        private String cocrPrkgTrcn;
        @JsonProperty("fscarPrkgTrcn")
        private String fscarPrkgTrcn;
        @JsonProperty("dspnPrkgTrcn")
        private String dspnPrkgTrcn;
        @JsonProperty("rprsTelNo")
        private String rprsTelNo;
    }
}
