package com.astro.smitesolver.discord.entity.totaldata;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class PickRateRanking {

    @Id
    private Integer godID;

    private String godName;

    private double pickRate;

    public PickRateRanking(Integer godID, String godName, double pickRate) {
        this.godID = godID;
        this.godName = godName;
        this.pickRate = pickRate;
    }

    public PickRateRanking() {
    }

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

    public double getPickRate() {
        return pickRate;
    }

    public void setPickRate(double pickRate) {
        this.pickRate = pickRate;
    }
}
