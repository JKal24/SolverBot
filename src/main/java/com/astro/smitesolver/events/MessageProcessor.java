package com.astro.smitesolver.events;

import com.astro.smitesolver.bot.SolverBot;
import com.astro.smitesolver.discord.entity.auxillary.Item;
import com.astro.smitesolver.discord.entity.totaldata.TotalGodData;
import com.astro.smitesolver.exception.CommandNotFoundException;
import com.astro.smitesolver.utils.Commands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MessageProcessor {

    @Autowired
    private SolverBot bot;

    public String processSolverEvent(String fullCommand) throws CommandNotFoundException {
        String[] commands = fullCommand.split(" ");
        String command = commands[0];

        // Getting god data event
        if (command.equals(Commands.stats.name())) {
            TotalGodData totalGodData;
            if (commands[commands.length - 1].equals(Commands.low.name())) {
                totalGodData = bot.getRequestedGod(commands[1], false);
            } else {
                totalGodData = bot.getRequestedGod(commands[1], true);
            }
            if (totalGodData == null) {
                return "No data available for, " + commands[1];
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
            return stringBuilder.toString();
        }
        throw new CommandNotFoundException("Could not execute the given function, " + command);
    }

}
