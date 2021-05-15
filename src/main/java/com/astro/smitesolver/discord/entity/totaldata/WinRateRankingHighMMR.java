package com.astro.smitesolver.discord.entity.totaldata;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "win_rate_ranking_high_mmr")
public class WinRateRankingHighMMR extends WinRateRanking {

    public WinRateRankingHighMMR(Integer godID, String godName, double winRate) {
        super(godID, godName, winRate);
    }

    public WinRateRankingHighMMR() {
    }
}
