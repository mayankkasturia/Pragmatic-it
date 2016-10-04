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
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.regex.Pattern;

import soundex.Soundex;


/**
 *
 *
 */
public class QueryLanguage {
	static boolean booleanQuery=true;
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
        System.out.println(":statistics - for index statistic");
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
            fileIndex =SimpleEngine.getIndex(corpusName,"PS");
			Iterator itr=fileIndex.entrySet().iterator();
			while(itr.hasNext()){
				Map.Entry entry=(Map.Entry)itr.next();
				index=(NaiveInvertedIndex) entry.getValue();
				fileNames=(ArrayList<String>) entry.getKey();
				//System.out.println(entry.getValue());


			}

            
            getVocabulary(index);
        }
          if (query.equalsIgnoreCase(":statistics")) {
            System.out.println("Enter the name of corpus: ");
            String corpusName = readQueryFromUser();
            NaiveInvertedIndex statsIndex = new NaiveInvertedIndex();
            fileIndex =SimpleEngine.getIndex(corpusName,"");
			Iterator itr=fileIndex.entrySet().iterator();
			while(itr.hasNext()){
				Map.Entry entry=(Map.Entry)itr.next();
				statsIndex=(NaiveInvertedIndex) entry.getValue();
				fileNames=(ArrayList<String>) entry.getKey();
				


			}
                        IndexStatistics.getStatistics(statsIndex);
        }
        //Pending
        // :index directoryname - index the folder specified by directoryname and then begins querying it
        
        if(query.equalsIgnoreCase(":index")){
            System.out.println("Enter the name of corpus: ");
            String corpusName = readQueryFromUser();
            NaiveInvertedIndex index = new NaiveInvertedIndex();
            fileIndex =SimpleEngine.getIndex(corpusName,"PS");
			Iterator itr=fileIndex.entrySet().iterator();
			while(itr.hasNext()){
				Map.Entry entry=(Map.Entry)itr.next();
				index=(NaiveInvertedIndex) entry.getValue();
				fileNames=(ArrayList<String>) entry.getKey();
				//System.out.println(entry.getValue());


			}

            int counter = 0;
            System.out.println("Enter Query or :q to return to Main Menu: ");
              while (counter == 0) {
                String query1 = readQueryFromUser();
                queryParser(index, query1,fileNames);
                
            if (query1.equalsIgnoreCase(":q")) {
            System.out.println("Exit index, return to the main menu!");
            main(new String[] {"a","b","c"});
        }
            }
           
        }
      
       if (query.equalsIgnoreCase(":soundex")) {
            String code1 = soundex.Soundex.soundex("Mani");
            String code2 = soundex.Soundex.soundex("Money");
            System.out.println(code1 + ": " + "Mani");
            System.out.println(code2 + ": " + "Money");
            
            System.out.println("Enter the name of corpus: ");
            String corpusName = readQueryFromUser();
            NaiveInvertedIndex index = new NaiveInvertedIndex();
            String algoType="SoundEX";
            fileIndex =SimpleEngine.getIndex(corpusName,algoType);
            
			Iterator itr=fileIndex.entrySet().iterator();
			while(itr.hasNext()){
				Map.Entry entry=(Map.Entry)itr.next();
				index=(NaiveInvertedIndex) entry.getValue();
				fileNames=(ArrayList<String>) entry.getKey();
				//System.out.println(entry.getValue());


			}

            int counter = 0;
            System.out.println("Enter Query or :q to return to Main Menu: ");
              while (counter == 0) {
                String query1 = readQueryFromUser();
                String temp[]=query1.split(":");
              temp[1]=Soundex.soundex(temp[1]);
              wordQuery(index,temp[1],"SoundEx");


              
                
            if (query1.equalsIgnoreCase(":q")) {
            System.out.println("Exit index, return to the main menu!");
            main(new String[] {"a","b","c"});
        }
            }

            
            
            
            //xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
        }
        main(new String[] {"a","b","c"});
    }

        public static Set notWordQuery(NaiveInvertedIndex index, String word,List docId) {
            System.out.println("I am a NOT Query");
            System.out.println("Word: "+word);
                String[] words = word.split("[ ]");
                Set<Integer> tempDocSet = new HashSet<Integer>();
                List<Integer> documentIds = new ArrayList<Integer>();
                Set<Integer> documentIds1 = new HashSet<Integer>();
                for(int i=0;i<docId.size();i++){
                	documentIds.add(i);
                }
                if(words.length>1)
                        {
                            tempDocSet = phraseWordQuery(index,word,docId);
                        } else{
                        	    tempDocSet=wordQuery(index,word,"");
                        	
                }
                
                documentIds.removeAll(tempDocSet);
             
                
            
            /*for(int a : documentIds)
            {
                System.out.println("Word is not present in: " + a);
            }*/
            return documentIds1;
        }
        
public static void queryParser(NaiveInvertedIndex index, String query,List<String> fileNames) throws IOException {
        String pharseIdentifier = "\"";
//int count = 0;
// Prepare a final outPut List.
 
String input = query;
if (input.equalsIgnoreCase(":q")) {
            System.out.println("Exit index, return to the main menu!");
            main(new String[] {"a","b","c"});
        }

StringTokenizer orTokenizer = new StringTokenizer(input, "+");
List<List<String>> resultList = new ArrayList<>();


int firstPhraseIndex = -1;
int lastPhraseIndex = -1;
String pharseQueryString = null;
String notQueryString=null;
while(orTokenizer.hasMoreTokens()){
	int count=0;
	Set<String> notSet=null;
	List<String> notList = null;
	
    Set<String> phraseSet;
List<String> phraseList = new ArrayList<>();
String andTokens = orTokenizer.nextToken();
firstPhraseIndex = andTokens.indexOf(pharseIdentifier);
lastPhraseIndex = andTokens.lastIndexOf(pharseIdentifier);
if (firstPhraseIndex > -1 && lastPhraseIndex > -1){
	if(firstPhraseIndex==0 || andTokens.charAt(firstPhraseIndex-1)!='!'){
pharseQueryString = andTokens.substring(firstPhraseIndex, lastPhraseIndex);
            phraseSet = phraseWordQuery(index, pharseQueryString,fileNames);
            //pharseQueryString.replaceAll(" ","&");
            phraseList = new ArrayList<>(phraseSet);
	}else{
		notQueryString = andTokens.substring(firstPhraseIndex, lastPhraseIndex);
		notSet=notWordQuery(index, notQueryString,fileNames);
		notList=new ArrayList<>(notSet);
		
	}
}
if (firstPhraseIndex == 0){
    if(lastPhraseIndex+1 == input.length()){
        
        //break;
    }
    else{
        andTokens = andTokens.substring(lastPhraseIndex + 1, andTokens.length());
    }
}else if(lastPhraseIndex+1 == input.length()){
    andTokens = andTokens.substring(0,firstPhraseIndex-1);
}
//andTokens = andTokens.replaceAll(pharseQueryString, "");
//if(!andTokens.isEmpty()){
Set<String> andTokensResultSet = new TreeSet<>();
List<String> andTokensResults = new ArrayList<>();
StringTokenizer andTokensizer = new StringTokenizer(andTokens, " ");
System.out.println("Count of andTokensizer elements: " + andTokensizer.countTokens());

//count++;
while(andTokensizer.hasMoreTokens()){
	Boolean notQueryBoolean=false;
    String toStem = andTokensizer.nextToken();
    String word = SimpleEngine.callPoterStem(toStem);
    if(toStem.charAt(0)=='!'){
    	notQueryBoolean=true;
    	if(notSet==null){
    	notSet=notWordQuery(index, word,fileNames);
    	notList=new ArrayList<>(notSet);
		
    	}
    	else{
    		notSet.retainAll(notWordQuery(index, word,fileNames));
    		notList.retainAll(notSet);
    		
    		
    	}
    	
    	
    }
    
    
   
// do a function search passing andTokensizer.nextToken and store it in andTokensResults.
// do a intersection of  the results of other token with andTokensResults and store the same in andTokensResults.
    
    if(andTokensResultSet.isEmpty() &&count==0){
    			//counter=0
                andTokensResultSet = wordQuery(index, word,"");//add ps
                //counter =1
                count++;
                
                
                } else /*if(notQueryBoolean==true)*/{
                	
                		/*andTokensResultSet=new HashSet<>();*/
                		andTokensResultSet.retainAll(index.getDocumentId(word));
                	
                }
                if(!phraseList.isEmpty()){
                    andTokensResultSet.retainAll(phraseList);
                }
                if(notList!=null){
                	andTokensResultSet.retainAll(notList);
                	
                }
                andTokensResults = new ArrayList<>(andTokensResultSet);
                
}
count=0;
if(andTokensResults.isEmpty()){
     if(!phraseList.isEmpty()){
    	 andTokensResultSet=new HashSet();
                    andTokensResultSet.addAll(phraseList);
                }
     if( notList!=null && !notList.isEmpty()){
    	 andTokensResultSet=new HashSet();
    	 
    		andTokensResultSet.addAll(notList);
    	 
     }

}
andTokensResults = new ArrayList<>(andTokensResultSet);
// now do a function search passing pharseQueryString if pharseQueryString is not null AND do a intersection of  the results
// store the results of all the andTokens into the result list.
resultList.add(andTokensResults);
 
}
 List<String> finalResultList = new ArrayList<>();
 Set<String> set = new HashSet<>();
 
 

// Now traverse the resultList and do the union of each list stored.
 for (List a : resultList) {
            set.addAll(a);
        }
        finalResultList = new ArrayList<>(set);

// finally add the result of strictPhrase into the same and return your result.
        
//        if(count<=1 ) {
//            if(!phraseList.isEmpty())
//            {
//            set.retainAll(phraseList);
//            }
//        }
//        else{
//            set.addAll(phraseList);
//        }
        finalResultList = new ArrayList<>(set);
        Iterator iterator = finalResultList.iterator();
        //count = 0;
        while (iterator.hasNext()) {
            System.out.println("Query Parser Index: " + fileNames.get(Integer.parseInt(iterator.next().toString())));
        } 
    }
    
    public static String readQueryFromUser() {
        Scanner s = new Scanner(System.in);
        String query = s.nextLine();
        return query;
    }

    public static Set wordQuery(NaiveInvertedIndex index, String word, String algoType) {
        System.out.println("I am General Query");
        Set<Integer> tempDocSet = new HashSet<>();
        System.out.println(index.getPostings(word));
        if(algoType.equals("SoundEx")&&index.getPostings(word)!=null){
        	Iterator itr=index.getPostings(word).entrySet().iterator();
        	System.out.println("File Names:");
        	while(itr.hasNext()){
        		Map.Entry entry=(Map.Entry)itr.next();
        		System.out.println(SimpleEngine.soundExFileNames.get((int)entry.getKey()));
				
				//System.out.println(entry.getValue());
        	}
        }
        if(index.getPostings(word)==null){
            System.out.println("Word does not present, enter query again or :q to quit ");
        } else {

            tempDocSet = index.getPostings(word).keySet();
        }
        
                
        return tempDocSet;
    }
    
    public static Set phraseWordQuery(NaiveInvertedIndex index, String query,List<String> fileNames) {
        System.out.println("I am PHRASE Query");
        String token[] = index.getDictionary();
        HashMap<Integer, List<Integer>> tempPosSet1 = new HashMap<>();
        HashMap<Integer, List<Integer>> tempPosSet2;
        String[] word = query.split("[ ]");
        for (String temp : word) {
            //temp = temp.toLowerCase();//add ps
            String temp3[] = SimpleEngine.callPC(temp);
                for(String temp2: temp3){
//            int y = Arrays.binarySearch(token, temp2);
//            if (y < 0) {
//                System.out.println("Word does not present ");
//                System.exit(0);
            if(index.getPostings(temp2)==null){
            System.out.println("Word does not present, enter query again or :q to quit ");
        }
             else if (tempPosSet1.isEmpty()) {
                tempPosSet1 = index.getPostings(temp2);
                //System.out.println("Temp Doc set with Postings1: " + tempPosSet1);
            } else {
                tempPosSet2 = index.getPostings(temp2);
                //System.out.println("Temp Doc set with Postings1: " + tempPosSet1);
                //System.out.println("Temp Doc set with Postings2: " + tempPosSet2);
                HashMap<Integer, List<Integer>> tempPosSet3 = new HashMap<>();
                for (Integer k : tempPosSet1.keySet()) {
                    ArrayList<Integer> setVal = new ArrayList<>();
                    List<Integer> values = tempPosSet1.get(k);
                    if (tempPosSet2.containsKey(k)) {
                        for (int i = 0; i < values.size(); i++) {
                            if (tempPosSet2.get(k).contains(values.get(i) + 1)) {
                                setVal.add(values.get(i) + 1);
                                //System.out.println("print value: " + k + ": [" + setVal + "]");
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
        System.out.println("Phrase Query Index: " + tempPosSet1);
        //phraseWordQuery(index);
        }
        return tempPosSet1.keySet();
    }
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
