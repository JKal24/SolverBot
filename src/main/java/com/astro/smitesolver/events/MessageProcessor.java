package com.astro.smitesolver.events;

import com.astro.smitesolver.bot.SolverBot;
import com.astro.smitesolver.discord.entity.auxillary.Item;
import com.astro.smitesolver.discord.entity.totaldata.TotalGodData;
import com.astro.smitesolver.exception.CommandNotFoundException;
import com.astro.smitesolver.exception.GodNotFoundException;
import com.astro.smitesolver.utils.Commands;
import discord4j.core.spec.EmbedCreateSpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@Component
public class MessageProcessor {

    @Autowired
    private SolverBot bot;

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

    @Async
    public Future<String> processSolverEvent(String... commands) {
        String command = commands[0];
        CompletableFuture<String> completableFuture = new CompletableFuture<>();

        // Getting god data event
        if (command.equals(Commands.stats.name())) {
            boolean isLow = commands[commands.length - 1].equals(Commands.low.name());
            TotalGodData totalGodData;
            try {
                totalGodData = bot.getRequestedGod(commands[1].toLowerCase(), !isLow);
            } catch (GodNotFoundException e) {
                completableFuture.complete(e.getMessage());
                return completableFuture;
            }

            StringBuilder stringBuilder = new StringBuilder();

            stringBuilder.append(totalGodData.getGodName());
            stringBuilder.append(String.format("\nWin Rate: %.2f \nPick Rate: %.2f \nBan Rate: %.2f \n",
                    totalGodData.getNewPatchWinRate(), totalGodData.getNewPatchPickRate(), totalGodData.getNewPatchBanRate()));

            stringBuilder.append(String.format("Average Damage Done: %d \nAverage Basic Attack Damage: %d \nAverage Damage Mitigated: %d\n",
                    totalGodData.getAverageDamageDone(), totalGodData.getAverageBasicAttackDamage(), totalGodData.getAverageDamageMitigated()));

            Map<Item, Integer> items = totalGodData.getNewPatchPopularItems();

            stringBuilder.append("Popular Items: ");
            int selectTen = 10;
            for (Map.Entry<Item, Integer> itemEntry : items.entrySet()) {
                if (selectTen <= 0) {
                    break;
                }
                Item selectedItem = itemEntry.getKey();
                stringBuilder.append(selectedItem.getItemName());
                stringBuilder.append("\n");
                // Add item image uri in the future?
                selectTen--;
            }

            Map<String, Integer> actives = totalGodData.getPopularActives();

            stringBuilder.append("Popular Actives: ");
            int selectFour = 4;
            for (Map.Entry<String, Integer> activeEntry : actives.entrySet()) {
                if (selectFour <= 0) {
                    break;
                }
                stringBuilder.append(activeEntry.getKey());
                stringBuilder.append("\n");
                selectFour--;
            }
            completableFuture.complete(stringBuilder.toString());

        } else if (command.equals(Commands.winrate.name())) {
            boolean isLow = commands[commands.length - 1].equals(Commands.low.name());
            completableFuture.complete(bot.getWinRateLeaderboard(!isLow).toString());

        } else if (command.equals(Commands.pickrate.name())) {
            boolean isLow = commands[commands.length - 1].equals(Commands.low.name());
            completableFuture.complete(bot.getPickRateLeaderboard(!isLow).toString());

        } else if (command.equals(Commands.banrate.name())) {
            boolean isLow = commands[commands.length - 1].equals(Commands.low.name());
            completableFuture.complete(bot.getBanRateLeaderboard(!isLow).toString());

        } else if (command.equals(Commands.update.name())) {
            int numDays = Integer.parseInt(commands[1]);
            try {
                bot.requestUpdate(numDays);
            } catch (CommandNotFoundException e) {
                completableFuture.complete("Could not update data");
            }
            completableFuture.complete("Update maintenance is complete");

        } else {
            completableFuture.complete("Here are the available commands: \n" +
                    "\n" +
                    "   -stats: s!stats <god name> <low>\n" +
                    "   -update: s!update <number of days>\n" +
                    "\n" +
                    "   -winrate: s!winrate <low>\n" +
                    "   -pickrate: s!pickrate <low>\n" +
                    "   -banrate: s!banrate <low>\n" +
                    "   ---displays leaderboard data\n" +
                    "\n" +
                    "    <low> sets the data to give only low mmr information\n" +
                    "      ---default is high mmr information\n" +
                    "\n" +
                    "    <number of days> must be at least 1 but less than or equal to 30\n");

        }
        return completableFuture;
    }

}
