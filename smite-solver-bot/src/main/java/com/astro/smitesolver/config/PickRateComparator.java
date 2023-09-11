package com.astro.smitesolver.config;

import com.astro.smitesolver.discord.entity.totaldata.PickRateRanking;

import java.util.Comparator;

public class PickRateComparator<T extends PickRateRanking> implements Comparator<T> {
    @Override
    public int compare(T o1, T o2) {
        return Double.compare(o2.getPickRate(), o1.getPickRate());
    }
}
