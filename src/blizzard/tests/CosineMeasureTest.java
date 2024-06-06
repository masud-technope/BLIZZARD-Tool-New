package blizzard.tests;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import blizzard.similarity.CosineMeasure;
import blizzard.similarity.CosineSimilarityMeasure;

public class CosineMeasureTest {
	
	@Test
	public void testCosineSimilarity() {
		int[] list1= {4,5,6,1,9};
		int [] list2= {8,9,3,6,2};
		double similarity = CosineMeasure.getCosineSimilarity(list1, list2);
		System.out.println("Similarity"+ similarity);
	}
	
	@Test
	public void testGetMode() {
		int list[]= {3,4};
		double mode=CosineMeasure.getMode(list);
		System.out.println(mode);
		assertEquals(5, (long)CosineMeasure.getMode(list));
	}
	
	@Test
	public void testCosineSimilarityForTexts() {
		String title1 = "This is a simple string, nothing serious!";
		String title2 = "To be or not to be, that is the question!";
		CosineSimilarityMeasure measure = new CosineSimilarityMeasure(title1,
				title2);
		double similarity = measure.get_cosine_similarity_score(true);
		System.out.println("Similarity score:" + similarity);
	}
	
	
	

}
