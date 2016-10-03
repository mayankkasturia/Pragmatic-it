
import java.util.HashMap;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Mayankkasturia
 */
public class IndexStatistics {

    static void getStatistics(NaiveInvertedIndex index) {
        String token[] = index.getDictionary();
         for (String vocabTerm : token) {
                System.out.println(vocabTerm);
            }
            System.out.println("Number of terms: " + index.getTermCount());
        
    }
    
    
    //number of types: distrinct string and lowercase
    //Number of term: normalize and stem
}
