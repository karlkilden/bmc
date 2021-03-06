package com.kildeen.bm.run;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.emptyStandardInputStream;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;

import com.kildeen.bm.run.Client;
import com.kildeen.bm.run.CommandRunner;
public class ClientTest {

	private static final String COMMAND = "a cmd";

	@Rule
	public final SystemOutRule systemOutRule = new SystemOutRule().enableLog().mute();

	@Rule
	public final TextFromStandardInputStream systemInMock = emptyStandardInputStream();

	private Client client;
	private CommandRunner commandHandler;

	@Before
	public void testName() throws Exception {
		commandHandler = mock(CommandRunner.class);
		client = new Client(commandHandler);
	}
	
	@Test
	public void runner_notNull() throws Exception {
		assertThat(client.getCommandRunner()).isNotNull();
	}

	@Test
	public void client_can_start() throws Exception {
	    systemInMock.provideLines("1");
		client.start();
		verifyOut(Client.START);
		verify(commandHandler).handle("1");
	}

	private void verifyOut(String expected) {
		Assertions.assertThat(systemOutRule.getLog()).contains(expected);
	}

	@Test
	public void start_loop_catches_line() throws Exception {
		when(commandHandler.handle(COMMAND)).thenReturn(COMMAND);
	    systemInMock.provideLines(COMMAND);

		startAndAcceptOneCommand();
		verify(commandHandler).handle(COMMAND);
		verifyOut(COMMAND);
	}

	@Test
	public void exit() throws Exception {
	    systemInMock.provideLines(Client.EXIT);
		startAndAcceptOneCommand();
	}



	private void startAndAcceptOneCommand() {
		client.start();
	}

}
