package com.astro.smitesolver.bot;

import com.astro.smitesolver.config.PickRateComparator;
import com.astro.smitesolver.discord.entity.totaldata.*;
import com.astro.smitesolver.discord.service.DataFetchingService;
import com.astro.smitesolver.discord.repository.GodNameRepository;
import com.astro.smitesolver.exception.CommandNotFoundException;
import com.astro.smitesolver.exception.UpdateDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class SolverBot {

    @Autowired
    private DataFetchingService dataFetchingService;

    @Autowired
    private GodNameRepository godNameRepository;

    public TotalGodData getRequestedGod(String name, boolean isHighMMR) {
        int godID = godNameRepository.findByName(name).getGodID();
        return dataFetchingService.getPerformanceData(godID, isHighMMR);
    }

    public List<? extends WinRateRanking> getWinRateLeaderboard(boolean highMMR) {
        return dataFetchingService.getWinRates(highMMR);
    }

    public List<? extends PickRateRanking> getPickRateLeaderboard(boolean highMMR) {
        return dataFetchingService.getPickRates(highMMR);
    }

    public List<? extends BanRateRanking> getBanRateLeaderboard(boolean highMMR) {
        return dataFetchingService.getBanRates(highMMR);
    }

    public boolean requestUpdate(int numDays) throws CommandNotFoundException {
        try {
            dataFetchingService.requestUpdate(numDays);
        } catch (UpdateDataException e) {
            throw new CommandNotFoundException("Update could not be requested");
        }
        return false;
    }

    private <T extends Comparable<T>> List<T> sortLeaderboards(List<T> leaderboards) {
        Collections.sort(leaderboards);
        return leaderboards;
    }
}
