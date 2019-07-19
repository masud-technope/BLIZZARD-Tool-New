package blizzard.bug.report.classification;

import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExceptionExtractor {

	public static HashSet<String> getExceptionMessages(String reportDesc) {
		HashSet<String> exceptions = new HashSet<>();
		String excepRegex = "(.)+Exception";
		Pattern p = Pattern.compile(excepRegex);
		Matcher m = p.matcher(reportDesc);
		while (m.find()) {
			String exception = reportDesc.substring(m.start(), m.end());
			String[] parts = exception.split("\\p{Punct}+|\\d+|\\s+");
			// System.out.println(exception);
			for (String part : parts) {
				if (part.endsWith("Exception") || part.endsWith("Error")) {
					if (part.equals("Exception") || part.equals("Error")) {
						// avoid the generic exception
					} else {
						exceptions.add(part);
					}
				}
			}
		}
		return exceptions;
	}
}
