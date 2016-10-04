

import static java.lang.Double.parseDouble;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
    public static boolean DESC = false;
    static long[] totalSize = new long[2];
    static void getStatistics(NaiveInvertedIndex index, int a) {
        //number of types: distrinct string and lowercase
        DecimalFormat df = new DecimalFormat("###.###");
        String token[] = index.getDictionary();
        HashMap<String,Integer> frequency = new HashMap<String,Integer>();
        HashMap<String,Integer> sortByValue2= new HashMap<String,Integer>();
        ArrayList<Integer> averageOfPL = new ArrayList<Integer>();
        ArrayList<Integer> stringMemory = new ArrayList<Integer>();
        ArrayList<Double> tenfreq = new ArrayList<Double>();
        ArrayList<Integer> termPos=new ArrayList<Integer>();
        int posSum=0;
        
            System.out.println("1) Number of terms: " + index.getTermCount());//1
            System.out.println("   Number of types: " + QueryLanguage.numberType);
            for(String temp:token){
			HashMap<Integer,List<Integer>> finalList=(index.getPostings(temp));
                        averageOfPL.add(finalList.keySet().size());
                        frequency.put(temp, finalList.keySet().size());
                        stringMemory.add(temp.length());
                                 
                                    Iterator itr3=finalList.entrySet().iterator();
                                    while(itr3.hasNext())
                                    {
                                    Map.Entry entry3=(Map.Entry)itr3.next();
                                    
                                    termPos= (ArrayList<Integer>) entry3.getValue();
                                    //System.out.println(entry3.getValue()+" : "+termPos.size());
                                    
                                   
                                  
                                   int posComputation=48+4*termPos.size();
                                   posSum=posSum+posComputation;
                                   //System.out.println(posSum);
                                  }//while loop
            }//for loop
            double sum = 0;
            for(int i = 0; i < averageOfPL.size(); i++){
            sum += averageOfPL.get(i);}
            int stringSum=0;
            for(int i = 0; i < stringMemory.size(); i++){
            stringSum += stringMemory.get(i);}
            double newKB = sum/averageOfPL.size();
            System.out.println("2) Average Number of documents in posting list od a term in the index "+ df.format(newKB));//2
            System.out.println("3) Ten most frequent terms of the corpus : ");
            sortByValue2=sortByValue(frequency);
            //printMap(sortByValue2);
            
            Iterator itr=sortByValue2.entrySet().iterator();
		while(itr.hasNext())
                    {
		Map.Entry entry=(Map.Entry)itr.next();
                double val= parseDouble(entry.getValue().toString());
                double val2=(double)index.getTermCount();
                double val3= val/val2;
                tenfreq.add(val3);
               System.out.println(">>>>"+entry.getKey() + " : "+ val3);
                    }
            System.out.println("4) Approximate total memory requirement : ");  
            int hP;
            hP=24+36*index.getTermCount();
            System.out.println("a) HashMap/Dictionary takes 24 + 36("+index.getTermCount()+") = "+hP+" bytes");
            int sM=40+2*stringSum;
            System.out.println("b) String terms requires 40 + 2("+stringSum+")= "+sM+" bytes");
            int docSum=0;
            Iterator itr2=frequency.entrySet().iterator();
		while(itr2.hasNext())
                    {
                    Map.Entry entry2=(Map.Entry)itr2.next();
                    docSum += Integer.parseInt(entry2.getValue().toString());
                    }
            int posListM=24+8*docSum;
            System.out.println("c) Posting List for whole index requires 24 + 8("+docSum+")= "+posListM+" bytes");
            System.out.println("d) Position for documents requires  48 + 4("+termPos.size()+")= "+posSum+" bytes");
            int totalMemory =hP+sM+posListM+posSum;
            System.out.println("e) Total memory requirement "+hP+" + "+sM+" + "+posListM+" + "+posSum+" = "+totalMemory+" bytes");
            totalSize[a] = totalMemory;
    }//end of getstatics
    public static void grandTotal(){
            long grand=totalSize[0]+totalSize[1];
            System.out.println("f) Total memory requirement for genral index and soundex = "+grand+" bytes");
    }
    public static HashMap<String,Integer> sortByValue(HashMap<String,Integer> frequency)
            {
    //System.out.println("After sorting descindeng order......");
        HashMap<String, Integer> sortedMapDesc = sortByComparator(frequency, DESC);     
        return sortedMapDesc;

    }

    private static HashMap<String, Integer> sortByComparator(Map<String, Integer> unsortMap, final boolean order)
    {

        List<Entry<String, Integer>> list = new LinkedList<Entry<String, Integer>>(unsortMap.entrySet());

        // Sorting the list based on values
        Collections.sort(list, new Comparator<Entry<String, Integer>>()
        {
            public int compare(Entry<String, Integer> o1,
                    Entry<String, Integer> o2)
            {
                if (order)
                {
                    return o1.getValue().compareTo(o2.getValue());
                }
                else
                {
                    return o2.getValue().compareTo(o1.getValue());

                }
            }
        });

        // Maintaining insertion order with the help of LinkedList
        HashMap<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        for (Entry<String, Integer> entry : list)
        {
            if (sortedMap.size() > 9) break;
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }

    public static void printMap(HashMap<String, Integer> map)
    {
        for (Entry<String, Integer> entry : map.entrySet())
        {
            System.out.println(entry.getKey() + " : "+ entry.getValue());
        }
    }
    
    }



    
    
    
    

