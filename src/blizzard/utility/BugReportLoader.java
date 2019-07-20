package blizzard.utility;

import java.util.ArrayList;

public class BugReportLoader {

	public static String loadBugReport(String repoName, int bugID) {
		String brFile = blizzard.config.StaticData.HOME_DIR + "/BR-Raw/" + repoName + "/" + bugID + ".txt";
		return ContentLoader.loadFileContent(brFile);
	}

	public static String loadBRTitle(String repoName, int bugID) {
		String brFile = blizzard.config.StaticData.HOME_DIR + "/BR-Raw/" + repoName + "/" + bugID + ".txt";
		ArrayList<String> lines = ContentLoader.getAllLinesOptList(brFile);
		if (lines.isEmpty()) {
			return new String();
		}
		return lines.get(0);
	}
}
