package se.kildeen.bm;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import se.kildeen.bm.Client;
import se.kildeen.bm.Col;
import se.kildeen.bm.CommandMapping;
import se.kildeen.bm.CommandRunner;
import se.kildeen.bm.boot.CommandBootstrap;
import se.kildeen.bm.boot.CommandGroupDefinition;

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
