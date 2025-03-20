package org.nano.runCommand.data.dto;

import java.util.List;
import java.util.Objects;

public class NPCCommand {
    private final int id;
    private List<String> commands;

    public NPCCommand(int id, List<String> commands) {
        this.id = id;
        this.commands = commands;
    }

    public int getId() {
        return id;
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
        NPCCommand that = (NPCCommand) obj;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
