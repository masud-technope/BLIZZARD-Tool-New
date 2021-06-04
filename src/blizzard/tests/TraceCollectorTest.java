package blizzard.tests;

import static org.junit.Assert.assertNotNull;
import java.util.ArrayList;
import org.junit.Test;
import blizzard.bug.report.classification.TraceCollector;
import blizzard.bug.report.classification.TraceLoader;
import blizzard.config.StaticData;
import blizzard.utility.BugReportLoader;

public class TraceCollectorTest {

	@Test
	public void testCollectTraces() {
		String repoName = "eclipse.jdt.core";
		int bugID = 380048;
		String bugReport = BugReportLoader.loadBugReport(repoName, bugID);
		TraceCollector traceCollector = new TraceCollector(null);
		ArrayList<String> entries = traceCollector.extractStackTraces(bugReport);
		System.out.println(entries);
		assertNotNull(entries);
	}

	@Test
	public void testCollectTraceEntries() {
		String repoName = "eclipse.jdt.core";
		int bugID = 88845;
		String traceFile = StaticData.STACK_TRACE_DIR + "/" + repoName + "/" + bugID;
		TraceCollector traceCollector = new TraceCollector(traceFile);
		assertNotNull(traceCollector.collectTraceEntries());
	}
	
	@Test
	public void testTraceLoader() {
		String repoName = "eclipse.jdt.core";
		int bugID = 384317;
		assertNotNull(TraceLoader.loadStackTraces(repoName, bugID)); 
	}

}
