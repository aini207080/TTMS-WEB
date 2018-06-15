package com.xiyou.ttms.bean;

import java.math.BigDecimal;

public class Play {
    private Integer playId;

    private Integer playTypeId;

    private Integer playLangId;

    private String playName;

    private String playIntroduction;

    private String playImage;

    private Integer playLength;

    private BigDecimal playTicketPrice;

    private Short playStatus;

    public Integer getPlayId() {
        return playId;
    }

    public void setPlayId(Integer playId) {
        this.playId = playId;
    }

    public Integer getPlayTypeId() {
        return playTypeId;
    }

    public void setPlayTypeId(Integer playTypeId) {
        this.playTypeId = playTypeId;
    }

    public Integer getPlayLangId() {
        return playLangId;
    }

    public void setPlayLangId(Integer playLangId) {
        this.playLangId = playLangId;
    }

    public String getPlayName() {
        return playName;
    }

    public void setPlayName(String playName) {
        this.playName = playName == null ? null : playName.trim();
    }

    public String getPlayIntroduction() {
        return playIntroduction;
    }

    public void setPlayIntroduction(String playIntroduction) {
        this.playIntroduction = playIntroduction == null ? null : playIntroduction.trim();
    }

    public String getPlayImage() {
        return playImage;
    }

    public void setPlayImage(String playImage) {
        this.playImage = playImage == null ? null : playImage.trim();
    }

    public Integer getPlayLength() {
        return playLength;
    }

    public void setPlayLength(Integer playLength) {
        this.playLength = playLength;
    }

    public BigDecimal getPlayTicketPrice() {
        return playTicketPrice;
    }

    public void setPlayTicketPrice(BigDecimal playTicketPrice) {
        this.playTicketPrice = playTicketPrice;
    }

    public Short getPlayStatus() {
        return playStatus;
    }

    public void setPlayStatus(Short playStatus) {
        this.playStatus = playStatus;
    }
}