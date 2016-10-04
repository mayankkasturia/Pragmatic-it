

import static junit.framework.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;


public class QueryLanguageTestFinal {
	
	QueryLanguage queryLanguage = new QueryLanguage();

	@Test
	public void testMain() {
	
	}

	@Test
	public void testStringParser() {
		
	}

	@Test
	public void testQueryParserToQuit() throws Exception {
		NaiveInvertedIndex index = new NaiveInvertedIndex();
		String query = ":q";
		List<String> fileNames = new ArrayList<String>();
		QueryLanguage.queryParser(index, query, fileNames);
		
	}
	
	@Test
	public void testQueryParserToSearch() throws Exception {
		NaiveInvertedIndex index = new NaiveInvertedIndex();
		String query = "find this";
		List<String> fileNames = new ArrayList<String>();
		QueryLanguage.queryParser(index, query, fileNames);
		
	}

	@Test
	public void testReadQueryFromUser() {
		
		String query = QueryLanguage.readQueryFromUser();
		assertNotNull(query);
	}

	@Test
	public void testWordQuery() {
		
	}

	@Test
	public void testPhraseWordQuery() {
		NaiveInvertedIndex index = new NaiveInvertedIndex();
		String query = "";
		List<String> fileNames = new ArrayList<String>();
		QueryLanguage.phraseWordQuery(index, query, fileNames);
	}

	@Test
	public void testCallStemmer() throws Exception{
		QueryLanguage.callStemmer();
		
	}

	@Test
	public void testGetVocabulary() {
		NaiveInvertedIndex NaiveInvertedIndex = new NaiveInvertedIndex();
		QueryLanguage.getVocabulary(NaiveInvertedIndex);
	}

}
