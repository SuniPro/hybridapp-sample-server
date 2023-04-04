package com.hybird.example.user.payMent.util;

import lombok.Getter;

@Getter
public enum CreditCard {
    CARD_047("08", "047", "롯데카드")
    , CARD_026("01", "026", "BC카드")
    , CARD_016("02", "016", "국민카드")
    , CARD_027("07", "027", "현대카드")
    , CARD_008("03", "008", "하나카드")
    , CARD_043("12", "043", "NH카드")
    , CARD_029("06", "029", "신한카드")
    , CARD_031("04", "031", "삼성카드")
    , CARD_044("40", "044", "카카오페이")
    , CARD_045("42", "045", "네이버페이")
    , CARD_025("ETC", "025", "기타카드");

    private String niceCardCode;
    private String cardCode;
    private String cardName;

    CreditCard(String niceCardCode, String cardCode, String cardName) {
        this.niceCardCode = niceCardCode;
        this.cardCode = cardCode;
        this.cardName = cardName;
    }

    public static String getCardCode(final String niceCardCode) {
        if (niceCardCode == null) {
            return CreditCard.CARD_025.getCardCode();
        }
        for (CreditCard card : values()) {
            if (niceCardCode.equals(card.getNiceCardCode())) {
                return card.getCardCode();
            }
        }

        return CreditCard.CARD_025.getCardCode();
    }

    public static String getCardName(final String niceCardCode) {
        if (niceCardCode == null) {
            return CreditCard.CARD_025.getCardName();
        }
        for (CreditCard card : values()) {
            if (niceCardCode.equals(card.getNiceCardCode())) {
                return card.getCardName();
            }
        }

        return CreditCard.CARD_025.getCardName();
    }
}
