package main;

public class WaveletTreeNode {
	String val;
	WaveletTreeNode left;
	WaveletTreeNode right;
	public WaveletTreeNode(String val) {
		this.val = val;
		left = right = null;
	}
	public WaveletTreeNode() {
		this.val = "";
		left = right = null;
	}
}
