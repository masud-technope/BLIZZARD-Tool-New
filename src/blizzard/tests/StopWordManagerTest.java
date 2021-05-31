package blizzard.tests;

import org.junit.Test;
import blizzard.stopwords.StopWordManager;

public class StopWordManagerTest {
	
	@Test
	public void testStopWordRemoval() {
		// TODO Auto-generated method stub
		StopWordManager manager = new StopWordManager();
		String str = "statement protected java Boolean lang expression Quick "
				+ "Invert operator omits AdvancedQuickAssistProcessor";
		System.out.println(manager.getRefinedSentence(str));
	}
}
