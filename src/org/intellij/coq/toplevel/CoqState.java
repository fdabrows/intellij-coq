/*
 * IntelliJ-coqplugin  / Plugin IntelliJ for Coq
 * Copyright (c) 2016
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.intellij.coq.toplevel;

public class CoqState {

    public final int globalCounter;
    public final int proofCounter;
    public final int offset;

    public CoqState(CoqTopLevelPrompt prompt, int offset){
        this.globalCounter = prompt.getGlobalCounter();
        this.proofCounter = prompt.getProofCounter();
        this.offset = offset;
    }

    public String toString(){
        return "(" + globalCounter +" | " + proofCounter + " | " + offset +")";
    }

}
