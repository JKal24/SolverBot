package com.astro.smitesolver.utils;

public enum Commands {
    help("Commands"),
    stats("God Statistics"),
    low("low"),
    update("Update Status"),
    winrate("Win-rate Leaderboard"),
    pickrate("Pick-rate Leaderboard"),
    banrate("Ban-rate Leaderboard");

    public final String description;

    public String getDescription() {
        return this.description;
    }

    private Commands(String description) {
        this.description = description;
    }
}
