package com.astro.smitesolver.discord.entity.totaldata;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class WinRateRanking {
    @Id
    private Integer godID;

    private String godName;

    private double winRate;

    public WinRateRanking(Integer godID, String godName, double winRate) {
        this.godID = godID;
        this.godName = godName;
        this.winRate = winRate;
    }

    public WinRateRanking() { }

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

    public double getWinRate() {
        return winRate;
    }

    public void setWinRate(double winRate) {
        this.winRate = winRate;
    }
}
