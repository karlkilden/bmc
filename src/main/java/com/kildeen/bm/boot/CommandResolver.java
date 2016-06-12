package com.kildeen.bm.boot;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.kildeen.bm.CommandMapping;

/**
 * Reads a {@link CommandGroup} with reflection. Each method that can be
 * validated to a CommandMapping will be included in the result. Invalid methods
 * are ignored.
 * 
 * @author Kalle
 *
 */
public class CommandResolver {
	private final Map<String, String>  cmdToMethodName = new HashMap<>();
	private final Class<?> commandGroup;
	private final MethodReader commandReader;

	public CommandResolver(Class<?> commandGroup, MethodReader commandReader) {
		this.commandGroup = commandGroup;
		this.commandReader = commandReader;
	}

	/**
	 * Triggers the read and collects commands mapped to the actual method name
	 * @return A map with cmd -> method name. 
	 */
	public Map<String, String> build() {

		for (Method m : commandGroup.getDeclaredMethods()) {
			MethodReadMode methodReadMode = commandGroup.isAnnotationPresent(CommandMapping.class)
					? MethodReadMode.ALLOW_ALL : MethodReadMode.REQUIRE_ANNOTATION;
			Optional<String> possibleCommand = commandReader.resolveCommandMappingName(m, methodReadMode);
			if (possibleCommand.isPresent()) {
				final String cmd = possibleCommand.get();
				final String methodName = m.getName();
				addCommandOrThrowIfDuplicated(cmd, methodName);
			}
		}
		return Collections.unmodifiableMap(cmdToMethodName);
	}

	private void addCommandOrThrowIfDuplicated(final String cmd, final String methodName) {
		if (cmdToMethodName.containsKey(cmd)) {
			BootErrorCode.DUPLICATED.illegalArgument( cmd, commandGroup.getName());
		} else {
			cmdToMethodName.put(cmd, methodName);
		}
	}
}
