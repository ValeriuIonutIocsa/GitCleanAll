package com.personal.git;

import org.junit.jupiter.api.Test;

class AppStartGitCleanAllTest {

	@Test
	void testMain() {

		final String rootFolderPathString = "C:\\IVI";
		final String[] args = new String[] { rootFolderPathString };
		AppStartGitCleanAll.main(args);
	}
}
