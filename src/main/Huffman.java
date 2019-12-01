public class Huffman {
	 public class TreeNode {
		 public char c;
		 public int frequency;
		 public TreeNode left, right;
		 
		 public TreeNode(int frequency, char c) {
		    this.c = c;
		    this.frequency = frequency;
		    this.left = this.right = null;

		 }
		 public TreeNode(int frequency, char c, TreeNode left, TreeNode right) {
			    this.c = c;
			    this.frequency = frequency;
			    this.left = left;
			    this.right = right;

			 }
		 
	}
	 
	public Map<Character, String> huffman(String L) throws IOException{
			Map<Character, String> codeTable = new HashMap<>();
			int n = L.length();
			Map<Character, Integer> count = new HashMap<>();
			for(int i = 0; i < n; i++) {
				count.putIfAbsent(L.charAt(i), 0);
				count.put(L.charAt(i), count.get(L.charAt(i)) + 1);
			}
			
			int k = count.size();
			PriorityQueue<TreeNode> pq = new PriorityQueue<TreeNode>(k, new Comparator<TreeNode>() {
				@Override
				public int compare(TreeNode o1, TreeNode o2) {
					return o1.frequency - o2.frequency;
				}
			});
			
			for(Character key : count.keySet()) {
				TreeNode node = new TreeNode(count.get(key), key);
				pq.offer(node);
			}
			while(pq.size() >= 2) {
				TreeNode left = pq.poll();
				TreeNode right = pq.poll();
				TreeNode father = new TreeNode(left.frequency + right.frequency, ' ', left, right);
				pq.offer(father);
			}
			TreeNode root = pq.poll();
			constructCodeTable(root, "", codeTable);
	
			return codeTable;
	}
	
	public void constructCodeTable(TreeNode node, String sub, Map<Character, String> codeTable) {
		if(node.left == null && node.right == null) {
			codeTable.put(node.c, sub);
			return;
		}
		if(node.left != null) {
			constructCodeTable(node.left, sub + "0", codeTable);
		}
		if(node.right != null) {
			constructCodeTable(node.right, sub + "1", codeTable);
		}
	}

			
	  

	
	public static void main(String[] args) throws IOException {
		test t = new Huffman();
		
		Map<Character, String> map = t.huffman("we will we will r u");
		for(Character key : map.keySet()) {
			System.out.println(key + ": " + map.get(key));
		}
	}
}

