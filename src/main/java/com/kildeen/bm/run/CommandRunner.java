package com.kildeen.bm.run;

import com.kildeen.bm.ExceptionHelper;
import com.kildeen.bm.boot.CommandGroupDefinition;
import com.kildeen.bm.boot.CommandStorage;

/**
 * This class accepts commands that was provided passed in originally by a
 * {@link CommandGroupDefinition}
 * 
 * @author Kalle
 *
 */
public class CommandRunner {
	private static final String OK = " OK";

	public static final String UNKNOWN_CMD = "unknown cmd";
	private CommandStorage commandStorage;

	public CommandRunner(CommandStorage commandStorage) {
		this.commandStorage = commandStorage;

	}

	/**
	 * Unknown commands will just be echoed with 'unknown command'. If the
	 * command is mapped to a method that method will be called
	 */
	public String handle(String cmd) {
		String method = commandStorage.getMethod(cmd);
		if (method == null) {
			return UNKNOWN_CMD;
		} else {
			invokeMethod(cmd, method);
			return okResult(cmd);
		}
	}

	private void invokeMethod(String cmd, String method) {
		try {
			Object g = commandStorage.getCommandGroup(cmd);
			g.getClass().getDeclaredMethod(method, new Class[] {}).invoke(g, new Object[] {});
		} catch (Exception e) {
			ExceptionHelper.throwAsRuntimeException(e);
		}
	}

	String okResult(String cmd) {
		return cmd + OK;
	}

}
