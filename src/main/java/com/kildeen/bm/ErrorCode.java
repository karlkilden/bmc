package com.kildeen.bm;

/**
 * A class that lists all errors and arranges them accordingly to when they can
 * occur. Codes are separated from the message to substain
 * 
 * @author Kalle
 *
 */
public interface ErrorCode {

	static final String BM = "BM_";

	String getCode();

	String getMessage();

	default String build(Object... params) {
		return String.format(getCode() + " " + getMessage(), params);
	}

	default void illegalArgument(Object... params) {
		throw new IllegalArgumentException(build(params));
	}

}
