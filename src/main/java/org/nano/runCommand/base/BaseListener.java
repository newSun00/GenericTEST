package org.nano.runCommand.base;

import org.bukkit.entity.Player;

import java.util.List;

public abstract class BaseListener {
    public void run(Player sender, List<String> commands){
        commands.forEach(sender::performCommand);
    }

}
