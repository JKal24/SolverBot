package com.astro.smitesolver.discord.entity.totaldata;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity(name = "pick_rate_ranking_high_mmr")
public class PickRateRankingHighMMR extends PickRateRanking {
    public PickRateRankingHighMMR(Integer godID, String godName, double pickRate) {
        super(godID, godName, pickRate);
    }

    public PickRateRankingHighMMR() {
    }
}
