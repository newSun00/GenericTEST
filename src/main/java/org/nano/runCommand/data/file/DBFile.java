package org.nano.runCommand.data.file;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.nano.runCommand.RunCommand;
import org.nano.runCommand.data.core.GlobalCache;
import org.nano.runCommand.data.dto.ArmourStandCommand;
import org.nano.runCommand.data.dto.BlockCommand;
import org.nano.runCommand.data.dto.NPCCommand;
import org.nano.runCommand.util.TargetType;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class DBFile {
    private File file;
    private FileConfiguration configuration;

    public static DBFile instance;
    public static DBFile getInstance(){
        if (instance == null) {
            instance = new DBFile();
        }
        return instance;
    }

    private void setup(String fileName){
        File pluginFolder = Objects.requireNonNull(RunCommand.getProvidingPlugin(RunCommand.class)).getDataFolder();
        File folder = new File(pluginFolder, "db");
        if (!folder.exists()) {
            folder.mkdirs();
        }
        file = new File(folder, fileName+".yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ignored) {
            }
        }
        configuration = YamlConfiguration.loadConfiguration(file);
    }
    public void loadAll() {
        for (TargetType type : TargetType.values()) {
            setup(type.name());

            switch (type) {
                case AMOUR_STAND -> {
                    for (String key : configuration.getKeys(false)) {
                        List<String> commands = configuration.getStringList(key + ".Command");
                        GlobalCache.set(type,new ArmourStandCommand(UUID.fromString(key), commands));
                    }
                }
                case BLOCK -> {
                    for (String key : configuration.getKeys(false)) {
                        String[] parts = key.split("_");
                        if (parts.length != 4) continue;

                        World world = Bukkit.getWorld(parts[0]);
                        double x = Double.parseDouble(parts[1]);
                        double y = Double.parseDouble(parts[2]);
                        double z = Double.parseDouble(parts[3]);

                        if (world == null) continue;

                        Location location = new Location(world, x, y, z);
                        List<String> commands = configuration.getStringList(key + ".Command");

                        GlobalCache.set(type, new BlockCommand(location, commands));
                    }
                }
                case NPC -> {
                    for (String key : configuration.getKeys(false)) {
                        List<String> commands = configuration.getStringList(key + ".Command");
                        GlobalCache.set(type,new NPCCommand(Integer.parseInt(key), commands));
                    }
                }
            }
        }
    }

    public void save(TargetType type){
        Bukkit.getScheduler().runTask(RunCommand.getPlugin(RunCommand.class),()->{
            setup(type.name());
            switch (type){
                case AMOUR_STAND -> {
                    initFile();
                    List<ArmourStandCommand> armourStand = GlobalCache.getList(type, ArmourStandCommand.class);
                    for ( ArmourStandCommand k : armourStand ){
                        configuration.set(k.getUuid().toString()+".Command",k.getCommands());
                    }
                }
                case BLOCK -> {
                    initFile();
                    List<BlockCommand> block = GlobalCache.getList(type, BlockCommand.class);
                    for ( BlockCommand k : block ){
                        String path = k.getLoc().getWorld().getName() +"_"+k.getLoc().getBlockX()+"_"+k.getLoc().getBlockY()+"_"+k.getLoc().getBlockZ();
                        configuration.set(path+".Command",k.getCommands());
                    }
                }
                case NPC -> {
                    initFile();
                    List<NPCCommand> npc = GlobalCache.getList(type, NPCCommand.class);
                    for ( NPCCommand k : npc ){
                        configuration.set(k.getId()+".Command",k.getCommands());
                    }
                }
            }
            saveFile();
        });
    }
    private void saveFile(){
        try {
            configuration.save(file);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
    private void initFile() {
        configuration.getKeys(false).forEach(key -> configuration.set(key, null));
    }

}
