
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class TSTStringTokenizer {
	String[] fileName;
	private TST<TSTNode> tst;
	
	public TSTStringTokenizer(TST<TSTNode> tst) {
		this.tst = tst;
	}
	
	public String getFileName(int fileIdx) {
		return fileName[fileIdx];
	}
	
	public static String PrefixString(String stringline){
		String identifiers = "~!@#$%^&*()_+{}|:\"<>?-+[]\\;',./`=";
		
		if (identifiers.indexOf(stringline.charAt(0)) == -1){
			return stringline;
		}
		else if(stringline.length() == 1){
			return null;
		}else{
			return PrefixString(stringline.substring(1));
		}
	}
	
	public static String SuffixString(String stringline){
		String identifiers = "~!@#$%^&*()_+{}|:\"<>?-+[]\\;',./`=";
		if (identifiers.indexOf(stringline.charAt(stringline.length()-1)) == -1){
			return stringline;
		}
		return SuffixString(stringline.substring(0, stringline.length()-1));
	}
	
	
	public void readFile() {
		String[] filelist;
		
		ArrayList<ArrayList<String>> store_all = new ArrayList<ArrayList<String>>();
		
		File file = new File("text");
		filelist = file.list();
		
		fileName = new String[filelist.length];
		
		for(int i = 0; i < filelist.length; i++){
			fileName[i] = filelist[i].substring(0, filelist[i].length()-4);
			//System.out.println(fileName[i]);
		}
		
		
		try{
			for (int i = 0; i < filelist.length; i++) {
				ArrayList<String> store = new ArrayList<String>();
				// If the file isn't .txt file, skip it
				if(!filelist[i].substring(filelist[i].length()-4).equals(".txt")) continue;
				
				File f = new File("text/" + filelist[i]);
		    	InputStream input = new FileInputStream(f);
		    	BufferedReader read = new BufferedReader(new InputStreamReader(input));
		    	
		    	//System.out.println();
		    	store.add(filelist[i]);
		    	String stringline = read.readLine();
		    	int row_number = 1;
		    	
		    	//if(stringline != null)
		    	while(stringline !=null){
		    		StringTokenizer st = new StringTokenizer(stringline);
					String row_number_string = "row: "+row_number;
					store.add(row_number_string);
					while( st.hasMoreElements() ){									
						String temp = st.nextToken();
						
						if(temp.length() == 1){
							if((temp.charAt(0)>= 'a' && temp.charAt(0) <= 'z') ||
							    (temp.charAt(0)>= 'A' && temp.charAt(0) <= 'Z')) {															
								store.add(temp);
								// Add the word to TST
								TSTNode tstNode = new TSTNode(i, row_number);
								tst.put(temp, tstNode);
							}
						}else{
							String Prefix_fixed = PrefixString(temp);
							if(Prefix_fixed != null){
								String final_string =SuffixString(Prefix_fixed);
								if(final_string != null){
									store.add(final_string);
									// Add the word to TST
									TSTNode tstNode = new TSTNode(i, row_number);
									tst.put(temp, tstNode);
								}
							}
						}
					}	    		
		    		stringline = read.readLine();
		    		row_number++;
		    	}
		    	
		    	
		    	store_all.add(store);
	
		    	read.close();
		    	input.close();
			}
			
		}catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }
		
				/* For debug
		for(ArrayList<String> tempo: store_all){
		
			for(String temp1: tempo){
				System.out.println(temp1);
			
			}
			System.out.println();
			System.out.println();
		}*/
		
	
	}
	
	public static void main(String[] args) {
		TSTStringTokenizer tk = new TSTStringTokenizer(new TST<TSTNode>(new IndexTable()));
		tk.readFile();			
	}

}
