package com.astro.smitesolver.discord.entity.totaldata;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "ban_rate_ranking_high_mmr")
public class BanRateRankingHighMMR extends BanRateRanking {
    public BanRateRankingHighMMR(Integer godID, String godName, double banRate) {
        super(godID, godName, banRate);
    }

    public BanRateRankingHighMMR() {
    }
}
