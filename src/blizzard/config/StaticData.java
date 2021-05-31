package blizzard.config;

public class StaticData {

	public static String HOME_DIR = "C:\\MyWorks\\MyResearch\\BugLocalization\\BLIZZARD\\BLIZZARD-Replication-Package-ESEC-FSE2018";
	public static String STACK_TRACE_DIR = HOME_DIR + "/BR-ST-StackTraces";
	public static String STOPWORD_DIR = HOME_DIR + "/data";
	public static String CORPUS_INDEX_KEY_MAPPING = HOME_DIR + "/Lucene-Index2File-Mapping";
	public static String INDEX_DIR = HOME_DIR + "/Lucene-Index";
	public static String CORPUS_DIR = HOME_DIR + "/Corpus";
	public static String GOLDSET_DIR = HOME_DIR + "/Goldset";

	public static double SIGNIFICANCE_THRESHOLD = 0.001;
	public static int DOI_TOPK_THRESHOLD = 5;

	public static int MAX_PE_SUGGESTED_QUERY_LEN = 30;
	public static int MAX_NL_SUGGESTED_QUERY_LEN = 8;
	public static int MAX_ST_SUGGESTED_QUERY_LEN = 11;

	public static int MAX_QUERY_LEN = 1024;
	public static int MAX_ST_ENTRY_LEN = 10;
	public static int MAX_ST_THRESHOLD = 10;

	public static int TOKEN_LEN_THRESHOLD = 3;

	public static boolean DISCARD_TOO_GOOD = false;

	public static int CROWD_API_OCC_THRESHOLD = 5;

}
