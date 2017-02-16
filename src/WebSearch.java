import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class WebSearch {

	public IndexTable idxTable = new IndexTable();
	public TST<TSTNode> tst = new TST<TSTNode>(idxTable);
	public TSTStringTokenizer token;

	public void displayFile(SortNode sortN) {
		String fileName = token.getFileName(sortN.fileIdx);
		StdOut.println("html/" + fileName + ".html");
		StdOut.println("Count = " + sortN.cnt);

		try {
			File f = new File("text/" + fileName + ".txt");
			InputStream input = new FileInputStream(f);
			BufferedReader read = new BufferedReader(new InputStreamReader(input));

			String stringline;
			int row_number = 0;

			while( (stringline = read.readLine()) !=null){
				row_number++;
				if(row_number < sortN.line) {
					continue;
				}
				else if(row_number > sortN.line) {
					break;
				}
				else {
					StdOut.println(stringline);
				}   		
			}
			read.close();
			input.close();

		}catch (FileNotFoundException e) {  
			e.printStackTrace();  
		} catch (IOException e) {  
			e.printStackTrace();  
		}

		StdOut.println("\n");
	}

	public static void main(String[] args) {
		WebSearch webS = new WebSearch();
		SortNode[] cntArray = null;

		// Read text files and put words to TST
		webS.token = new TSTStringTokenizer(webS.tst);
		webS.token.readFile();

		while(true) {
			StdOut.println("\n\nType a key word to search web pages('Q' to exit the program)");
			String keyword = StdIn.readString();
			if(keyword.equals("Q")) break;

			int wordIdx = webS.tst.get(keyword);
			if(wordIdx != -1) {
				cntArray = webS.idxTable.getCntArray(wordIdx);
				quickSort.quicksort(cntArray);
			}

			if(cntArray != null) {
				/*
			for( int i = 0; i < cntArray.length; i++) {
				StdOut.println("i:" + i + ";  cnt:" + cntArray[i].cnt + "; fileidx:" + cntArray[i].fileIdx);
			}*/

				// The sort of cnt is increasing order. We want to display it in decreasing order.
				for(int i = cntArray.length-1; i>=0; i--) {
					// cnt=0 means the file doesn't contain the key word.
					if(cntArray[i].cnt == 0) break;
					webS.displayFile( cntArray[i] );
				}
			}
		}

	}

}
