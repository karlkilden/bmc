package se.kildeen.bm;

import org.junit.Test;

import se.kildeen.bm.UtilityClassTestUtil;

public class UtilityClassTestUtilTest {
	@Test
	public void testUtilityClass() throws Exception {
		UtilityClassTestUtil.assertWellDefined(UtilityClassTestUtil.class);
	}
}
