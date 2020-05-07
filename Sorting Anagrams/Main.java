package com.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.*;

public class Main {
	
	public static ArrayList<List<String>> groupAnagrams(String[] strs) {
        if(strs.length==0)
            return new ArrayList<List<String>>();
		ArrayList list = new ArrayList<String>();
		
        /* map of string, list */
        Map<String, List<String>> theMap = new HashMap<String, List<String>>();

        /* an array to hold the counts of each letter */
        int[] count = new int[26];

        /* loop through each of the strings */
        for (String s : strs) {
            /* fill the count arrays */
            Arrays.fill(count, 0);
            for( char c : s.toCharArray() ){
//                System.out.println("char c:" + c);
                int indexOfChar = c - 'a';
//                System.out.println("index" + indexOfChar);
                count[indexOfChar]++;
            }

            /* string build a string for the key */
            StringBuilder sb = new StringBuilder();

            /* loop through and make these into strings */
            for (int i : count ) {
                sb.append(i);
            }

            /* add the keys to the map if they're not there */
            if( !theMap.containsKey( sb.toString() ) )
                theMap.put(sb.toString(), new LinkedList<String>());

            /* add the string to that spot in the map */
            theMap.get(sb.toString()).add(s);

        }

        return new ArrayList<List<String>>( theMap.values() );
    }

    public static void main(String[] args) {
	// write your code here
        String[] strings = {"eat","tea","tan","ate","nat","bat"};

        ArrayList<List<String>> ans = groupAnagrams(strings);

        for(List<String> list : ans) {
            for (String s : list)
                System.out.println(s + " ");
            System.out.println();
        }

    }
}
