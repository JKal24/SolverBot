package com.astro.smitesolver.discord.entity.totaldata;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class BanRateRanking {

    @Id
    private Integer godID;

    private String godName;

    private double banRate;

    public BanRateRanking(Integer godID, String godName, double banRate) {
        this.godID = godID;
        this.godName = godName;
        this.banRate = banRate;
    }

    public BanRateRanking() { }

    public Integer getGodID() {
        return godID;
    }

    public void setGodID(Integer godID) {
        this.godID = godID;
    }

    public String getGodName() {
        return godName;
    }

    public void setGodName(String godName) {
        this.godName = godName;
    }

    public double getBanRate() {
        return banRate;
    }

    public void setBanRate(double banRate) {
        this.banRate = banRate;
    }

}
