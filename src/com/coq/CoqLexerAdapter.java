package com.coq;

import com.intellij.lexer.FlexAdapter;

import java.io.Reader;

/**
 * Created by Dabrowski on 27/11/2015.
 */
public class CoqLexerAdapter extends FlexAdapter{
    public CoqLexerAdapter() {
        super(new CoqLexer((Reader) null));
    }
}
