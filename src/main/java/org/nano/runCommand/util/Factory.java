package org.nano.runCommand.util;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Factory {

    public String joining(String[] commands) {
        return Arrays.stream(commands)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.joining(" "));
    }
    public ArmorStand getArmorStandAtLocation(Location location) {
        return location.getWorld().getEntitiesByClass(ArmorStand.class).stream()
                .filter(armorStand -> armorStand.getLocation().distance(location) < 1.0)
                .findFirst()
                .orElse(null);
    }

    public Location getEyeLocation(Player sender){
        Block block = sender.getTargetBlockExact(20);
        if ( block == null || block.getType().equals(Material.AIR)) {
            return null;
        }
        return block.getLocation();
    }

    public ArmorStand getEyeArmorStand(Player sender){
        Location playerLocation = sender.getEyeLocation();
        Vector direction = playerLocation.getDirection();
        double maxDistance = 10.0;
        for (double distance = 1; distance <= maxDistance; distance++) {
            Location checkLocation = playerLocation.clone().add(direction.clone().multiply(distance));
            ArmorStand armorStand = new Factory().getArmorStandAtLocation(checkLocation);
            if (armorStand != null) {
                return armorStand;
            }
        }
        return null;
    }
    public int getEyeNPC(Player sender){
        NPCRegistry registry = CitizensAPI.getNPCRegistry();
        Location eyeLoc = (sender).getEyeLocation();
        RayTraceResult res = eyeLoc.getWorld().rayTraceEntities(eyeLoc, eyeLoc.getDirection(), 10.0, 0.1,
                e -> !e.equals(sender));
        if (res != null && registry.isNPC(res.getHitEntity())) {
            NPC hit = registry.getNPC(res.getHitEntity());
            return hit.getId();
        }
        return -1;
    }
}
