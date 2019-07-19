package blizzard.utility;

public class BugReportLoader {
	public static String loadBugReport(String repoName, int bugID) {
		String brFile = blizzard.config.StaticData.HOME_DIR + "/BR-Raw/" + repoName
				+ "/" + bugID + ".txt";
		return ContentLoader.loadFileContent(brFile);
	}
}
