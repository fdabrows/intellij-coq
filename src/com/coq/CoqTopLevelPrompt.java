package com.coq;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dabrowski on 05/01/2016.
 */
public class CoqTopLevelPrompt {

    private static final Pattern p_prompt =
            Pattern.compile("<prompt>([a-zA-Z0-9_]+)\\s<\\s([0-9]+)\\s\\|([a-zA-Z0-9_]+)?\\|\\s([0-9]+)\\s<\\s</prompt>");


    private String name1;
    private int num1;
    private String name2;
    private int num2;

    public CoqTopLevelPrompt(String prompt){
        Matcher m = p_prompt.matcher(prompt);
        if (m.matches()) {
            name1 = m.group(1);
            num1 = Integer.valueOf(m.group(2));
            name2 = m.group(2);
            num2 = Integer.valueOf(m.group(4));
        }
        else {
        }
    }

    public int getGlobalCounter(){
        return num1;
    }

    public int getProofCounter(){
        return num2;
    }

}
