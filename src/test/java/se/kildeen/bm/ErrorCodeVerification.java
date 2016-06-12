package se.kildeen.bm;

import static org.assertj.core.api.Assertions.assertThat;

import junit.framework.Assert;
import se.kildeen.bm.ErrorCode;

public class ErrorCodeVerification {

	public void assertErrorCode(Exception e, ErrorCode errorCode) {
		assertThat(e.getMessage()).isEqualTo(errorCode.build());
	}
	
	public void assertContainsErrorCode(Exception e, ErrorCode errorCode) {
		assertThat(e.getMessage()).contains(errorCode.getCode());
	}

	public void missingException(ErrorCode code) {
		Assert.fail("Expected exception with code:" + code.getCode());
	}
}
