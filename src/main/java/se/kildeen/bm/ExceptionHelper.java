package se.kildeen.bm;

@SuppressWarnings({ "unchecked" })
public final class ExceptionHelper<T extends Throwable> {
	private ExceptionHelper() {
	};

	private void throwException(Throwable exception) throws T {
		throw (T) exception;
	}

	public static void throwAsRuntimeException(Throwable throwable) {
		new ExceptionHelper<RuntimeException>().throwException(throwable);
	}
}