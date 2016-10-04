/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
public class PorterStemmerTest {
    
    public PorterStemmerTest() {
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
     * Test of processToken method, of class PorterStemmer.
     */
    @Test
    public void testProcessToken_step1a_1() {
        System.out.println("processToken: SSES->SS example caresses->caress:");
        String token = "caresses";
        String expResult = "caress";
        String result = PorterStemmer.processToken(token);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    @Test
    public void testProcessToken_step1a_2() {
        System.out.println("processToken: 1a.b ends with ies --> i: ponies --> poni");
        String token = "ponies";
        String expResult = "poni";
        String result = PorterStemmer.processToken(token);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    @Test
    public void testProcessToken_step1c_1() {
        System.out.println("processToken: ends with y: happy --> happi");
        String token = "happy";
        String expResult = "happi";
        String result = PorterStemmer.processToken(token);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    
    @Test
    public void testProcessToken_step1c_1b() {
        System.out.println("processToken: ends with y: sky --> sky");
        String token = "sky";
        String expResult = "sky";
        String result = PorterStemmer.processToken(token);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    @Test
    public void testProcessToken_step1a_3() {
        System.out.println("processToken: step 1a.3 - token ends with s and not ss: cats --> cat");
        String token = "cats";
        String expResult = "cat";
        String result = PorterStemmer.processToken(token);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    
    @Test
    public void testProcessToken_step1a_c() {
        System.out.println("processToken: SS->SS: caress --> caress");
        String token = "caress";
        String expResult = "caress";
        String result = PorterStemmer.processToken(token);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    @Test
    public void testProcessToken_step1b_1() {
        System.out.println("processToken: ends with eed --> e: agreed --> agree");
        String token = "agreed";
        String expResult = "agree";
        String result = PorterStemmer.processToken(token);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    
    @Test
    public void testProcessToken_step1b_2() {
        System.out.println("processToken: ends with ed: plastered --> plaster");
        String token = "plastered";
        String expResult = "plaster";
        String result = PorterStemmer.processToken(token);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    
    @Test
    public void testProcessToken_step1b_3() {
        System.out.println("processToken: ends with ing: motoring --> motor");
        String token = "motoring";
        String expResult = "motor";
        String result = PorterStemmer.processToken(token);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    
    @Test
    public void testProcessToken_step1b_3b() {
        System.out.println("processToken: ends with ing: sing --> sing");
        String token = "sing";
        String expResult = "sing";
        String result = PorterStemmer.processToken(token);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    
    @Test
    public void testProcessToken_step1b_4a() {
        System.out.println("If the second or third of the rules in Step 1b");
        System.out.println("processToken: ends with at-->ate: conflat(ed) --> conflate: further processing will output conflat");
        String token = "conflated";
        String expResult = "conflat";
        String result = PorterStemmer.processToken(token);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    
    @Test
    public void testProcessToken_step1b_4b() {
        System.out.println("If the second or third of the rules in Step 1b");
        System.out.println("processToken: ends with bl-->ble: troubl(ed) --> trouble");
        String token = "troubled";
        String expResult = "trouble";
        String result = PorterStemmer.processToken(token);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    
    @Test
    public void testProcessToken_step1b_4c() {
        System.out.println("If the second or third of the rules in Step 1b");
        System.out.println("processToken: ends with iz-->ize: siz(ed) --> size");
        String token = "sized";
        String expResult = "size";
        String result = PorterStemmer.processToken(token);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }

    @Test
    public void testProcessToken_step1b_4d() {
        System.out.println("If the second or third of the rules in Step 1b");
        System.out.println("processToken: (*d and not (*L or *S or *Z)): hopp(ing) --> hop and after further processing hope");
        String token = "hopping";
        String expResult = "hope";
        String result = PorterStemmer.processToken(token);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    
    @Test
    public void testProcessToken_step1b_4d_1() {
        System.out.println("If the second or third of the rules in Step 1b");
        System.out.println("processToken: (*d and not (*L or *S or *Z)): fizz(ed) --> fizz");
        String token = "fizzed";
        String expResult = "fizz";
        String result = PorterStemmer.processToken(token);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    
    @Test
    public void testProcessToken_step1b_4e() {
        System.out.println("If the second or third of the rules in Step 1b");
        System.out.println("processToken: (m=1 and *o): fil(ing) --> file");
        String token = "filing";
        String expResult = "file";
        String result = PorterStemmer.processToken(token);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    
    @Test
    public void testProcessToken_step2_a() {
        System.out.println("processToken: (m>0) ATIONAL --> ATE: relational -> relate and further processing will output relat");
        String token = "relational";
        String expResult = "relat";
        String result = PorterStemmer.processToken(token);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    
    @Test
    public void testProcessToken_step2_b() {
        System.out.println("processToken: (m>0) TIONAL --> TION: conditional -> condition");
        String token = "conditional";
        String expResult = "condition";
        String result = PorterStemmer.processToken(token);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    
    @Test
    public void testProcessToken_step2_c() {
        System.out.println("processToken: (m>0) ENCI --> ENCE: valenci -> valence and further processing will output:valenc ");
        String token = "valenci";
        String expResult = "valenc";
        String result = PorterStemmer.processToken(token);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    
    @Test
    public void testProcessToken_step2_d() {
        System.out.println("processToken: (m>0) ANCI --> ANCE: hesitanci -> hesitance and further processing will stem to:hesit ");
        String token = "hesitanci";
        String expResult = "hesit";
        String result = PorterStemmer.processToken(token);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    
    @Test
    public void testProcessToken_step2_e() {
        System.out.println("processToken: (m>0) IZER --> IZE: digitizer -> digitize: final stem will be: digit");
        String token = "digitizer";
        String expResult = "digit";
        String result = PorterStemmer.processToken(token);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    
    @Test
    public void testProcessToken_step2_f() {
        System.out.println("processToken: (m>0) ABLI --> ABLE: conformabli -> conformable:final stem will be: conform");
        String token = "conformabli";
        String expResult = "conform";
        String result = PorterStemmer.processToken(token);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    
    @Test
    public void testProcessToken_step2_g() {
        System.out.println("processToken: (m>0) ALLI --> AL: radicalli -> radical: final stem will be: radic");
        String token = "radicalli";
        String expResult = "radic";
        String result = PorterStemmer.processToken(token);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    
    @Test
    public void testProcessToken_step2_h() {
        System.out.println("processToken: (m>0) ENTLI --> ENT: differentli -> different: final stem will be: differ");
        String token = "differentli";
        String expResult = "differ";
        String result = PorterStemmer.processToken(token);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    
    @Test
    public void testProcessToken_step2_i() {
        System.out.println("processToken: (m>0) ELI --> E: vileli -> vile");
        String token = "vileli";
        String expResult = "vile";
        String result = PorterStemmer.processToken(token);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    
     @Test
    public void testProcessToken_step2_j() {
        System.out.println("processToken: (m>0) OUSLI --> OUS: analogousli -> analogous: final stem will be: analog");
        String token = "analogousli";
        String expResult = "analog";
        String result = PorterStemmer.processToken(token);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    
    @Test
    public void testProcessToken_step2_k() {
        System.out.println("processToken: (m>0) IZATION --> IZE: vietnamization -> vietnamize: final stem will be: vietnam");
        String token = "vietnamization";
        String expResult = "vietnam";
        String result = PorterStemmer.processToken(token);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    
    @Test
    public void testProcessToken_step2_l() {
        System.out.println("processToken: (m>0) ATION --> ATE: predication -> predicate: final stem will be: predic");
        String token = "predication";
        String expResult = "predic";
        String result = PorterStemmer.processToken(token);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    
    @Test
    public void testProcessToken_step2_m() {
        System.out.println("processToken: (m>0) ATOR --> ATE: operator -> operate: final stem will be oper");
        String token = "operator";
        String expResult = "oper";
        String result = PorterStemmer.processToken(token);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    @Test
    public void testProcessToken_step2_n() {
        System.out.println("processToken: (m>0) ALISM --> AL: feudalism -> feudal");
        String token = "feudalism";
        String expResult = "feudal";
        String result = PorterStemmer.processToken(token);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    
    @Test
    public void testProcessToken_step2_o() {
        System.out.println("processToken: (m>0)  IVENESS->IVE: decisiveness -> decisive: final stem will be: decis");
        String token = "decisiveness";
        String expResult = "decis";
        String result = PorterStemmer.processToken(token);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    
    @Test
    public void testProcessToken_step2_p() {
        System.out.println("processToken: (m>0)  FULNESS->FUL: hopefulness -> hopeful: final stem will be: hope");
        String token = "hopefulness";
        String expResult = "hope";
        String result = PorterStemmer.processToken(token);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    
    @Test
    public void testProcessToken_step2_q() {
        System.out.println("processToken: (m>0)  OUSNESS->OUS: callousness -> callous");
        String token = "callousness";
        String expResult = "callous";
        String result = PorterStemmer.processToken(token);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    
    @Test
    public void testProcessToken_step2_r() {
        System.out.println("processToken: (m>0)  ALITI->AL: formaliti -> formal");
        String token = "formaliti";
        String expResult = "formal";
        String result = PorterStemmer.processToken(token);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    
    @Test
    public void testProcessToken_step2_s() {
        System.out.println("processToken: (m>0)  IVITI->IVE: sensitiviti -> sensitive: final stem will be: sensit");
        String token = "sensitiviti";
        String expResult = "sensit";
        String result = PorterStemmer.processToken(token);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    
    @Test
    public void testProcessToken_step2_t() {
        System.out.println("processToken: (m>0)  BILITI->BLE: sensibiliti -> sensible: final stem will be: sensibl");
        String token = "sensibiliti";
        String expResult = "sensibl";
        String result = PorterStemmer.processToken(token);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    
    @Test
    public void testProcessToken_step3_a() {
        System.out.println("processToken: (m>0)  ICATE->IC: triplicate -> triplic");
        String token = "triplicate";
        String expResult = "triplic";
        String result = PorterStemmer.processToken(token);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    @Test
    public void testProcessToken_step3_b() {
        System.out.println("processToken: (m>0)  ATIVE->: formative -> form");
        String token = "formative";
        String expResult = "form";
        String result = PorterStemmer.processToken(token);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    
    @Test
    public void testProcessToken_step3_c() {
        System.out.println("processToken: (m>0)  ALIZE->AL: formalize -> formal");
        String token = "formalize";
        String expResult = "formal";
        String result = PorterStemmer.processToken(token);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    
    @Test
    public void testProcessToken_step3_d() {
        System.out.println("processToken: (m>0)  ICITI->IC: electriciti -> electric: final stem will be: electr");
        String token = "electriciti";
        String expResult = "electr";
        String result = PorterStemmer.processToken(token);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    
    @Test
    public void testProcessToken_step3_e() {
        System.out.println("processToken: (m>0)  ICAL->IC: electrical -> electric: final stem will be: electr");
        String token = "electrical";
        String expResult = "electr";
        String result = PorterStemmer.processToken(token);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    
    @Test
    public void testProcessToken_step3_f() {
        System.out.println("processToken: (m>0)  FUL->IC: hopeful -> hope");
        String token = "hopeful";
        String expResult = "hope";
        String result = PorterStemmer.processToken(token);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    
    @Test
    public void testProcessToken_step3_g() {
        System.out.println("processToken: (m>0)  NESS->: goodness -> good");
        String token = "goodness";
        String expResult = "good";
        String result = PorterStemmer.processToken(token);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    
    @Test
    public void testProcessToken_step4_a() {
        System.out.println("processToken: (m>1) AL->: revival -> reviv");
        String token = "revival";
        String expResult = "reviv";
        String result = PorterStemmer.processToken(token);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    
    @Test
    public void testProcessToken_step4_b() {
        System.out.println("processToken: (m>1) ANCE->: allowance -> allow");
        String token = "allowance";
        String expResult = "allow";
        String result = PorterStemmer.processToken(token);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    
    @Test
    public void testProcessToken_step4_c() {
        System.out.println("processToken: (m>1) ENCE->: inference -> infer");
        String token = "inference";
        String expResult = "infer";
        String result = PorterStemmer.processToken(token);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    
    @Test
    public void testProcessToken_step4_d() {
        System.out.println("processToken: (m>1) ER->: airliner -> airlin");
        String token = "airliner";
        String expResult = "airlin";
        String result = PorterStemmer.processToken(token);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    
    @Test
    public void testProcessToken_step4_e() {
        System.out.println("processToken: (m>1) IC->: gyroscopic -> gyroscop");
        String token = "gyroscopic";
        String expResult = "gyroscop";
        String result = PorterStemmer.processToken(token);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    
    @Test
    public void testProcessToken_step4_f() {
        System.out.println("processToken: (m>1) ABLE->: adjustable -> adjustable");
        String token = "adjustable";
        String expResult = "adjust";
        String result = PorterStemmer.processToken(token);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    
    @Test
    public void testProcessToken_step4_g() {
        System.out.println("processToken: (m>1) IBLE->: defensible -> defens");
        String token = "defensible";
        String expResult = "defens";
        String result = PorterStemmer.processToken(token);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    
    @Test
    public void testProcessToken_step4_h() {
        System.out.println("processToken: (m>1) ANT->: irritant -> irrit");
        String token = "irritant";
        String expResult = "irrit";
        String result = PorterStemmer.processToken(token);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    
    @Test
    public void testProcessToken_step4_i() {
        System.out.println("processToken: (m>1) EMENT->: replacement -> replace: final stem will be: replac");
        String token = "replacement";
        String expResult = "replac";
        String result = PorterStemmer.processToken(token);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    
    @Test
    public void testProcessToken_step4_j() {
        System.out.println("processToken: (m>1) MENT->: adjustment -> adjust");
        String token = "adjust";
        String expResult = "adjust";
        String result = PorterStemmer.processToken(token);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    
    @Test
    public void testProcessToken_step4_k() {
        System.out.println("processToken: (m>1) ENT->: dependent -> depend");
        String token = "dependent";
        String expResult = "depend";
        String result = PorterStemmer.processToken(token);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    
    @Test
    public void testProcessToken_step4_l() {
        System.out.println("processToken: (m>1) and (*S or *T)) ION ->: adoption -> adopt");
        String token = "adoption";
        String expResult = "adopt";
        String result = PorterStemmer.processToken(token);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    
    @Test
    public void testProcessToken_step4_m() {
        System.out.println("processToken: (m>1) OU ->: homologou -> homolog");
        String token = "homologou";
        String expResult = "homolog";
        String result = PorterStemmer.processToken(token);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    
    @Test
    public void testProcessToken_step4_n() {
        System.out.println("processToken: (m>1) ISM ->: communism -> commun");
        String token = "communism";
        String expResult = "commun";
        String result = PorterStemmer.processToken(token);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    
    @Test
    public void testProcessToken_step4_o() {
        System.out.println("processToken: (m>1) ATE ->: activate -> activ");
        String token = "activate";
        String expResult = "activ";
        String result = PorterStemmer.processToken(token);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    
    @Test
    public void testProcessToken_step4_p() {
        System.out.println("processToken: (m>1) ITI ->: angulariti -> angular");
        String token = "angulariti";
        String expResult = "angular";
        String result = PorterStemmer.processToken(token);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    
    @Test
    public void testProcessToken_step4_q() {
        System.out.println("processToken: (m>1) OUS ->: homologous -> homolog");
        String token = "homologous";
        String expResult = "homolog";
        String result = PorterStemmer.processToken(token);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    
    @Test
    public void testProcessToken_step4_r() {
        System.out.println("processToken: (m>1) IVE ->: effective -> effect");
        String token = "effective";
        String expResult = "effect";
        String result = PorterStemmer.processToken(token);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    
    @Test
    public void testProcessToken_step4_s() {
        System.out.println("processToken: (m>1) IVE ->: bowdlerize -> bowdler");
        String token = "bowdlerize";
        String expResult = "bowdler";
        String result = PorterStemmer.processToken(token);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    
    @Test
    public void testProcessToken_step5_a() {
        System.out.println("processToken: (m>1) E ->: probate -> probat");
        String token = "probate";
        String expResult = "probat";
        String result = PorterStemmer.processToken(token);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    
    @Test
    public void testProcessToken_step5_a_1() {
        System.out.println("processToken: (m>1) E ->: rate -> rate");
        String token = "rate";
        String expResult = "rate";
        String result = PorterStemmer.processToken(token);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    
    @Test
    public void testProcessToken_step5_a_2() {
        System.out.println("processToken: ((m=1 and not *o) E ->: cease -> ceas");
        String token = "cease";
        String expResult = "cease";
        String result = PorterStemmer.processToken(token);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    
    @Test
    public void testProcessToken_step5_b() {
        System.out.println("processToken: (m > 1 and *d and *L)-> single letter ->: controll -> control");
        String token = "controll";
        String expResult = "control";
        String result = PorterStemmer.processToken(token);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    
    @Test
    public void testProcessToken_step5_b_1() {
        System.out.println("processToken: (m > 1 and *d and *L)-> single letter ->: roll -> roll");
        String token = "roll";
        String expResult = "roll";
        String result = PorterStemmer.processToken(token);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    
}
