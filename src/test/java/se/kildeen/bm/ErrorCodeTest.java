package se.kildeen.bm;

import static org.assertj.core.api.Assertions.assertThat;
import static se.kildeen.bm.boot.BootErrorCode.EMPTY_DEFINITION;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import se.kildeen.bm.boot.BootErrorCode;

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
