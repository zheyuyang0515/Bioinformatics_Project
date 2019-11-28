package main;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BwtConverterTest {
	
	@Test
	public void testBwtEncode() {
		BwtConverter bwtConverter = new BwtConverter();
		Bwt bwt = bwtConverter.bwtEncode("banana");
		assertEquals("annb$aa", bwt.L);
		assertEquals("$aaabnn", bwt.F);
	}
	@Test
	public void testBwtDecode() {
		BwtConverter bwtConverter = new BwtConverter();
		String source = bwtConverter.bwtDecode(new Bwt("$aaabnn", "annb$aa"));
		assertEquals("banana", source);
	}
}
