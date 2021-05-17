package com.astro.smitesolver.config;

import com.astro.smitesolver.discord.entity.totaldata.WinRateRanking;

import java.util.Comparator;

public class WinRateComparator<T extends WinRateRanking> implements Comparator<T> {
    @Override
    public int compare(T o1, T o2) {
        return Double.compare(o1.getWinRate(), o2.getWinRate());
    }
}
