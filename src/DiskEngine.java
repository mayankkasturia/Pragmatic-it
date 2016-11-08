
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class DiskEngine {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);

		System.out.println("Menu:");
		System.out.println("1) Build index");
		System.out.println("2) Read and query index");
		System.out.println("Choose a selection:");
		int menuChoice = scan.nextInt();
		scan.nextLine();

		switch (menuChoice) {
		case 1:
			System.out.println("Enter the name of a directory to index: ");
			String folder = scan.nextLine();

			IndexWriter writer = new IndexWriter(folder);
			writer.buildIndex();
			break;

		case 2:
			System.out.println("Enter the name of an index to read:");
			String indexName = scan.nextLine();

			DiskInvertedIndex index = new DiskInvertedIndex(indexName);
			System.out.println("Enter the mode to operate:");
			System.out.println("1.Boolean Query Mode");
			System.out.println("2.Ranked Retrival Mode");
			int choice = scan.nextInt();
			scan.nextLine();

			switch (choice) {
			case 1:
				break;

			case 2:

				while (true) {
					System.out.println("Enter one or more search terms, separated " + "by spaces:");
					String input = scan.nextLine();

					if (input.equals("EXIT")) {
						break;
					}
					input = input.trim();
					//String[] in = input.split(" ");
					//for (String inputString : in) {

						HashMap<Integer, List<Integer>> postingsList = index
								.GetPostings(input.toLowerCase());

						if (postingsList == null) {
							System.out.println("Term not found");
						} else {
							//System.out.print("Docs: ");
							// old
							/*
							 * for (int post : postingsList) { //new changes
							 * starts System.out.print("Posting List: "+post);
							 * //new changes ends
							 * System.out.print(index.getFileNames().get(post) +
							 * " "); }*/
							 
							/*Iterator it = postingsList.entrySet().iterator();
							
							while (it.hasNext()) {
								System.out.println();
								Map.Entry pair = (Map.Entry) it.next();
								System.out.print("DocID: " + index.getFileNames().get((int) pair.getKey()));
								System.out.print(" Position: " + pair.getValue());
								System.out.println();
								// System.out.println(pair.getKey() + " = " +
								// pair.getValue());
								// it.remove(); // avoids a
								// ConcurrentModificationException
							}
							System.out.println();
							System.out.println();*/
						}
/*						ArrayList<Double> accum = (ArrayList<Double>) index.getwQTwDTAccumulator();
						double sum = 0;
						Iterator itr = accum.iterator();
						ArrayList<Integer> LD = DiskInvertedIndex.getDocWeightWD();
						int i = 0;
						while (itr.hasNext()) {
							Double temp = (Double) itr.next();
							if (temp != 0) {
								// accum.add(i, temp/LD.get(i));
								System.out.println("Accumulator: " + temp);
								i++;
							}

						}*/

					//}

				}
				/*System.out.println("Top 2 documents: ");
				for (int i = 0; i < 2; i++) {
					DiskInvertedIndex.getmFileNames().get(i);
				}*/
				break;
			}

			break;
		}
	}
}
