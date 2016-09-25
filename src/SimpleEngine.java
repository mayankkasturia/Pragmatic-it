
import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.*;
import java.util.*;

/**
A very simple search engine. Uses an inverted index over a folder of TXT files.
*/
public class SimpleEngine {

   public static void main(String[] args) throws IOException {
      final Path currentWorkingPath = Paths.get("C:\\Users\\Mayankkasturia\\Documents\\NetBeansProjects\\homework3\\files").toAbsolutePath();
      
      // the inverted index
      final NaiveInvertedIndex index = new NaiveInvertedIndex();
      
      // the list of file names that were processed
      final List<String> fileNames = new ArrayList<String>();

      
      // This is our standard "walk through all .txt files" code.
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
            if (file.toString().endsWith(".txt") ) {
               // we have found a .txt file; add its name to the fileName list,
               // then index the file and increase the document ID counter.
              //System.out.println("Indexing file " + file.getFileName());
               
               
               fileNames.add(file.getFileName().toString());
             
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
      
     // printResults(index, fileNames);
      searchWord(index,fileNames);
      
      // Implement the same program as in Homework 1: ask the user for a term,
      // retrieve the postings list for that term, and print the names of the 
      // documents which contain the term.
      
      
   }

   /**
   Indexes a file by reading a series of tokens from the file, treating each 
   token as a term, and then adding the given document's ID to the inverted
   index for the term.
   @param file a File object for the document to index.
   @param index the current state of the index for the files that have already
   been processed.
   @param docID the integer ID of the current document, needed when indexing
   each term from the document.
   */
   private static void indexFile(File file, NaiveInvertedIndex index, 
    int docID) throws FileNotFoundException{
      // TO-DO: finish this method for indexing a particular file.
      // Construct a SimpleTokenStream for the given File.
      // Read each token from the stream and add it to the index.
       
       SimpleTokenStream simpleTokenObj= new SimpleTokenStream(file);
       int pos=0;
       while(simpleTokenObj.hasNextToken()){
          // String fn= file.getName();
           index.addTerm(simpleTokenObj.nextToken(), docID,pos);
           pos++;
           
       }
       
       
    
      
      
   }

   private static void printResults(NaiveInvertedIndex index, 
    List<String> fileNames) {
     
      // TO-DO: print the inverted index.
      // Retrieve the dictionary from the index. (It will already be sorted.)
      // For each term in the dictionary, retrieve the postings list for the
      // term. Use the postings list to print the list of document names that
      // contain the term. (The document ID in a postings list corresponds to 
      // an index in the fileNames list.)
      
      // Print the postings list so they are all left-aligned starting at the
      // same column, one space after the longest of the term lengths. Example:
      // 
      // as:      document0 document3 document4 document5
      // engines: document1
      // search:  document2 document4  
       
       
       String token[]=index.getDictionary();
       for(String temp:token){
           HashMap<Integer,List<Integer>> finalList=(index.getPostings(temp));
           System.out.println(temp+" : "+finalList);
       }
       
   }
   public static void searchWord(NaiveInvertedIndex index, List<String> fileNames) {
       String token[]=index.getDictionary();
         System.out.println("Enter a term to search for: ");
        Scanner s = new Scanner(System.in);
        String word = s.next();

        if (word.equalsIgnoreCase("quit")) {
            System.out.println("Bye!");
            System.exit(0);
        }
        
       // int y = Arrays.binarySearch(token, word);
           
        try{
            if(("null").equals(index.getPostings(word).toString())){
                System.out.println("Word does not present ");
                searchWord(index,fileNames);
        
            }
            else{
                 HashMap<Integer,List<Integer>> mIndex=index.getPostings(word);
                 Iterator itr=mIndex.entrySet().iterator();
                 while(itr.hasNext()){
                      Map.Entry entry=(Map.Entry)itr.next();
                      System.out.println(fileNames.get(Integer.parseInt(entry.getKey().toString()))+" ");
                      //System.out.println(entry.getValue());
                      
                    
         
                    
                   }
                
                searchWord(index,fileNames);
            }
        }
        catch(NullPointerException e){
            System.out.println("Word does not present ");
                searchWord(index,fileNames);
            
        }
  
    }
}
