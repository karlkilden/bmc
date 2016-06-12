package se.kildeen.bm;

import org.junit.Test;

import se.kildeen.bm.ExceptionHelper;

public class ExceptionHelperTest {
	@Test
	public void testUtilityClass() throws Exception {
		UtilityClassTestUtil.assertWellDefined(ExceptionHelper.class);
	}
}
