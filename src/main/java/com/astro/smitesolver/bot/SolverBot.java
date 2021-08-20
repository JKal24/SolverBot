package com.astro.smitesolver.bot;

import com.astro.smitesolver.discord.entity.totaldata.*;
import com.astro.smitesolver.discord.service.DataFetchingService;
import com.astro.smitesolver.discord.repository.GodNameRepository;
import com.astro.smitesolver.exception.CommandNotFoundException;
import com.astro.smitesolver.exception.GodNotFoundException;
import com.astro.smitesolver.exception.UpdateDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.scheduling.annotation.Scheduled;
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
        try {
            name = name.substring(0, 1).toUpperCase() + name.substring(1);
            int godID = godNameRepository.findByName(name).getGodID();
            return dataFetchingService.getPerformanceData(godID, isHighMMR);
        } catch (InvalidDataAccessResourceUsageException e) {
            throw new GodNotFoundException("Could not find data for: " + name);
        }
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

    @Scheduled(cron = "* * */6 * * ?")
    public void doUpdate() throws CommandNotFoundException {
        try {
            dataFetchingService.requestUpdate(1);
        } catch (UpdateDataException updateDataException) {
            throw new CommandNotFoundException("Update could not be requested");
        }
    }

    // Used for development mode

    public void doUpdate(int numDays) throws CommandNotFoundException {
        try {
            dataFetchingService.requestUpdate(numDays);
        } catch (UpdateDataException updateDataException) {
            throw new CommandNotFoundException("Update could not be requested");
        }
    }
}