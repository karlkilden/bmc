package com.kildeen.bm.boot;

import static java.lang.String.join;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class CommandStorage {

	private final Map<String, Object> commandsToCommandGroupClasses = new HashMap<>();
	private final Map<String, String> cmdToMethods = new HashMap<>();

	void add(Object currentCommandGroup, Map<String, String> currentCmdToMethods) {
		Optional<String> potentialDuplicate = currentCmdToMethods.keySet().stream()
				.filter(commandsToCommandGroupClasses::containsKey).findFirst();

		throwIfDuplicateFound(currentCommandGroup, potentialDuplicate);

		commandsToCommandGroupClasses.putAll(mapCommandsToClass(currentCommandGroup, currentCmdToMethods));
		cmdToMethods.putAll(currentCmdToMethods);
	}

	private void throwIfDuplicateFound(Object currentCommandGroupClass, Optional<String> potentialDuplicate) {

		if (potentialDuplicate.isPresent()) {
			String duplicate = potentialDuplicate.get();
			BootErrorCode.DUPLICATED_ACROSS_GROUPS.illegalArgument(duplicate,
					currentCommandGroupClass.getClass().getName(), getCommandGroup(duplicate).getClass().getName());
		}
	}

	private Map<String, Object> mapCommandsToClass(Object currentCommandGroup, Map<String, String> cmdToMethods) {
		return cmdToMethods.keySet().stream().collect(toMap(k -> k, k -> currentCommandGroup));
	}

	public Set<String> getSupportedCommands() {
		return cmdToMethods.keySet();
	}

	public Object getCommandGroup(String cmd) {
		return commandsToCommandGroupClasses.get(cmd);
	}

	public String getMethod(String cmd) {
		return cmdToMethods.get(cmd);
	}
	
	@Override
	public String toString() {
		Set<Object> addedGroups = new HashSet<>(commandsToCommandGroupClasses.values());
		StringBuilder sb = new StringBuilder();
		sb.append("commandGroups:");
		sb.append(join(", ", addedGroups.stream().map(g -> g.getClass().getName()).collect(toList())));
		sb.append(System.lineSeparator());
		sb.append("Command count:");
		sb.append(cmdToMethods.size());
		return sb.toString();
	}
	
	
}
