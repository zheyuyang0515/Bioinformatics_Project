package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BwtConverter {
	public static Bwt bwtEncode(String kmer) {
		StringBuilder sb = new StringBuilder(kmer + "$");
		String[] arr = new String[sb.length()];
		for(int i = 0; i < arr.length; i++) {
			char head = sb.charAt(0);
			sb = sb.delete(0, 1);
			arr[i] = sb.append(head).toString();
		}
		Arrays.sort(arr, new Comparator<String>() {
			@Override
			public int compare(String s1, String s2) {
				return s1.compareTo(s2);
			}
		});
		StringBuilder F = new StringBuilder();
		StringBuilder L = new StringBuilder();
		for(String s : arr) {
			L.append(s.charAt(s.length() - 1));
			F.append(s.charAt(0));
		}
		return new Bwt(F.toString(), L.toString());
	}
	
	public String bwtDecode(Bwt bwt) {
		String F = bwt.F;
		String L = bwt.L;
		Map<Character, List<Integer>> map = new HashMap<>();
		for(int i = 0; i < F.length(); i++) {
			char ch = F.charAt(i);
			map.putIfAbsent(ch, new ArrayList<>());
			map.get(ch).add(i);
		}
		StringBuilder result = new StringBuilder();
		int index = 0;
		while(true) {
			char chL = L.charAt(index);
			if(chL == '$') {
				break;
			}
			result.append(L.charAt(index));
			if(map.get(chL).size() == 1) {
				index = map.get(chL).get(0);	
			} else {
				int count = 0;
				for(int i = 0; i <= index; i++) {
					if(L.charAt(i) == chL) {
						count++;
					}
				}
				index = map.get(chL).get(0) + count - 1;
			}
		}
		return result.reverse().toString();
	}
}
