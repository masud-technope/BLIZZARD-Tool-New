package blizzard.lucenecheck;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import blizzard.utility.ContentLoader;
import blizzard.config.StaticData;

public class ClassResultRankMgr {

	String repoName;
	ArrayList<String> results;
	ArrayList<String> goldset;
	public static HashMap<String, String> keyMap = new HashMap<>();
	String keyfile;

	public ClassResultRankMgr(String repoName, ArrayList<String> results, ArrayList<String> goldset) {
		this.repoName = repoName;
		this.results = results;
		this.goldset = goldset;
		this.keyfile = StaticData.CORPUS_INDEX_KEY_MAPPING + "/" + this.repoName + ".ckeys";
		if (keyMap.isEmpty()) {
			// load only the HashMap is empty
			loadKeys();
		}
	}

	public void loadKeys() {
		ArrayList<String> lines = ContentLoader.getAllLinesOptList(this.keyfile);
		for (String line : lines) {
			String[] parts = line.split(":");
			String key = parts[0] + ".java";
			/* important insight - each line contains two : in windows file path. Be careful*/
			keyMap.put(key, parts[2].trim()); // startled me
		}
	}

	protected ArrayList<String> translateResults() {
		ArrayList<String> translated = new ArrayList<>();
		for (String fileURL : results) {
			String key = new File(fileURL).getName();
			if (keyMap.containsKey(key)) {
				String orgFileName = keyMap.get(key);
				orgFileName = orgFileName.replace('\\', '/');
				translated.add(orgFileName);
			}
		}
		return translated;
	}

	protected ArrayList<String> translateResults(int TOPK) {
		ArrayList<String> translated = new ArrayList<>();
		for (String fileURL : results) {
			String key = new File(fileURL).getName();
			if (keyMap.containsKey(key)) {
				String orgFileName = keyMap.get(key);
				orgFileName = orgFileName.replace('\\', '/');
				translated.add(orgFileName);
				if (translated.size() == TOPK)
					break;
			}
		}
		return translated;
	}

	protected ArrayList<String> translate2Dotted(ArrayList<String> itemset) {
		// translate the item set using dots
		ArrayList<String> temp = new ArrayList<>();
		for (String fileURL : itemset) {
			String cfileURL = fileURL.replace('\\', '.');
			cfileURL = cfileURL.replace('/', '.');
			temp.add(cfileURL);
		}
		return temp;
	}

	public int getFirstGoldRank() {
		int foundIndex = -1;
		int index = 0;
		ArrayList<String> results = translateResults();
		outer: for (String elem : results) {
			index++;
			for (String item : goldset) {
				if (elem.endsWith(item)) {
					foundIndex = index;
					break outer;
				}
			}
		}
		return foundIndex;
	}

	public int getFirstGoldRank(int TOPK) {
		int foundIndex = -1;
		int index = 0;
		ArrayList<String> results = translateResults(TOPK);
		outer: for (String elem : results) {
			index++;
			for (String item : goldset) {
				if (elem.endsWith(item)) {
					foundIndex = index;
					break outer;
				}
			}
		}
		return foundIndex;
	}

	protected ArrayList<Integer> getCorrectRanks() {
		ArrayList<Integer> foundIndices = new ArrayList<>();
		ArrayList<String> results = translateResults();
		int index = 0;
		for (String elem : results) {
			index++;
			for (String item : goldset) {
				if (elem.endsWith(item)) {
					foundIndices.add(index);
				}
			}
		}
		return foundIndices;
	}

	public ArrayList<Integer> getCorrectRanksDotted(int TOPK) {
		ArrayList<Integer> foundIndices = new ArrayList<>();
		ArrayList<String> cgoldset = translate2Dotted(this.goldset);
		this.results = translateResults(TOPK);
		ArrayList<String> cresults = translate2Dotted(this.results);
		int index = 0;
		for (String elem : cresults) {
			index++;
			for (String item : cgoldset) {
				if (elem.endsWith(item)) {
					foundIndices.add(index);
				}
			}
		}
		return foundIndices;
	}
}
