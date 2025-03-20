package org.nano.runCommand.data.core;

import org.nano.runCommand.data.dto.ArmourStandCommand;
import org.nano.runCommand.data.dto.BlockCommand;
import org.nano.runCommand.data.dto.NPCCommand;
import org.nano.runCommand.util.TargetType;

import java.util.*;

public class GlobalCache {
    private static final Map<TargetType, List<Object>> commandCache = new HashMap<>();

    public static <T> void set(TargetType key, T data) {
        commandCache.computeIfAbsent(key, k -> new ArrayList<>()).add(data);
    }

    public static <T> List<T> getList(TargetType key, Class<T> clazz) {
        List<Object> objects = commandCache.get(key);
        if (objects == null) return Collections.emptyList();

        List<T> result = new ArrayList<>();
        for (Object obj : objects) {
            if (clazz.isInstance(obj)) {
                result.add(clazz.cast(obj));
            }
        }
        return result;
    }
    public static <T> Object get(TargetType key, Class<T> clazz, Object v){
        List<Object> objects = commandCache.get(key);
        if (objects == null) return null;

        for (Object obj : objects) {
            if ( clazz.isInstance(obj) ) {
                if (clazz.equals(ArmourStandCommand.class)) {
                    if (obj instanceof ArmourStandCommand command && command.getUuid().equals(v)) {
                        return command;
                    }
                } else if (clazz.equals(BlockCommand.class)) {
                    if (obj instanceof BlockCommand command && command.getLoc().equals(v)) {
                        return command;
                    }
                } else if (clazz.equals(NPCCommand.class)) {
                    if (obj instanceof NPCCommand command && (command.getId() == (int) v)) {
                        return command;
                    }
                }
            }
        }
        return null;
    }
    public static <T> void remove(TargetType key, Class<T> clazz, Object identifier) {
        List<Object> objects = commandCache.get(key);
        if (objects == null) return;

        objects.removeIf(obj -> {
            if (clazz.equals(ArmourStandCommand.class)) {
                return obj instanceof ArmourStandCommand cmd && cmd.getUuid().equals(identifier);
            } else if (clazz.equals(BlockCommand.class)) {
                return obj instanceof BlockCommand cmd && cmd.getLoc().equals(identifier);
            } else if (clazz.equals(NPCCommand.class)) {
                return obj instanceof NPCCommand cmd && cmd.getId() == (int) identifier;
            }
            return false;
        });

        commandCache.put(key, objects);
    }



}
