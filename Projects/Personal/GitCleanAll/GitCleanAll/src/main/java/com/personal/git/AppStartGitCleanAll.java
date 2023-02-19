package com.personal.git;

import java.io.File;
import java.nio.file.Files;
import java.time.Instant;
import java.util.List;

import com.utils.io.ListFileUtils;
import com.utils.io.PathUtils;
import com.utils.log.Logger;

final class AppStartGitCleanAll {

	private AppStartGitCleanAll() {
	}

	public static void main(
			final String[] args) {

		if (args.length == 0) {
			Logger.printError("insufficient arguments");

		} else {
			String rootFolderPathString = args[0];
			rootFolderPathString = PathUtils.computeAbsolutePath("root folder", null, rootFolderPathString);
			if (rootFolderPathString != null) {

				work(rootFolderPathString);
			}
		}
	}

	private static void work(
			final String rootFolderPathString) {

		final Instant start = Instant.now();
		try {
			Logger.printProgress("cleaning Git repositories inside folder:");
			Logger.printLine(rootFolderPathString);

			final List<String> gitFolderPathStringList = ListFileUtils.listFilesRecursively(rootFolderPathString,
					filePath -> Files.isDirectory(filePath) && ".git".equals(PathUtils.computeFileName(filePath)));
			for (final String gitFolderPathString : gitFolderPathStringList) {

				final String gitRepoPathString = PathUtils.computeParentPath(gitFolderPathString);
				cleanGitRepo(gitRepoPathString);
			}

			Logger.printFinishMessage(start);

		} catch (final Exception exc) {
			Logger.printError("error occurred while cleaning Git repositories");
			Logger.printException(exc);
		}
	}

	private static void cleanGitRepo(
			final String gitRepoPathString) {

		try {
			Logger.printProgress("cleaning Git repository:");
			Logger.printLine(gitRepoPathString);

			final Process process = new ProcessBuilder()
					.command("cmd", "/c", "git_clean")
					.directory(new File(gitRepoPathString))
					.inheritIO()
					.start();
			process.waitFor();

		} catch (final Exception exc) {
			Logger.printError("error occurred while cleaning Git repository:" +
					System.lineSeparator() + gitRepoPathString);
			Logger.printException(exc);
		}
	}
}
