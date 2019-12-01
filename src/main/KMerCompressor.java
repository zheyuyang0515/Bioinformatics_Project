package main;

import java.io.File;
import java.io.FileReader;
import java.io.LineNumberReader;

public class KMerCompressor {
	private static int getFileLine(String fileName) throws Exception {
		File file = new File(fileName);
		LineNumberReader reader = new LineNumberReader(new FileReader(file));
		reader.skip(file.length());
		int lines = reader.getLineNumber();
		reader.close();
		return lines;
	}
	public static void main(String[] args) {
		try {
			System.out.println(getFileLine("SRR065000.fastq"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
