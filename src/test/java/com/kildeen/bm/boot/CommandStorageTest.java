package com.kildeen.bm.boot;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import com.kildeen.bm.Col;
import com.kildeen.bm.ErrorCodeVerification;
import com.kildeen.bm.boot.BootErrorCode;
import com.kildeen.bm.boot.CommandStorage;

import junit.framework.Assert;

public class CommandStorageTest {
	private static final String FAKE_METHOD_SUFFIX = "method";
	private static final String NO_TOP_LEVEL_1 = "cmd3";
	private static final String NO_TOP_LEVEL_2 = "cmd4";
	private static final String TOP_LEVEL_2 = "cmd2";
	private static final String TOP_LEVEL_1 = "cmd";
	Set<String> addedCmds;
	private CommandStorage cg;

	@Before
	public void before() throws Exception {
		addedCmds = new HashSet<>();
		cg = createStorage();
	}

	@Test
	public void supportedCommands_contains_all_added() throws Exception {
		assertThat(cg.getSupportedCommands()).containsOnlyElementsOf(addedCmds);
	}

	@Test
	public void each_group_is_mapped_as_value_to_all_its_commands() throws Exception {
		assertCmdMapping(TOP_LEVEL_1, TopLevelMappingMock.class);
		assertCmdMapping(TOP_LEVEL_2, TopLevelMappingMock.class);
		assertCmdMapping(NO_TOP_LEVEL_1, NoTopLevelMappingMock.class);
		assertCmdMapping(NO_TOP_LEVEL_2, NoTopLevelMappingMock.class);
	}

	@Test
	public void duplicates_across_groups_are_found() throws Exception {

		CommandStorage cg = new CommandStorage();

		try {
			cg.add(new TopLevelMappingMock(), getMapAndSaveCmdsForVerify(TOP_LEVEL_1, TOP_LEVEL_2));
			cg.add(new TopLevelMappingMock(), getMapAndSaveCmdsForVerify(TOP_LEVEL_1, TOP_LEVEL_2));
			Assert.fail("expected: " + BootErrorCode.DUPLICATED_ACROSS_GROUPS);
		} catch (IllegalArgumentException e) {
			new ErrorCodeVerification().assertContainsErrorCode(e, BootErrorCode.DUPLICATED_ACROSS_GROUPS);
		}

	}

	@Test
	public void getMethod_should_match_input_map() throws Exception {
		for (String cmd : addedCmds) {
			assertThat(cg.getMethod(cmd)).isEqualTo(cmd + FAKE_METHOD_SUFFIX);
		}
	}
	
	@Test
	public void toString_lists_commandGroups_and_total_count() throws Exception {
		assertThat(cg.toString()).contains(NoTopLevelMappingMock.class.getName(), TopLevelMappingMock.class.getName(), "Command count:"+addedCmds.size());
	}

	private void assertCmdMapping(String cmd, Class<?> expected) {
		assertThat(cg.getCommandGroup(cmd).getClass()).isEqualTo(expected);
	}

	private CommandStorage createStorage() {
		CommandStorage cg = new CommandStorage();
		cg.add(new TopLevelMappingMock(), getMapAndSaveCmdsForVerify(TOP_LEVEL_1, TOP_LEVEL_2));
		cg.add(new NoTopLevelMappingMock(), getMapAndSaveCmdsForVerify(NO_TOP_LEVEL_1, NO_TOP_LEVEL_2));
		return cg;
	}

	private Map<String, String> getMapAndSaveCmdsForVerify(String cmd, String otherCommand) {
		List<String> cmds = Col.newArrayList(cmd, otherCommand);
		addedCmds.addAll(cmds);
		Map<String, String> value = cmds.stream().collect(Collectors.toMap(v -> v, v -> v + FAKE_METHOD_SUFFIX));
		return value;
	}

}
