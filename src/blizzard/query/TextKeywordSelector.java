package blizzard.query;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import acer.coderank.query.expansion.ACERQueryProviderMinimal;
import blizzard.config.StaticData;
import blizzard.lucenecheck.LuceneSearcher;
import blizzard.text.normalizer.TextNormalizer;
import samurai.splitter.SamuraiSplitter;
import token.manager.TokenTracebackManager;
import blizzard.utility.ContentLoader;
import blizzard.utility.ItemSorter;
import blizzard.utility.MiscUtility;

public class TextKeywordSelector {

	String title;
	String bugDesc;
	int TOPK;
	String repoName;

	public TextKeywordSelector(String repoName, String title, String bugDesc,
			int TOPK, boolean otherMethod) {
		this.repoName = repoName;
		this.title = title;
		this.bugDesc = bugDesc;
		this.TOPK = TOPK;
	}

	public TextKeywordSelector(String repoName, String title, String bugDesc,
			int TOPK) {
		this.repoName = repoName;
		this.title = title;
		this.bugDesc = bugDesc;
		this.TOPK = TOPK;
	}

	@Deprecated
	protected ArrayList<String> performSanitization(ArrayList<String> terms) {
		// perform some sanitization on the query
		SamuraiSplitter ssplitter = new SamuraiSplitter(terms);
		HashMap<String, String> expanded = ssplitter.getSplittedTokenMap();
		HashSet<String> uniques = new HashSet<>();
		for (String key : expanded.keySet()) {
			String expandedSingle = expanded.get(key);
			if (expandedSingle.trim().length() > key.length()) {
				terms.addAll(MiscUtility.str2List(expandedSingle.toLowerCase()));
			} else {
				uniques.add(key.toLowerCase());
			}
		}
		return new ArrayList<String>(uniques);
	}

	@Deprecated
	protected ArrayList<String> removeDuplicates(ArrayList<String> keywords) {
		// remove duplicate keywords
		ArrayList<String> flagged = new ArrayList<>();
		for (int i = 0; i < keywords.size(); i++) {
			String target = keywords.get(i);
			String first = keywords.get(i).toLowerCase();
			for (int j = 0; j < keywords.size(); j++) {
				if (i == j)
					continue;
				String second = keywords.get(j).toLowerCase();
				if (first.startsWith(second) || first.endsWith(second)) {
					flagged.add(target);
					break;
				}
			}
		}
		keywords.removeAll(flagged);
		return keywords;
	}

	@Deprecated
	protected ArrayList<String> refineTokens(ArrayList<String> keywords) {
		ArrayList<String> refined = new ArrayList<>();
		TokenTracebackManager manager = new TokenTracebackManager();
		for (String keyword : keywords) {
			String rkeyword = manager.tracebackToken(keyword.toLowerCase());
			// if (!refined.contains(rkeyword)) {
			refined.add(rkeyword);
			// }
		}
		return refined;
	}

	public String getSearchTermsWithCR(int expansionSize) {

		String indexFolder = StaticData.HOME_DIR + "/Lucene-Index/" + repoName;
		String corpusFolder = StaticData.HOME_DIR + "/Corpus/" + repoName;
		String baselineQuery = new TextNormalizer(this.title).normalizeText();
		String normDesc = new TextNormalizer(this.bugDesc).normalizeText();

		ACERQueryProviderMinimal acer = new ACERQueryProviderMinimal(repoName,
				0, baselineQuery, indexFolder, corpusFolder, null);
		String sourceTerms = acer.getExtendedQuery(expansionSize);

		return normDesc + "\t" + sourceTerms;

	}

	protected ArrayList<String> getMiniCorpus(ArrayList<String> results) {
		ArrayList<String> corpusLines = new ArrayList<>();
		for (String fileURL : results) {
			String fileName = new File(fileURL).getName();
			String srcURL = StaticData.HOME_DIR + "/corpus/norm-class/"
					+ repoName + "/" + fileName;
			String srcContent = ContentLoader.loadFileContent(srcURL);
			// ArrayLis<String> lines=ContentLoader.getAllLinesOptList(srcURL);
			corpusLines.add(srcContent);
		}
		return corpusLines;
	}

	protected ArrayList<String> getTopK(String itemStr, int TopK) {
		ArrayList<String> candidates = new ArrayList<>();
		ArrayList<String> items = MiscUtility.str2List(itemStr);
		for (String sterm : items) {
			if (sterm.length() >= 3) {
				sterm = sterm.toLowerCase();
				candidates.add(sterm);
				if (candidates.size() == TOPK) {
					break;
				}
			}
		}
		return candidates;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}
}
