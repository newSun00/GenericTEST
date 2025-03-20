package org.nano.runCommand;

import org.bukkit.plugin.java.JavaPlugin;
import org.nano.runCommand.command.Command;
import org.nano.runCommand.data.file.DBFile;
import org.nano.runCommand.listener.AmourStandListener;
import org.nano.runCommand.listener.BlockListener;
import org.nano.runCommand.listener.NPCListener;

import java.util.Objects;

public final class RunCommand extends JavaPlugin {

    @Override
    public void onEnable() {
        new DBFile().loadAll();

        Objects.requireNonNull(getCommand("명령어설정관리")).setExecutor(new Command());

        getServer().getPluginManager().registerEvents(new AmourStandListener(),this);
        getServer().getPluginManager().registerEvents(new NPCListener(),this);
        getServer().getPluginManager().registerEvents(new BlockListener(),this);
    }

    @Override
    public void onDisable() {

    }
}
