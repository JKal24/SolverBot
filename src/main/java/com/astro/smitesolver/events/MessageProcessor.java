package com.astro.smitesolver.events;

import com.astro.smitesolver.bot.SolverBot;
import com.astro.smitesolver.discord.entity.auxillary.Item;
import com.astro.smitesolver.discord.entity.totaldata.BanRateRanking;
import com.astro.smitesolver.discord.entity.totaldata.PickRateRanking;
import com.astro.smitesolver.discord.entity.totaldata.TotalGodData;
import com.astro.smitesolver.discord.entity.totaldata.WinRateRanking;
import com.astro.smitesolver.exception.CommandNotFoundException;
import com.astro.smitesolver.exception.GodNotFoundException;
import com.astro.smitesolver.utils.Commands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@Component
public class MessageProcessor {

    @Autowired
    private SolverBot bot;

    @Value("${MODE}")
    private String mode;

    public String getInfoName(String command) {
        if (command.equals(Commands.stats.name())) {
            return Commands.stats.getDescription();

        } else if(command.equals(Commands.update.name())) {
            return Commands.update.getDescription();

        } else if(command.equals(Commands.winrate.name())) {
            return Commands.winrate.getDescription();

        } else if(command.equals(Commands.pickrate.name())) {
            return Commands.pickrate.getDescription();

        } else if(command.equals(Commands.banrate.name())) {
            return Commands.banrate.getDescription();

        } else {
            return Commands.help.getDescription();

        }
    }

    public Map<String, Future<String>> processSolverEvent(String... commands) {
        String command = commands[0];

        Map<String, Future<String>> completableFutures = new LinkedHashMap<>();

        // Getting god data event
        if (command.equals(Commands.stats.name())) {
            boolean isLow = commands[commands.length - 1].equals(Commands.low.name());
            TotalGodData totalGodData;
            try {
                totalGodData = bot.getRequestedGod(commands[1].toLowerCase(), !isLow);
            } catch (GodNotFoundException e) {
                CompletableFuture<String> error = new CompletableFuture<>();
                error.complete(e.getMessage());
                return completableFutures;
            }

            CompletableFuture<String> statistics = new CompletableFuture<>();

            statistics.complete(String.format("""
                            %-20s %.2f%%\s
                            %-20s %.2f%%\s
                            %-20s %.2f%%
                            %-20s %d
                            
                            **Average Performance**
                            %-35s %d\s
                            %-35s %d
                            """,
                    "Win Rate:", totalGodData.getNewPatchWinRate() * 100.0,
                    "Pick Rate:", totalGodData.getNewPatchPickRate() * 100.0,
                    "Ban Rate:", totalGodData.getNewPatchBanRate(),
                    "Matches Played:", totalGodData.getNewPatchMatchesPlayed(),
                    "Average Damage Per Minute:", totalGodData.getAverageDamageDone(),
                    "Average Mitigated Per Minute:", totalGodData.getAverageDamageMitigated()));
            completableFutures.put(String.format("Statistics for %s\n", totalGodData.getGodName()), statistics);

            CompletableFuture<String> itemsList = new CompletableFuture<>();

            StringBuilder itemBuilder = new StringBuilder();
            Map<Item, Integer> items = totalGodData.getNewPatchPopularItems();

            int selectTopTen = 10;
            for (Map.Entry<Item, Integer> itemEntry : items.entrySet()) {
                if (selectTopTen <= 0) {
                    break;
                }
                Item selectedItem = itemEntry.getKey();
                itemBuilder.append(selectedItem.getItemName()).append("\n");
                // Add item image uri in the future?
                selectTopTen--;
            }

            itemsList.complete(itemBuilder.toString());
            completableFutures.put("Popular Items\n", itemsList);

            CompletableFuture<String> activesList = new CompletableFuture<>();

            StringBuilder activesBuilder = new StringBuilder();
            Map<String, Integer> actives = totalGodData.getPopularActives();

            int selectFour = 4;
            for (Map.Entry<String, Integer> activeEntry : actives.entrySet()) {
                if (selectFour <= 0) {
                    break;
                }
                activesBuilder.append(activeEntry.getKey());
                activesBuilder.append("\n");
                selectFour--;
            }
            activesList.complete(activesBuilder.toString());
            completableFutures.put("Popular Actives", activesList);

        } else if (command.equals(Commands.winrate.name())) {
            CompletableFuture<String> winrate = new CompletableFuture<>();
            boolean isLow = commands[commands.length - 1].equals(Commands.low.name());
            int topThirty = 30;

            StringBuilder winRateBuilder = new StringBuilder();
            for (WinRateRanking winRateRanking : bot.getWinRateLeaderboard(!isLow)) {
                if (topThirty <= 0) break;
                winRateBuilder.append(String.format("%-20s %.2f\n", winRateRanking.getGodName(), winRateRanking.getWinRate()));
                topThirty--;
            }

            winrate.complete(winRateBuilder.toString());
            completableFutures.put("Winrate Leaderboard", winrate);

        } else if (command.equals(Commands.pickrate.name())) {
            CompletableFuture<String> pickrate = new CompletableFuture<>();
            boolean isLow = commands[commands.length - 1].equals(Commands.low.name());
            int topThirty = 30;

            StringBuilder pickRateBuilder = new StringBuilder();
            for (PickRateRanking pickRateRanking : bot.getPickRateLeaderboard(!isLow)) {
                if (topThirty <= 0) break;
                pickRateBuilder.append(String.format("%-20s %.2f\n", pickRateRanking.getGodName(), pickRateRanking.getPickRate()));
                topThirty--;
            }

            pickrate.complete(pickRateBuilder.toString());
            completableFutures.put("Pickrate Leaderboard", pickrate);

        } else if (command.equals(Commands.banrate.name())) {
            CompletableFuture<String> banrate = new CompletableFuture<>();
            boolean isLow = commands[commands.length - 1].equals(Commands.low.name());
            int topThirty = 30;

            StringBuilder banRateBuilder = new StringBuilder();
            for (BanRateRanking banRateRanking : bot.getBanRateLeaderboard(!isLow)) {
                if (topThirty <= 0) break;
                banRateBuilder.append(String.format("%-20s %.2f\n", banRateRanking.getGodName(), banRateRanking.getBanRate()));
                topThirty--;
            }

            banrate.complete(banRateBuilder.toString());
            completableFutures.put("Banrate Leaderboard", banrate);

        } else {

            CompletableFuture<String> commandInfo = new CompletableFuture<>();
            commandInfo.complete("""
                    Here are the available commands:\s

                       -stats: s!stats <god name> <low>

                       -winrate: s!winrate <low>
                       -pickrate: s!pickrate <low>
                       -banrate: s!banrate <low>
                       ---displays top 30 leaderboard data

                        <low> sets the data to give only low mmr information
                          ---default is high mmr information
                    """);
            completableFutures.put("Commands", commandInfo);
        }
        return completableFutures;
    }

}
