package com.astro.smitesolver.discord.service;

import com.astro.smitesolver.discord.entity.auxillary.GodName;
import com.astro.smitesolver.discord.entity.dailydata.DailyGodData;
import com.astro.smitesolver.discord.entity.dailydata.DailyGodDataHighMMR;
import com.astro.smitesolver.discord.entity.dailydata.DailyGodDataLowMMR;
import com.astro.smitesolver.discord.entity.totaldata.*;
import com.astro.smitesolver.exception.GodNotFoundException;
import com.astro.smitesolver.discord.repository.*;
import com.astro.smitesolver.discord.repository.RecordedMatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class DataCompilationService {

    private final static Logger LOGGER = Logger.getLogger(DataCompilationService.class.getName());

    @Autowired
    private RecordedMatchRepository recordedMatchRepository;

    @Autowired
    private DailyLowMMRDailyGodDataRepository dailyLowMMRGodDataRepository;

    @Autowired
    private DailyHighMMRDailyGodDataRepository dailyHighMMRGodDataRepository;

    @Autowired
    private HighMMRPerformanceRepository highMMRPerformanceRepository;

    @Autowired
    private LowMMRPerformanceRepository lowMMRPerformanceRepository;

    @Autowired
    private HighMMRBanRateRepository highMMRBanRateRepository;

    @Autowired
    private HighMMRPickRateRepository highMMRPickRateRepository;

    @Autowired
    private HighMMRWinRateRepository highMMRWinRateRepository;

    @Autowired
    private LowMMRBanRateRepository lowMMRBanRateRepository;

    @Autowired
    private LowMMRPickRateRepository lowMMRPickRateRepository;

    @Autowired
    private LowMMRWinRateRepository lowMMRWinRateRepository;

    @Autowired
    private UpdateService updateService;

    public void configureGodData(Map<Integer, DailyGodDataHighMMR> highMMRMap, Map<Integer, DailyGodDataLowMMR> lowMMRMap, int highMMRMatchesCount, int lowMMRMatchesCount) {
        // Gets the totalMatches played for high and low mmr and filters by deletion date and new patch date

        LocalDate deletionDate = LocalDate.ofInstant(Instant.now(), ZoneId.of("UTC")).minusDays(UpdateService.DATA_DELETION_DAY_LIMIT);
        LocalDate patchDate = updateService.getVersionUpdateDate();

        int totalMatchesHighMMR = 0;
        int totalMatchesLowMMR = 0;

        int newPatchMatchesHighMMR = 0;
        int newPatchMatchesLowMMR = 0;

        for (MatchRecordedData data : recordedMatchRepository.findAll()) {
            if (data.getDate().isBefore(deletionDate)) {
                recordedMatchRepository.delete(data);
            } else {
                totalMatchesHighMMR += data.getMatchesPlayedHighMMR();
                totalMatchesLowMMR += data.getMatchesPlayedLowMMR();
                if (data.getDate().isAfter(patchDate)) {
                    newPatchMatchesHighMMR += data.getMatchesPlayedHighMMR();
                    newPatchMatchesLowMMR += data.getMatchesPlayedLowMMR();
                }
            }
        }

        deleteHighMMRGodData(deletionDate, patchDate, totalMatchesHighMMR, newPatchMatchesHighMMR);
        deleteLowMMRData(deletionDate, patchDate, totalMatchesLowMMR, newPatchMatchesLowMMR);

        for (GodName godName : updateService.getGods()) {
            int godID = godName.getGodID();

            DailyGodDataHighMMR dataHighMMR = highMMRMap.get(godID);
            DailyGodDataLowMMR dataLowMMR = lowMMRMap.get(godID);

            try {
                dailyHighMMRGodDataRepository.save(dataHighMMR);
                dailyLowMMRGodDataRepository.save(dataLowMMR);

                addHighMMRGodData(godID, dataHighMMR, totalMatchesHighMMR + highMMRMatchesCount,
                        newPatchMatchesHighMMR + highMMRMatchesCount);

                addLowMMRGodData(godID, dataLowMMR, totalMatchesLowMMR + lowMMRMatchesCount,
                        newPatchMatchesLowMMR + lowMMRMatchesCount);
            } catch (InvalidDataAccessApiUsageException e) {
                LOGGER.log(Level.WARNING, "Could not detect any daily data for, " + godName.getGodName());
            }
        }
    }

    public void configureLeaderboards() {
        for (TotalGodDataHighMMR data : highMMRPerformanceRepository.findAll()) {
            highMMRWinRateRepository.save(new WinRateRankingHighMMR(data.getGodID(), data.getGodName(), data.getNewPatchWinRate()));
            highMMRPickRateRepository.save(new PickRateRankingHighMMR(data.getGodID(), data.getGodName(), data.getNewPatchPickRate()));
            highMMRBanRateRepository.save(new BanRateRankingHighMMR(data.getGodID(), data.getGodName(), data.getNewPatchBanRate()));
        }

        for (TotalGodDataLowMMR data : lowMMRPerformanceRepository.findAll()) {
            lowMMRWinRateRepository.save(new WinRateRankingLowMMR(data.getGodID(), data.getGodName(), data.getNewPatchWinRate()));
            lowMMRPickRateRepository.save(new PickRateRankingLowMMR(data.getGodID(), data.getGodName(), data.getNewPatchPickRate()));
            lowMMRBanRateRepository.save(new BanRateRankingLowMMR(data.getGodID(), data.getGodName(), data.getNewPatchBanRate()));
        }
    }

    public void configureMatchData(LocalDate date, Integer matchesPlayedHighMMR, Integer matchesPlayedLowMMR) {
        recordedMatchRepository.save(new MatchRecordedData(date, matchesPlayedHighMMR, matchesPlayedLowMMR));
    }

    public void addHighMMRGodData(int godID, DailyGodDataHighMMR dataHighMMR, int totalMatches, int newPatchMatches) {
        highMMRPerformanceRepository.findById(godID).ifPresentOrElse(godData -> {
            TotalGodDataHighMMR foundData = processAddedGodData(godData, dataHighMMR, totalMatches, newPatchMatches);
            highMMRPerformanceRepository.save(foundData);
        }, () -> updateService.findGod(godID).ifPresentOrElse(name -> {
                    try {
                        highMMRPerformanceRepository.save(processAddedGodData(new TotalGodDataHighMMR(godID, name.getGodName()),
                                dataHighMMR, totalMatches, newPatchMatches));
                    } catch (JpaSystemException e) {
                        LOGGER.log(Level.WARNING, "Could not process god data, " + e.getMessage());
                    } catch (NullPointerException e) {
                        LOGGER.log(Level.WARNING, "Could not find god data, " + e.getMessage());
                    }
                },
                () -> {
                    throw new GodNotFoundException(String.format("Could not find appropriate data for the god ID: %d", godID));
                }));
    }

    public void addLowMMRGodData(int godID, DailyGodDataLowMMR dataLowMMR, int totalMatches, int newPatchMatches) {
        lowMMRPerformanceRepository.findById(godID).ifPresentOrElse(godData ->
                        lowMMRPerformanceRepository.save(processAddedGodData(godData, dataLowMMR, totalMatches, newPatchMatches)),
                () -> updateService.findGod(godID).ifPresentOrElse(name -> {
                            try {
                                lowMMRPerformanceRepository.save(processAddedGodData(new TotalGodDataLowMMR(godID, name.getGodName()),
                                        dataLowMMR, totalMatches, newPatchMatches));
                            } catch (JpaSystemException e) {
                                LOGGER.log(Level.WARNING, "Could not process god data, " + e.getMessage());
                            } catch (NullPointerException e) {
                                LOGGER.log(Level.WARNING, "Could not find god data, " + e.getMessage());
                            }
                        },
                        () -> {
                            throw new GodNotFoundException(String.format("Could not find appropriate data for the god ID: %d", godID));
                        }));
    }

    public void deleteHighMMRGodData(LocalDate deletionDate, LocalDate patchDate, int totalMatches, int newPatchTotalMatches) {
        for (DailyGodDataHighMMR dataHighMMR : dailyHighMMRGodDataRepository.findAll()) {

            if (dataHighMMR.getDate().isBefore(deletionDate)) {

                highMMRPerformanceRepository.findById(dataHighMMR.getGodID()).ifPresentOrElse(godData -> {

                    highMMRPerformanceRepository.save(processDeletedGodData(godData, dataHighMMR, totalMatches));

                    if (dataHighMMR.getDate().isBefore(patchDate)) {
                        highMMRPerformanceRepository.save(processNewPatchDeletedGodData(godData, dataHighMMR, newPatchTotalMatches));
                    }

                }, null);

                dailyHighMMRGodDataRepository.delete(dataHighMMR);
            }
        }


    }

    public void deleteLowMMRData(LocalDate deletionDate, LocalDate patchDate, int totalMatches, int newPatchTotalMatches) {
        for (DailyGodDataLowMMR dataLowMMR : dailyLowMMRGodDataRepository.findAll()) {

            if (dataLowMMR.getDate().isBefore(deletionDate)) {

                lowMMRPerformanceRepository.findById(dataLowMMR.getGodID()).ifPresentOrElse(godData -> {

                    lowMMRPerformanceRepository.save(processDeletedGodData(godData, dataLowMMR, totalMatches));

                    if (dataLowMMR.getDate().isBefore(patchDate)) {
                        lowMMRPerformanceRepository.save(processNewPatchDeletedGodData(godData, dataLowMMR, newPatchTotalMatches));
                    }

                }, null);

                dailyLowMMRGodDataRepository.delete(dataLowMMR);
            }
        }
    }


    private <T extends TotalGodData, H extends DailyGodData> T processAddedGodData(T godData, H dailyGodData, double totalMatches, int newPatchMatches) {
        int totalMatchesPlayed = godData.getTotalMatchesPlayed();
        int matchesAdded = dailyGodData.getMatchesPlayed();

        godData.setAverageBasicAttackDamage(calcAdditionAverageStat(godData.getAverageBasicAttackDamage(), dailyGodData.getAverageBasicAttackDPM(),
                totalMatchesPlayed, matchesAdded));
        godData.setAverageDamageDone(calcAdditionAverageStat(godData.getAverageDamageDone(), dailyGodData.getAverageDPM(),
                totalMatchesPlayed, matchesAdded));
        godData.setAverageDamageMitigated(calcAdditionAverageStat(godData.getAverageDamageMitigated(), dailyGodData.getAverageDmgMitigatedPerMin(),
                totalMatchesPlayed, matchesAdded));

        int numMatches = utils.roundZero(totalMatchesPlayed + matchesAdded);
        int newPatchNumMatches = utils.roundZero(godData.getNewPatchMatchesPlayed() + matchesAdded);

        int bans = dailyGodData.getBans();
        int numBans = godData.getTotalBans() + bans;
        int newPatchNumBans = godData.getNewPatchBans() + bans;

        int wins = dailyGodData.getWins();
        int numWins = godData.getTotalWins() + wins;
        int newPatchNumWins = godData.getNewPatchWins() + wins;

        godData.setTotalMatchesPlayed(numMatches);
        godData.setNewPatchMatchesPlayed(newPatchNumMatches);

        godData.setTotalBans(numBans);
        godData.setNewPatchBans(newPatchNumBans);

        godData.setTotalWins(numWins);
        godData.setNewPatchWins(newPatchNumWins);

        godData.setMovingPickRate(numMatches / utils.roundZero(totalMatches));
        godData.setNewPatchPickRate(calcAdditionPercentageStat(godData.getNewPatchPickRate(), matchesAdded, totalMatchesPlayed, newPatchMatches));

        godData.setMovingBanRate(numBans / (double) utils.roundZero(numMatches));
        godData.setNewPatchBanRate(calcAdditionPercentageStat(godData.getNewPatchBanRate(), dailyGodData.getBans(), totalMatchesPlayed, matchesAdded));

        godData.setMovingWinRate(numWins / (double) utils.roundZero(numMatches));
        godData.setNewPatchWinRate(calcAdditionPercentageStat(godData.getNewPatchWinRate(), dailyGodData.getWins(), totalMatchesPlayed, matchesAdded));

        godData.setSkinsUsed(addNameCountMap(godData.getSkinsUsed(), dailyGodData.getSkinsUsed().entrySet()));
        godData.setPopularActives(addNameCountMap(godData.getPopularActives(), dailyGodData.getPopularActives().entrySet()));
        godData.setPopularItems(addNameCountMap(godData.getPopularItems(), dailyGodData.getPopularItems().entrySet()));
        godData.setNewPatchPopularItems(addNameCountMap(godData.getNewPatchPopularItems(), dailyGodData.getPopularItems().entrySet()));

        return godData;
    }

    private <T extends TotalGodData, H extends DailyGodData> T processDeletedGodData(T godData, H dailyGodData, double totalMatches) {
        int totalMatchesPlayed = utils.roundZero(godData.getTotalMatchesPlayed());
        int matchesCut = dailyGodData.getMatchesPlayed();

        godData.setAverageBasicAttackDamage(calcDeletionAverageStat(godData.getAverageBasicAttackDamage(),
                dailyGodData.getAverageBasicAttackDPM(), totalMatchesPlayed, matchesCut));
        godData.setAverageDamageDone(calcDeletionAverageStat(godData.getAverageDamageDone(),
                dailyGodData.getAverageDPM(), totalMatchesPlayed, matchesCut));
        godData.setAverageDamageMitigated(calcDeletionAverageStat(godData.getAverageDamageMitigated(),
                dailyGodData.getAverageDmgMitigatedPerMin(), totalMatchesPlayed, matchesCut));

        int numMatches = utils.roundZero(totalMatchesPlayed - matchesCut);
        int numBans = godData.getTotalBans() - dailyGodData.getBans();
        int numWins = godData.getTotalWins() - dailyGodData.getWins();

        godData.setTotalMatchesPlayed(numMatches);
        godData.setTotalBans(numBans);
        godData.setTotalWins(numWins);

        godData.setMovingPickRate(numMatches / totalMatches);
        godData.setMovingBanRate(numBans / (double) numMatches);
        godData.setMovingWinRate(numWins / (double) numMatches);

        godData.setSkinsUsed(removeNameCountMap(godData.getSkinsUsed(), dailyGodData.getSkinsUsed().entrySet()));
        godData.setPopularActives(removeNameCountMap(godData.getPopularActives(), dailyGodData.getPopularActives().entrySet()));
        godData.setPopularItems(removeNameCountMap(godData.getPopularItems(), dailyGodData.getPopularItems().entrySet()));

        return godData;
    }

    private <T extends TotalGodData, H extends DailyGodData> T processNewPatchDeletedGodData(T godData, H dailyGodData, double newPatchMatches) {
        int numNewPatchMatches = utils.roundZero(godData.getNewPatchMatchesPlayed() - dailyGodData.getMatchesPlayed());
        int numNewPatchBans = godData.getNewPatchBans() - dailyGodData.getBans();
        int numNewPatchWins = godData.getNewPatchWins() - dailyGodData.getWins();

        godData.setNewPatchMatchesPlayed(numNewPatchMatches);
        godData.setNewPatchBans(numNewPatchBans);
        godData.setNewPatchWins(numNewPatchWins);

        godData.setNewPatchPickRate(numNewPatchMatches / newPatchMatches);
        godData.setNewPatchBanRate(numNewPatchBans / (double) numNewPatchMatches);
        godData.setNewPatchWinRate(numNewPatchWins / (double) numNewPatchMatches);

        godData.setNewPatchPopularItems(removeNameCountMap(godData.getNewPatchPopularItems(), dailyGodData.getPopularItems().entrySet()));

        return godData;
    }

    private int calcDeletionAverageStat(int totalStat, int dailyStat, int totalMatchesPlayed, int matchesCut) {
        int newTotalMatches = utils.roundZero(totalMatchesPlayed - matchesCut);
        return (totalStat * totalMatchesPlayed - dailyStat * matchesCut) / newTotalMatches;
    }

    private int calcAdditionAverageStat(int totalStat, int dailyStat, int totalMatchesPlayed, int matchesAdded) {
        int newTotalMatches = utils.roundZero(totalMatchesPlayed + matchesAdded);
        return ((totalStat * totalMatchesPlayed) + (dailyStat * matchesAdded)) / newTotalMatches;
    }

    private double calcAdditionPercentageStat(double totalStat, int dailyTotal, int totalMatchesPlayed, double matchesAdded) {
        return ((totalStat * totalMatchesPlayed) + dailyTotal) / (totalMatchesPlayed + matchesAdded);
    }

    private <T> Map<T, Integer> removeNameCountMap(Map<T, Integer> map, Set<Map.Entry<T, Integer>> entrySet) {
        for (Map.Entry<T, Integer> entry : entrySet) {
            T key = entry.getKey();
            int value = map.getOrDefault(key, 0) - entry.getValue();
            map.put(key, Math.max(value, 0));
        }

        return map;
    }

    public <T> Map<T, Integer> addNameCountMap(Map<T, Integer> map, Set<Map.Entry<T, Integer>> entrySet) {
        for (Map.Entry<T, Integer> entry : entrySet) {
            T key = entry.getKey();
            map.put(key, map.getOrDefault(key, 0) + entry.getValue());
        }

        List<Map.Entry<T, Integer>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue());

        Map<T, Integer> result = new LinkedHashMap<>();
        for (Map.Entry<T, Integer> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }

}
