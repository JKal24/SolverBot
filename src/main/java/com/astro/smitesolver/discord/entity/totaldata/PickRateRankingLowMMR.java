package com.astro.smitesolver.discord.entity.totaldata;

import jakarta.persistence.Entity;

@Entity(name = "pick_rate_ranking_low_mmr")
public class PickRateRankingLowMMR extends PickRateRanking{
    public PickRateRankingLowMMR(Integer godID, String godName, double pickRate) {
        super(godID, godName, pickRate);
    }

    public PickRateRankingLowMMR() {
    }
}
