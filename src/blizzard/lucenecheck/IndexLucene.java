package blizzard.lucenecheck;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import blizzard.config.StaticData;

public class IndexLucene {

	String repoName;
	String index;
	String docs;
	public int totalIndexed = 0;

	public IndexLucene(String repoName) {
		this.index = StaticData.INDEX_DIR + "/" + repoName;
		this.docs = StaticData.CORPUS_DIR + "/" + repoName;
		// make the index offline, too risky!
		// this.makeIndexFolder(repoName);
		System.out.println("Index:" + this.index);
		System.out.println("Docs:" + this.docs);
	}

	public IndexLucene(String indexFolder, String docsFolder) {
		this.index = indexFolder;
		this.docs = docsFolder;
	}

	protected void makeIndexFolder(String repoName) {
		new File(this.index + "/" + repoName).mkdir();
		this.index = this.index + "/" + repoName;
	}

	public void indexCorpusFiles() {
		try {
			Directory dir = FSDirectory.open(new File(index).toPath());
			Analyzer analyzer = new StandardAnalyzer();
			// Analyzer analyzer=new EnglishAnalyzer(Version.LUCENE_44);
			IndexWriterConfig config = new IndexWriterConfig(analyzer);
			IndexWriter writer = new IndexWriter(dir, config);
			indexDocs(writer, new File(this.docs));
			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void clearIndexFiles() {
		File[] files = new File(this.index).listFiles();
		for (File f : files) {
			f.delete();
		}
		System.out.println("Index cleared successfully.");
	}

	protected void indexDocs(IndexWriter writer, File file) {
		if (file.canRead()) {
			if (file.isDirectory()) {
				String[] files = file.list();
				if (files != null) {
					for (int i = 0; i < files.length; i++) {
						indexDocs(writer, new File(file, files[i]));
					}
				}
			} else {
				FileInputStream fis;
				try {
					fis = new FileInputStream(file);
				} catch (FileNotFoundException fnfe) {
					return;
				}
				try {
					Document doc = new Document();

					Field pathField = new StringField("path", file.getPath(),
							Field.Store.YES);
					doc.add(pathField);

					doc.add(new TextField("contents", new BufferedReader(
							new InputStreamReader(fis, "UTF-8"))));

					writer.addDocument(doc);

					totalIndexed++;

				} catch (UnsupportedEncodingException e) {

					e.printStackTrace();
				} catch (CorruptIndexException e) {

					e.printStackTrace();
				} catch (IOException e) {

					e.printStackTrace();
				} finally {
					try {
						fis.close();
					} catch (IOException e) {

						e.printStackTrace();
					}
				}
			}
		}
	}

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		String repoName = "tomcat70";

		String docs = StaticData.HOME_DIR + "/Corpus/" + repoName;
		String index = StaticData.HOME_DIR + "/Lucene-Index/" + repoName;

		IndexLucene indexer = new IndexLucene(index, docs);
		indexer.indexCorpusFiles();
		System.out.println("Files indexed:" + indexer.totalIndexed);
		long end = System.currentTimeMillis();
		System.out.println("Time needed:" + (end - start) / 1000 + " s");
	}
}
