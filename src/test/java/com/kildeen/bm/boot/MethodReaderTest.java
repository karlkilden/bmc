package com.kildeen.bm.boot;

import static com.kildeen.bm.boot.MethodReadMode.ALLOW_ALL;
import static com.kildeen.bm.boot.MethodReadMode.REQUIRE_ANNOTATION;
import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Method;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import com.kildeen.bm.CommandMapping;
import com.kildeen.bm.boot.MethodReadMode;
import com.kildeen.bm.boot.MethodReader;

public class MethodReaderTest {

	private MethodReader mr;

	@Before
	public void before() throws Exception {
		mr = new MethodReader();
	}

	@Test
	public void no_anno_and_REQUIRE_ANNOTATION_is_empty() throws Exception {
		assertCmd("testName", REQUIRE_ANNOTATION, null);
	}

	@Test
	public void anno_and_REQUIRE_ANNOTATION_matches_custom() throws Exception {
		assertCmd("testName2", REQUIRE_ANNOTATION, "customName");
	}

	@Test
	public void anno_and_REQUIRE_ANNOTATION_matches() throws Exception {
		assertCmd("testName3", REQUIRE_ANNOTATION, "testName3");
	}

	@Test
	public void no_anno_and_ALLOW_ALL_is_methodName() throws Exception {
		assertCmd("testName", ALLOW_ALL, "testName");
	}

	@Test
	public void anno_and_ALLOW_ALL_matches_custom() throws Exception {
		assertCmd("testName2", ALLOW_ALL, "customName");
	}

	@Test
	public void anno_and_ALLOW_ALL_matches() throws Exception {
		assertCmd("testName3", ALLOW_ALL, "testName3");
	}

	private void assertCmd(String name, MethodReadMode readMode, String expected) throws NoSuchMethodException {
		Optional<String> mappingName = mr.resolveCommandMappingName(getMethod(name), readMode);

		if (expected == null) {
			assertThat(mappingName.isPresent()).isFalse();
		} else {
			assertThat(mappingName.get()).isEqualTo(expected);

		}
	}

	private Method getMethod(String name) throws NoSuchMethodException {
		return CommandClassMock.class.getDeclaredMethod(name, new Class[0]);
	}

	@CommandMapping
	@SuppressWarnings("unused")
	private static class CommandClassMock {
		void testName() throws Exception {

		}

		@CommandMapping(cmd = "customName")
		void testName2() throws Exception {

		}

		@CommandMapping
		void testName3() throws Exception {

		}
	}

}
