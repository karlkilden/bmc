package com.kildeen.bm.run;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import com.kildeen.bm.Col;
import com.kildeen.bm.CommandMapping;
import com.kildeen.bm.boot.CommandGroupDefinition;

/**
 * This class demonstrates a full run of the program
 * 
 * @author Kalle
 *
 */
public class RunExampleTest {
	private StringBuilder sb;

	ClientBoot clientBoot;

	@Before
	public void before() throws Exception {
		sb = new StringBuilder();

		// The boot requires a CommandGroupDefinition, we define one using a
		// lambda, this is of course optional.
		CommandGroupDefinition definition = () -> {
			// This is the example instance with methods that we want to expose
			ExampleCommandGroup exampleCommandGroup = new ExampleCommandGroup(sb);
			// If we want more instances to be exposed we add them as well to
			// the list below
			List<Object> allCommandGroups = Col.newArrayList(exampleCommandGroup);
			return allCommandGroups;
		};
		// ClientBoot is the class we construct with the CommandGroupDefinition.
		// It will manage the boot for us
		// Internally it has a Client object that can run against System.in. In
		// this test we go with the other option, handling it commands directly
		clientBoot = new ClientBoot(definition);
	}

	@Test
	public void run_methods_in_this_class() throws Exception {
		clientBoot.getCommandRunner().handle("methodOne"); 
		clientBoot.getCommandRunner().handle("methodTwo_overriden");
		// We ran one method mapped by it's methodName and one overriden

		Assertions.assertThat(sb.toString()).contains("methodOne", "methodTwo_overriden");
		// For this test we added all the calls to a StringBuilder
	}

	@CommandMapping
	public static class ExampleCommandGroup {

		private StringBuilder sb;

		public ExampleCommandGroup(StringBuilder sb) {
			this.sb = sb;
		}

		public void methodOne() throws Exception {
			sb.append("methodOne");
		}

		@CommandMapping(cmd = "methodTwo_overriden")
		public void methodTwo() throws Exception {
			sb.append("methodTwo_overriden");

		}
	}
}
