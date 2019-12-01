package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class WaveletTree {
	class Element {
		Character ch;
		String code;
		public Element(Character ch, String code) {
			this.ch = ch;
			this.code = code;
		}
	}
	public void insertWT(Map<Character, String> codeTable, WaveletTreeNode root, String L, int len, int level) {
		char[] lp = new char[L.length()];
		int lSize = 0;
		char[] rp = new char[L.length()];
		int rSize = 0;
		for(int i = 0; i < len; i++) {
			String s = codeTable.get(L.charAt(i));
			char ch = s.charAt(level);
			if(ch == '1') {
				root.val += '1';
				rp[rSize] = L.charAt(i);
				rSize++;
			} else {
				root.val += '0';
				lp[lSize] = L.charAt(i);
				lSize++;
			}
		}
		if(root.left != null) {
			insertWT(codeTable, root.left, String.valueOf(lp), lSize, level + 1);
		} else if(root.right != null) {
			insertWT(codeTable, root.right, String.valueOf(rp), rSize, level + 1);
		}
		
	}
	public WaveletTreeNode constructWT(Map<Character, String> codeTable) {
		WaveletTreeNode root = new WaveletTreeNode();
		List<Element> list = new ArrayList<>();
		for(Character key : codeTable.keySet()) {
			list.add(new Element(key, codeTable.get(key)));
		}
		Collections.sort(list, new Comparator<Element>() {
			@Override
			public int compare(Element e1, Element e2) {
				return e1.ch - e2.ch;
			}
		});
		for(int i = 0; i < list.size(); i++) {
			Element element = list.get(i);
			int len = element.code.length();
			WaveletTreeNode node = root;
			for(int j = 0; j < len; j++) {
				if(element.code.charAt(j) == 0) {
					if(node.left == null) {
						node.left = new WaveletTreeNode();
					}
					node = node.left;
				} else {
					if(node.right == null) {
						node.right = new WaveletTreeNode();
					}
					node = node.right;
				}
			}
		}
		return root;
	}
}
