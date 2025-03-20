package org.nano.runCommand.data.dto;

import org.bukkit.Location;

import java.util.List;
import java.util.Objects;

public class BlockCommand {
    private final Location loc;
    private List<String> commands;

    public BlockCommand(Location loc, List<String> commands) {
        this.loc = loc;
        this.commands = commands;
    }

    public Location getLoc() {
        return loc;
    }

    public List<String> getCommands() {
        return commands;
    }

    public void setCommands(List<String> commands) {
        this.commands = commands;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        BlockCommand that = (BlockCommand) obj;
        return loc.equals(that.loc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(loc);
    }
}
