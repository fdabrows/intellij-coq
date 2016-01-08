package com.coq;


import com.intellij.lang.Language;

/**
 * Created by Dabrowski on 21/11/2015.
 */
public class CoqLanguage extends Language {

    public static final CoqLanguage INSTANCE = new CoqLanguage();

    private CoqLanguage(){
        super("Coq");
    }

}
