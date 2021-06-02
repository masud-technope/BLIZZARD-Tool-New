package blizzard.tests;

import org.junit.Test;

import blizzard.text.normalizer.Stemmer;

public class StemmerTest {

	String texts = "Mars jar creation considerably slower Linux big jar source files jar created split takes Seconds Windows unchanged faster Windows HDD Linux box SSD";

	@Test
	public void testStemming() {
		Stemmer stemmer = new Stemmer();
		String[] words = texts.split("\\s+");
		for (String word : words) {
			String stemmed = stemmer.stripAffixes(word);
			System.out.println(word+"=>"+stemmed);
		}
	}
}
