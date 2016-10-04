
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
        //number of types: distrinct string and lowercase
        DecimalFormat df = new DecimalFormat("###.###");
        String token[] = index.getDictionary();
        HashMap<String,Integer> frequency = new HashMap<String,Integer>();
        ArrayList<Integer> averageOfPL = new ArrayList<Integer>();
//         for (String vocabTerm : token) {
//                System.out.println(vocabTerm);
//            }
            System.out.println("1) Number of terms: " + index.getTermCount());
            for(String temp:token){
			HashMap<Integer,List<Integer>> finalList=(index.getPostings(temp));
			//System.out.println(temp+" : "+finalList.keySet());
                        averageOfPL.add(finalList.keySet().size());
//                        List<Integer> l = new ArrayList<Integer>(finalList.keySet());
//                        List<Integer> freq =new ArrayList<Integer>();
//                        freq=finalList.keySet()
                        frequency.put(temp, finalList.keySet().size());
                        
                                  }
            
            Iterator itr=frequency.entrySet().iterator();
		while(itr.hasNext()){
		Map.Entry entry=(Map.Entry)itr.next();
		System.out.println(fileNames.get(Integer.parseInt(entry.getKey().toString()))+" ");
            
            int i;
            double sum = 0;
            for(i = 0; i < averageOfPL.size(); i++){
            sum += averageOfPL.get(i);}
            double newKB = sum/averageOfPL.size();
            System.out.println("2) Average Number of documents in posting list od a term in the index "+ df.format(newKB));
            
            
    
    }}
    
    
    
    

