package se.kildeen.bm.boot;

import se.kildeen.bm.CommandMapping;
@CommandMapping

public class DuplicatedCommandMock {

	public void testName() throws Exception {

	}

	@CommandMapping(cmd = "testName")
	public void testName2() throws Exception {

	}
}