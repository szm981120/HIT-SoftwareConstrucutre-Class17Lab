/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P4.twitter;

import java.util.ArrayList;
import java.util.List;

/**
 * Filter consists of methods that filter a list of tweets for those matching a
 * condition.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class Filter {

	/**
	 * Find tweets written by a particular user.
	 * 
	 * @param tweets   a list of tweets with distinct ids, not modified by this
	 *                 method.
	 * @param username Twitter username, required to be a valid Twitter username as
	 *                 defined by Tweet.getAuthor()'s spec.
	 * @return all and only the tweets in the list whose author is username, in the
	 *         same order as in the input list.
	 */
	public static List<Tweet> writtenBy(List<Tweet> tweets, String username) {
		List<Tweet> writtenByList = new ArrayList<Tweet>();
		for (Tweet t : tweets) {
			if (t.getAuthor().equalsIgnoreCase(username)) // 用tweet的作者和username作比较，忽略大小写
				writtenByList.add(t);
		}
		return writtenByList;
	}

	/**
	 * Find tweets that were sent during a particular timespan.
	 * 
	 * @param tweets   a list of tweets with distinct ids, not modified by this
	 *                 method.
	 * @param timespan timespan
	 * @return all and only the tweets in the list that were sent during the
	 *         timespan, in the same order as in the input list.
	 */
	public static List<Tweet> inTimespan(List<Tweet> tweets, Timespan timespan) {
		List<Tweet> inTimespanList = new ArrayList<Tweet>();
		for (Tweet t : tweets) {
			if (t.getTimestamp().equals(timespan.getStart()) || t.getTimestamp().equals(timespan.getEnd())
					|| (t.getTimestamp().isAfter(timespan.getStart()) && t.getTimestamp().isBefore(timespan.getEnd())))
				inTimespanList.add(t);
		}
		return inTimespanList;
	}

	/**
	 * Find tweets that contain certain words.
	 * 
	 * @param tweets a list of tweets with distinct ids, not modified by this
	 *               method.
	 * @param words  a list of words to search for in the tweets. A word is a
	 *               nonempty sequence of nonspace characters.
	 * @return all and only the tweets in the list such that the tweet text (when
	 *         represented as a sequence of nonempty words bounded by space
	 *         characters and the ends of the string) includes *at least one* of the
	 *         words found in the words list. Word comparison is not case-sensitive,
	 *         so "Obama" is the same as "obama". The returned tweets are in the
	 *         same order as in the input list.
	 */
	public static List<Tweet> containing(List<Tweet> tweets, List<String> words) {
		List<Tweet> containingList = new ArrayList<Tweet>();
		/* 遍历所有tweet */
		for (Tweet t : tweets) {
			/* 因为要搜索的词不区分大小写，把tweet内容全部变为小写后在用空格分割 */
			String[] text = t.getText().toLowerCase().split(" ");
			secondloop: for (String w : words) { // 逐个检查目标单词
				for (String s : text) { // 对每个目标单词，逐个检查tweet内容中的单词
					/*
					 * 如果某单词包含目标单词， 且无任何其它前缀， 且和目标单词长度相同或者后缀有合法标点，
					 * 那么这就是一个搜索成功的单词，该tweet加入结果列表，并直接跳出外层循环
					 */
					if (s.contains(w.toLowerCase())
							&& (s.indexOf(w.toLowerCase()) == 0
									|| validPunctuation(s.charAt(s.indexOf(w.toLowerCase()) - 1)))
							&& (s.indexOf(w.toLowerCase()) + w.length() == s.length()
									|| validPunctuation(s.charAt(w.length())))) {
						containingList.add(t);
						break secondloop;
					}
				}
			}
		}
		return containingList;
	}

	/**
	 * 
	 * @param c the character about to be decided
	 * @return whether c is a valid punctuation
	 */
	public static boolean validPunctuation(char c) {
		String validPunctuationString = ".,!?':;\"";
		if (validPunctuationString.indexOf(String.valueOf(c)) == -1)
			return false;
		else
			return true;
	}

}
