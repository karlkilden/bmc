package com.kildeen.bm;

@SuppressWarnings({ "unchecked" })
public final class ExceptionHelper<T extends Throwable> {
	private ExceptionHelper() {
	};

	private void throwException(final Throwable exception) throws T {
		throw (T) exception;
	}

	public static void throwAsRuntimeException(final Throwable throwable) {
		new ExceptionHelper<RuntimeException>().throwException(throwable);
	}
}