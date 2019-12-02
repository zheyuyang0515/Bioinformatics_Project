package main;

import java.util.Map;

public class KMers {
	public static Map<String, Integer> fetchKmers(String s, int len, Map<String, Integer> map) {
		if(s.length() == 0) {
			return map;
		}
		StringBuilder sb = new StringBuilder();
		int count = 0;
		for(int i = 0; i < s.length(); i++) {
			sb.append(s.charAt(i));
			count++;
			if(count == len) {
				String temp = sb.toString();
				map.putIfAbsent(temp, 0);
				map.put(temp, map.get(temp) + 1);
				sb = sb.delete(0, 1);
				count--;
			}
		}
		return map;
	}
}
