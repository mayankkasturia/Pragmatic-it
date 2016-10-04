

import java.io.IOException;
import static junit.framework.Assert.assertNotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;
import org.junit.Test;


public class QueryLanguageTestFinal {
	
	QueryLanguage queryLanguage = new QueryLanguage();
        static HashMap<List<String>, NaiveInvertedIndex> fileIndex = new HashMap<List<String>, NaiveInvertedIndex>();
        static ArrayList<String> fileNames = new ArrayList<String>();
        static NaiveInvertedIndex testIndex = new NaiveInvertedIndex();
        
        @BeforeClass
        public static void setUpClass() throws IOException {
        String corpusName = "test_files";
        //NaiveInvertedIndex testIndex = new NaiveInvertedIndex();
        fileIndex = SimpleEngine.getIndex(corpusName, "PS");
        HashMap<Integer, List<Integer>> finalList = new HashMap<>();
        Iterator itr = fileIndex.entrySet().iterator();
        while (itr.hasNext()) {
            Map.Entry entry = (Map.Entry) itr.next();
            testIndex = (NaiveInvertedIndex) entry.getValue();
            fileNames = (ArrayList<String>) entry.getKey();
        }
        String token[] = testIndex.getDictionary();
        for (String t : token) {
            finalList = (testIndex.getPostings(t));
        }

    }
	
	@Test
	public void testQueryParserToSearch_SingleWord() throws Exception {
                System.out.println("\n Test Query Parser(): for Single Word'");
                String query = "Explore";
                String toStem = query;
                List <String> expectedResult = new ArrayList<>();
                List <String> actualRes = new ArrayList<>();
                expectedResult.add("article1.json");
                expectedResult.add("article7.json");
                Set<String> actualResult = new TreeSet<>();
                String word = SimpleEngine.callPoterStem(toStem);
                actualResult = QueryLanguage.wordQuery(testIndex, word,"");
                Iterator iterator = actualResult.iterator();
                while (iterator.hasNext()) {
                actualRes.add(fileNames.get(Integer.parseInt(iterator.next().toString())));
                } 
		assertEquals(expectedResult, actualRes);
	}
        @Test
	public void testQueryParser_EmptyPhrase() {
            System.out.println("\n Test queryParser(): for Empty Phrase'");
		String query = "";
                String toStem = query;
                List <String> expectedResult = new ArrayList<>();
                List <String> actualRes = new ArrayList<>();
                Set<String> actualResult = new TreeSet<>();
                String word = SimpleEngine.callPoterStem(toStem);
                actualResult = QueryLanguage.phraseWordQuery(testIndex, word,fileNames);
                Iterator iterator = actualResult.iterator();
                while (iterator.hasNext()) {
                actualRes.add(fileNames.get(Integer.parseInt(iterator.next().toString())));
                } 
		assertEquals(expectedResult, actualRes);
	}
        
        @Test
	public void testQueryParserToSearch_LongPhrase() {
                System.out.println("\n Test Query Parser(): for Long Phrase'");
		String query = "Learn About the Park     Photos Multimedia     Photo";
                String toStem = query;
                List <String> expectedResult = new ArrayList<>();
                List <String> actualRes = new ArrayList<>();
                expectedResult.add("article1.json");
                Set<String> actualResult = new TreeSet<>();
                String word = SimpleEngine.callPoterStem(toStem);
                actualResult = QueryLanguage.phraseWordQuery(testIndex, word,fileNames);
                Iterator iterator = actualResult.iterator();
                while (iterator.hasNext()) {
                actualRes.add(fileNames.get(Integer.parseInt(iterator.next().toString())));
                } 
		assertEquals(expectedResult, actualRes);
	}


	@Test
	public void testWordQuery_NormalizeAndSearch_Reef() {
                System.out.println("\n Test Word Query(): to Normailise and search a word: Input: @#$#Reef#$# '");
		String query = "@#$#Reef#$#";
                String toStem = query;
                List <String> expectedResult = new ArrayList<>();
                List <String> actualRes = new ArrayList<>();
                expectedResult.add("article3.json");
                Set<String> actualResult = new TreeSet<>();
                String word = SimpleEngine.callPoterStem(toStem);
                actualResult = QueryLanguage.wordQuery(testIndex, word,"");
                Iterator iterator = actualResult.iterator();
                while (iterator.hasNext()) {
                actualRes.add(fileNames.get(Integer.parseInt(iterator.next().toString())));
                } 
		assertEquals(expectedResult, actualRes);
	}
@Test
	public void testWordQuery_SearchEmptyWord() {
                System.out.println("\n Test wordQuery(): Empty Word'");
		String query = "";
                String toStem = query;
                List <String> expectedResult = new ArrayList<>();
                List <String> actualRes = new ArrayList<>();
                Set<String> actualResult = new TreeSet<>();
                String word = SimpleEngine.callPoterStem(toStem);
                actualResult = QueryLanguage.wordQuery(testIndex, word,"");
                Iterator iterator = actualResult.iterator();
                while (iterator.hasNext()) {
                actualRes.add(fileNames.get(Integer.parseInt(iterator.next().toString())));
                } 
		assertEquals(expectedResult, actualRes);
	}

	@Test
	public void testPhraseWordQuery_ValidQuery_ReturnTwoDocs() {
                System.out.println("\n Test phraseQuery(): for a phrase returning two documents'");
		String query = "Explore This Park";
                String toStem = query;
                List <String> expectedResult = new ArrayList<>();
                List <String> actualRes = new ArrayList<>();
                expectedResult.add("article1.json");
                expectedResult.add("article7.json");
                Set<String> actualResult = new TreeSet<>();
                String word = SimpleEngine.callPoterStem(toStem);
                actualResult = QueryLanguage.phraseWordQuery(testIndex, word,fileNames);
                Iterator iterator = actualResult.iterator();
                while (iterator.hasNext()) {
                actualRes.add(fileNames.get(Integer.parseInt(iterator.next().toString())));
                } 
		assertEquals(expectedResult, actualRes);
	}
        @Test
	public void testPhraseWordQuery_EmptyPhrase() {
            System.out.println("\n Test phraseWord(): for Empty Phrase'");
		String query = "";
                String toStem = query;
                List <String> expectedResult = new ArrayList<>();
                List <String> actualRes = new ArrayList<>();
                Set<String> actualResult = new TreeSet<>();
                String word = SimpleEngine.callPoterStem(toStem);
                actualResult = QueryLanguage.phraseWordQuery(testIndex, word,fileNames);
                Iterator iterator = actualResult.iterator();
                while (iterator.hasNext()) {
                actualRes.add(fileNames.get(Integer.parseInt(iterator.next().toString())));
                } 
		assertEquals(expectedResult, actualRes);
	}

}
