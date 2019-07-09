package debug;
///*

//
//This program is used for removing all the comments in a program code.
//
//Example 1:
//Input: 
//source = ["/*Test program */", "int main()", "{ ", "  // variable declaration ", "int a, b, c;", "/* This is a test", "   multiline  ", "   comment for ", "   testing */", "a = b + c;", "}"]
//
//The line by line code is visualized as below:
//
///*Test program */
//int main()
//{ 
//  // variable declaration 
//int a, b, c;
///* This is a test
//   multiline  
//   comment for 
//   testing */
//a = b + c;
//}
//
//Output: ["int main()","{ ","  ","int a, b, c;","a = b + c;","}"]
//
//The line by line code is visualized as below:
//
//int main()
//{ 
//
//int a, b, c;
//a = b + c;
//}
//
//Explanation: 
//The string /* denotes a block comment, including line 1 and lines 6-9. The string // denotes line 4 as comments.
//
//Example 2:
//
//Input: 
//source = ["a/*comment", "line", "more_comment*/b"]
//
//Output: ["ab"]
//
//Explanation: 
//The original source string is "a/*comment\nline\nmore_comment*/b", where we have bolded the newline characters.  
//After deletion, the implicit newline characters are deleted, leaving the string "ab", which when delimited by newline characters becomes ["ab"].
//
//
//Note:
//
//The length of source is in the range [1, 100].
//The length of source[i] is in the range [0, 80].
//Every open block comment is eventually closed.
//There are no single-quote, double-quote, or control characters in the source code.
//
//*/

import java.util.ArrayList;
import java.util.List;

class RemoveComments {
	public List<String> removeComments(String[] source) {
		assert source.length >= 1 && source.length <= 100 : "The length of source is in the range [1,100]."; // new
		boolean inBlock = false;
		StringBuilder newline = new StringBuilder();
		List<String> ans = new ArrayList<String>(); // List<String> ans = new List();
		for (String line : source) {
			assert line.length() >= 0 && line.length() <= 80 : "The length of source[i] is in the range [0,80]."; // new
			int i = 0;
			char[] chars = line.toCharArray();
			if (!inBlock)
				newline = new StringBuilder();
			while (i < line.length()) {
				assert chars[i] >= 32 && chars[i] <= 127 && chars[i] != 34
						&& chars[i] != 39 : "There are no single-quote, double-quote, or control characters in the source code."; // new
				if (!inBlock && i + 1 < line.length() && chars[i] == '/' && chars[i + 1] == '*') { // && i+1 <=
																									// line.length()
					inBlock = true;
					i += 2; // new
					continue; // new
				} else if (inBlock && i + 1 < line.length() && chars[i] == '*' && chars[i + 1] == '/') { // && i + 1 <
																											// line.length()
					inBlock = false;
					i += 2; // new
					continue; // new
				} else if (!inBlock && i + 1 < line.length() && chars[i] == '/' && chars[i + 1] == '/') { // this is a
																											// new else
																											// if
					break;
				} else if (!inBlock) {
					newline.append(chars[i]);
				}
				i++;
			}
			if (!inBlock && newline.length() > 0) { // inBlock &&
				ans.add(new String(newline));
			}
		}
		assert inBlock == false : "Every open block comment is eventually closed."; // new
		return ans;
	}
}