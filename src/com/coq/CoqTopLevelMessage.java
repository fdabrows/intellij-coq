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

package com.coq;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dabrowski on 31/12/2015.
 */
public class CoqTopLevelMessage {

//    private static final Pattern p_nomoresubgoal =
//            Pattern.compile("No more subgoals.");
//    private static final Pattern p_moresubgoal =
//            Pattern.compile("([0-9]+)\\ssubgoals,\\ssubgoal\\s([0-9]+)\\s\\(ID\\s([0-9]+)\\)");

//    private static final Pattern p_error = Pattern.compile("Error:\\s(.*)");

    private static int cpt = 0;

    private List<String> message;

    public CoqTopLevelMessage(List<String> msg){
        message = msg;
    }

    public String message(){
        String msg = "";
        if (message == null) return msg;
        for (int i =0; i < message.size(); i++)
            msg += (message.get(i) + "\n");
        return msg;
    }

    @Override
    public String toString(){
        return message();
    }
}
