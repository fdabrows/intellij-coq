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

package org.intellij.coq.compiler;

import org.intellij.coq.toolWindow.CompilerView;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.util.Pair;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.Nullable;

import javax.swing.event.EventListenerList;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dabrowski on 12/01/2016.
 */
public class CoqBaseCompiler {


    String compilerText;

    public static CompilerView compilerView;

    private final EventListenerList listeners = new EventListenerList();

    public void addCompilerTextListener(CompilerTextListener listener){
        listeners.add(CompilerTextListener.class, listener);
    }

    private void fireCompilerTextChanged(){
        for (CompilerTextListener listener : listeners.getListeners(CompilerTextListener.class)){
            listener.compilerTextChangee(compilerText);
        }
    }

    private void showMsg(String txt){
        compilerText += "\n" + txt;
        fireCompilerTextChanged();
    }

    public static List<VirtualFile> getSubdirs(VirtualFile file) {

        VirtualFile[] files = file.getChildren();
        List<VirtualFile> dirs = new ArrayList<>();

        for (VirtualFile f : files) {
            if (f.isDirectory()) {
                dirs.add(f);
                dirs.addAll(getSubdirs(f));
            }
        }
        return dirs;
    }

    // files : list of directories for coqdep

    public void compile(Project project, VirtualFile file, List<VirtualFile> subdirs) throws IOException, InterruptedException {

        //CompilerView compilerView = (CompilerView) ToolWindowManager.getInstance(project).getToolWindow("Coq Compiler");
        String str0 = file.getPath().replaceFirst(project.getBasePath()+"/src/", "");
        compilerView.addTextln("Compiling " + str0);

        Runtime runtime = Runtime.getRuntime();
        Sdk projectSdk = ProjectRootManager.getInstance(project).getProjectSdk();
        List<String> cmd = new ArrayList<>();
        cmd.add(projectSdk.getHomePath() + "/bin/coqc");
        cmd.add("-I");
        cmd.add(project.getBasePath()+"/src");
        for (VirtualFile dir : subdirs) {cmd.add("-I"); cmd.add(dir.getPath());}
        cmd.add(file.getPath());
        File dir = new File(project.getBasePath()+"/src");

        String msg="";
        for (String str : cmd) msg+= " " + str;
        compilerView.addTextln(msg);

        //compilerView.addText(msg);
        //compilerView.addText("Compilation");


        Process p = runtime.exec(cmd.toArray(new String[0]), new String[0], dir);
        BufferedReader processOutput = new BufferedReader(new InputStreamReader(p.getInputStream()));
        BufferedReader processError = new BufferedReader(new InputStreamReader(p.getErrorStream()));


        if (p.waitFor() > 0) {
            String str;
            while ((str = processError.readLine()) != null)
                compilerView.addText(str);
            return;
        }

        String str;
        while ((str = processOutput.readLine()) != null)
            compilerView.addText(str);
        return;

    }

    @Nullable
    List<VirtualFile> getDependencies(Project project, List<VirtualFile> files, List<VirtualFile> subdirs) throws IOException, InterruptedException {


        Runtime runtime = Runtime.getRuntime();
        Sdk projectSdk = ProjectRootManager.getInstance(project).getProjectSdk();

        List<String> cmd = new ArrayList<>();
        cmd.add(projectSdk.getHomePath() + "/bin/coqdep");
        cmd.add("-I");
        cmd.add(project.getBasePath()+"/src");
        for (VirtualFile file : subdirs) {cmd.add("-I"); cmd.add(file.getPath());}
        for (VirtualFile file : files) cmd.add(file.getPath());

        File dir = new File(project.getBasePath()+"/src");

        compilerView.addTextln("Computing dependencies");

        Process p = runtime.exec(cmd.toArray(new String[0]), new String[0], dir);

        BufferedReader processOutput = new BufferedReader(new InputStreamReader(p.getInputStream()));
        BufferedReader processError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

        if (p.waitFor() > 0){
            String str;
            while ((str = processError.readLine()) != null)
                compilerView.addText(str);
            return null;
        }

        List<String> lines = new ArrayList<>();
        String str;
        while ((str = processOutput.readLine()) != null)
            lines.add(str);

        List<Pair<String,String>> pairs = new ArrayList<>();

        for (String str2 : lines){
            String[] parts = str2.split(":");
            String [] left = parts[0].split(" ");
            String [] right = parts[1].split(" ");
            Pattern pattern = Pattern.compile("[^.]*.vo");

            for (String a : left)
                for (String b : right){
                    Matcher matcher1 = pattern.matcher(a);
                    Matcher matcher2 = pattern.matcher(b);

                    if (matcher1.matches() && matcher2.matches()){
                        String s1 = a.substring(0, a.length()-1);
                        String s2 = b.substring(0, b.length()-1);
                        pairs.add(new Pair<String,String>(s1,s2));
                    }

                }

            files.sort((o1, o2) -> {
                if (o1.getPath().equals(o2.getPath())) return 0;
                for (Pair<String, String> p1 : pairs) {
                    if (p1.getFirst().equals(o1.getPath()) &&
                            p1.getSecond().equals(o2.getPath())) return 1;
                }
                return -1;
            });
        }

        compilerView.addTextln("...Done!");
        return files;
    }

}