package com.kildeen.bm;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import com.kildeen.bm.ExceptionHelper;

import junit.framework.Assert;

/**
 * Adapted from Commons Testing
 * 
 * @author Kalle
 *
 */
public final class UtilityClassTestUtil {

	private static void finalAndOneConstructor(final Class<?> clazz) {

		if (!Modifier.isFinal(clazz.getModifiers())) {
			Assert.fail("Utility classes should be final");
		}
		if (clazz.getDeclaredConstructors().length != 1) {
			Assert.fail("Utility classes should have one constructor");

		}
	}

	private static void constructorShouldBePrivate(final Constructor<?> constructor)
			throws ReflectiveOperationException {

		if (constructor.isAccessible() || !Modifier.isPrivate(constructor.getModifiers())) {
			Assert.fail("The constructor should be private");

		}

	}

	private static void methodsStaticAndNotUsingInheritence(Class<?> clazz) {

		for (final Method method : clazz.getDeclaredMethods()) {
			if (!Modifier.isStatic(method.getModifiers()) && method.getDeclaringClass().equals(method.getClass())) {
				Assert.fail("all methods should be private");

			}
		}
	}

	public static void assertWellDefined(final Class<?> clazz) {

		try {
			finalAndOneConstructor(clazz);
			final Constructor<?> constructor = clazz.getDeclaredConstructor();
			constructorShouldBePrivate(constructor);
			createInstanceToCleanupCoverage(constructor);
			methodsStaticAndNotUsingInheritence(clazz);
		} catch (ReflectiveOperationException e) {
			ExceptionHelper.throwAsRuntimeException(e);
		}
	}

	private static void createInstanceToCleanupCoverage(Constructor<?> constructor)
			throws ReflectiveOperationException {
		constructor.setAccessible(true);
		constructor.newInstance();
		constructor.setAccessible(false);
	}

	private UtilityClassTestUtil() {

	}
}