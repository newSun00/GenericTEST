package org.nano.runCommand.listener;

import net.citizensnpcs.api.event.NPCRightClickEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.nano.runCommand.base.BaseListener;
import org.nano.runCommand.data.core.GlobalCache;
import org.nano.runCommand.data.dto.NPCCommand;
import org.nano.runCommand.util.TargetType;

import java.util.List;

public class NPCListener extends BaseListener implements Listener {
    @EventHandler
    public void clickNPC(NPCRightClickEvent event){
        Player player = event.getClicker();
        List<NPCCommand> v = GlobalCache.getList(TargetType.NPC, NPCCommand.class);

        v.forEach(clazz->{
            if (clazz.getId() == event.getNPC().getId()){
                super.run(player,clazz.getCommands());
            }
        });
    }
}
