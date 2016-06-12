package com.kildeen.bm.run;

import com.kildeen.bm.VisibleForTesting;
import com.kildeen.bm.boot.CommandBootstrap;
import com.kildeen.bm.boot.CommandGroupDefinition;

public class ClientBoot {

	@VisibleForTesting("dependecies are created and not exposed to keep the public API as simple as possible")
	Client client;

	public ClientBoot(final CommandGroupDefinition... commandGroupDefinitions) {
		final CommandBootstrap boot = new CommandBootstrap(commandGroupDefinitions);
		client = new Client(new CommandRunner(boot.getCommandStorage()));
	}

	public void runLoop() {
		client.start();
	}

	public CommandRunner getCommandRunner() {
		return client.getCommandRunner();
		
	}
}
