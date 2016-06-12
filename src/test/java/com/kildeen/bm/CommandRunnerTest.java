package com.kildeen.bm;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.kildeen.bm.CommandRunner;
import com.kildeen.bm.boot.CommandStorage;
import com.kildeen.bm.boot.TopLevelMappingMock;

public class CommandRunnerTest {

	private static final String COMMAND = "cmd";
	private CommandStorage commandStorage;
	private CommandRunner cr;

	@Before
	public void before() throws Exception {
		commandStorage = Mockito.mock(CommandStorage.class);
		cr = new CommandRunner(commandStorage);

	}

	@Test
	public void unknownCommand() throws Exception {
		Assertions.assertThat(cr.handle("not recorded")).isEqualTo(CommandRunner.UNKNOWN_CMD);
	}
	

	@Test
	public void found_cmd_invokes_method() throws Exception {
		TopLevelMappingMock spyEnabledCommandGroup = Mockito.spy(new TopLevelMappingMock());
		recordCommandMethodGroup(COMMAND, "testName", spyEnabledCommandGroup);

		String okResult = cr.handle(COMMAND);
		Assertions.assertThat(okResult).isEqualTo(cr.okResult(COMMAND));
		Mockito.verify(spyEnabledCommandGroup).testName();
	}
	
	@Test
	public void okResult_should_append_ok() throws Exception {
	assertThat(cr.okResult("test")).isEqualTo("test OK");
	}

	@Test
	public void problem_during_invoke_is_thrown() throws Exception {
		recordCommandMethodGroup(COMMAND, "testName", new CommandRunnerTest());

		try {
			cr.handle(COMMAND);
			Assert.fail("Exception expected, the method should not have been found");
		} catch (Exception e) {
			// The exception is from reflection, we do not enhance it thus nothing to test
		}
	}

	private void recordCommandMethodGroup(String cmd, String value, Object group) {
		when(commandStorage.getMethod(cmd)).thenReturn(value);
		when(commandStorage.getCommandGroup(COMMAND)).thenReturn(group);
	}
}
