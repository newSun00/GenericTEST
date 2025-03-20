package org.nano.runCommand.command;

import org.bukkit.Location;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.nano.runCommand.controller.CommandController;
import org.nano.runCommand.util.Factory;
import org.nano.runCommand.util.TargetType;

public class Command implements CommandExecutor {
    private final CommandController controller = new CommandController();
    private final Factory factory = new Factory();

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, org.bukkit.command.@NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if ( commandSender instanceof Player sender && sender.isOp() ){
            if( strings.length == 0 ) return false;
            switch (strings[0]){
                case "블럭적용"-> block(sender,strings);
                case "아머스탠드적용"-> armourStand(sender,strings);
                case "NPC적용"-> npc(sender,strings);
                case "목록" -> management(sender,strings);
                case "삭제" -> remove(sender);
            }
        }
        return false;
    }

    /**
     * @param sender < call Player
     * @param strings < command ~ index 1 ~
     * @description
     *  </Call List Commands>
     */
    private void management(Player sender, String[] strings) {
        switch ( strings[1] ){
            case "아머스탠드" -> controller.listCommand(sender, TargetType.AMOUR_STAND);
            case "블럭"-> controller.listCommand(sender,TargetType.BLOCK);
            case "NPC"-> controller.listCommand(sender,TargetType.NPC);
        }
    }

    /**
     * @param sender < call Player
     * @description
     *  </Delete Class <T>
     */
    private void remove(Player sender){
        Location loc = factory.getEyeLocation(sender);
        ArmorStand armorStand = factory.getEyeArmorStand(sender);
        int id = factory.getEyeNPC(sender);

        if ( loc != null ) {
            controller.removeCommand(loc);
            sender.sendMessage(" 블럭의 정보를 삭제 했습니다. ");
        }
        if ( armorStand != null ) {
            controller.removeCommand(armorStand);
            sender.sendMessage(" 아머스탠드의 정보를 삭제 했습니다. ");
        }
        if ( id != -1 ){
            controller.removeCommand(id);
            sender.sendMessage(" NPC의 정보를 삭제 했습니다. ");
        }
    }

    /**
     * @param sender < call Player
     * @param strings < command ~ index 1 ~
     */
    private void block(Player sender, String[] strings) {
        Location loc = factory.getEyeLocation(sender);
        if ( loc == null ){
            sender.sendMessage(" 객체를 인지하지 못했습니다. ");
            sender.sendMessage(" 다시 시도해주세요.");
            sender.sendMessage(" - 블럭 : 너무 먼 곳 또는 블럭으로 인식해야 되는 블럭들");
        }else controller.addCommand(TargetType.BLOCK, strings, loc);
    }

    /**
     * @param sender < call Player
     * @param strings < command ~ index 1 ~
     */
    private void armourStand(Player sender, String[] strings) {
        ArmorStand armorStand = factory.getEyeArmorStand(sender);
        if ( armorStand == null ){
            sender.sendMessage(" 객체를 인지하지 못했습니다. ");
            sender.sendMessage(" 다시 시도해주세요.");
            sender.sendMessage(" - 아머스탠드 : 정확히 아머스탠드를 보고 있어야 할 것.");
        } else controller.addCommand(TargetType.AMOUR_STAND, strings, armorStand.getUniqueId());
    }

    /**
     * @param sender < call Player
     * @param strings < command ~ index 1 ~
     */
    private void npc(Player sender, String[] strings){
        int id = factory.getEyeNPC(sender);
        if ( id == -1){
            sender.sendMessage(" 객체를 인지하지 못했습니다. ");
            sender.sendMessage(" 다시 시도해주세요.");
            sender.sendMessage(" - NPC : 정확히 NPC를 보고있어야 할 것");
        }else controller.addCommand(TargetType.NPC, strings, id);
    }
}
