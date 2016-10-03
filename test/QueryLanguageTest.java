/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.List;
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
public class QueryLanguageTest {
    
    public QueryLanguageTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of main method, of class QueryLanguage.
     */
    @Test
    public void testMain() throws Exception {
        System.out.println("main");
        String[] args = null;
        QueryLanguage.main(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of queryParser method, of class QueryLanguage.
     */
    @Test
    public void testQueryParser() throws Exception {
        System.out.println("queryParser");
        NaiveInvertedIndex index = null;
        String query = "";
        List<String> fileNames = null;
        QueryLanguage.queryParser(index, query, fileNames);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of readQueryFromUser method, of class QueryLanguage.
     */
    @Test
    public void testReadQueryFromUser() {
        System.out.println("readQueryFromUser");
        String expResult = "index";
        String result = QueryLanguage.readQueryFromUser();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of wordQuery method, of class QueryLanguage.
     */
    @Test
    public void testWordQuery() {
        System.out.println("wordQuery");
        NaiveInvertedIndex index = null;
        String word = "";
        Set expResult = null;
        Set result = QueryLanguage.wordQuery(index, word);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of phraseWordQuery method, of class QueryLanguage.
     */
    @Test
    public void testPhraseWordQuery() {
        System.out.println("phraseWordQuery");
        NaiveInvertedIndex index = null;
        String query = "fine philosophy";
        List<String> fileNames = null;
        Set expResult = null;
        expResult.add("chapter10.txt");
        Set result = QueryLanguage.phraseWordQuery(index, query, fileNames);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of callStemmer method, of class QueryLanguage.
     */
    @Test
    public void testCallStemmer() throws Exception {
        System.out.println("callStemmer");
        QueryLanguage.callStemmer();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getVocabulary method, of class QueryLanguage.
     */
    @Test
    public void testGetVocabulary() {
        System.out.println("getVocabulary");
        NaiveInvertedIndex index = null;
        QueryLanguage.getVocabulary(index);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
