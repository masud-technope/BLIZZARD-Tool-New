package blizzard.tests;

import org.junit.Test;
import blizzard.text.normalizer.TextNormalizer;

public class TextNormalizerTest {
	
	String texts="On Mars jar creation is considerably slower on Linux.\r\n" +  
			"We have a big jar with only the source files. This jar used to be created in a split second. Now it takes 1,5 Seconds or so. On Windows it seems to be unchanged.\r\n" +  
			"It is even faster on Windows and HDD than on my Linux box with an SSD.";
	
	@Test
	public void testNormalizeSimple() {
		TextNormalizer normalizer=new TextNormalizer(texts);
		System.out.println(normalizer.normalizeSimple());
	}
	
	@Test
	public void testNormalizeBaseline() {
		TextNormalizer normalizer=new TextNormalizer(texts);
		System.out.println(normalizer.normalizeBaseline());
	}
	
	@Test
	public void testNormalizeWithStemming() {
		TextNormalizer normalizer=new TextNormalizer(texts);
		System.out.println(normalizer.normalizeSimpleWithStemming());
	}
	
	@Test
	public void testNormalizeSimpleCode() {
		TextNormalizer normalizer=new TextNormalizer(texts);
		System.out.println(normalizer.normalizeSimpleCode());
	}
	
	@Test
	public void testNormalizeSimpleNonCode() {
		TextNormalizer normalizer=new TextNormalizer(texts);
		System.out.println(normalizer.normalizeSimpleNonCode());
	}
	
	@Test
	public void testNormalizeText() {
		TextNormalizer normalizer=new TextNormalizer(texts);
		System.out.println(normalizer.normalizeText());
	}
	
	@Test
	public void testNormalizeSimpleCodeDiscardSmall() {
		TextNormalizer normalizer=new TextNormalizer(texts);
		System.out.println(normalizer.normalizeSimpleCodeDiscardSmall(texts));
	}
		

}
