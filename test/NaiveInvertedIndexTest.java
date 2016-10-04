/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//import  QueryLanguage.readQueryFromUser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author manikhanuja
 */
public class NaiveInvertedIndexTest {

    static HashMap<List<String>, NaiveInvertedIndex> fileIndex = new HashMap<List<String>, NaiveInvertedIndex>();
    static ArrayList<String> fileNames = new ArrayList<String>();

    public NaiveInvertedIndexTest() {
    }

    @BeforeClass
    public static void setUpClass() throws IOException {
        String corpusName = "test_files";
        NaiveInvertedIndex testIndex = new NaiveInvertedIndex();
        fileIndex = SimpleEngine.getIndex(corpusName, "PS");

    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws IOException {

    }

    @After
    public void tearDown() {
    }

    /**
     * Test of addTerm method, of class NaiveInvertedIndex.
     */
    /**
     * Test of getPostings method, of class NaiveInvertedIndex.
     */
    @Test
    public void testGetPostings_ValidString() {
        System.out.println("\n getPostings(): for word: 'Studies'");
        String term = "studi";
        NaiveInvertedIndex testIndex = new NaiveInvertedIndex();
        HashMap<Integer, List<Integer>> expResult = new HashMap<>();
        List<Integer> a = new ArrayList<>();
        a.add(2);
        expResult.put(1, a);
        HashMap<Integer, List<Integer>> finalList = new HashMap<>();
        Iterator itr = fileIndex.entrySet().iterator();
        while (itr.hasNext()) {
            Map.Entry entry = (Map.Entry) itr.next();
            testIndex = (NaiveInvertedIndex) entry.getValue();
            fileNames = (ArrayList<String>) entry.getKey();
        }
        //String token[] = testIndex.getDictionary();
        finalList = (testIndex.getPostings(term));
        System.out.println("Actual Result " + finalList);
        System.out.println("Expected Result " + expResult);
        assertEquals(expResult, finalList);
    }

    @Test
    public void testGetPostings_forEntireDictionary() {
        System.out.println("\n getPostings(): for word: 'Studies'");
        NaiveInvertedIndex testIndex = new NaiveInvertedIndex();
        HashMap<Integer, List<Integer>> expResult = new HashMap<>();
        List<Integer> a = new ArrayList<>();
        List<Integer> b = new ArrayList<>();
        a.add(2);
        a.add(6);
        b.add(2);
        expResult.put(0, a);
        expResult.put(2, b);
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
        System.out.println("\n Expected Result " + expResult.keySet());
        assertEquals(expResult, finalList);
    }

    @Test
    public void testGetPostings_InvalidString() {
        System.out.println("\n getPostings(): Verfiy for Invalid term");
        String term = "adfkjelc";
        NaiveInvertedIndex testIndex = new NaiveInvertedIndex();
        HashMap<Integer, List<Integer>> expResult = null;
        HashMap<Integer, List<Integer>> finalList = new HashMap<>();
        Iterator itr = fileIndex.entrySet().iterator();
        while (itr.hasNext()) {
            Map.Entry entry = (Map.Entry) itr.next();
            testIndex = (NaiveInvertedIndex) entry.getValue();
            fileNames = (ArrayList<String>) entry.getKey();
        }
        finalList = (testIndex.getPostings(term));
        System.out.println("Actual Result " + finalList);
        System.out.println("Expected Result " + expResult);
        assertEquals(expResult, finalList);
    }

    @Test
    public void testGetPostings_EmptyString() {
        System.out.println("\n getPostings(): Verify for Empty String");
        String term = "";
        NaiveInvertedIndex testIndex = new NaiveInvertedIndex();
        HashMap<Integer, List<Integer>> expResult = null;
        List<Integer> a = new ArrayList<>();
        HashMap<Integer, List<Integer>> finalList = new HashMap<>();
        Iterator itr = fileIndex.entrySet().iterator();
        while (itr.hasNext()) {
            Map.Entry entry = (Map.Entry) itr.next();
            testIndex = (NaiveInvertedIndex) entry.getValue();
            fileNames = (ArrayList<String>) entry.getKey();
        }
        finalList = (testIndex.getPostings(term));
        System.out.println("Actual Result " + finalList);
        System.out.println("Expected Result " + expResult);
        assertEquals(expResult, finalList);
    }

    /**
     * Test of getTermCount method, of class NaiveInvertedIndex.
     */
    @Test
    public void testGetTermCount() {
        System.out.println("\n getTermCount(): Validate the term count");
        String term = "";
        int count;
        NaiveInvertedIndex testIndex = new NaiveInvertedIndex();
        int expResult = 16;
        for (Map.Entry entry : fileIndex.entrySet()) {
            testIndex = (NaiveInvertedIndex) entry.getValue();
            fileNames = (ArrayList<String>) entry.getKey();
        }

        count = (testIndex.getTermCount());
        System.out.println("Actual Result " + count);
        System.out.println("Expected Result " + expResult);
        assertEquals(expResult, count);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getDocumentId method, of class NaiveInvertedIndex.
     */
    @Test
    public void testGetDocumentId_ValidTerm() {
        System.out.println("\n  getDocumentIds(): Validation for fetching documentIds for a word: 'Studies'");
        String term = "studi";
        NaiveInvertedIndex testIndex = new NaiveInvertedIndex();
        Set<Integer> expResult = new HashSet<>();
        expResult.add(1);
        Set<Integer> finalList;
        Iterator itr = fileIndex.entrySet().iterator();
        while (itr.hasNext()) {
            Map.Entry entry = (Map.Entry) itr.next();
            testIndex = (NaiveInvertedIndex) entry.getValue();
            fileNames = (ArrayList<String>) entry.getKey();
        }
        //String token[] = testIndex.getDictionary();
        finalList = (testIndex.getDocumentId(term));
        System.out.println("Actual Result " + finalList);
        System.out.println("Expected Result " + expResult);
        assertEquals(expResult, finalList);
    }

    @Test(expected = NullPointerException.class)
    public void testGetDocumentId_InvalidTerm() {
        System.out.println("\n getDocumentIds(): Validation for NullPointerException in function");
        String term = "adfdafd";
        NaiveInvertedIndex testIndex = new NaiveInvertedIndex();
        Set<Integer> expResult = new HashSet<>();
        //expResult.add(null);
        Set<Integer> finalList;
        Iterator itr = fileIndex.entrySet().iterator();
        while (itr.hasNext()) {
            Map.Entry entry = (Map.Entry) itr.next();
            testIndex = (NaiveInvertedIndex) entry.getValue();
            fileNames = (ArrayList<String>) entry.getKey();
        }
        //String token[] = testIndex.getDictionary();
        finalList = (testIndex.getDocumentId(term));
    }

    /**
     * Test of getDictionary method, of class NaiveInvertedIndex.
     */
    @Test
    public void testGetDictionary() {
        String[] expResult = {"reef", "product", "learn", "about", "count", "photo", "the", "biotechnician", "multimedia", "studi", "thi", "coral", "and", "plant", "explor", "park"};
        System.out.println("\n getDictionary(): Validation for good input");
        NaiveInvertedIndex testIndex = new NaiveInvertedIndex();
        Iterator itr = fileIndex.entrySet().iterator();
        while (itr.hasNext()) {
            Map.Entry entry = (Map.Entry) itr.next();
            testIndex = (NaiveInvertedIndex) entry.getValue();
            fileNames = (ArrayList<String>) entry.getKey();
        }
        String token[] = testIndex.getDictionary();
        System.out.println("Print Actual Dictionary");
        for (String a : token) {
            System.out.print(" " + a);
        }
        System.out.println("\n Print Expected Dictionary");
        for (String a : expResult) {
            System.out.print(" " + a);
        }
        assertArrayEquals(expResult, token);
    }

}
