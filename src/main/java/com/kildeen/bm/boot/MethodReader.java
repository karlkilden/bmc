package com.kildeen.bm.boot;

import java.lang.reflect.Method;
import java.util.Optional;

import com.kildeen.bm.CommandMapping;
import com.kildeen.bm.TreadSafe;

/**
 * Method reader that accepts a {@link java.lang.reflect.Method} and extracts the CommandMapping.
 * 
 * @author Kalle
 * 
 */
@TreadSafe
public class MethodReader {

	Optional<String> resolveCommandMappingName(Method m, MethodReadMode mode) {
		String cmd;
		CommandMapping annotation = m.getAnnotation(CommandMapping.class);
		if (annotation == null) {
			return returnEmptyOrMethodName(m, mode);
		}
		cmd = annotation.cmd();
		if (cmd.isEmpty()) {
			cmd = m.getName();
		}
		return Optional.ofNullable(cmd);
	}

	private Optional<String> returnEmptyOrMethodName(Method m, MethodReadMode mode) {
		return Optional.ofNullable(annotationRequired(mode) ? null : m.getName());
	}

	private boolean annotationRequired(MethodReadMode mode) {
		return mode == MethodReadMode.REQUIRE_ANNOTATION;
	}

}
