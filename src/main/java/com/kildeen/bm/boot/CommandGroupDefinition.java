package com.kildeen.bm.boot;

import java.util.List;
@FunctionalInterface
public interface CommandGroupDefinition {

	List<Object> getCommandGroups();
}
