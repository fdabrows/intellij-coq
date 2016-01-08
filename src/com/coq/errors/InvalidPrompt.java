package com.coq.errors;

/**
 * Created by dabrowski on 05/01/2016.
 */
public class InvalidPrompt extends Exception {

    public final String str;

    public InvalidPrompt(String str){
        this.str = str;
    }
}
