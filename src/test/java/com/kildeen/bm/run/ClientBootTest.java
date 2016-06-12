package com.kildeen.bm.run;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.emptyStandardInputStream;

import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;
import org.mockito.Mockito;

import com.kildeen.bm.Col;
import com.kildeen.bm.boot.CommandGroupDefinition;
import com.kildeen.bm.run.RunExampleTest.ExampleCommandGroup;

public class ClientBootTest {
	@Rule
	public final TextFromStandardInputStream systemInMock = emptyStandardInputStream();
	@Rule
	public final SystemOutRule systemOutRule = new SystemOutRule().mute();
	
	@Test
	public void testName() throws Exception {
	    systemInMock.provideLines("exit");

		CommandGroupDefinition definition = () -> {
			return Col.newArrayList(new ExampleCommandGroup(new StringBuilder()));
		};
		ClientBoot cb = new ClientBoot(definition);
		cb.client = Mockito.spy(cb.client);
		cb.runLoop();
		Mockito.verify(cb.client).start();
		
		assertThat(cb.getCommandRunner()).isNotNull();
	}
}
