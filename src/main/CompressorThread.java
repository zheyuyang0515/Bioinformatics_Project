package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.spi.DirStateFactory.Result;

public class CompressorThread implements Runnable {
	private Thread t;
	int totalLineNum;
	int totalBlockNum;
	String fileName;
	int kmerLen;
	public CompressorThread(int totalLineNum, String fileName, int totalBlockNum, int kmerLen) {
		this.totalLineNum = totalLineNum;
		this.fileName = fileName;
		this.totalBlockNum = totalBlockNum;
		this.kmerLen = kmerLen;
	}
	public void start () {
		if (t == null) {
	        t = new Thread(this);
	        t.start ();
	    }
    }
	@Override
	public void run() {
		// TODO Auto-generated method stub
		File file = new File(fileName);
		FileReader fileReader = null;
		try {
			fileReader = new FileReader(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LineNumberReader reader = new LineNumberReader(fileReader);
		//List<String> result = new ArrayList<>();
		int currentLine = 1;
		while(true) {
			int block = KMerCompressor.setLineNum();
			if(block >= totalBlockNum) {
				//System.out.println(result.size());
				break;
			}
			int line = block * 4 + 2;
			String ctx = "";
			while(currentLine <= line) {
				try {
					ctx = reader.readLine();
					currentLine++;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			Map<String, Integer> map = KMers.fetchKmers(ctx, kmerLen, KMerCompressor.kmersMap);
			/*for(String key : map.keySet()) {
				System.out.println(key + ": " + map.get(key));
			}*/
			//System.out.println(result.size());
			//System.out.println(ctx);
			/*try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		}
	}

}
