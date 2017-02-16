
import java.io.File;

import java.io.IOException;

import java.io.PrintWriter;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;

public class JsoupConverter {

	// Convert html infile to text outfile
	public static void html2text(String infile, String outfile) {
		try {

			Document doc = Jsoup.parse(new File(infile),"utf-8");
			//makes html() preserve linebreaks and spacing
			doc.outputSettings(new Document.OutputSettings().prettyPrint(false));
			doc.select("br").append("\\n");
			doc.select("p").prepend("\\n\\n");
			String s = doc.html().replaceAll("\\\\n", "\n");    
			String text =  Jsoup.clean(s, "", Whitelist.none(), new Document.OutputSettings().prettyPrint(false));

			PrintWriter out = new PrintWriter(outfile);
			out.println(text);
			out.close();	

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {

		// Get all the files' name under directory "html"
		String[] filelist;
		File file = new File("html");
		filelist = file.list();

		// Convert all the html files to text files under directory "text"
		for (int i = 0; i < filelist.length; i++) {
			
			// Check if file isn't XXX.htm, then skip it
			String str = filelist[i].substring(filelist[i].length()-4);
			if(!str.equals(".htm"))
				continue;
			
			System.out.println("file " + filelist[i]);
			String filename = filelist[i].substring(0, filelist[i].length()-4);
			html2text("html/"+filelist[i], "text/"+filename+".txt");
		}
	}

}
