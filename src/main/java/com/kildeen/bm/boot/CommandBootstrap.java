package com.kildeen.bm.boot;

import static com.kildeen.bm.Col.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CommandBootstrap {

	private final MethodReader methodReader = new MethodReader();
	private final CommandStorage commandStorage = new CommandStorage();

	public CommandBootstrap(final CommandGroupDefinition... commandGroupDefinitions) {
		for (CommandGroupDefinition commandGroupDefinition : commandGroupDefinitions) {
			storeCommands(commandGroupDefinition);
		}
	}

	CommandBootstrap() {
	}

	void storeCommands(final CommandGroupDefinition definition) {
		List<Object> commandGroups = copyCommandGroupClazzesThrowIfEmpty(definition);
		for (Object commandGroup : commandGroups) {
			Map<String, String> commandsToMethodNames = getCommandResolver(commandGroup.getClass(), methodReader)
					.build();
			commandStorage.add(commandGroup, commandsToMethodNames);
		}
	}

	public CommandStorage getCommandStorage() {
		return commandStorage;
	}

	private List<Object> copyCommandGroupClazzesThrowIfEmpty(final CommandGroupDefinition cmds) {
		final List<Object> commandDefinitions = new ArrayList<>(emptyIfNull(cmds.getCommandGroups()));
		if (commandDefinitions.isEmpty()) {
			BootErrorCode.EMPTY_DEFINITION.illegalArgument();
		}
		return commandDefinitions;
	}

	CommandResolver getCommandResolver(final Class<?> commandGroup, final MethodReader methodReader) {
		return new CommandResolver(commandGroup, methodReader);
	}
}
