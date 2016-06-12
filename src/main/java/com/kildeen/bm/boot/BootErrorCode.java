package com.kildeen.bm.boot;

import com.kildeen.bm.ErrorCode;

/**
 * A name space for all errors that can occur during bootstrap
 *
 */
public enum BootErrorCode implements ErrorCode {
	EMPTY_DEFINITION(BM + "100", "The provided CommandGroupDefinition cannot be empty"), 
	
	DUPLICATED(BM + "101", "The command %s is duplicated in %s"),

	DUPLICATED_ACROSS_GROUPS(
			BM + "102", "The command %1$s was found in both %2$s and %3$s. Duplicates are not allowed");
	
	private String code;
	private String message;

	private BootErrorCode(String code, String message) {
		this.code = code;
		this.message = message;

	}
	
	public String getCode() {
		return code;
	}
	public String getMessage() {
		return message;
	}
}