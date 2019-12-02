package main;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public class Coding {

	 
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
					sb = new StringBuilder();
					res.add(sb.append(count).toString());
					count = 1;
					pre = s.charAt(i);
				}
				
				if(i == n - 1) {
					sb = new StringBuilder();
					res.add(sb.append(count).toString());
				}
			}

			
	        return res;
	}

	
//第一位：是否是同�?个kmer
//第二位：是否是同�?个小波树的节�?
//第三位：是哪种coding方法
//第四位：是否是属于前�?个byte
//后四�?: 存数�?
	
//
	public List<Byte> gammaCoding(List<String> runlength, String s, boolean isRoot) {
		List<String> resultTemp = new ArrayList<>();
		List<Byte> res = new ArrayList<>();
		List<Integer> temp = new ArrayList<>();
		
		for(int i = 0; i < runlength.size(); i++) {
			temp.add(Integer.parseInt(runlength.get(i)));
		}
		
		
		for(Integer te : temp) {
			//sb.append(gammaOperation(te));
			//System.out.println(te);
			resultTemp.add(gammaOperation(te));
		}
		
		
		int curPos = 0;
		
		for(Integer tInteger : temp) {
			if(tInteger < 8) {
				String sub = s.substring(curPos, curPos + tInteger);
				//直接编码
				int dircLen = (int) Math.ceil(tInteger / 4.0);
				for(int i = 0; i < dircLen;i++) {
					String subsub = i != dircLen - 1 ? sub.substring(i * 4, i * 4 + 4) : sub.substring(i * 4);
					byte b = stringToByte(subsub);
					if(!isRoot || curPos != 0 || i != 0) {
						b = (byte) (b | -128);
					}
					if(curPos != 0 || i != 0) {
						b = (byte) (b | 64);
					}
					b = (byte) (b | 32);
					if(i != 0) {
						b = (byte) (b | 16);
					}
					res.add(b);
				}
				curPos = curPos + tInteger;
				
			}else {
				//间接编码,runlength + gamma
				//比如�?8�?0，那么对8进行 gamma编码，第5位记录是�?0还是1进行编码，所以剩�?3位存数据
				String gamma =  gammaOperation(tInteger);
				//System.out.println(gamma);
				int undircLen = (int) Math.ceil(gamma.length() / 3.0);
				//System.out.println(undircLen);
				for(int i = 0; i < undircLen; i++) {
					String s2 = i != undircLen - 1 ? gamma.substring(i * 3, i * 3 + 3) : gamma.substring(i * 3);
					byte b = stringToByte(s2);
					if(!isRoot || curPos != 0 || i != 0) {
						b = (byte) (b | -128);
					}
					if(curPos != 0 || i != 0) {
						b = (byte) (b | 64);
					}
					b = (byte) (b | 32);
					if(i != 0) {
						b = (byte) (b | 16);
					}
					if(s.charAt(curPos) == '0') {
						b = (byte) (b | 8);
					}
					res.add(b);
				}
				curPos = curPos + tInteger;
					
					
				
			}
		}
		
		/* 
		int sLength = resultTemp.size();
		int byteLength = sLength;
		for(String s : resultTemp) {
			int lenTemp = (int) Math.ceil(s.length() / 8.0);
			byteLength += lenTemp;
		}
		
		//System.out.println(byteLength);
		
		byte[] result = new byte[byteLength];
		int j = 0;
		while(j < byteLength) {
			for(String s : resultTemp) {
				//System.out.println(s);
				int len = (int) Math.ceil(s.length() / 8.0);
				//String lenTemp = String.valueOf(len) ;
				byte b = 0;
				result[j] = (byte) (b | len);
				j++;
				for(int i = 0; i < len; i++) {
					
					if(i != len - 1) {
						result[j++] = stringToByte(s.substring(i * 8, i * 8 + 8));
					}else {
						//System.out.println(stringToByte(s.substring(i * 8)));
						result[j++] = stringToByte(s.substring(i * 8));	
					}
				}
				
			}
		}
		*/
		
		return res;
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
	
//�?00111...string转成byte
	public byte stringToByte(String str) {
	 int res = 0;
	 if (null == str) {
	  return 0;
	 }
	 int length = str.length();
	 while (length < 8) {
		 str = "0" + str;
		 length++;
	 	}
	 //System.out.println(str);
	 if (length == 8) {// 8 bit处理
		  if (str.charAt(0) == '0') {// 正数
		   res = Integer.parseInt(str, 2);
		  } else {// 负数
		   res = Integer.parseInt(str, 2) - 256;
		  }
		 } /*else {// 4 bit处理
		  res = Integer.parseInt(str, 2);
		 }*/
	 return (byte) res;
	}
	
	
	
	public static void main(String[] args) throws IOException {
		Coding t = new Coding();
		List<String> runlength;
		List<Byte> res;
		String string = "00000000000000000";
		runlength = t.runLengthCoding(string); 
		res = t.gammaCoding(runlength, string, true);
	//String reString = t.gammaOperation(6);
		//System.out.print(reString);
	    for(byte re : res)
	    	System.out.println(re);
	}
}

