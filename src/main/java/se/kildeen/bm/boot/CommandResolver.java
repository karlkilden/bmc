package se.kildeen.bm.boot;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import se.kildeen.bm.CommandMapping;

/**
 * Reads a {@link CommandGroup} with reflection. Each method that can be
 * validated to a CommandMapping will be included in the result. Invalid methods
 * are ignored.
 * 
 * @author Kalle
 *
 */
public class CommandResolver {
	private Map<String, String> cmdToMethodName;
	private Class<?> commandGroup;
	private MethodReader commandReader;

	public CommandResolver(Class<?> commandGroup, MethodReader commandReader) {
		this.commandGroup = commandGroup;
		this.commandReader = commandReader;
	}

	/**
	 * Triggers the read and collects commands mapped to the actual method name
	 * @return A map with cmd -> method name. 
	 */
	public Map<String, String> build() {
		cmdToMethodName = new HashMap<>();

		for (Method m : commandGroup.getDeclaredMethods()) {
			MethodReadMode methodReadMode = commandGroup.isAnnotationPresent(CommandMapping.class)
					? MethodReadMode.ALLOW_ALL : MethodReadMode.REQUIRE_ANNOTATION;
			Optional<String> possibleCommand = commandReader.resolveCommandMappingName(m, methodReadMode);
			if (possibleCommand.isPresent()) {
				String cmd = possibleCommand.get();
				String methodName = m.getName();
				addCommandOrThrowIfDuplicated(cmd, methodName);
			}
		}
		return Collections.unmodifiableMap(cmdToMethodName);
	}

	private void addCommandOrThrowIfDuplicated(String cmd, String methodName) {
		if (cmdToMethodName.containsKey(cmd)) {
			BootErrorCode.DUPLICATED.illegalArgument( cmd, commandGroup.getName());
		} else {
			cmdToMethodName.put(cmd, methodName);
		}
	}
}
