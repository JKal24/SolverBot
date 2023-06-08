package com.astro.smitesolver.discord.entity.totaldata;

import jakarta.persistence.Entity;

@Entity(name = "win_rate_ranking_low_mmr")
public class WinRateRankingLowMMR extends WinRateRanking {

    public WinRateRankingLowMMR(Integer godID, String godName, double winRate) {
        super(godID, godName, winRate);
    }

    public WinRateRankingLowMMR() {
    }
}
