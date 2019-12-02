package main;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.LineNumberReader;
import java.util.ArrayList;
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
	private static int kmersBlockNum;
	//set line number++
	public static synchronized int setLineNum() {
		blockNum++;
		return blockNum - 1;
	}
	public static synchronized int setKmersLineNum() {
		kmersBlockNum++;
		return kmersBlockNum - 1;
	}
	public static void main(String[] args) throws Exception {
		if(args.length != 4) {
			throw new IllegalArgumentException("Argument Format Error: 4 arguments required!");
		}
		kmersMap = new ConcurrentHashMap<>();
		String fileName = args[0];
		int threadNum = Integer.parseInt(args[1]);
		int kmerLen = Integer.parseInt(args[2]);
		String outputSource = args[3];
		blockNum = 0;
		int totalLineNum = getFileLine(fileName);
		int totalBlockNum = totalLineNum / 4;
		List<Thread> list = new ArrayList<>();
		for(int i = 0; i < threadNum; i++) {
			Thread thread = new Thread(new KmersThread(totalLineNum, fileName, totalBlockNum, kmerLen));
			thread.start();
			list.add(thread);
		}		
		for(Thread thread : list) {
			thread.join();
		}
		System.out.println(kmersMap.size() + " kmers create successfully, now writing to the output file " + outputSource + "...");
		int sequenctNum = 1;
		FileWriter writer = new FileWriter(outputSource);
		for(String key : kmersMap.keySet()) {
			writer.write('>');
			writer.write("SEQUENCE_" + sequenctNum);
			writer.write('\n');
			writer.write(key);
			writer.write('\n');
			writer.flush();
			sequenctNum++;
		}
		writer.close();
		System.out.println("output file " + outputSource + " create successfully.");
		totalLineNum = getFileLine(outputSource);
		totalBlockNum = totalLineNum / 2;
		List<Thread> compressorThreadList = new ArrayList<>();
		kmersBlockNum = 0;
		for(int i = 0; i < threadNum; i++) {
			Thread thread = new Thread(new CompressorThread(totalLineNum, outputSource, totalBlockNum));
			thread.start();
			compressorThreadList.add(thread);
		}		
		for(Thread thread : compressorThreadList) {
			thread.join();
		}
	}
}
