import java.io.*;
import java.nio.ByteBuffer;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
Writes an inverted indexing of a directory to disk.
*/
public class IndexWriter {

   private String mFolderPath;

   /**
   Constructs an IndexWriter object which is prepared to index the given folder.
   */
   public IndexWriter(String folderPath) {
      mFolderPath = folderPath;
   }

   /**
   Builds and writes an inverted index to disk. Creates three files: 
   vocab.bin, containing the vocabulary of the corpus; 
   postings.bin, containing the postings list of document IDs;
   vocabTable.bin, containing a table that maps vocab terms to postings locations
   */
   public void buildIndex() {
      buildIndexForDirectory(mFolderPath);
   }

   /**
   Builds the normal NaiveInvertedIndex for the folder.
   */
   private static void buildIndexForDirectory(String folder) {
      NaiveInvertedIndex index = new NaiveInvertedIndex();

      // Index the directory using a naive index
      indexFiles(folder, index);

			// at this point, "index" contains the in-memory inverted index 
      // now we save the index to disk, building three files: the postings index,
      // the vocabulary list, and the vocabulary table.

      // the array of terms
      String[] dictionary = index.getDictionary();
      System.out.println("");
      System.out.println("Test:");
      System.out.println("Dictionary");
      for(String temp:dictionary){
    	  System.out.println(temp);
      }
      // an array of positions in the vocabulary file
      long[] vocabPositions = new long[dictionary.length];
      System.out.println("Vocab Length:" +dictionary.length);

      buildVocabFile(folder, dictionary, vocabPositions);
      buildPostingsFile(folder, index, dictionary, vocabPositions);
   }

   /**
   Builds the postings.bin file for the indexed directory, using the given
   NaiveInvertedIndex of that directory.
   */
   private static void buildPostingsFile(String folder, NaiveInvertedIndex index,
    String[] dictionary, long[] vocabPositions) {
      FileOutputStream postingsFile = null;
      try {
         postingsFile = new FileOutputStream(
          new File(folder, "postings.bin")
         );

         // simultaneously build the vocabulary table on disk, mapping a term index to a
         // file location in the postings file.
         FileOutputStream vocabTable = new FileOutputStream(
          new File(folder, "vocabTable.bin")
         );
         //new
         FileOutputStream docWeights = new FileOutputStream(
                 new File(folder, "docWeights.bin")
                );
         HashMap<Integer,Double> weightMap=new HashMap<Integer,Double>();
         //new ends

         // the first thing we must write to the vocabTable file is the number of vocab terms.
         byte[] tSize = ByteBuffer.allocate(4)
          .putInt(dictionary.length).array();
         vocabTable.write(tSize, 0, tSize.length);
         int vocabI = 0;
         for (String s : dictionary) {
            // for each String in dictionary, retrieve its postings.
            // old List<Integer> postings = index.getPostings(s);
        	 //new starts
        	 HashMap hm= index.getPostings(s);
        	 List<Integer> postings=new ArrayList();
        	 
        	 Iterator it = hm.entrySet().iterator();
        	    while (it.hasNext()) {
        	        Map.Entry pair = (Map.Entry)it.next();
        	        postings.add((Integer) pair.getKey());
        	        //System.out.println(pair.getKey() + " = " + pair.getValue());
        	       // it.remove(); // avoids a ConcurrentModificationException
        	    }

            // write the vocab table entry for this term: the byte location of the term in the vocab list file,
            // and the byte location of the postings for the term in the postings file.
            byte[] vPositionBytes = ByteBuffer.allocate(8)
             .putLong(vocabPositions[vocabI]).array();
            vocabTable.write(vPositionBytes, 0, vPositionBytes.length);
            

            byte[] pPositionBytes = ByteBuffer.allocate(8)
             .putLong(postingsFile.getChannel().position()).array();
            vocabTable.write(pPositionBytes, 0, pPositionBytes.length);
            System.out.println();
            System.out.println("String writing:"+s+" Vocab Position: "+vocabPositions[vocabI]+"Posting Position: "+postingsFile.getChannel().position());

            // write the postings file for this term. first, the document frequency for the term, then
            // the document IDs, encoded as gaps.
            byte[] docFreqBytes = ByteBuffer.allocate(4)
             .putInt(postings.size()).array();
            postingsFile.write(docFreqBytes, 0, docFreqBytes.length);
            System.out.println("doc Frequency: "+postings.size());

            int lastDocId = 0;
            for (int docId : postings) {
               byte[] docIdBytes = ByteBuffer.allocate(4)
                .putInt(docId - lastDocId).array(); // encode a gap, not a doc ID
               System.out.println("docId - lastDocId: "+(docId-lastDocId));
               
               
            
               postingsFile.write(docIdBytes, 0, docIdBytes.length);
          	 List<Integer> position=new ArrayList();
          	int lastPositionId=0;
          	position=(List<Integer>) hm.get(docId);
          	int termFreq=position.size();
          /*//logarithms Default
          	Double temp=1+Math.log(termFreq);
            byte[] wdDefaultBytes = ByteBuffer.allocate(8)
                    .putDouble(temp).array(); // encode a gap, not a doc ID
                   System.out.println("docId - lastDocId: "+(docId-lastDocId));
                   
          //Logarithm tf-idf
          Integer temp1=termFreq;
          byte[] wdTfIDFBytes = ByteBuffer.allocate(8)
                    .putInt(temp1).array(); // encode a gap, not a doc ID
                 System.out.println("docId - lastDocId: "+(docId-lastDocId));
                 
           //Logarithm Okapi
                 Double KD=index.get
                 byte[] wdOkapiBytes = ByteBuffer.allocate(8)
                         .putInt(temp1).array(); // encode a gap, not a doc ID
                      System.out.println("docId - lastDocId: "+(docId-lastDocId));*/
                 
                 
          	
          	byte[] TermFreqBytes = ByteBuffer.allocate(4)
                    .putInt(termFreq).array();
          	
          	
          	 postingsFile.write(TermFreqBytes, 0, TermFreqBytes.length);
          	 
          	 //calculating weights starts
          	 if(!weightMap.containsKey(docId)){
          		 Double d=1+java.lang.Math.log(termFreq);
          		 d=d*d;
          		 weightMap.put(docId, d);
          		 
          	 }else{
          		 Double temp=weightMap.get(docId);
          		Double d=1+java.lang.Math.log(termFreq);
         		 d=d*d;
         		 d=d+temp;
         		weightMap.put(docId, d);
          		 
          	 }
          	 //calculating weights ends
          	 
          	
          	for(int posId=0;posId<termFreq;posId++){
          	
          		byte[] termPosBytes = ByteBuffer.allocate(4)
                        .putInt(position.get(posId) - lastPositionId).array(); // encode a gap, not a doc ID
          		System.out.println("position.get(posId) - lastPositionId: "+(position.get(posId) - lastPositionId));
                    
                       postingsFile.write(termPosBytes, 0, termPosBytes.length);
                       
                       lastPositionId=position.get(posId);
          	}
          	

               
               lastDocId = docId;
            }

            vocabI++;
         }
         vocabTable.close();
         postingsFile.close();
         //calculating weight values
         TreeMap tm=new TreeMap(weightMap);
         Iterator itr = tm.entrySet().iterator();
 	    while (itr.hasNext()) {
 	        Map.Entry pair = (Map.Entry)itr.next();
 	        int docId=(int) pair.getKey();
 	        Double d=(Double) pair.getValue();
 	        //System.out.println("OLD value for  "+docId+" is :"+pair.getValue() );
 	        d=Math.sqrt(d);
 	 
 	       System.out.println("new value for  "+docId+" is :"+d.intValue() );
 	      byte[] docWeightBytes = ByteBuffer.allocate(8)
 	             .putDouble(d).array();
 	     docWeights.write(docWeightBytes, 0, docWeightBytes.length);
 	        
 	    }
 	   docWeights.close();

         
      }
      catch (FileNotFoundException ex) {
      }
      catch (IOException ex) {
      }
      finally {
         try {
            postingsFile.close();
         }
         catch (IOException ex) {
         }
      }
   }

   private static void buildVocabFile(String folder, String[] dictionary,
    long[] vocabPositions) {
      OutputStreamWriter vocabList = null;
      try {
         // first build the vocabulary list: a file of each vocab word concatenated together.
         // also build an array associating each term with its byte location in this file.
         int vocabI = 0;
         vocabList = new OutputStreamWriter(
          new FileOutputStream(new File(folder, "vocab.bin")), "ASCII"
         );
         
         int vocabPos = 0;
         for (String vocabWord : dictionary) {
            // for each String in dictionary, save the byte position where that term will start in the vocab file.
            vocabPositions[vocabI] = vocabPos;
            vocabList.write(vocabWord); // then write the String
            vocabI++;
            vocabPos += vocabWord.length();
         }
      }
      catch (FileNotFoundException ex) {
         System.out.println(ex.toString());
      }
      catch (UnsupportedEncodingException ex) {
         System.out.println(ex.toString());
      }
      catch (IOException ex) {
         System.out.println(ex.toString());
      }
      finally {
         try {
            vocabList.close();
         }
         catch (IOException ex) {
            System.out.println(ex.toString());
         }
      }
   }

   private static void indexFiles(String folder, final NaiveInvertedIndex index) {
	   final Path currentWorkingPath = Paths.get(folder).toAbsolutePath();

		// the inverted index
		//final NaiveInvertedIndex index = new NaiveInvertedIndex();
		final NaiveInvertedIndex bodyIndex = new NaiveInvertedIndex();
		final NaiveInvertedIndex authorIndex = new NaiveInvertedIndex();

		// the list of file names that were processed
		final List<String> fileNames = new ArrayList<String>();
		


		// This is our standard "walk through all .txt files" code.
		try {
			Files.walkFileTree(currentWorkingPath, new SimpleFileVisitor<Path>() {
				int mDocumentID  = 0;

				public FileVisitResult preVisitDirectory(Path dir,
						BasicFileAttributes attrs) {
					// make sure we only process the current working directory
					if (currentWorkingPath.equals(dir)) {
						return FileVisitResult.CONTINUE;
					}
					return FileVisitResult.SKIP_SUBTREE;
				}

				public FileVisitResult visitFile(Path file,
						BasicFileAttributes attrs) throws FileNotFoundException {
					// only process .txt files
					
					/*if(algoType.equals("SoundEX")){
						soundExFileNames.add(file.getFileName().toString());
						indexFile(file.toFile(), bodyIndex,authorIndex, mDocumentID,algoType);
						
						mDocumentID++;
					}
					//if (file.toString().endsWith(".txt") ) {
					else{*/
						// we have found a .txt file; add its name to the fileName list,
						// then index the file and increase the document ID counter.
						//System.out.println("Indexing file " + file.getFileName());


						fileNames.add(file.getFileName().toString());

						indexFile(file.toFile(), index, mDocumentID);
						mDocumentID++;
					//}
					return FileVisitResult.CONTINUE;
				}

				// don't throw exceptions if files are locked/other errors occur
				public FileVisitResult visitFileFailed(Path file,
						IOException e) {

					return FileVisitResult.CONTINUE;
				}

			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
/*		if(algoType.equals("SoundEX")){
			System.out.println("Body:");
			printResults(bodyIndex, fileNames);
			System.out.println("Author:");
			printResults(authorIndex, fileNames);
			bodyIndexMap.put(fileNames, bodyIndex);
			authorIndexMap.put(fileNames,authorIndex);
			
			return authorIndexMap;
			

			
		}else{

			printResults(index, fileNames);
			fileIndexMap.put(fileNames,index);
			return fileIndexMap;
		}*/
		//searchWord(index,fileNames);

		// Implement the same program as in Homework 1: ask the user for a term,
		// retrieve the postings list for that term, and print the names of the 
		// documents which contain the term.

/*      int documentID = 0;
      final Path currentWorkingPath = Paths.get(folder).toAbsolutePath();
      
      try {
         Files.walkFileTree(currentWorkingPath, new SimpleFileVisitor<Path>() {
            int mDocumentID  = 0;
            
            public FileVisitResult preVisitDirectory(Path dir,
             BasicFileAttributes attrs) {
               // make sure we only process the current working directory
               if (currentWorkingPath.equals(dir)) {
                  return FileVisitResult.CONTINUE;
               }
               return FileVisitResult.SKIP_SUBTREE;
            }
            
            public FileVisitResult visitFile(Path file,
             BasicFileAttributes attrs) {
               // only process .txt files
               if (file.toString().endsWith(".txt")) {
                  // we have found a .txt file; add its name to the fileName list,
                  // then index the file and increase the document ID counter.
                  // System.out.println("Indexing file " + file.getFileName());
                  
                  
                  indexFile(file.toFile(), index, mDocumentID);
                  mDocumentID++;
               }
               return FileVisitResult.CONTINUE;
            }
            
            // don't throw exceptions if files are locked/other errors occur
            public FileVisitResult visitFileFailed(Path file,
             IOException e) {
               
               return FileVisitResult.CONTINUE;
            }
            
         });
      }
      catch (IOException ex) {
         Logger.getLogger(IndexWriter.class.getName()).log(Level.SEVERE, null, ex);
      }
*/   }
   
//new starts   
	private static void indexFile(File file, NaiveInvertedIndex index, 
			int docID) throws FileNotFoundException{
		// TO-DO: finish this method for indexing a particular file.
		// Construct a SimpleTokenStream for the given File.
		// Read each token from the stream and add it to the index.
		
		JsonParser parser = new JsonParser();
		String body = new String();
		JsonElement a =   parser.parse(new FileReader(file));
		JsonObject jsonObject = (JsonObject) a;

	    body = (String) jsonObject.get("body").toString();
	    //System.out.println("body: "+body);
	    
	    SimpleTokenStream simpleTokenObj= new SimpleTokenStream(body);

		//SimpleTokenStream simpleTokenObj= new SimpleTokenStream(file);
		int pos=0;
		while(simpleTokenObj.hasNextToken()){
			// String fn= file.getName();
			//Write code for PorterStemmer
			String temp[]= callPC(simpleTokenObj.nextToken());
			for(String temp1: temp){
				index.addTerm(temp1, docID,pos);   
			}
			pos++;

		}

	}
	
	public static String[] callPC(String token){
		String[] processToken;

		ArrayList<String> normalizeToken=new ArrayList<>();
		normalizeToken = NormalizeToken.normalizeToken(token);
		//System.out.println("Normalized Token: " + normalizeToken);
		int i=0;
		processToken=new String[normalizeToken.size()];
		for(String tkn:normalizeToken){
			//System.out.println("we are on "+tkn);
			processToken[i] = PorterStemmer.processToken(tkn);
			//System.out.println("stem: " + processToken);
			i++;
		}

		return processToken;

	}
	
	//new ends


/*   private static void indexFile(File fileName, NaiveInvertedIndex index,
    int documentID) {

      try {
         SimpleTokenStream stream = new SimpleTokenStream(fileName);
         while (stream.hasNextToken()) {
            String term = stream.nextToken();
            String stemmed = PorterStemmer.processToken(term);

            if (stemmed != null && stemmed.length() > 0) {
               index.addTerm(stemmed, documentID);
            }
         }
      }
      catch (Exception ex) {
         System.out.println(ex.toString());
      }
   }
*/}
