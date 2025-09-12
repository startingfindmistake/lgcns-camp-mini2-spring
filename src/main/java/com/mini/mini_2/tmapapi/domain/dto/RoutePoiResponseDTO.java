package com.mini.mini_2.tmapapi.domain.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class RoutePoiResponseDTO {

    @JsonProperty("searchPoiInfo")
    private SearchPoiInfo searchPoiInfo;

    @Data
    public static class SearchPoiInfo {
        @JsonProperty("totalCount")
        private String totalCount;

        @JsonProperty("contFlag")
        private String contFlag;

        @JsonProperty("pois")
        private Pois pois;
    }

    @Data
    public static class Pois {
        @JsonProperty("poi")
        private List<Poi> poi;
    }

    @Data
    public static class Poi {
        @JsonProperty("id")
        private String id;

        @JsonProperty("pkey")
        private String pkey;

        @JsonProperty("name")
        private String name;

        @JsonProperty("orgName")
        private String orgName;

        @JsonProperty("frontLat")
        private String frontLat;

        @JsonProperty("frontLon")
        private String frontLon;

        @JsonProperty("centerLat")
        private String centerLat;

        @JsonProperty("centerLon")
        private String centerLon;

        @JsonProperty("famousFoodYn")
        private String famousFoodYn;

        @JsonProperty("classCd")
        private String classCd;

        @JsonProperty("classCdA")
        private String classCdA;

        @JsonProperty("classCdB")
        private String classCdB;

        @JsonProperty("classCdC")
        private String classCdC;

        @JsonProperty("classCdD")
        private String classCdD;

        @JsonProperty("addr")
        private String addr;

        @JsonProperty("roadName")
        private String roadName;

        @JsonProperty("radius")
        private String radius;

        @JsonProperty("visitCountTotal")
        private String visitCountTotal;

        @JsonProperty("visitCount3Month")
        private String visitCount3Month;

        @JsonProperty("parkYn")
        private String parkYn;

        @JsonProperty("srchParkType")
        private String srchParkType;

        @JsonProperty("srchParkDtlType")
        private String srchParkDtlType;

        @JsonProperty("srchParkAbleNum")
        private String srchParkAbleNum;

        @JsonProperty("srchParkTotNum")
        private String srchParkTotNum;

        @JsonProperty("fastEvChargerAvailableCount")
        private String fastEvChargerAvailableCount;

        @JsonProperty("fastEvChargerTotalCount")
        private String fastEvChargerTotalCount;

        @JsonProperty("fastEvChargerYn")
        private String fastEvChargerYn;

        @JsonProperty("normalEvChargerAvailableCount")
        private String normalEvChargerAvailableCount;

        @JsonProperty("normalEvChargerTotalCount")
        private String normalEvChargerTotalCount;

        @JsonProperty("normalEvChargerYn")
        private String normalEvChargerYn;

        @JsonProperty("ugrndYn")
        private String ugrndYn;

        @JsonProperty("dataKind")
        private String dataKind;

        @JsonProperty("groupSubLists")
        private GroupSubLists groupSubLists;

        @JsonProperty("evChargers")
        private EvChargers evChargers;
    }

    @Data
    public static class GroupSubLists {
        @JsonProperty("groupSub")
        private List<GroupSub> groupSub;
    }

    @Data
    public static class GroupSub {
        @JsonProperty("subPkey")
        private String subPkey;

        @JsonProperty("subSeq")
        private String subSeq;

        @JsonProperty("subName")
        private String subName;

        @JsonProperty("subCenterY")
        private String subCenterY;

        @JsonProperty("subCenterX")
        private String subCenterX;

        @JsonProperty("subNavY")
        private String subNavY;

        @JsonProperty("subNavX")
        private String subNavX;

        @JsonProperty("subRpFlag")
        private String subRpFlag;

        @JsonProperty("subPoiId")
        private String subPoiId;

        @JsonProperty("subNavSeq")
        private String subNavSeq;

        @JsonProperty("subParkYn")
        private String subParkYn;

        @JsonProperty("subSrchParkType")
        private String subSrchParkType;

        @JsonProperty("subSrchParkDtlType")
        private String subSrchParkDtlType;

        @JsonProperty("subSrchParkAbleNum")
        private String subSrchParkAbleNum;

        @JsonProperty("subClassCd")
        private String subClassCd;

        @JsonProperty("subClassNmA")
        private String subClassNmA;

        @JsonProperty("subClassNmB")
        private String subClassNmB;

        @JsonProperty("subClassNmC")
        private String subClassNmC;

        @JsonProperty("subClassNmD")
        private String subClassNmD;

        @JsonProperty("subFastEvChargerAvailableCount")
        private String subFastEvChargerAvailableCount;

        @JsonProperty("subFastEvChargerTotalCount")
        private String subFastEvChargerTotalCount;

        @JsonProperty("subFastEvChargerYn")
        private String subFastEvChargerYn;

        @JsonProperty("subNormalEvChargerAvailableCount")
        private String subNormalEvChargerAvailableCount;

        @JsonProperty("subNormalEvChargerTotalCount")
        private String subNormalEvChargerTotalCount;

        @JsonProperty("subNormalEvChargerYn")
        private String subNormalEvChargerYn;
    }

    @Data
    public static class EvChargers {
        @JsonProperty("evCharger")
        private List<EvCharger> evCharger;
    }

    @Data
    public static class EvCharger {
        @JsonProperty("operatorId")
        private String operatorId;

        @JsonProperty("stationId")
        private String stationId;

        @JsonProperty("chargerId")
        private String chargerId;

        @JsonProperty("status")
        private String status;

        @JsonProperty("type")
        private String type;

        @JsonProperty("powerType")
        private String powerType;

        @JsonProperty("operatorName")
        private String operatorName;

        @JsonProperty("chargingDateTime")
        private String chargingDateTime;

        @JsonProperty("updateDateTime")
        private String updateDateTime;

        @JsonProperty("isFast")
        private String isFast;

        @JsonProperty("isAvailable")
        private String isAvailable;
    }
}