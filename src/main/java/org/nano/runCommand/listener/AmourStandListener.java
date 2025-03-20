package org.nano.runCommand.listener;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.nano.runCommand.base.BaseListener;
import org.nano.runCommand.data.core.GlobalCache;
import org.nano.runCommand.data.dto.ArmourStandCommand;
import org.nano.runCommand.util.TargetType;

import java.util.List;

public class AmourStandListener extends BaseListener implements Listener {

    @EventHandler
    public void clickAmourStand(PlayerInteractAtEntityEvent event){
        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();

        if ( entity instanceof ArmorStand armorStand){
            List<ArmourStandCommand> v = GlobalCache.getList(TargetType.AMOUR_STAND, ArmourStandCommand.class);
            v.forEach(clazz->{
               if (clazz.getUuid().equals(armorStand.getUniqueId())){
                    super.run(player,clazz.getCommands());
                }
            });
        }
    }
}
