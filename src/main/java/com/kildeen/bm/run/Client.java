package com.kildeen.bm.run;

import java.util.Scanner;

public class Client {

	static final String EXIT = "exit";
	static final String START = "Bm is ready";
	static final String HELP = "help";

	private final Scanner sc;

	private final CommandRunner commandRunner;

	public Client(final CommandRunner commandRunner) {
		this.commandRunner = commandRunner;
		sc = new Scanner(System.in);
	}

	public void start() {
		System.out.println(START);

		while (sc.hasNext()) {
			String s = sc.nextLine();
			if (EXIT.equals(s)) {
				break;
			}
			System.out.println(handleCommand(s));
		}
	}
	
	public String handleCommand(final String command) {
		return commandRunner.handle(command);
	}
	
	/**
	 * The runner that can handle commands. Exposed for clients that wish to do their own plumming.
	 */
	public CommandRunner getCommandRunner() {
		return commandRunner;
	}

}
