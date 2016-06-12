package com.kildeen.bm.boot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.kildeen.bm.ErrorCodeVerification;
import com.kildeen.bm.boot.BootErrorCode;
import com.kildeen.bm.boot.CommandBootstrap;
import com.kildeen.bm.boot.CommandGroupDefinition;

public class CommandBootstrapTest {

	private CommandGroupDefinition commandDefinition;
	private CommandBootstrap bootstrap;

	@Before
	public void before() throws Exception {
		commandDefinition = Mockito.mock(CommandGroupDefinition.class);

		bootstrap = Mockito.spy(new CommandBootstrap());
	}

	
	@Test
	public void commandGroups_are_stored() throws Exception {
		store(new MethodLevelMappingMock(), new TopLevelMappingMock());
		verifyCmdMappedToClass("testName",TopLevelMappingMock.class);
		verifyCmdMappedToClass("testName2",TopLevelMappingMock.class);
		verifyCmdMappedToClass("testName3",MethodLevelMappingMock.class);
		verifyCmdMappedToClass("testName4",MethodLevelMappingMock.class);
	}
	


	private void verifyCmdMappedToClass(String cmd, Class<?> commandGroupClazz) {
		Object group = bootstrap.getCommandStorage().getCommandGroup(cmd);
		assertThat(group.getClass()).isEqualTo(commandGroupClazz);
	}

	@Test
	public void null_definition_should_throw() throws Exception {
		when(getCommandGroups()).thenReturn(null);
		assertEmptyException();

	}

	@Test
	public void empty_definition_should_throw() throws Exception {
		when(getCommandGroups()).thenReturn(new ArrayList<>());
		assertEmptyException();
	}

	private List<Object> getCommandGroups() {
		return commandDefinition.getCommandGroups();
	}

	private void store(Object... clazzesToStore) {
		when(getCommandGroups()).thenReturn(newArrayList(clazzesToStore));
		bootstrap = Mockito.spy(new CommandBootstrap(commandDefinition));
	}

	private void assertEmptyException() {
		try {
			bootstrap.storeCommands(commandDefinition);
			new com.kildeen.bm.ErrorCodeVerification().missingException(BootErrorCode.EMPTY_DEFINITION);
		} catch (IllegalArgumentException e) {
			new ErrorCodeVerification().assertErrorCode(e, BootErrorCode.EMPTY_DEFINITION);
		}
	}

}
