package systems.kinau.fishingbot.command.commands;

import systems.kinau.fishingbot.FishingBot;
import systems.kinau.fishingbot.command.Command;
import systems.kinau.fishingbot.command.CommandExecutor;

public class LookCommand extends Command {

    public LookCommand() {
        super("look", FishingBot.getI18n().t("command-look-desc"));
    }

    @Override
    public void onCommand(String label, String[] args, CommandExecutor executor) {
        if (args.length >= 2) {
            try {
                float yaw = Float.valueOf(args[0]);
                float pitch = Float.valueOf(args[1]);
                int speed = 5;
                if (args.length == 3)
                    speed = Integer.valueOf(args[2]);
                FishingBot.getInstance().getCurrentBot().getPlayer().look(yaw, pitch, speed);
            } catch (NumberFormatException ex) {
                sendMessage("/look <yaw> <pitch> [speed]", executor);
            }
            return;
        }
        sendMessage("/look <yaw> <pitch> [speed]", executor);
    }

}