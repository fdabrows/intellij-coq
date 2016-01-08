package com.coq;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.HighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import com.coq.psi.CoqTypes;
import org.jetbrains.annotations.NotNull;

import static com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey;

public class CoqSyntaxHighlighter extends SyntaxHighlighterBase {

    public static final TextAttributesKey COMMAND = createTextAttributesKey("COQ_COMMAND", DefaultLanguageHighlighterColors.STRING);
    public static final TextAttributesKey KEYWORD = createTextAttributesKey("COQ_KEY", DefaultLanguageHighlighterColors.KEYWORD);
    public static final TextAttributesKey KIND = createTextAttributesKey("COQ_KIND", DefaultLanguageHighlighterColors.PREDEFINED_SYMBOL);
    public static final TextAttributesKey NAME = createTextAttributesKey("COQ_NAME", DefaultLanguageHighlighterColors.IDENTIFIER);
    public static final TextAttributesKey FORMULAE = createTextAttributesKey("COQ_FORMULAE", DefaultLanguageHighlighterColors.KEYWORD);
    public static final TextAttributesKey TACTIC = createTextAttributesKey("COQ_TACTICS", DefaultLanguageHighlighterColors.KEYWORD);
    public static final TextAttributesKey BAD_CHARACTER = createTextAttributesKey("COQ_BAD_CHARACTER", HighlighterColors.BAD_CHARACTER);
    public static final TextAttributesKey COMMENT = createTextAttributesKey("COQ_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT);

    private static final TextAttributesKey[] COMMAND_KEYS = new TextAttributesKey[]{COMMAND};
    private static final TextAttributesKey[] KEYWORD_KEYS = new TextAttributesKey[]{KEYWORD};
    private static final TextAttributesKey[] KIND_KEYS = new TextAttributesKey[]{KIND};
    private static final TextAttributesKey[] NAME_KEYS = new TextAttributesKey[]{NAME};
    private static final TextAttributesKey[] FORMULAE_KEYS = new TextAttributesKey[]{FORMULAE};
    private static final TextAttributesKey[] TACTIC_KEYS = new TextAttributesKey[]{TACTIC};
    private static final TextAttributesKey[] BAD_CHAR_KEYS = new TextAttributesKey[]{BAD_CHARACTER};
    private static final TextAttributesKey[] COMMENT_KEYS = new TextAttributesKey[]{COMMENT};
    private static final TextAttributesKey[] EMPTY_KEYS = new TextAttributesKey[0];



    @NotNull
    @Override
    public Lexer getHighlightingLexer() {
        return new CoqLexerAdapter();
    }

    @NotNull
    @Override
    public TextAttributesKey[] getTokenHighlights(IElementType tokenType) {
        if (tokenType.equals(CoqTypes.PROOF_KW)) {
            return KEYWORD_KEYS;
        } else if (tokenType.equals(CoqTypes.QED)) {
            return KEYWORD_KEYS;
        } else if (tokenType.equals(CoqTypes.ADMITTED)) {
            return KEYWORD_KEYS;
        } else if (tokenType.equals(CoqTypes.DEFINED)) {
            return KEYWORD_KEYS;
        } else if (tokenType.equals(CoqTypes.COMMAND)) {
            return COMMAND_KEYS;
        } else if (tokenType.equals(CoqTypes.KEYWORD)) {
            return FORMULAE_KEYS;
        } else if (tokenType.equals(CoqTypes.TACTIC)) {
            return TACTIC_KEYS;
        } else if (tokenType.equals(CoqTypes.DOT)) {
            return KEYWORD_KEYS;
        } else if (tokenType.equals(CoqTypes.THEOREM_KW)) {
            return KEYWORD_KEYS;
        } else if (tokenType.equals(CoqTypes.LEMMA_KW)) {
            return KEYWORD_KEYS;
        } else if (tokenType.equals(CoqTypes.REMARK_KW)) {
            return KEYWORD_KEYS;
        } else if (tokenType.equals(CoqTypes.FACT_KW)) {
            return KEYWORD_KEYS;
        } else if (tokenType.equals(CoqTypes.COROLLARY_KW)) {
            return KEYWORD_KEYS;
        } else if (tokenType.equals(CoqTypes.PROPOSITION_KW)) {
            return KEYWORD_KEYS;
        } else if (tokenType.equals(CoqTypes.EXAMPLE_KW)) {
            return KEYWORD_KEYS;
        } else if (tokenType.equals(CoqTypes.INDUCTIVE_KW)) {
            return KEYWORD_KEYS;
        } else if (tokenType.equals(CoqTypes.DEFINITION_KW)) {
            return KEYWORD_KEYS;
        } else if (tokenType.equals(CoqTypes.FIXPOINT_KW)) {
            return KEYWORD_KEYS;
        } else if (tokenType.equals(CoqTypes.MATCH)) {
            return KEYWORD_KEYS;
        } else if (tokenType.equals(CoqTypes.WITH)) {
            return KEYWORD_KEYS;
        } else if (tokenType.equals(CoqTypes.END)) {
            return KEYWORD_KEYS;
        } else  if (tokenType.equals(TokenType.BAD_CHARACTER)) {
            return BAD_CHAR_KEYS;
        } else {
            return EMPTY_KEYS;
        }
    }
}