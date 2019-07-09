/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P4.twitter;

import java.lang.Thread.State;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Extract consists of methods that extract information from a list of tweets.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class Extract {

	/**
	 * Get the time period spanned by tweets.
	 * 
	 * @param tweets list of tweets with distinct ids, not modified by this method.
	 * @return a minimum-length time interval that contains the timestamp of every
	 *         tweet in the list.
	 */
	public static Timespan getTimespan(List<Tweet> tweets) {
		Instant start, end; // timespan的起始和结束
		/* 初始化 */
		start = tweets.get(0).getTimestamp();
		end = tweets.get(0).getTimestamp();
		for (Tweet t : tweets) { // 遍历所有tweet
			if (t.getTimestamp().isBefore(start)) // 求出发表时间的最早值
				start = t.getTimestamp();
			if (t.getTimestamp().isAfter(end)) // 求出发表时间的最晚值
				end = t.getTimestamp();
		}
		Timespan timespan = new Timespan(start, end);
		return timespan;
	}

	/**
	 * Get usernames mentioned in a list of tweets.
	 * 
	 * @param tweets list of tweets with distinct ids, not modified by this method.
	 * @return the set of usernames who are mentioned in the text of the tweets. A
	 *         username-mention is "@" followed by a Twitter username (as defined by
	 *         Tweet.getAuthor()'s spec). The username-mention cannot be immediately
	 *         preceded or followed by any character valid in a Twitter username.
	 *         For this reason, an email address like bitdiddle@mit.edu does NOT
	 *         contain a mention of the username mit. Twitter usernames are
	 *         case-insensitive, and the returned set may include a username at most
	 *         once.
	 */
	public static Set<String> getMentionedUsers(List<Tweet> tweets) {
		Set<String> mentionedUsers = new HashSet<String>();
		for (Tweet t : tweets) {	// 遍历所有tweet
			String text = t.getText();
			int index = 0;	// index是每次检索字符@时，检索的起始索引，每次检索后要更新
			while ((index = text.indexOf("@", index)) >= 0) {
				String username = "";	// 初始化被提及的用户名
				int i = 0;	// i是对当前检索到的@附近的字串的一个检查过程中的循环变量
				/* 如果@在字串的开始
				 * 或者@的前面仍有非法字符
				 * 那么可以暂时认定是一次成功的检索 */
				if (index == 0 || !validCharacterInUsername(text.charAt(index - 1))) {
					/* 从@后面的一个索引位开始检索字串，直到末尾或遇到一个非法字符终止 */
					for (i = index + 1; i < text.length() && validCharacterInUsername(text.charAt(i)); i++)
						username += String.valueOf(text.charAt(i));	// 这期间都是合法的用户名
					username = username.toLowerCase();	// 用户名不区分大小写
					/* 如果该用户名未被搜集
					 * 或用户名长度大于0，就是存在用户名
					 * 那么就把它加入到mentionedUsers */
					if (!mentionedUsers.contains(username) && username.length() > 0)
						mentionedUsers.add(username);
					index = i;	// 更新检索起始索引
				} else {
					index += 1;	// 当前检索的@不是真正的提及标志，继续检索
				}
			}
		}
		return mentionedUsers;
	}

	public static boolean validCharacterInUsername(char c) {
		if (c >= 'A' && c <= 'Z') {
			return true;
		} else if (c >= 'a' && c <= 'z') {
			return true;
		} else if (c == '_' || c == '-') {
			return true;
		} else if (c >= '0' && c <= '9') {
			return true;
		} else {
			return false;
		}
	}

}
