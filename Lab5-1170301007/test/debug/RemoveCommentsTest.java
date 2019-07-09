package debug;

import static org.junit.Assert.*;

import java.util.List;
import java.util.ArrayList;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class RemoveCommentsTest {

	/*
	 * Test strategy
	 * 	Overall, the test strategy tests default cases that the example indicates in spec, a few equivalence cases, 
	 * 	and a few assertion cases.
	 * 
	 * 	testDefault
	 * 		A case that example 1 indicates in spec.
	 * 	testDefault2
	 * 		A case that example 2 indicates in spec.
	 * 	testBlockNestInLine
	 * 		Block comment nests in line comment.
	 * 	testLineNestInBlock
	 * 		Line comment nests in block comment.
	 * 	testLineNestInLine
	 * 		Line comment nests in line comment. In fact, only the first // works as comment notation. The rests are all comments.
	 * 	testBlockNestInBlock
	 * 		Block comment nests in block comment. In fact, only the first pair of block comment notation works. The rests are all comments.
	 * 	testSourceLengthAssertion
	 * 		Test AssertionError when the length of source is greater than 100.
	 * 	testSourceContentLengthAssertion
	 * 		Test AssertionError when the length of source[i] is greater than 80.
	 * 	testDoubleQuoteCharacterAssertion
	 * 		Test AssertionError when double-quote shows in source[i].
	 * 	testSingleQuoteCharacterAssertion
	 * 		Test AssertionError when single-quote shows in source[i].
	 * 	testControlCharacterAssertion
	 * 		Test AssertionError when control character shows in source[i].
	 * 	testBlockClosedAssertion
	 * 		Test AssertionError when a block comment has no end notation.
	 */

	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false;
	}

	@Test
	public void testDefault() {
		RemoveComments rc = new RemoveComments();
		List<String> ans = new ArrayList<String>();
		ans.add("int main()");
		ans.add("{ ");
		ans.add("  ");
		ans.add("int a, b, c;");
		ans.add("a = b + c;");
		ans.add("}");
		String[] source = { "/*Test program */", "int main()", "{ ", "  // variable declaration ", "int a, b, c;",
				"/* This is a test", "   multiline  ", "   comment for ", "   testing */", "a = b + c;", "}" };
		assertEquals(ans, rc.removeComments(source));
	}

	@Test
	public void testDefault2() {
		RemoveComments rc = new RemoveComments();
		List<String> ans = new ArrayList<String>();
		ans.add("ab");
		String[] source = { "a/*comment", "line", "{ ", "more_comment*/b" };
		assertEquals(ans, rc.removeComments(source));
	}

	@Test
	public void testBlockNestInLine() {
		RemoveComments rc = new RemoveComments();
		List<String> ans = new ArrayList<String>();
		ans.add("j");
		ans.add("kk");
		String[] source = { "// /* a b c*/", "/* bb", "//abc", "rr*/j", "kk" };
		assertEquals(ans, rc.removeComments(source));
	}

	@Test
	public void testLineNestInBlock() {
		RemoveComments rc = new RemoveComments();
		List<String> ans = new ArrayList<String>();
		ans.add("j");
		ans.add("kk");
		String[] source = { "// /* a b c*/", "/* bb", "a//bc", "rr*/j", "kk" };
		assertEquals(ans, rc.removeComments(source));
	}

	public void testLineNestInLine() {
		RemoveComments rc = new RemoveComments();
		List<String> ans = new ArrayList<String>();
		ans.add("j");
		ans.add("kk");
		String[] source = { "//// /* a b c*/", "/* bb", "a//bc", "rr*/j", "kk" };
		assertEquals(ans, rc.removeComments(source));
	}

	@Test
	public void testBlockNestInBlock() {
		RemoveComments rc = new RemoveComments();
		List<String> ans = new ArrayList<String>();
		ans.add("j");
		ans.add("kk");
		String[] source = { "//// /* a b c*/", "/* bb", "/*a//b/*c", "rr*/j", "kk" };
		assertEquals(ans, rc.removeComments(source));
	}

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void testSourceLengthAssertion() {
		exception.expect(AssertionError.class);
		exception.expectMessage("The length of source is in the range [1,100].");
		RemoveComments rc = new RemoveComments();
		List<String> ans = new ArrayList<String>();
		ans.add("j");
		ans.add("kk");
		String[] source = new String[101];
		for (int i = 0; i < 101; i++) {
			source[i] = "a";
		}
		assertEquals(ans, rc.removeComments(source));
	}

	@Test
	public void testSourceContentLengthAssertion() {
		exception.expect(AssertionError.class);
		exception.expectMessage("The length of source[i] is in the range [0,80].");
		RemoveComments rc = new RemoveComments();
		List<String> ans = new ArrayList<String>();
		String moreThanAHundredString = "Java comments are notes in a Java code file that are ignored by the compiler and runtime engine. "
				+ "They are used to annotate the code in order to clarify its design and purpose. "
				+ "You can add an unlimited number of comments to a Java file, "
				+ "but there are some best practices to follow when using comments.";
		ans.add("j");
		ans.add("kk");
		String[] source = { moreThanAHundredString, "kk" };
		assertEquals(ans, rc.removeComments(source));
	}

	@Test
	public void testDoubleQuoteCharacterAssertion() {
		exception.expect(AssertionError.class);
		exception.expectMessage("There are no single-quote, double-quote, or control characters in the source code.");
		RemoveComments rc = new RemoveComments();
		List<String> ans = new ArrayList<String>();
		ans.add("j");
		ans.add("kk");
		String[] source = { "/* a\" b c*/", "rrj", "kk" };
		assertEquals(ans, rc.removeComments(source));
	}

	@Test
	public void testSingleQuoteCharacterAssertion() {
		exception.expect(AssertionError.class);
		exception.expectMessage("There are no single-quote, double-quote, or control characters in the source code.");
		RemoveComments rc = new RemoveComments();
		List<String> ans = new ArrayList<String>();
		ans.add("j");
		ans.add("kk");
		String[] source = { "/* a\' b c*/", "rrj", "kk" };
		assertEquals(ans, rc.removeComments(source));
	}

	@Test
	public void testControlCharacterAssertion() {
		exception.expect(AssertionError.class);
		exception.expectMessage("There are no single-quote, double-quote, or control characters in the source code.");
		RemoveComments rc = new RemoveComments();
		List<String> ans = new ArrayList<String>();
		ans.add("j");
		ans.add("kk");
		String[] source = { "/* a\n b c*/", "rrj", "kk" };
		assertEquals(ans, rc.removeComments(source));
	}

	@Test
	public void testBlockClosedAssertion() {
		exception.expect(AssertionError.class);
		exception.expectMessage("Every open block comment is eventually closed.");
		RemoveComments rc = new RemoveComments();
		List<String> ans = new ArrayList<String>();
		ans.add("j");
		ans.add("kk");
		String[] source = { "/* a b c*/", "/* bb", "/*a//b/*c", "rrj", "kk" };
		assertEquals(ans, rc.removeComments(source));
	}
}
