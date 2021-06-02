package blizzard.tests;

import org.junit.Test;
import blizzard.similarity.MyTokenizer;

public class TokenizerTest {

	@Test
	public void testTokenizeTexts() {
		String texts = "This is a simple texts LittleCodeItem";
		MyTokenizer tokenizer = new MyTokenizer(texts);
		System.out.println(tokenizer.tokenize_text_item());
	}
	
	@Test
	public void testTokenizeCodeElements() {
		String texts = "ArrayList List EmailAddress EmailClient";
		MyTokenizer tokenizer = new MyTokenizer(texts);
		System.out.println(tokenizer.tokenize_code_item());
	}
}
