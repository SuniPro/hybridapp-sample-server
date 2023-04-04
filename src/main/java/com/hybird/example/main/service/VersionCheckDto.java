package com.hybird.example.main.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VersionCheckDto implements Serializable {
    private String device; //단말종류
    private String version; //설치버전
    @JsonProperty("force_update")
    private Boolean forceUpdate; //강제 업데이트 여부
}
