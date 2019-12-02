package main;

import java.io.File;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class KMerCompressor {
	private static int getFileLine(String fileName) throws Exception {
		File file = new File(fileName);
		LineNumberReader reader = new LineNumberReader(new FileReader(file));
		reader.skip(file.length());
		int lines = reader.getLineNumber();
		reader.close();
		return lines;
	}
	public static Map<String, Integer> kmersMap;
	private static int blockNum;
	//set line number++
	public static synchronized int setLineNum() {
		blockNum++;
		return blockNum - 1;
	}
	public static void main(String[] args) throws Exception {
		if(args.length < 3) {
			throw new IllegalArgumentException("Argument Format Error: 2 arguments required!");
		}
		kmersMap = new ConcurrentHashMap<>();
		String fileName = args[0];
		int threadNum = Integer.parseInt(args[1]);
		int kmerLen = Integer.parseInt(args[2]);
		blockNum = 0;
		int totalLineNum = getFileLine(fileName);
		int totalBlockNum = totalLineNum / 4;
		List<Thread> list = new ArrayList<>();
		for(int i = 0; i < threadNum; i++) {
			Thread thread = new Thread(new CompressorThread(totalLineNum, fileName, totalBlockNum, kmerLen));
			thread.start();
			list.add(thread);
		}		
		for(Thread thread : list) {
			thread.join();
		}
		System.out.println(kmersMap.size());
		/*for(String key : kmersMap.keySet()) {
			System.out.println(key + ": " + kmersMap.get(key));
		}*/
	}
}
