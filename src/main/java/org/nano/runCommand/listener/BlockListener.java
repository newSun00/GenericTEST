package org.nano.runCommand.listener;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.nano.runCommand.base.BaseListener;
import org.nano.runCommand.data.core.GlobalCache;
import org.nano.runCommand.data.dto.BlockCommand;
import org.nano.runCommand.util.TargetType;

import java.util.List;

public class BlockListener extends BaseListener implements Listener {
    @EventHandler
    public void clickBlock(PlayerInteractEvent event) {
        if (event.getHand() == EquipmentSlot.HAND && event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {

            Player player = event.getPlayer();
            Block block = event.getClickedBlock();

            if (block == null || block.getType().equals(Material.AIR)) return;

            List<BlockCommand> v = GlobalCache.getList(TargetType.BLOCK, BlockCommand.class);
            v.forEach(clazz -> {
                if (clazz.getLoc().equals(block.getLocation())) {
                    super.run(player, clazz.getCommands());
                }
            });
        }
    }
}
