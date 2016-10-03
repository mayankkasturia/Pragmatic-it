/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.HashMap;
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
public class NaiveInvertedIndexTest {
    
    public NaiveInvertedIndexTest() {
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
     * Test of addTerm method, of class NaiveInvertedIndex.
     */
    @Test
    public void testAddTerm() {
        System.out.println("addTerm");
        String term = "";
        Integer documentID = null;
        int pos = 0;
        NaiveInvertedIndex instance = new NaiveInvertedIndex();
        instance.addTerm(term, documentID, pos);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPostings method, of class NaiveInvertedIndex.
     */
    @Test
    public void testGetPostings() {
        System.out.println("getPostings");
        String term = "";
        NaiveInvertedIndex instance = new NaiveInvertedIndex();
        HashMap<Integer, List<Integer>> expResult = null;
        HashMap<Integer, List<Integer>> result = instance.getPostings(term);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTermCount method, of class NaiveInvertedIndex.
     */
    @Test
    public void testGetTermCount() {
        System.out.println("getTermCount");
        NaiveInvertedIndex instance = new NaiveInvertedIndex();
        int expResult = 0;
        int result = instance.getTermCount();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDocumentId method, of class NaiveInvertedIndex.
     */
    @Test
    public void testGetDocumentId() {
        System.out.println("getDocumentId");
        String term = "";
        NaiveInvertedIndex instance = new NaiveInvertedIndex();
        Set<Integer> expResult = null;
        Set<Integer> result = instance.getDocumentId(term);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDictionary method, of class NaiveInvertedIndex.
     */
    @Test
    public void testGetDictionary() {
        System.out.println("getDictionary");
        NaiveInvertedIndex instance = new NaiveInvertedIndex();
        String[] expResult = null;
        String[] result = instance.getDictionary();
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
