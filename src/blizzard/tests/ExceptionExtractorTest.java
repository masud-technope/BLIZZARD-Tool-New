package blizzard.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.util.HashSet;
import org.junit.Test;
import blizzard.bug.report.classification.ExceptionExtractor;
import blizzard.bug.report.classification.TraceLoader;
import blizzard.utility.BugReportLoader;

public class ExceptionExtractorTest {
	
	@Test
	public void testExtractException() {
		String repoName = "eclipse.jdt.core";
		int bugID = 380048;
		String bugReport = BugReportLoader.loadBugReport(repoName, bugID);
		//System.out.println(bugReport);
		HashSet<String> messages = ExceptionExtractor.getExceptionMessages(bugReport); 
		System.out.println(messages);
		boolean hasMessage=false;
		if(!messages.isEmpty()) {
			hasMessage=true;
		}
		assertTrue(hasMessage);
	}
	
	@Test
	public void testNotExtractException() {
		String repoName = "eclipse.jdt.core";
		int bugID = 384317;
		String bugReport = BugReportLoader.loadBugReport(repoName, bugID);
		//System.out.println(bugReport);
		HashSet<String> messages = ExceptionExtractor.getExceptionMessages(bugReport); 
		System.out.println(messages);
		boolean hasMessage=false;
		if(!messages.isEmpty()) {
			hasMessage=true;
		}
		assertFalse(hasMessage);
	}
	
	

}
