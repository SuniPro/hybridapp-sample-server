package com.hybird.example.user.payMent.vo;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberBasketVO implements Serializable {
    private String memberId;
    private String moveType;
    private String unitPackageType;
    private String ticketSeq;
    private Integer ticketCount;
}
