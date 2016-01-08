package com.coq;

/**
 * Created by dabrowski on 31/12/2015.
 */
public class CoqState {

    public int globalCounter;
    public int proofCounter;
    public int offset;

    public CoqState(int globalCounter, int proofCounter, int offset){
        this.globalCounter = globalCounter;
        this.proofCounter = proofCounter;
        this.offset = offset;
    }

    public CoqState(CoqTopLevelPrompt prompt, int offset){
        this.globalCounter = prompt.getGlobalCounter();
        this.proofCounter = prompt.getProofCounter();
        this.offset = offset;
    }

    public String toString(){
        return "(" + globalCounter +" | " + proofCounter + " | " + offset +")";
    }

}
