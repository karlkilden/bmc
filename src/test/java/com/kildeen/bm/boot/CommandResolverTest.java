package com.kildeen.bm.boot;

import static org.mockito.Mockito.spy;

import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.kildeen.bm.ErrorCodeVerification;
import com.kildeen.bm.boot.BootErrorCode;
import com.kildeen.bm.boot.CommandResolver;
import com.kildeen.bm.boot.MethodReadMode;
import com.kildeen.bm.boot.MethodReader;

public class CommandResolverTest {

	private MethodReader methodReader;

	@Before
	public void before() throws Exception {
		methodReader = spy(new MethodReader());
	}
	
	@Test
	public void no_class_mapping_gives_REQUIRE_ANNOTATION() throws Exception {
		verify(NoTopLevelMappingMock.class, MethodReadMode.REQUIRE_ANNOTATION);
	}
	
	@Test
	public void class_mapping_gives_ALLOW_ALL() throws Exception {
		verify(TopLevelMappingMock.class, MethodReadMode.ALLOW_ALL);
	}

	@Test
	public void throws_on_duplicated_cmd() throws Exception {
		try {
			createResolverAndGetCommands(DuplicatedCommandMock.class);
			new ErrorCodeVerification().missingException(BootErrorCode.DUPLICATED);
		} catch (IllegalArgumentException e) {
			new ErrorCodeVerification().assertContainsErrorCode(e, BootErrorCode.DUPLICATED);
		}
	}

	private void verify(Class<?> commandGroup, MethodReadMode readMode) {
		createResolverAndGetCommands(commandGroup);
		for (Method m : commandGroup.getDeclaredMethods()) {
			Mockito.verify(methodReader).resolveCommandMappingName(m, readMode);
		}
	}

	private void createResolverAndGetCommands(Class<?> commandGroup) {
		CommandResolver cr = new CommandResolver(commandGroup, methodReader);
		cr.build();
	}



}
