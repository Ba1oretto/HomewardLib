package com.baioretto.baiolib.dev.command;

import com.baioretto.baiolib.ServerConfig;
import com.baioretto.baiolib.dev.mock.UnitTest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class UnitTestCommand extends Command {
    public UnitTestCommand() {
        super(ServerConfig.MOCK_TEST_COMMAND_NAME);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        UnitTest.doMock();
        return true;
    }
}
