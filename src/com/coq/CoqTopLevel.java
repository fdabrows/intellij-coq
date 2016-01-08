package com.coq;

import com.coq.errors.CoqtopPathError;
import com.coq.errors.InvalidPrompt;
import com.coq.errors.NoCoqProcess;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by dabrowski on 31/12/2015.
 */
public class CoqTopLevel {

    public static final String UNDO = "Undo.";
    public static final String QUIT = "Quit.";

    private Process p;
    private String proc;
    private BufferedReader processOutput;
    private BufferedWriter processInput;
    private BufferedReader processError;

    private static final Pattern p_prompt =
            Pattern.compile("<prompt>([a-zA-Z0-9_]+)\\s<\\s([0-9]+)\\s\\|([a-zA-Z0-9_]+)?\\|\\s([0-9]+)\\s<\\s</prompt>");

    private CoqTopLevel(String str){
        this.proc = str;
    }

    public static CoqTopLevel getCoqTopLevel() throws CoqtopPathError {
        String proc = CoqSettings.getInstance().coqbin;
        if (proc == null) throw new CoqtopPathError();
        else if (new File(proc+"/coqtop").isFile())
            return new CoqTopLevel(proc + "/coqtop -emacs");
        else throw new CoqtopPathError();
        }

    public String readPrePrompt() throws IOException {
        return processError.readLine();
    }

    public String readPrompt() throws IOException, InvalidPrompt {
        StringBuilder builder = new StringBuilder();
        while (processError.ready()) {
            char c = (char) (processError.read());
            builder.append(c);
            if (c == '>') {
                String str = builder.toString();
                if (p_prompt.matcher(str).matches()) return str;
            }
        }
        throw new InvalidPrompt("");
    }

    public List<String> readMessage() throws IOException {
        List<String> messageList = new ArrayList<>();
        messageList.add(processOutput.readLine());
        while (processOutput.ready()) {
            messageList.add(processOutput.readLine());
        }
        return messageList;
    }

    public CoqTopLevelResponse start() throws IOException, InvalidPrompt {

        Runtime runtime = Runtime.getRuntime();
        p = runtime.exec(proc);
        processOutput = new BufferedReader(new InputStreamReader(p.getInputStream()));
        processInput = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));
        processError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

        String prePrompt = readPrePrompt();
        CoqTopLevelPrompt prompt = new CoqTopLevelPrompt(readPrompt());
        CoqTopLevelMessage message = new CoqTopLevelMessage(readMessage());

        return new CoqTopLevelResponse(prePrompt, prompt, message);
    }

    public void stop() throws IOException {
            processOutput.close();
            processInput.close();
            p.destroy();
            processOutput = null;
            processInput = null;
            p = null;
    }

    public CoqTopLevelResponse send (String cmd) throws InvalidPrompt, IOException {

        String preprompt;
        CoqTopLevelPrompt prompt;
        CoqTopLevelMessage message;

        if (p == null) start();
            processInput.write(cmd + "\n");
            processInput.flush();
            preprompt = readPrePrompt();
            prompt = new CoqTopLevelPrompt(readPrompt());
            message = null;
            if (processOutput.ready()) message = new CoqTopLevelMessage(readMessage());
            return new CoqTopLevelResponse(preprompt, prompt, message);

    }

    public CoqTopLevelResponse backTo(int n) throws InvalidPrompt, IOException, NoCoqProcess {
        return send("BackTo "+n+".");
    }
}
