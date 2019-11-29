package main;

public class WaveletTreeNode {
	int val;
	WaveletTreeNode left;
	WaveletTreeNode right;
	public WaveletTreeNode(int val) {
		this.val = val;
		left = right = null;
	}
	public WaveletTreeNode() {
		this.val = -1;
		left = right = null;
	}
}
