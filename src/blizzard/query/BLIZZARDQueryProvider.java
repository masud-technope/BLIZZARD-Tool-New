package blizzard.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import blizzard.bug.report.classification.BugReportClassifier;
import blizzard.bug.report.classification.ExceptionExtractor;
import blizzard.bug.report.classification.TraceLoader;
import blizzard.config.StaticData;
import blizzard.text.normalizer.TextNormalizer;
import blizzard.utility.BugReportLoader;
import blizzard.utility.ContentLoader;
import blizzard.utility.ItemSorter;
import blizzard.utility.MiscUtility;
import blizzard.query.PEKeywordSelector;

public class BLIZZARDQueryProvider {

	String repoName;
	int bugID;
	String reportGroup;
	public String reportContent;
	String bugReportTitle;
	public boolean hasException = false;

	public BLIZZARDQueryProvider(String repoName, int bugID, String bugtitle, String reportContent) {
		this.repoName = repoName;
		this.bugID = bugID;
		this.bugReportTitle = bugtitle;
		this.reportContent = reportContent;
		this.reportGroup = new BugReportClassifier(this.reportContent).determineReportClass();
	}

	protected ArrayList<String> getSalientItemsFromST(ArrayList<String> traces) {
		TraceClassesSelector tcSelector = new TraceClassesSelector(traces, false);
		HashMap<String, Double> itemMapC = tcSelector.getSalientClasses();
		return getTopKItems(itemMapC);
	}

	public String provideBLIZZARDQuery() {

		// System.out.println(title);
		ArrayList<String> blizzardKeywords = new ArrayList<>();
		System.out.println("Report group : " + reportGroup);

		switch (reportGroup) {
		case "ST":
			ArrayList<String> traces = TraceLoader.loadStackTraces(repoName, bugID);
			ArrayList<String> salientItems = getSalientItemsFromST(traces);

			HashSet<String> exceptions = ExceptionExtractor.getExceptionMessages(this.reportContent);

			// adding the title
			if (!bugReportTitle.isEmpty()) {
				String normTitle = new TextNormalizer(bugReportTitle).normalizeSimple();
				blizzardKeywords.add(normTitle);
			}

			// adding the exception
			if (!exceptions.isEmpty()) {
				hasException = true;
				blizzardKeywords.addAll(exceptions);
			}

			// adding the trace keywords
			blizzardKeywords.addAll(salientItems);
			break;

		case "NL":
			// invoke ACER (basic version without ML)
			TextKeywordSelector kwSelector = new TextKeywordSelector(repoName, bugReportTitle, reportContent,
					StaticData.MAX_NL_SUGGESTED_QUERY_LEN);
			String extended = kwSelector.getSearchTermsWithCR(StaticData.MAX_NL_SUGGESTED_QUERY_LEN);
			salientItems = MiscUtility.str2List(extended);
			blizzardKeywords.addAll(salientItems);
			break;

		case "PE":
			// invoke STRICT (basic version without ML)
			PEKeywordSelector peSelector = new PEKeywordSelector(bugReportTitle, reportContent,
					StaticData.MAX_PE_SUGGESTED_QUERY_LEN);
			salientItems = peSelector.getSearchTerms();
			blizzardKeywords.addAll(salientItems);
			break;

		default:
			break;
		}

		return MiscUtility.list2Str(blizzardKeywords);
	}

	protected String cleanEntity(String itemName) {
		String[] parts = itemName.split("\\p{Punct}+|\\s+|\\d+");
		return MiscUtility.list2Str(parts);
	}

	protected ArrayList<String> getTopKItems(HashMap<String, Double> tokendb) {
		List<Map.Entry<String, Double>> sorted = ItemSorter.sortHashMapDouble(tokendb);
		ArrayList<String> selected = new ArrayList<>();
		for (int i = 0; i < sorted.size(); i++) {
			Map.Entry<String, Double> entry = sorted.get(i);
			ArrayList<String> keyTokens = MiscUtility.str2List(entry.getKey());
			for (String keyToken : keyTokens) {
				if (keyToken.length() >= StaticData.TOKEN_LEN_THRESHOLD) {
					if (!selected.contains(keyToken)) {
						selected.add(keyToken);
					}
				}
			}
			// selected.add(entry.getKey());
			if (reportGroup.equals("ST")) {
				if (selected.size() == StaticData.MAX_ST_SUGGESTED_QUERY_LEN)
					break;
			}
		}
		return selected;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String repoName = "eclipse.jdt.ui";
		int bugID = 187316;
		String brFile = StaticData.HOME_DIR + "/BR-Raw/" + repoName + "/" + bugID + ".txt";
		String title = BugReportLoader.loadBRTitle(repoName, bugID);
		String bugReportContent = ContentLoader.loadFileContent(brFile);

		BLIZZARDQueryProvider blizzardProvider = new BLIZZARDQueryProvider(repoName, bugID, title, bugReportContent);
		System.out.println(blizzardProvider.provideBLIZZARDQuery());
	}
}
