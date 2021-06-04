package blizzard.tests;

import org.junit.Test;
import blizzard.bug.report.classification.BugReportClassifier;
import blizzard.utility.BugReportLoader;
import static org.junit.Assert.assertTrue;

public class BugReportClassifierTest {

	@Test
	public void testBRClassificationNL() {
		String repoName = "eclipse.jdt.core";
		int bugID = 395663;
		String bugReport = BugReportLoader.loadBugReport(repoName, bugID);
		BugReportClassifier classifier = new BugReportClassifier(bugReport);
		boolean response = false;
		if (classifier.determineReportClass().equals("NL")) {
			response = true;
		}
		assertTrue(response);
	}
	
	
	@Test
	public void testBRClassificationPE() {
		String repoName = "eclipse.jdt.core";
		int bugID = 388085;
		String bugReport = BugReportLoader.loadBugReport(repoName, bugID);
		BugReportClassifier classifier = new BugReportClassifier(bugReport);
		boolean response = false;
		if (classifier.determineReportClass().equals("PE")) {
			response = true;
		}
		assertTrue(response);
	}
	
	@Test
	public void testBRClassificationST() {
		String repoName = "eclipse.jdt.core";
		int bugID = 427207;
		String bugReport = BugReportLoader.loadBugReport(repoName, bugID);
		BugReportClassifier classifier = new BugReportClassifier(bugReport);
		boolean response = false;
		if (classifier.determineReportClass().equals("ST")) {
			response = true;
		}
		assertTrue(response);
	}
}
