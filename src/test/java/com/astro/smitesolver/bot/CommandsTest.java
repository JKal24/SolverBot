package com.astro.smitesolver.bot;

import com.astro.smitesolver.discord.entity.totaldata.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CommandsTest {

    @Autowired
    SolverBot bot;

//    @Test
//    public void getGod1() {
//        TotalGodData godData = bot.getRequestedGod("Sol", true);
//
//        // Must be a win rate between 1.0 and 0.0 unless pick rate is 0, then it is 0.0.
//
//        assertTrue((godData.getMovingPickRate() == 0 && godData.getMovingWinRate() == 0) ||
//                (godData.getMovingPickRate() != 0 && godData.getMovingWinRate() >= 0 && godData.getMovingWinRate() <= 1.0));
//    }

    @Test
    public void getLeaderboard1() {

        List<WinRateRankingHighMMR> winRateLeaderboard = (List<WinRateRankingHighMMR>) bot.getWinRateLeaderboard(true);

        for (WinRateRankingHighMMR winRateRankingHighMMR : winRateLeaderboard) {
            assertTrue(winRateRankingHighMMR.getWinRate() >= 0.0);
        }
    }

    @Test
    public void getLeaderboard2() {
        List<BanRateRankingLowMMR> banRateRankingLowMMRS = (List<BanRateRankingLowMMR>) bot.getBanRateLeaderboard(false);

        for (BanRateRankingLowMMR banRateRankingLowMMR : banRateRankingLowMMRS) {
            assertTrue(banRateRankingLowMMR.getBanRate() >= 0.0);
        }
    }

    @Test
    public void getLeaderboard3() {
        List<PickRateRankingHighMMR> pickRateLeaderboard = (List<PickRateRankingHighMMR>) bot.getPickRateLeaderboard(true);

        for (PickRateRankingHighMMR pickRateRankingHighMMR : pickRateLeaderboard) {
            assertTrue(pickRateRankingHighMMR.getPickRate() >= 0.0);
        }
    }

}
