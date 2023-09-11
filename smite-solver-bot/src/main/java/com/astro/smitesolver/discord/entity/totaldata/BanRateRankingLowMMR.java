package com.astro.smitesolver.discord.entity.totaldata;

import jakarta.persistence.Entity;

@Entity(name = "ban_rate_ranking_low_mmr")
public class BanRateRankingLowMMR extends BanRateRanking {
    public BanRateRankingLowMMR(Integer godID, String godName, double banRate) {
        super(godID, godName, banRate);
    }

    public BanRateRankingLowMMR() {
    }
}
