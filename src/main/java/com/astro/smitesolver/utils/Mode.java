package com.astro.smitesolver.utils;

public enum Mode {
    ARENA(435),
    JOUST(448),
    CONQUEST(426),
    ASSAULT(445),
    CLASH(466),
    CONQUEST_LEAGUE(451),
    JOUST_LEAGUE(450),
    MOTD(434),
    JOUST_CUSTOM(441),
    SIEGE(459),
    DUEL(440),
    ARENA_AI_MEDIUM(468),
    JOUST_AI_MEDIUM(456),
    ARENA_TUTORIAL(462),
    ARENA_CUSTOM(438),
    CONQUEST_CUSTOM(429),
    CONQUEST_AI_MEDIUM(461),
    ARENA_AI_VERY_EASY(457),
    CONQUEST_AI_EASY(476),
    JOUST_AI_EASY(474),
    CONQUEST_LEAGUE_CONTROLLER(504),
    ARENA_PRACTICE_MEDIUM(472),
    JOUST_LEAGUE_CONTROLLER(503),
    ASSAULT_CUSTOM(446),
    ASSAULT_AI_MEDIUM(454),
    JOUST_PRACTICE_MEDIUM(473),
    ARENA_PRACTICE_EASY(443),
    CLASH_CUSTOM(467),
    CLASH_AI_MEDIUM(469),
    ASSAULT_AI_EASY(481),
    SIEGE_CUSTOM(460),
    CONQUEST_PRACTICE_MEDIUM(475),
    JOUST_PRACTICE_EASY(464),
    DUEL_CONTROLLER(502),
    CONQUEST_PRACTICE_EASY(458),
    CLASH_AI_EASY(478),
    ASSAULT_PRACTICE_MEDIUM(480),
    ASSAULT_PRACTICE_EASY(479),
    CLASH_PRACTICE_MEDIUM(477),
    CLASH_PRACTICE_EASY(470),
    CLASH_TUTORIAL(471),
    BASIC_TUTORIAL(436);

    private Integer mode;

    Mode(Integer identifier) { this.mode = identifier; }

    public String getModeID() { return mode.toString(); }
}
