package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class CompressorThread implements Runnable{
	private Thread t;
	int totalLineNum;
	int totalBlockNum;
	String fileName;
	String resultFile;
	public CompressorThread(int totalLineNum, String fileName, int totalBlockNum, String resultFile) {
		this.totalLineNum = totalLineNum;
		this.fileName = fileName;
		this.totalBlockNum = totalBlockNum;
		this.resultFile = resultFile;
	}
	public void start () {
		if (t == null) {
	        t = new Thread(this);
	        t.start ();
	    }
    }
	public static byte[] file2byte(String path)
    {
        try {
            FileInputStream in =new FileInputStream(new File(path));
            byte[] data=new byte[in.available()];
            in.read(data);
            in.close();
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
	public synchronized static void traverseWT(WaveletTreeNode root, String output) throws IOException {
		if(root == null) {
			return;
		}
		Queue<WaveletTreeNode> queue = new LinkedList<>();
		List<List<Byte>> result = new ArrayList<>();
		queue.offer(root);
		Coding c = new Coding();
		int listSize = 0;
		while(!queue.isEmpty()) {
			int size = queue.size();
			for(int i = 0; i < size; i++) {
				WaveletTreeNode node = queue.poll();
				if(node.val.length() != 0) {
					List<String> runlength = c.runLengthCoding(node.val);
					List<Byte> compressedCode = node == root ? c.gammaCoding(runlength, node.val, true) : c.gammaCoding(runlength, node.val, false);
					result.add(compressedCode);
					listSize += compressedCode.size();
					/*for(byte b : compressedCode) {
						//System.out.println(b);
						writeConcurrent(b, outputStream);
					}*/
					if(node.left != null) {
						queue.offer(node.left);
					}
					if(node.right != null) {
						queue.offer(node.right);
					}
				}
			}
		}
		FileOutputStream outputStream  =new FileOutputStream(new File(output), true);
		writeConcurrent(result, outputStream, listSize);
		outputStream.close();
	}
	private synchronized static void writeConcurrent(List<List<Byte>> result, FileOutputStream outputStream, int size) {
		byte[] arr = new byte[size];
		int count = 0;
		for(List<Byte> list : result) {
			for(byte b : list) {
				arr[count] = b;
				count++;
			}
		}
		try {
			outputStream.write(arr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			try {
				traverseWT(root, resultFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
