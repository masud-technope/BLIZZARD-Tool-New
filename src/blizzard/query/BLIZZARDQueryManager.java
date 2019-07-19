package blizzard.query;

import java.util.ArrayList;
import java.util.HashMap;
import blizzard.utility.BugReportLoader;
import blizzard.utility.ContentLoader;

public class BLIZZARDQueryManager {

	String repoName;
	String bugIDFile;
	HashMap<Integer, String> reportMap;
	HashMap<Integer, String> reportTitleMap;
	HashMap<Integer, String> suggestedQueryMap;

	public BLIZZARDQueryManager(String repoName, String bugIDFile) {
		this.repoName = repoName;
		this.bugIDFile = bugIDFile;
		this.suggestedQueryMap = new HashMap<>();
		this.reportTitleMap = new HashMap<Integer, String>();
		this.reportMap = loadReportMap();
		this.reportTitleMap = loadReportTitleMap(reportMap);
	}

	protected String extractTitle(String reportContent) {
		String title = new String();
		String[] lines = reportContent.split("\n");
		if (lines.length > 0) {
			title = lines[0];
		}
		return title;
	}

	protected HashMap<Integer, String> loadReportMap() {
		ArrayList<Integer> bugs = ContentLoader.getAllLinesInt(this.bugIDFile);
		HashMap<Integer, String> reportMap = new HashMap<>();
		for (int bugID : bugs) {
			String reportContent = BugReportLoader.loadBugReport(repoName, bugID);
			reportMap.put(bugID, reportContent);
		}
		return reportMap;
	}

	protected HashMap<Integer, String> loadReportTitleMap(HashMap<Integer, String> reportMap) {
		HashMap<Integer, String> titleMap = new HashMap<Integer, String>();
		for (int bugID : reportMap.keySet()) {
			String reportContent = reportMap.get(bugID);
			String title = extractTitle(reportContent);
			titleMap.put(bugID, title);
		}
		return titleMap;
	}

	public HashMap<Integer, String> getSuggestedQueries() {
		System.out.println("Query reformulation may take a few minutes. Please wait...");
		for (int bugID : this.reportMap.keySet()) {
			String reportContent = this.reportMap.get(bugID);
			String title=this.reportTitleMap.get(bugID);
			BLIZZARDQueryProvider provider = new BLIZZARDQueryProvider(this.repoName, bugID, title, reportContent);
			String suggestedQuery = provider.provideBLIZZARDQuery();
			System.out.println("Done: " + bugID);
			this.suggestedQueryMap.put(bugID, suggestedQuery);
		}
		System.out.println("Query Reformulation completed successfully :-)");
		return this.suggestedQueryMap;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String repoName = "ecf";
		String bugIDFile = "./input/bugs.txt";
		System.out.println(new BLIZZARDQueryManager(repoName, bugIDFile).getSuggestedQueries());
	}
}
