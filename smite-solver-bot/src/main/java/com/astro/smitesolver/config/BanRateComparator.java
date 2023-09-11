package com.astro.smitesolver.config;

import com.astro.smitesolver.discord.entity.totaldata.BanRateRanking;

import java.util.Comparator;

public class BanRateComparator<T extends BanRateRanking> implements Comparator<T> {
    @Override
    public int compare(T o1, T o2) {
        return Double.compare(o2.getBanRate(), o1.getBanRate());
    }
}
