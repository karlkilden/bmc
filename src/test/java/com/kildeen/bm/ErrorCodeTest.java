package com.kildeen.bm;

import static com.kildeen.bm.boot.BootErrorCode.EMPTY_DEFINITION;
import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import com.kildeen.bm.boot.BootErrorCode;

public class ErrorCodeTest {

	@Test
	public void build_with_params() throws Exception {
		String msg = BootErrorCode.DUPLICATED_ACROSS_GROUPS.build("1", "2", "3");
		Assertions.assertThat( msg).contains("1", "2", "3");
	}
	
	@Test
	public void illegalArgument() throws Exception {
		try {
			EMPTY_DEFINITION.illegalArgument();
		} catch (IllegalArgumentException e) {
			assertThat(EMPTY_DEFINITION.build()).isEqualTo(e.getMessage());

			
		}
	}

}
