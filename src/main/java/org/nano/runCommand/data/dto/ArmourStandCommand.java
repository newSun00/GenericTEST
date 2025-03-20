package org.nano.runCommand.data.dto;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class ArmourStandCommand{

    private final UUID uuid;
    private List<String> commands;

    public ArmourStandCommand(UUID uuid, List<String> commands) {
        this.uuid = uuid;
        this.commands = commands;
    }

    public UUID getUuid() {
        return uuid;
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
        ArmourStandCommand that = (ArmourStandCommand) obj;
        return uuid.equals(that.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }
}
