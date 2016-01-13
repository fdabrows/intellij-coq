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
package org.intellij.coq.actions;

import org.intellij.coq.CoqIcons;
import com.intellij.ide.actions.CreateFileFromTemplateAction;
import com.intellij.ide.actions.CreateFileFromTemplateDialog;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.InputValidatorEx;
import com.intellij.psi.PsiDirectory;
import com.sun.istack.internal.Nullable;

/**
 * Created by dabrowski on 11/01/2016.
 */
public class CreateCoqFileAction extends CreateFileFromTemplateAction {

    private static final String NEW_COQ_MODULE = "New Coq Module";

    public CreateCoqFileAction() {
        super(null, null, null);
    }

    @Override
    protected void buildDialog(Project project, PsiDirectory psiDirectory, CreateFileFromTemplateDialog.Builder builder) {
        builder.
                setTitle(NEW_COQ_MODULE).
                addKind("Empty module", CoqIcons.FILE, "Coq Module").
               /* addKind("Header file", ErlangIcons.HEADER, "Erlang Header").
                addKind("EUnit tests", ErlangIcons.EUNIT, "Erlang EUnit Tests").
                addKind("OTP application", ErlangIcons.OTP_APPLICATION, "Erlang Application").
                addKind("OTP application resource file", ErlangIcons.OTP_APP_RESOURCE, "Erlang Application Resource File").
                addKind("OTP supervisor", ErlangIcons.OTP_SUPERVISOR, "Erlang Supervisor").
                addKind("OTP gen_server", ErlangIcons.OTP_GEN_SERVER, "Erlang Gen Server").
                addKind("OTP gen_fsm", ErlangIcons.OTP_GEN_FSM, "Erlang Gen FSM").
                addKind("OTP gen_event", ErlangIcons.OTP_GEN_EVENT, "Erlang Gen Event").*/
                setValidator(new InputValidatorEx() {
                    @Override
                    public boolean checkInput(String inputString) {
                        return getErrorText(inputString) == null;
                    }

                    @Override
                    public boolean canClose(String inputString) {
                        return getErrorText(inputString) == null;
                    }

                    @Nullable
                    @Override
                    public String getErrorText(String inputString) {
                        return null;
                    }
                })
        ;
    }

    @Override
    protected String getActionName(PsiDirectory psiDirectory, String s, String s1) {
        return NEW_COQ_MODULE;
    }

  /*  private static final String NEW_ERLANG_FILE = "New Erlang File";

    public CreateErlangFileAction() {
        super(NEW_ERLANG_FILE, "", ErlangIcons.FILE);
    }

    @Override
    protected void buildDialog(final Project project, PsiDirectory directory, CreateFileFromTemplateDialog.Builder builder) {
        builder.
                setTitle(NEW_ERLANG_FILE).
                addKind("Empty module", ErlangIcons.FILE, "Erlang Module").
                addKind("Header file", ErlangIcons.HEADER, "Erlang Header").
                addKind("EUnit tests", ErlangIcons.EUNIT, "Erlang EUnit Tests").
                addKind("OTP application", ErlangIcons.OTP_APPLICATION, "Erlang Application").
                addKind("OTP application resource file", ErlangIcons.OTP_APP_RESOURCE, "Erlang Application Resource File").
                addKind("OTP supervisor", ErlangIcons.OTP_SUPERVISOR, "Erlang Supervisor").
                addKind("OTP gen_server", ErlangIcons.OTP_GEN_SERVER, "Erlang Gen Server").
                addKind("OTP gen_fsm", ErlangIcons.OTP_GEN_FSM, "Erlang Gen FSM").
                addKind("OTP gen_event", ErlangIcons.OTP_GEN_EVENT, "Erlang Gen Event").
                setValidator(new InputValidatorEx() {
                    @Override
                    public boolean checkInput(String inputString) {
                        return getErrorText(inputString) == null;
                    }

                    @Override
                    public boolean canClose(String inputString) {
                        return getErrorText(inputString) == null;
                    }

                    @Nullable
                    @Override
                    public String getErrorText(String inputString) {
                        return !StringUtil.isEmpty(inputString) && FileUtil.sanitizeName(inputString).equals(inputString) ? null :
                                "'" + inputString + "'" + " is not a valid Erlang module name";
                    }
                })
        ;
    }

    @Override
    protected String getActionName(PsiDirectory directory, String newName, String templateName) {
        return NEW_ERLANG_FILE;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CreateErlangFileAction;
    }
*/
}
