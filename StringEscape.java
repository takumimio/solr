package myPrograms;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.lang3.StringEscapeUtils;

public class StringEscape {

	public static void main(String[] args) throws IOException {
		String xmlinFile = "/Users/hal/Desktop/SigmodRecord.xml";
		BufferedReader br = new BufferedReader(new FileReader(xmlinFile));
		String xmloutFile = "/Users/hal/Desktop/SigmodRecord3.xml";
		FileWriter bw = new  FileWriter(xmloutFile);
		for (String line; (line = br.readLine()) != null;) {
			line = StringEscapeUtils.unescapeHtml3(line);
			bw.write(line);
			bw.write("\n");
			bw.flush();
		}
		System.out.println("Done");
	}
}
