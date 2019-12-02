package main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class WaveletTreeTest {
	@Test
	public void testConstruct() {
		Map<Character, String> codeTable = new HashMap<>();
		codeTable.put('A', "0");
		codeTable.put('C', "100");
		codeTable.put('G', "101");
		codeTable.put('T', "110");
		codeTable.put('$', "111");
		WaveletTree waveletTree = new WaveletTree();
		WaveletTreeNode root = waveletTree.constructWT(codeTable);
		assertNotEquals(null, root);
	}
	@Test
	public void testInsert() {
		Map<Character, String> codeTable = new HashMap<>();
		codeTable.put('A', "0");
		codeTable.put('C', "100");
		codeTable.put('G', "101");
		codeTable.put('T', "110");
		codeTable.put('$', "111");
		String L = "CTGCACCAATTCCGG$";
		WaveletTree waveletTree = new WaveletTree();
		WaveletTreeNode root = waveletTree.constructWT(codeTable);
		waveletTree.insertWT(codeTable, root, L, L.length(), 0);
		List<String> result = new ArrayList<>();
		dfs(root, result);
		List<String> assertList = Arrays.asList("1111011001111111", "", "0100001100001", "010000011", "", "", "0001", "", "");
		assertEquals(assertList, result);
	}
	
	private void dfs(WaveletTreeNode node, List<String> result) {
		if(node == null) {
			return;
		}
		result.add(node.val);
		dfs(node.left, result);
		dfs(node.right, result);
		
	}
}
