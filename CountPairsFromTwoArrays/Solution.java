package org.twizzy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* this challenge was to take two arrays
containing pairs, and find which value 
stands out */

public class Solution {
    public static int solution(int[] x, int[] y) {
        assert(x.length > 0 && x.length < 100);
        assert(y.length > 0 && y.length < 100);
        int frstNumOfPair = 0;

        /* combine the two arrays into one */
        int size = x.length + y.length;
        List<Integer> z = new ArrayList<Integer>(size);
//        int[] z = new int[size];
        for(int i = 0; i < x.length; i ++){
            assert(x[i] > -1000 && x[i] < 1000);
            z.add(x[i]);
        }
        for(int i = 0; i < y.length; i ++){
            assert(y[i] > -1000 && y[i] < 1000);
            z.add(y[i]);
        }

        /* sort using collections.sort and quick sort */
        Collections.sort(z);

        /* look in new array for first occurance of a non-pair of like-values */
        for(int i = 0; i < z.size(); i++){

            System.out.println("loop:" + z.get(i));

            /* set every first number */
            if( i % 2 == 0 ) {
                frstNumOfPair = z.get(i);
                continue;
            }

            /* check against first or return last num */
            if( i == z.size() - 1 || frstNumOfPair != z.get(i) )
                return frstNumOfPair;
        }

        /* if we made it here something went wrong */
        return 0;

    }
}