package com.udacity.sandwichclub.utils;

import java.util.List;

public class MiscUtils {

    public static String getDisplayStringFromListArray(List<String> list) {
        if (list.size() > 0) {
            return list.toString().substring(1, list.toString().length() - 1);
        }
        return null;
    }
}
