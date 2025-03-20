package org.nano.runCommand.controller;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.nano.runCommand.data.core.GlobalCache;
import org.nano.runCommand.data.dto.ArmourStandCommand;
import org.nano.runCommand.data.dto.BlockCommand;
import org.nano.runCommand.data.dto.NPCCommand;
import org.nano.runCommand.data.file.DBFile;
import org.nano.runCommand.util.Factory;
import org.nano.runCommand.util.TargetType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CommandController {
    /**
     * @param type / KEY ARMOUR_STAND, BLOCK, NPC
     */
    public void listCommand(Player sender, TargetType type) {
        switch (type) {
            case AMOUR_STAND ->{
                List<ArmourStandCommand> armourStand = GlobalCache.getList(type, ArmourStandCommand.class);
                for ( ArmourStandCommand k : armourStand ){
                    sender.sendMessage(""+k.getUuid());
                    for ( String command : k.getCommands() ) {
                        sender.sendMessage("   - /" +command);
                    }
                }
            }
            case BLOCK -> {
                List<BlockCommand> block = GlobalCache.getList(type, BlockCommand.class);
                for ( BlockCommand k : block ){
                    sender.sendMessage(k.getLoc().getX()+", "+k.getLoc().getY()+", "+k.getLoc().getZ());
                    for ( String command : k.getCommands() ) {
                        sender.sendMessage("   - /" +command);
                    }
                }
            }
            case NPC -> {
                List<NPCCommand> block = GlobalCache.getList(type, NPCCommand.class);
                for ( NPCCommand k : block ){
                    sender.sendMessage(""+k.getId());
                    for ( String command : k.getCommands() ) {
                        sender.sendMessage("   - /" +command);
                    }
                }
            }
        }
    }

    /**
     * @param type type ->
     * @param command command
     * @param v / instanceof Location, int, UUID
     */
    public void addCommand(TargetType type, String[] command, Object v) {
        command[0] = "";
        if (v instanceof UUID uuid) {
            ArmourStandCommand k = (ArmourStandCommand) GlobalCache.get(type, ArmourStandCommand.class, uuid);
            if (k != null) {
                List<String> commands = k.getCommands();
                String format = new Factory().joining(command);
                commands.add(format);
                k.setCommands(commands);
            } else {
                List<String> commands = new ArrayList<>();
                String format = new Factory().joining(command);
                commands.add(format);
                ArmourStandCommand dt = new ArmourStandCommand(uuid, commands);
                GlobalCache.set(type, dt);
            }
        }
        else if (v instanceof Location loc) {
            BlockCommand k = (BlockCommand) GlobalCache.get(type, BlockCommand.class, loc);
            if (k != null) {
                List<String> commands = k.getCommands();
                String format = new Factory().joining(command);
                commands.add(format);
                k.setCommands(commands);
            } else {
                List<String> commands = new ArrayList<>();
                String format = new Factory().joining(command);
                commands.add(format);
                BlockCommand dt = new BlockCommand(loc, commands);
                GlobalCache.set(type, dt);
            }
        }
        else if (v instanceof Integer id) {
            NPCCommand k = (NPCCommand) GlobalCache.get(type, NPCCommand.class, id);
            if (k != null) {
                List<String> commands = k.getCommands();
                String format = new Factory().joining(command);
                commands.add(format);
                k.setCommands(commands);
            } else {
                List<String> commands = new ArrayList<>();
                String format = new Factory().joining(command);
                commands.add(format);
                NPCCommand dt = new NPCCommand(id, commands);
                GlobalCache.set(type, dt);
            }
        }
        DBFile.getInstance()
                .save(type);
    }

    /**
     * @param v instanceof UUID , Location , Integer
     */
    public void removeCommand(Object v) {
        if (v instanceof ArmorStand ar) {

            List<ArmourStandCommand> armourStand = GlobalCache.getList(TargetType.AMOUR_STAND, ArmourStandCommand.class);
            armourStand.stream()
                    .filter(k->k.getUuid().equals(ar.getUniqueId()))
                    .findFirst()
                    .ifPresent(k->GlobalCache.remove(TargetType.AMOUR_STAND,k.getClass(),ar.getUniqueId()));

            DBFile.getInstance()
                    .save(TargetType.AMOUR_STAND);
        }
        else if (v instanceof Location loc) {
            List<BlockCommand> block = GlobalCache.getList(TargetType.BLOCK, BlockCommand.class);

            block.stream()
                    .filter(k->k.getLoc().equals(loc))
                    .findFirst()
                    .ifPresent(k->GlobalCache.remove(TargetType.BLOCK,k.getClass(),v));

            DBFile.getInstance()
                    .save(TargetType.BLOCK);
        }
        else if (v instanceof Integer id) {
            List<NPCCommand> npc = GlobalCache.getList(TargetType.NPC, NPCCommand.class);

            for (NPCCommand k : npc) {
                if (k.getId() == id) {
                    GlobalCache.remove(TargetType.NPC, k.getClass(),v);
                }
            }
            DBFile.getInstance()
                    .save(TargetType.NPC);
        }

    }

}
