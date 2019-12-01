import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public class Runlength {

	 
	public List<String> runLengthCoding(String s) throws IOException{
		   List<String> res = new ArrayList<>();
		    StringBuilder sb = new StringBuilder();
			int n = s.length();
			char pre = s.charAt(0);
			int count = 1;
			for(int i = 1; i < n; i++) {
				if(s.charAt(i) == pre) {
					count++;
				}else {
					res.add(sb.append(count).toString());
					count = 1;
					pre = s.charAt(i);
				}
				
				if(i == n - 1) {
					res.add(sb.append(count).toString());
				}
			}

			
	        return res;
	}
	
	public List<String> gammaCoding(List<String> runlength) {
		List<String> result = new ArrayList<>();
				
		//StringBuilder res = new StringBuilder();
		//StringBuilder sb = new StringBuilder();
		List<Integer> temp = new ArrayList<>();
		
		for(int i = 0; i < runlength.size(); i++) {
			temp.add(Integer.parseInt(runlength.get(i)));
		}
		
		for(Integer te : temp) {
			//sb.append(gammaOperation(te));
			result.add(gammaOperation(te));
		}
		
		return result;
	}
	
	private String gammaOperation(int num) {
		StringBuilder code = new StringBuilder();
		StringBuilder length = new StringBuilder();
		String offset = "";
		String tempCode = Integer.toBinaryString(num);
		
		if(tempCode.charAt(0) == '0') { 
			return null;
		}else {
			offset = tempCode.substring(1);
		}
		
		for(int i = 1; i < tempCode.length(); i++) {
			length.append(1);
		}
		length.append(0);
		code.append(length);
		code.append(offset);
		
		
		return code.toString();	
	}


			
	  

	
	public static void main(String[] args) throws IOException {
		Runlength t = new Runlength();
		List<String> runlength;
		List<String> res;
		String string = "000111001111100";
		runlength = t.runLengthCoding(string); 
		res = t.gammaCoding(runlength);
		
	    for(String re : res)
	    	System.out.println(re);
	}
}

