/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;
import static searchengine.Soundex.soundex;

/**
 *
 *
 */
public class QueryLanguage {
	static boolean booleanQuery=false;
        public static void main(String[] args) throws IOException {
        	HashMap<List<String>,NaiveInvertedIndex> fileIndex=new HashMap<List<String>,NaiveInvertedIndex>();
        	ArrayList<String> fileNames=new ArrayList<String>();
   // public static void readQueryFromUser(NaiveInvertedIndex index) {

        System.out.println("Main Menu");
        System.out.println(":index - for indexing and querying");
        System.out.println(":stem - for normalizing and then stemming the token");
        System.out.println(":vocab - for printing the vocabulary");
        System.out.println(":soundex - for calling soundex algorithm");
        System.out.println(":q - to quit the application");
        String query = readQueryFromUser();
       
        // implementing special queries
        // :q to exit
        if (query.equalsIgnoreCase(":q")) {
            System.out.println("Bye!");
            System.exit(0);
        }
        // :stem token take the token string, stem it and then print the stemmed term
        if (query.equalsIgnoreCase(":stem")) {
            callStemmer();
        }
        
        //vocab - print all the terms in the vocabulary of the corpus, one term per line. Then print the count of the total number of vocabulary terms
        if (query.equalsIgnoreCase(":vocab")) {
            System.out.println("Enter the name of corpus: ");
            String corpusName = readQueryFromUser();
            NaiveInvertedIndex index = new NaiveInvertedIndex();
            fileIndex =SimpleEngine.getIndex(corpusName);
			Iterator itr=fileIndex.entrySet().iterator();
			while(itr.hasNext()){
				Map.Entry entry=(Map.Entry)itr.next();
				index=(NaiveInvertedIndex) entry.getValue();
				fileNames=(ArrayList<String>) entry.getKey();
				//System.out.println(entry.getValue());


			}

            
            getVocabulary(index);
        }
        //Pending
        // :index directoryname - index the folder specified by directoryname and then begins querying it
        
        if(query.equalsIgnoreCase(":index")){
            System.out.println("Enter the name of corpus: ");
            String corpusName = readQueryFromUser();
            NaiveInvertedIndex index = new NaiveInvertedIndex();
            fileIndex =SimpleEngine.getIndex(corpusName);
			Iterator itr=fileIndex.entrySet().iterator();
			while(itr.hasNext()){
				Map.Entry entry=(Map.Entry)itr.next();
				index=(NaiveInvertedIndex) entry.getValue();
				fileNames=(ArrayList<String>) entry.getKey();
				//System.out.println(entry.getValue());


			}

            int counter = 0;
            System.out.println("Enter Query or :q to return to Main Menu: ");
            while(counter == 0)
            {
            String query1 = readQueryFromUser();
            
            
            if(query1.contains(" ")){
                query1 = query1.replaceAll("[ ]", "&");
                //System.out.println("Replacing space with & operator: " + query1);
                andWordQuery(index,query1);
                booleanQuery=true;
            }
            if(query1.contains("+")){
                freeWordQuery(index,query1);
                booleanQuery=true;    
            }
            if(booleanQuery==false){
            	SimpleEngine.searchWord(index, fileNames,query1);
            	
            }
            
            
            if (query1.equalsIgnoreCase(":q")) {
            System.out.println("Exit index, return to the main menu!");
            main(new String[] {"a","b","c"});
        }
        }
        }
       /* if (query.equalsIgnoreCase(":soundex")) {
            String code1 = soundex("Mani");
            String code2 = soundex("Money");
            System.out.println(code1 + ": " + "Mani");
            System.out.println(code2 + ": " + "Money");
        }*/
        main(new String[] {"a","b","c"});
    }

public static String readQueryFromUser() {
        Scanner s = new Scanner(System.in);
        String query = s.nextLine();
        return query;
    }


    public static void freeWordQuery(NaiveInvertedIndex index, String query) {
         System.out.println("I am OR Query");
        String token[] = index.getDictionary();
        String [] word = query.split("[//+]");
       // String [] word = strSplit(query, "\\+");
        Set<Integer> tempDocSet = new HashSet<>();
        for (String temp : word) {
            temp = temp.toLowerCase();
            int y = Arrays.binarySearch(token, temp);
            if (y < 0) {
                System.out.println("Word does not present, enter query again or :q to quit ");
            } else {

                tempDocSet.addAll(index.getDocumentId(temp));
            }
        }
        System.out.println("New Index: " + tempDocSet);
       // freeWordQuery(index);
    }

    public static void andWordQuery(NaiveInvertedIndex index, String query) {
        System.out.println("I am AND Query");
        String token[] = index.getDictionary();
        String [] word = query.split("[&]");
        Set<Integer> tempDocSet = new HashSet<>();
        for (String temp : word) {
            temp = temp.toLowerCase();
            int y = Arrays.binarySearch(token, temp);
            if (y < 0) {
                System.out.println("Word does not present, enter query again or :q to quit ");
            } else if (tempDocSet.isEmpty()) {
                tempDocSet = index.getDocumentId(temp);
                //System.out.println("Temp Doc for " + temp + tempDocSet);
            } else {
                tempDocSet.retainAll(index.getDocumentId(temp));
                //System.out.println("Temp Doc for " + temp + tempDocSet);
            }
        }
        System.out.println("Returned Index: " + tempDocSet);
       // andWordQuery(index);
    }

    /*public static void phraseWordQuery(NaiveInvertedIndex index) {
        System.out.println("I am PHRASE Query");
        String token[] = index.getDictionary();
        String[] word = readQueryFromUser();
        HashMap<Integer, List<Integer>> tempPosSet1 = new HashMap<>();
        HashMap<Integer, List<Integer>> tempPosSet2;
        for (String temp : word) {
            temp = temp.toLowerCase();
            int y = Arrays.binarySearch(token, temp);
            if (y < 0) {
                System.out.println("Word does not present ");
                System.exit(0);
            } else if (tempPosSet1.isEmpty()) {
                tempPosSet1 = index.getPostings(temp);
                System.out.println("Temp Doc set with Postings1: " + tempPosSet1);
            } else {
                tempPosSet2 = index.getPostings(temp);
                System.out.println("Temp Doc set with Postings1: " + tempPosSet1);
                System.out.println("Temp Doc set with Postings2: " + tempPosSet2);
                HashMap<Integer, List<Integer>> tempPosSet3 = new HashMap<>();
                for (Integer k : tempPosSet1.keySet()) {
                    ArrayList<Integer> setVal = new ArrayList<>();
                    List<Integer> values = tempPosSet1.get(k);
                    if (tempPosSet2.containsKey(k)) {
                        for (int i = 0; i < values.size(); i++) {
                            if (tempPosSet2.get(k).contains(values.get(i) + 1)) {
                                setVal.add(values.get(i) + 1);
                                System.out.println("print value: " + k + ": [" + setVal + "]");
                            }
                            if (!setVal.isEmpty()) {
                                tempPosSet3.put(k, setVal);
                            }
                        }
                    }
                }
                tempPosSet1 = tempPosSet3;
            }
        }
        System.out.println("New Index: " + tempPosSet1);
        phraseWordQuery(index);
    } */
    public static void callStemmer() throws IOException
    {
        Scanner scan = new Scanner(System.in);
            System.out.println("Enter token");
            while (scan.hasNext()) {
                String token = scan.next();
                String processToken;
                if (token.equalsIgnoreCase(":q")) {
                    System.out.println("Exit Stemmer");
                    main(new String[] {"a","b","c"});
                }
                ArrayList<String> normalizeToken;
                normalizeToken = NormalizeToken.normalizeToken(token);
                System.out.println("Normalized Token: " + normalizeToken);
                for (String tkn : normalizeToken) {
                    System.out.println("we are on " + tkn);
                    processToken = PorterStemmer.processToken(tkn);
                    System.out.println("stem: " + processToken);
                }
            }
    }
    public static void getVocabulary(NaiveInvertedIndex index)
    {
        String token[] = index.getDictionary();
            System.out.println("Vocabulary! ");
            for (String vocabTerm : token) {
                System.out.println(vocabTerm);
            }
            System.out.println("Count of total vocabulary terms: " + index.getTermCount());
            System.out.println("Exit vocabulary and return to Main Menu!" );
    }
}
