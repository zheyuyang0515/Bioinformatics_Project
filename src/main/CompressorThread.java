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

public class CompressorThread implements Runnable{
	private Thread t;
	int totalLineNum;
	int totalBlockNum;
	String fileName;
	public CompressorThread(int totalLineNum, String fileName, int totalBlockNum) {
		this.totalLineNum = totalLineNum;
		this.fileName = fileName;
		this.totalBlockNum = totalBlockNum;
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
		int currentLine = 1;
		while(true) {
			int block = KMerCompressor.setKmersLineNum();
			if(block >= totalBlockNum) {
				break;
			}
			int line = block * 2 + 2;
			String kmer = "";
			while(currentLine <= line) {
				try {
					kmer = reader.readLine();
					currentLine++;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			Bwt bwt = BwtConverter.bwtEncode(kmer);
			Huffman huffman = new Huffman();
			Map<Character, String> codeTable = new HashMap<>();
			try {
				codeTable = huffman.huffman(bwt.L);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			WaveletTree waveletTree = new WaveletTree();
			WaveletTreeNode root = waveletTree.constructWT(codeTable);
			waveletTree.insertWT(codeTable, root, bwt.L, bwt.L.length(), 0);
			System.out.println(root.val);
		}
	}

}
