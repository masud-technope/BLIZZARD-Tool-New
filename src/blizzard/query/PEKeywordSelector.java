package blizzard.query;

import java.util.ArrayList;

import strict.query.SearchTermProvider;
import blizzard.config.StaticData;
import blizzard.utility.BugReportLoader;
import blizzard.utility.MiscUtility;

public class PEKeywordSelector {

	String title;
	String bugReportContent;
	int TOPK;

	public PEKeywordSelector(String title, String bugReportContent, int TOPK) {
		this.title = title;
		this.bugReportContent = bugReportContent;
		this.TOPK = TOPK;
		this.loadSTRICTConfigs();
	}

	protected void loadSTRICTConfigs() {
		// loading the required variables for the strict library
		System.setProperty("HOME_DIR", StaticData.HOME_DIR);
		System.setProperty("STOPWORD_DIR", "/data");
		System.setProperty("SAMURAI_DIR", "/tbdata");
		System.setProperty("MAX_ENT_MODELS_DIR", "/models");
	}

	public ArrayList<String> getSearchTerms() {
		SearchTermProvider keywordProvider = new SearchTermProvider("", 0, this.title, this.bugReportContent);
		String termStr = keywordProvider.provideSearchQuery("TPR");
		ArrayList<String> searchTerms = MiscUtility.str2List(termStr);
		ArrayList<String> keywords = new ArrayList<>();
		for (String sterm : searchTerms) {
			if (sterm.length() >= 3) {
				sterm = sterm.toLowerCase();
				keywords.add(sterm);
				if (keywords.size() == TOPK) {
					break;
				}
			}
		}
		return keywords;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String repoName = "eclipse.jdt.core";
		int bugID = 15036;
		String title = "Bug 15036  ASTVisitor.preVisit and ASTVisitor.postVisit not called correctly";
		String bugReportContent = BugReportLoader.loadBugReport(repoName, bugID);
		PEKeywordSelector peSelector = new PEKeywordSelector(title, bugReportContent, 30);
		System.out.println(peSelector.getSearchTerms());
	}
}
