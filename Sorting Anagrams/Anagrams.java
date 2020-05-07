package com.leetcode;

import java.util.*;

public class Anagrams {

    public static List<List<String>> groupAnagrams(String[] strs) {
        ArrayList list = new ArrayList<String>();
        if(strs.length==0)
            return list;

        /* map of string, list */
        Map<String, List> theMap = new HashMap<String, List>();

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

        return new ArrayList( theMap.values() );
    }
}
