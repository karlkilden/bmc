package com.kildeen.bm;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.kildeen.bm.Client;
import com.kildeen.bm.Col;
import com.kildeen.bm.CommandMapping;
import com.kildeen.bm.CommandRunner;
import com.kildeen.bm.boot.CommandBootstrap;
import com.kildeen.bm.boot.CommandGroupDefinition;

public class RunTest {

	Client client;

	@Before
	public void before() throws Exception {
		CommandGroupDefinition definition = () -> {
			return Col.newArrayList(new ExampleCommandGroup());
		};
		
		CommandBootstrap boot = new CommandBootstrap(definition);
		client = new Client(new CommandRunner(boot.getCommandStorage()));
	}

	@Test
	@Ignore
	public void run() throws Exception {
		client.start();
	}
	
	@CommandMapping
	public static class ExampleCommandGroup {
		
		
		public void methodOne() throws Exception {
			
		}

		public void methodTwo() throws Exception {
			
		}
	}
}
