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
			
		}
		return root;
		
	}
}
