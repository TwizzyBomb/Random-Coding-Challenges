package com.leetcode;

import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class Janitor {

    /*
     * Complete the 'efficientJanitor' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts FLOAT_ARRAY weight as parameter.
     */
    public static int efficientJanitor(List<Float> weight) {


        /* this is an O ( n^2 ) solution */
        int numOfTrips = 0;
        float n = weight.get(0);
        int weightSize = (int)n;
        boolean[] visited = new boolean[weightSize];
        for( int i = 0; i < weightSize; i++) {
            visited[i] = false;
        }

        //System.out.println("visited.length" + visited.length);

        /* first we want to sort the list */
        weight.remove(0);
        Collections.sort(weight);

        /* now we want to start with the heaviest weight
        and greedily see how much we can add to it from
        the bottom of the list */
        for(int i = 0; i < weight.size(); i++){
            //System.out.println("i = " + i);
            //System.out.println("visited[" + i + "]:" + visited[i]);
            if(visited[i] == true)
                continue;
            /* add heaviest to the heaviest of the lightest
            until we equal or are over 3 */
            float currWeight = weight.get(i);
            for ( int j = i + 1; j < weight.size(); j++ ){

                /* find the max weight we can add to it */
//                System.out.println("j = " + j);
//                System.out.println("j< weight.size():" + (j< weight.size()));
//                System.out.println("visited[j]!= true" + (visited[j]!= true));
//                System.out.println("currWeight + weight.get(j) <= 3" + (currWeight + weight.get(j) <= 3));
//                System.out.println("j = " + j);
                if( visited[j]!= true && currWeight + weight.get(j) <= 3 ){
                    currWeight += weight.get(j);

                    /* mark that we've visit this element */
                    visited[j] = true;

                }
            }
            numOfTrips++;
        }

        return numOfTrips;
    }

}
