package by.gvozdovich.partshop.controller.command;

import java.util.Optional;

/**
 * Factory to create instance of {@link Command}
 * @author Vadim Gvozdovich
 * @version 1.0
 */
public class CommandFactory {
    public static Optional<Command> defineCommand(String commandName) {
        Optional<Command> current = Optional.empty();

        if(commandName == null) {
            return current;
        }

        CommandType type = CommandType.valueOf(commandName.toUpperCase());
        current = Optional.of(type.getCommand());
        return current;
    }
}
