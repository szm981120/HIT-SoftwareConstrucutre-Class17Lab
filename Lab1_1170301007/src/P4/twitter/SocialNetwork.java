/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P4.twitter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * SocialNetwork provides methods that operate on a social network.
 * 
 * A social network is represented by a Map<String, Set<String>> where map[A] is
 * the set of people that person A follows on Twitter, and all people are
 * represented by their Twitter usernames. Users can't follow themselves. If A
 * doesn't follow anybody, then map[A] may be the empty set, or A may not even
 * exist as a key in the map; this is true even if A is followed by other people
 * in the network. Twitter usernames are not case sensitive, so "ernie" is the
 * same as "ERNie". A username should appear at most once as a key in the map or
 * in any given map[A] set.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class SocialNetwork {

	/**
	 * Guess who might follow whom, from evidence found in tweets.
	 * 
	 * @param tweets a list of tweets providing the evidence, not modified by this
	 *               method.
	 * @return a social network (as defined above) in which Ernie follows Bert if
	 *         and only if there is evidence for it in the given list of tweets. One
	 *         kind of evidence that Ernie follows Bert is if Ernie
	 * @-mentions Bert in a tweet. This must be implemented. Other kinds of evidence
	 *            may be used at the implementor's discretion. All the Twitter
	 *            usernames in the returned social network must be either authors
	 *            or @-mentions in the list of tweets.
	 */
	public static Map<String, Set<String>> guessFollowsGraph(List<Tweet> tweets) {
		Map<String, Set<String>> guessFollowsGraphMap = new HashMap<String, Set<String>>();
		int index = 0;
		for (Tweet t : tweets) { // 遍历所有tweet
			/* 关系映射图中的原像集中的用户一定只能是发表tweet的作者 */
			guessFollowsGraphMap.put(t.getAuthor().toLowerCase(), new HashSet<String>());
			String text = t.getText(); // 查看内容
			/* 注：下面的操作复用了Extract.java中的一些代码 */
			index = 0; // index是每次检索字符@时，检索的起始索引，每次检索后要更新
			while ((index = text.indexOf("@", index)) >= 0) {
				String mentionedName = ""; // 初始化被提及的用户名
				int i = 0; // i是对当前检索到的@附近的字串的一个检查过程中的循环变量
				/*
				 * 如果@在字串的开始 或者@的前面仍有非法字符 那么可以暂时认定是一次成功的检索
				 */
				if (index == 0 || !Extract.validCharacterInUsername(text.charAt(index - 1))) {
					/* 从@后面的一个索引位开始检索字串，直到末尾或遇到一个非法字符终止 */
					for (i = index + 1; i < text.length() && Extract.validCharacterInUsername(text.charAt(i)); i++)
						mentionedName += text.charAt(i); // 这期间都是合法的用户名
					/* 作者@自己，不能算做关注 */
					if (!t.getAuthor().equalsIgnoreCase(mentionedName))
						guessFollowsGraphMap.get(t.getAuthor().toLowerCase()).add(mentionedName.toLowerCase());
					/* 对于一个被关注的人，如果他没有发表tweet，也要加入到关系图中 */
					if (!guessFollowsGraphMap.keySet().contains(mentionedName.toLowerCase()))
						guessFollowsGraphMap.put(mentionedName.toLowerCase(), new HashSet<String>());
					index = i; // 更新检索起始索引
				} else {
					index += 1; // 当前检索的@不是真正的提及标志，继续检索
				}
			}
		}
		
		return guessFollowsGraphMap;
	}

	/**
	 * get smarter
	 * 
	 * @param tweets a list of tweets providing the evidence, not modified by this
	 *               method.
	 * @return a social network (as defined above) in which Ernie follows Bert if
	 *         and only if there is evidence for it in the given list of tweets. One
	 *         kind of evidence that Ernie follows Bert is if Ernie
	 * @-mentions Bert in a tweet. This must be implemented. Other kinds of evidence
	 *            may be used at the implementor's discretion. All the Twitter
	 *            usernames in the returned social network must be either authors
	 *            or @-mentions in the list of tweets.
	 * 
	 * triadic-closure If A and B follow each other, so do B and C, then A and C 
	 * 					should follow each other.
	 */
	public static Map<String, Set<String>> smarterGuessFollowsGraph(List<Tweet> tweets) {
		Map<String, Set<String>> guessFollowsGraphMap = new HashMap<String, Set<String>>();
		int index = 0;
		for (Tweet t : tweets) { // 遍历所有tweet
			/* 关系映射图中的原像集中的用户一定只能是发表tweet的作者 */
			guessFollowsGraphMap.put(t.getAuthor().toLowerCase(), new HashSet<String>());
			String text = t.getText(); // 查看内容
			/* 注：下面的操作复用了Extract.java中的一些代码 */
			index = 0; // index是每次检索字符@时，检索的起始索引，每次检索后要更新
			while ((index = text.indexOf("@", index)) >= 0) {
				String mentionedName = ""; // 初始化被提及的用户名
				int i = 0; // i是对当前检索到的@附近的字串的一个检查过程中的循环变量
				/*
				 * 如果@在字串的开始 或者@的前面仍有非法字符 那么可以暂时认定是一次成功的检索
				 */
				if (index == 0 || !Extract.validCharacterInUsername(text.charAt(index - 1))) {
					/* 从@后面的一个索引位开始检索字串，直到末尾或遇到一个非法字符终止 */
					for (i = index + 1; i < text.length() && Extract.validCharacterInUsername(text.charAt(i)); i++)
						mentionedName += text.charAt(i); // 这期间都是合法的用户名
					/* 作者@自己，不能算做关注 */
					if (!t.getAuthor().equalsIgnoreCase(mentionedName))
						guessFollowsGraphMap.get(t.getAuthor().toLowerCase()).add(mentionedName.toLowerCase());
					/* 对于一个被关注的人，如果他没有发表tweet，也要加入到关系图中 */
					if (!guessFollowsGraphMap.keySet().contains(mentionedName.toLowerCase()))
						guessFollowsGraphMap.put(mentionedName.toLowerCase(), new HashSet<String>());
					index = i;	// 更新检索起始索引
				} else {
					index += 1; // 当前检索的@不是真正的提及标志，继续检索
				}
			}
		}
		/* Triadic closure */
		HashMap<String, String> waitingAddedFollow = new HashMap<String, String>();
		for (String A : guessFollowsGraphMap.keySet()) {
			for (String B : guessFollowsGraphMap.get(A)) {
				for (String C : guessFollowsGraphMap.get(B)) {
					if (guessFollowsGraphMap.get(B).contains(A) && guessFollowsGraphMap.get(C).contains(B)) {
						waitingAddedFollow.put(A, C);
					}
				}
			}
		}
		for (String A : waitingAddedFollow.keySet()) {
			String C = waitingAddedFollow.get(A);
			if (!guessFollowsGraphMap.get(A).contains(C))
				guessFollowsGraphMap.get(A).add(C);
			if (!guessFollowsGraphMap.get(C).contains(A))
				guessFollowsGraphMap.get(C).add(A);
		}
		/* END triadic closure */
		return guessFollowsGraphMap;
	}

	/**
	 * Find the people in a social network who have the greatest influence, in the
	 * sense that they have the most followers.
	 * 
	 * @param followsGraph a social network (as defined above)
	 * @return a list of all distinct Twitter usernames in followsGraph, in
	 *         descending order of follower count.
	 */
	public static List<String> influencers(Map<String, Set<String>> followsGraph) {
		List<String> influencersList = new ArrayList<String>();
		Map<String, Integer> followerNumberMap = new HashMap<String, Integer>(); // 用户名到粉丝数的映射
		/*
		 * 先完善映射 遍历关注关系图
		 */
		for (String s : followsGraph.keySet()) {
			int number = 0; // 粉丝数变量
			for (Set<String> set : followsGraph.values())
				if (set.contains(s)) // 如果某用户关注人群中有s，那么s粉丝数加一
					number++;
			followerNumberMap.put(s, number); // 完善映射关系
		}
		/*
		 * 下面按映射表中的value值降序加入结果List 由于我们每次遍历得到一个最大粉丝数的用户， 都会在映射表中删除这个用户，
		 * 所以，一个循环条件就是Map不为空
		 */
		int max = 0;
		while (!followerNumberMap.isEmpty()) {
			String mostPopularPerson = null;
			/* 每次都要遍历用户集 */
			for (String s : followerNumberMap.keySet()) {
				if (followerNumberMap.get(s) >= max) { // 找到拥有最大粉丝数量的用户
					max = followerNumberMap.get(s);
					mostPopularPerson = s;
				}
			}
			if (mostPopularPerson != null) {
				influencersList.add(mostPopularPerson); // 把当前最大粉丝数量的用户加入结果List
				followerNumberMap.remove(mostPopularPerson); // 最大粉丝数量用户加入List之后移出Map
			}
			max = 0; // 粉丝数计数器重置
		}
		return influencersList;
	}

}
