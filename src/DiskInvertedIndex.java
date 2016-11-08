
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
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

public class DiskInvertedIndex {

	private String mPath;
	private static RandomAccessFile mVocabList;
	private static RandomAccessFile mPostings;
	private static RandomAccessFile mWeight;

	/*public static List<Double> getwQTwDTAccumulator() {
		return wQTwDTAccumulator;
	}*/

	private long[] mVocabTable;
	private static List<String> mFileNames;

	public static List<String> getmFileNames() {
		return mFileNames;
	}

	private static ArrayList<Integer> docWeightLD = new ArrayList<Integer>();

	public static ArrayList<Integer> getDocWeightLD() {
		return docWeightLD;
	}
	
	//private static List<Double> wQTwDTAccumulator = new ArrayList<Double>();
	private static HashMap<Integer,Double> wQTwDTAccumulator = new HashMap<Integer,Double>();

	public DiskInvertedIndex(String path) {
		try {
			mPath = path;
			mVocabList = new RandomAccessFile(new File(path, "vocab.bin"), "r");
			mPostings = new RandomAccessFile(new File(path, "postings.bin"), "r");
			mWeight = new RandomAccessFile(new File(path, "docWeights.bin"), "r");
			mVocabTable = readVocabTable(path);
			mFileNames = readFileNames(path);
		} catch (FileNotFoundException ex) {
			System.out.println(ex.toString());
		}
	}

	// My Old
	/*
	 * private static List<Integer> readLDWeight(RandomAccessFile
	 * weight,List<String> mFileNames) { int seekPos=0; for(int
	 * i=0;i<mFileNames.size();i++){ byte[] buffer = new byte[8]; try {
	 * weight.read(buffer, 0, buffer.length); int LDWeight =
	 * ByteBuffer.wrap(buffer).getInt(); docWeight.add(LDWeight);
	 * seekPos=seekPos+8; weight.seek(seekPos); System.out.println("Weight of: "
	 * +mFileNames.get(i)+" is :"+LDWeight); } catch (IOException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); } } return docWeight; }
	 */
	private static List<Integer> readLDWeight(RandomAccessFile weight, int docID, int termFreq) {
		int seekPos = 0;

		byte[] buffer = new byte[8];
		try {

			seekPos = seekPos + (8 * docID);
			weight.seek(seekPos);
			weight.read(buffer, 0, buffer.length);
			int LDWeight = ByteBuffer.wrap(buffer).getInt();
			docWeightLD.add(LDWeight);
			//System.out.println("Weight of: " + mFileNames.get(docID) + " is :" + LDWeight);
			System.out.println("Total FIles: " + mFileNames.size());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return docWeightLD;
	}

	private static HashMap<Integer, List<Integer>> readPostingsFromFile(RandomAccessFile postings,
			long postingsPosition) {
		try {
			// seek to the position in the file where the postings start.
			postings.seek(postingsPosition);

			// read the 4 bytes for the document frequency
			byte[] buffer = new byte[4];
			postings.read(buffer, 0, buffer.length);

			// use ByteBuffer to convert the 4 bytes into an int.
			int documentFrequency = ByteBuffer.wrap(buffer).getInt();
			// Wqt
			
			int totalFiles = mFileNames.size();
			Double wQT = (Double)Math.log(1 + (totalFiles / documentFrequency));

			// initialize the array that will hold the postings.
			int[] docIds = new int[documentFrequency];
			System.out.println("Reading: Doc Freq: " + documentFrequency);

			// write the following code:
			// read 4 bytes at a time from the file, until you have read as many
			// postings as the document frequency promised.
			//
			// after each read, convert the bytes to an int posting. this value
			// is the GAP since the last posting. decode the document ID from
			// the gap and put it in the array.
			//
			// repeat until all postings are read.
			List<Integer> posList = new ArrayList<Integer>();
			HashMap<Integer, List<Integer>> postMap = new HashMap<Integer, List<Integer>>();
			// postingsPosition=postingsPosition+4;
			int previousPos = 0;
			int previousDocId = 0;

			for (int i = 0; i < documentFrequency; i++) {
				posList = new ArrayList<Integer>();

				// postingsPosition++;
				postingsPosition = postingsPosition + 4;
				postings.seek(postingsPosition);
				postings.read(buffer, 0, buffer.length);
				docIds[i] = ByteBuffer.wrap(buffer).getInt() + previousDocId;
				previousDocId = docIds[i];
				postingsPosition = postingsPosition + 4;
				postings.seek(postingsPosition);
				postings.read(buffer, 0, buffer.length);
				int termfreq = ByteBuffer.wrap(buffer).getInt();
				// LdWeight
				readLDWeight(mWeight, docIds[i], termfreq);
				System.out.println("doc ID: A "+docIds[i]);

				Double wDT = (Double)(1 + Math.log(termfreq));
				if(!wQTwDTAccumulator.containsKey(docIds[i])){
					wQTwDTAccumulator.put(docIds[i], wQT*wDT);
					
				}else{
					wQTwDTAccumulator.put(docIds[i], wQTwDTAccumulator.get(docIds[i])+(wQT*wDT));
					
				}
				System.out.print("Position: ");
				while (termfreq != 0) {

					postingsPosition = postingsPosition + 4;
					postings.seek(postingsPosition);
					 if(termfreq>=0){
					postings.read(buffer, 0, buffer.length);
					int termPos = ByteBuffer.wrap(buffer).getInt() + previousPos;
					previousPos = ByteBuffer.wrap(buffer).getInt();
					posList.add(termPos);
					System.out.print(" " + termPos);
					 }

					termfreq--;

				}

				postMap.put(docIds[i], posList);

			}

			return postMap;
		} catch (IOException ex) {
			System.out.println(ex.toString());
		}
		return null;
	}

	public HashMap<Integer, List<Integer>> GetPostings(String term) {
		wQTwDTAccumulator = new HashMap<Integer,Double>();
		docWeightLD = new ArrayList<Integer>();
		String[] in = term.split(" ");

		for (String temp : in) {

			temp=PorterStemmer.processToken(temp);
			long postingsPosition = binarySearchVocabulary(temp);
			System.out.println("Posting Position: " + postingsPosition);
			// readLDWeight(mWeight,mFileNames);
			if (postingsPosition >= 0) {
				
				readPostingsFromFile(mPostings, postingsPosition);
				
				
				//return readPostingsFromFile(mPostings, postingsPosition);
			}
		}
		HashMap<Integer,Double> wQTwDTAccumulator1 = wQTwDTAccumulator;
		divideAdByLd();
		return new HashMap();
	}
	
	private void divideAdByLd(){
		//wQTwDTAccumulator docWeightLD
		for(int i=0;i<docWeightLD.size();i++){
			if(wQTwDTAccumulator.get(i) != null && wQTwDTAccumulator.get(i)> 0){
				Integer j=i;
				//docWeightLD.get(i);
                                if(docWeightLD.get(i)>0){
				wQTwDTAccumulator.put(j, wQTwDTAccumulator.get(i)/docWeightLD.get(i));}
			}
			
		}
		Object[] a = wQTwDTAccumulator.entrySet().toArray();
		Arrays.sort(a, new Comparator() {
		    public int compare(Object o1, Object o2) {
		        return ((Map.Entry<Integer, Double>) o2).getValue()
		                   .compareTo(((Map.Entry<Integer, Double>) o1).getValue());
		    }
		});
		int i=0;
		System.out.println();
		System.out.println("Top 10 Ranks are ");
		for (Object e : a) {
			if(i<10){
				System.out.println(mFileNames.get(((Map.Entry<Integer, Double>) e).getKey()) + " :"
		            + ((Map.Entry<Integer, Double>) e).getValue());
			}
		    i++;
		}
		   
	}

	private long binarySearchVocabulary(String term) {
		// do a binary search over the vocabulary, using the vocabTable and the
		// file vocabList.
		System.out.println("Term to search: " + term);
		int i = 0, j = mVocabTable.length / 2 - 1;
		while (i <= j) {
			try {
				int m = (i + j) / 2;
				long vListPosition = mVocabTable[m * 2];
				int termLength;
				if (m == mVocabTable.length / 2 - 1) {
					termLength = (int) (mVocabList.length() - mVocabTable[m * 2]);
				} else {
					termLength = (int) (mVocabTable[(m + 1) * 2] - vListPosition);
				}

				mVocabList.seek(vListPosition);

				byte[] buffer = new byte[termLength];
				mVocabList.read(buffer, 0, termLength);
				String fileTerm = new String(buffer, "ASCII");
				System.out.println("File Term" + fileTerm);

				int compareValue = term.compareTo(fileTerm);
				if (compareValue == 0) {
					// found it!
					return mVocabTable[m * 2 + 1];
				} else if (compareValue < 0) {
					j = m - 1;
				} else {
					i = m + 1;
				}
			} catch (IOException ex) {
				System.out.println(ex.toString());
			}
		}
		return -1;
	}

	private static List<String> readFileNames(String indexName) {
		try {
			final List<String> names = new ArrayList<String>();
			final Path currentWorkingPath = Paths.get(indexName).toAbsolutePath();

			Files.walkFileTree(currentWorkingPath, new SimpleFileVisitor<Path>() {
				int mDocumentID = 0;

				public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
					// make sure we only process the current working directory
					if (currentWorkingPath.equals(dir)) {
						return FileVisitResult.CONTINUE;
					}
					return FileVisitResult.SKIP_SUBTREE;
				}

				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
					// only process .txt files
					if (file.toString().endsWith(".json")) {
						names.add(file.toFile().getName());
					}
					return FileVisitResult.CONTINUE;
				}

				// don't throw exceptions if files are locked/other errors occur
				public FileVisitResult visitFileFailed(Path file, IOException e) {

					return FileVisitResult.CONTINUE;
				}

			});
			return names;
		} catch (IOException ex) {
			System.out.println(ex.toString());
		}
		return null;
	}

	private static long[] readVocabTable(String indexName) {
		try {
			long[] vocabTable;

			RandomAccessFile tableFile = new RandomAccessFile(new File(indexName, "vocabTable.bin"), "r");

			byte[] byteBuffer = new byte[4];
			tableFile.read(byteBuffer, 0, byteBuffer.length);

			int tableIndex = 0;
			vocabTable = new long[ByteBuffer.wrap(byteBuffer).getInt() * 2];
			byteBuffer = new byte[8];

			while (tableFile.read(byteBuffer, 0, byteBuffer.length) > 0) { // while
																			// we
																			// keep
																			// reading
																			// 4
																			// bytes
				vocabTable[tableIndex] = ByteBuffer.wrap(byteBuffer).getLong();
				tableIndex++;
			}
			tableFile.close();
			return vocabTable;
		} catch (FileNotFoundException ex) {
			System.out.println(ex.toString());
		} catch (IOException ex) {
			System.out.println(ex.toString());
		}
		return null;
	}

	public List<String> getFileNames() {
		return mFileNames;
	}

	public int getTermCount() {
		return mVocabTable.length / 2;
	}
}
