package com.example.socialnetworkgui.domain;

/**
 * Uses the generic Pair, concrete pair between 2 Strings
 */

public class OrderedStringPair extends Pair<String, String> {

    public OrderedStringPair(String first, String second) {
        if (first.compareTo(second) > 0) {
            String aux = first;
            first = second;
            second = aux;
        }
        super.setFirst(first);
        super.setSecond(second);
    }

}
