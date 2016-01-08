package com.coq;

/**
 * Created by dabrowski on 05/01/2016.
 */
public class CoqTopLevelResponse {

    public final String preprompt;
    public final CoqTopLevelMessage message;
    public final CoqTopLevelPrompt prompt;

    public CoqTopLevelResponse(String preprompt, CoqTopLevelPrompt prompt, CoqTopLevelMessage message){
        this.preprompt = preprompt;
        if (message != null) this.message = message;
        else this.message = new CoqTopLevelMessage(null);
        this.prompt = prompt;
    }

}
