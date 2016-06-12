package com.kildeen.bm;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.kildeen.bm.Col;

public class ColTest {
	@Test
	public void emptyIfNull() throws Exception {
		assertThat(Col.emptyIfNull(null)).isNotNull();
	}
	
	@Test
	public void emptyIfNull_same_if_not_null() throws Exception {
		List<Object> existing = new ArrayList<>();
		assertThat(existing).isSameAs(Col.emptyIfNull(existing));
	}
	
	@Test
	public void computeIfEmpty() throws Exception {
		assertThat(Col.computeIfEmpty(null, ArrayList::new)).isNotNull();
	}
	
	@Test
	public void computeIfEmpty_same_if_not_null() throws Exception {
		List<Object> existing = new ArrayList<>();
		assertThat(existing).isSameAs(Col.computeIfEmpty(existing, ArrayList::new));
	}
	
	@Test
	public void filtered() throws Exception {
		List<String> filtered = Col.filtered(Col.newArrayList("1", "2", "3", "6", "1"), e -> e.equals("1"));
		assertThat(filtered).containsExactly("1", "1");
	}
	
	@Test
	public void testUtilityClass() throws Exception {
		UtilityClassTestUtil.assertWellDefined(Col.class);
	}
	
}
