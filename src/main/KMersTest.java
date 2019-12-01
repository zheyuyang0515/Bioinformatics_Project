package main;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class KMersTest {
	@Test
	public void fetchKmersTest() {
		KMers kMers = new KMers();
		Map<String, Integer> map = kMers.fetchKmers("acagt", 1);
		Map<String, Integer> assertMap = new HashMap<String, Integer>();
		assertMap.put("a", 2);
		assertMap.put("c", 1);
		assertMap.put("g", 1);
		assertMap.put("t", 1);
		assertEquals(assertMap, map);
	}
}
