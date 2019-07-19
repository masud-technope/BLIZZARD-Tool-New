package blizzard.bug.report.classification;

import java.io.File;
import java.util.ArrayList;
import blizzard.config.StaticData;
import blizzard.utility.ContentLoader;

public class TraceLoader {

	public static ArrayList<String> loadStackTraces(String repoName, int bugID) {
		ArrayList<String> straces = new ArrayList<>();
		try {
			String traceFile = StaticData.STACK_TRACE_DIR + "/" + repoName + "/" + bugID + ".txt";
			File tFile = new File(traceFile);
			if (tFile.exists()) {
				straces = ContentLoader.getAllLinesList(traceFile);
			}
		} catch (Exception exc) {
			System.err.println("Failed to load the stack traces!" + exc.getMessage());
		}
		return straces;
	}

}
