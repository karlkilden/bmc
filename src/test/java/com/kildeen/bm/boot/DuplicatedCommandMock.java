package com.kildeen.bm.boot;

import com.kildeen.bm.CommandMapping;
@CommandMapping

public class DuplicatedCommandMock {

	public void testName() throws Exception {

	}

	@CommandMapping(cmd = "testName")
	public void testName2() throws Exception {

	}
}