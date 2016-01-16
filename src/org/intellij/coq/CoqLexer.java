/* The following code was generated by JFlex 1.4.3 on 16/01/16 22:37 */

package org.intellij.coq;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import org.intellij.coq.psi.CoqTypes;
import com.intellij.psi.TokenType;
import org.intellij.coq.CoqKeywords;


/**
 * This class is a scanner generated by 
 * <a href="http://www.jflex.de/">JFlex</a> 1.4.3
 * on 16/01/16 22:37 from the specification file
 * <tt>/Users/dabrowski/Workspace/Recherche/Current/ProofTool/intellij-coq/src/org/intellij/coq/Simple.flex</tt>
 */
class CoqLexer implements FlexLexer {
  /** initial size of the lookahead buffer */
  private static final int ZZ_BUFFERSIZE = 16384;

  /** lexical states */
  public static final int YYFIXPOINTNAME = 4;
  public static final int YYLEMMA = 14;
  public static final int YYINDUCTIVENAME = 8;
  public static final int YYLEMMANAME = 16;
  public static final int YYNAME = 18;
  public static final int YYFIXPOINT = 2;
  public static final int YYINITIAL = 0;
  public static final int YYDEFINITIONNAME = 12;
  public static final int YYDEFINITION = 10;
  public static final int YYTACTIC = 20;
  public static final int YYINDUCTIVE = 6;

  /**
   * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
   * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l
   *                  at the beginning of a line
   * l is of the form l = 2*k, k a non negative integer
   */
  private static final int ZZ_LEXSTATE[] = { 
     0,  0,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1, 
     1,  1,  2,  2,  1, 1
  };

  /** 
   * Translates characters to character classes
   */
  private static final String ZZ_CMAP_PACKED = 
    "\11\0\1\10\1\25\1\0\1\10\1\25\22\0\1\10\2\0\1\24"+
    "\3\0\1\1\1\5\1\7\1\6\1\0\1\20\1\21\1\4\1\0"+
    "\1\2\11\3\1\16\1\17\1\0\1\23\1\22\2\0\1\64\1\1"+
    "\1\47\1\26\1\55\1\45\2\1\1\57\2\1\1\41\3\1\1\52"+
    "\1\63\1\43\1\1\1\35\6\1\1\11\1\0\1\12\1\0\1\1"+
    "\1\0\1\42\1\1\1\46\1\60\1\27\1\30\1\1\1\36\1\31"+
    "\1\1\1\44\1\50\1\40\1\32\1\34\1\53\1\1\1\37\1\54"+
    "\1\33\1\61\1\62\1\65\1\56\1\51\1\1\1\13\1\15\1\14"+
    "\uff82\0";

  /** 
   * Translates characters to character classes
   */
  private static final char [] ZZ_CMAP = zzUnpackCMap(ZZ_CMAP_PACKED);

  /** 
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\3\0\1\1\1\2\1\3\1\4\1\1\1\5\1\6"+
    "\1\7\1\10\1\11\1\12\1\13\1\14\1\15\1\16"+
    "\2\1\1\17\16\2\1\20\1\2\2\21\3\20\16\2"+
    "\17\22\1\23\1\24\1\25\1\26\1\27\1\30\17\2"+
    "\1\27\1\30\17\2\17\22\1\24\1\2\1\31\13\2"+
    "\1\32\3\2\1\31\13\2\1\32\2\2\1\22\1\31"+
    "\13\22\1\32\2\22\6\2\1\33\7\2\1\34\6\2"+
    "\1\33\7\2\1\34\6\22\1\33\7\22\1\34\2\2"+
    "\1\35\1\36\4\2\1\37\6\2\1\35\1\36\4\2"+
    "\1\37\4\2\2\22\1\35\1\36\4\22\1\37\4\22"+
    "\3\2\1\40\12\2\1\40\7\2\3\22\1\40\7\22"+
    "\1\41\1\2\1\42\4\2\1\43\2\2\1\41\1\2"+
    "\1\42\4\2\1\43\2\2\1\41\1\22\1\42\4\22"+
    "\1\43\2\22\1\2\1\44\4\2\1\45\1\2\1\44"+
    "\4\2\1\45\1\22\1\44\4\22\1\45\1\2\1\46"+
    "\2\2\1\47\1\2\1\46\2\2\1\47\1\22\1\46"+
    "\2\22\1\47\1\50\1\51\1\2\1\50\1\51\1\2"+
    "\1\50\1\51\1\22\3\52";

  private static int [] zzUnpackAction() {
    int [] result = new int[368];
    int offset = 0;
    offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAction(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /** 
   * Translates a state to a row index in the transition table
   */
  private static final int [] ZZ_ROWMAP = zzUnpackRowMap();

  private static final String ZZ_ROWMAP_PACKED_0 =
    "\0\0\0\66\0\154\0\242\0\330\0\u010e\0\u0144\0\u017a"+
    "\0\u01b0\0\u01b0\0\u01b0\0\u01b0\0\u01b0\0\u01b0\0\u01b0\0\u01e6"+
    "\0\u01b0\0\u01b0\0\u021c\0\u0252\0\u0288\0\u02be\0\u02f4\0\u032a"+
    "\0\u0360\0\u0396\0\u03cc\0\u0402\0\u0438\0\u046e\0\u04a4\0\u04da"+
    "\0\u0510\0\u0546\0\u057c\0\u01b0\0\u05b2\0\u01b0\0\u05e8\0\u061e"+
    "\0\u0654\0\u068a\0\u06c0\0\u06f6\0\u072c\0\u0762\0\u0798\0\u07ce"+
    "\0\u0804\0\u083a\0\u0870\0\u08a6\0\u08dc\0\u0912\0\u0948\0\u097e"+
    "\0\u09b4\0\u09ea\0\u0a20\0\u0a56\0\u0a8c\0\u0ac2\0\u0af8\0\u0b2e"+
    "\0\u0b64\0\u0b9a\0\u0bd0\0\u0c06\0\u0c3c\0\u0c72\0\u0ca8\0\u01b0"+
    "\0\u0cde\0\u01b0\0\u01b0\0\242\0\242\0\u0d14\0\u0d4a\0\u0d80"+
    "\0\u0db6\0\u0dec\0\u0e22\0\u0e58\0\u0e8e\0\u0ec4\0\u0efa\0\u0f30"+
    "\0\u0f66\0\u0f9c\0\u0fd2\0\u1008\0\u01b0\0\u01b0\0\u103e\0\u1074"+
    "\0\u10aa\0\u10e0\0\u1116\0\u114c\0\u1182\0\u11b8\0\u11ee\0\u1224"+
    "\0\u125a\0\u1290\0\u12c6\0\u12fc\0\u1332\0\u1368\0\u139e\0\u13d4"+
    "\0\u140a\0\u1440\0\u1476\0\u14ac\0\u14e2\0\u1518\0\u154e\0\u1584"+
    "\0\u15ba\0\u15f0\0\u1626\0\u165c\0\u01b0\0\u1692\0\330\0\u16c8"+
    "\0\u16fe\0\u1734\0\u176a\0\u17a0\0\u17d6\0\u180c\0\u1842\0\u1878"+
    "\0\u18ae\0\u18e4\0\330\0\u191a\0\u1950\0\u1986\0\u05b2\0\u19bc"+
    "\0\u19f2\0\u1a28\0\u1a5e\0\u1a94\0\u1aca\0\u1b00\0\u1b36\0\u1b6c"+
    "\0\u1ba2\0\u1bd8\0\u05b2\0\u1c0e\0\u1c44\0\u1c7a\0\u09b4\0\u1cb0"+
    "\0\u1ce6\0\u1d1c\0\u1d52\0\u1d88\0\u1dbe\0\u1df4\0\u1e2a\0\u1e60"+
    "\0\u1e96\0\u1ecc\0\u09b4\0\u1f02\0\u1f38\0\u1f6e\0\u1fa4\0\u1fda"+
    "\0\u2010\0\u2046\0\u207c\0\330\0\u20b2\0\u20e8\0\u211e\0\u2154"+
    "\0\u218a\0\u21c0\0\u21f6\0\330\0\u222c\0\u2262\0\u2298\0\u22ce"+
    "\0\u2304\0\u233a\0\u05b2\0\u2370\0\u23a6\0\u23dc\0\u2412\0\u2448"+
    "\0\u247e\0\u24b4\0\u05b2\0\u24ea\0\u2520\0\u2556\0\u258c\0\u25c2"+
    "\0\u25f8\0\u09b4\0\u262e\0\u2664\0\u269a\0\u26d0\0\u2706\0\u273c"+
    "\0\u2772\0\u09b4\0\u27a8\0\u27de\0\330\0\330\0\u2814\0\u284a"+
    "\0\u2880\0\u28b6\0\330\0\u28ec\0\u2922\0\u2958\0\u298e\0\u29c4"+
    "\0\u29fa\0\u05b2\0\u05b2\0\u2a30\0\u2a66\0\u2a9c\0\u2ad2\0\u05b2"+
    "\0\u2b08\0\u2b3e\0\u2b74\0\u2baa\0\u2be0\0\u2c16\0\u09b4\0\u09b4"+
    "\0\u2c4c\0\u2c82\0\u2cb8\0\u2cee\0\u09b4\0\u2d24\0\u2d5a\0\u2d90"+
    "\0\u2dc6\0\u2dfc\0\u2e32\0\u2e68\0\330\0\u2e9e\0\u2ed4\0\u2f0a"+
    "\0\u2f40\0\u2f76\0\u2fac\0\u2fe2\0\u3018\0\u304e\0\u3084\0\u05b2"+
    "\0\u30ba\0\u30f0\0\u3126\0\u315c\0\u3192\0\u31c8\0\u31fe\0\u3234"+
    "\0\u326a\0\u32a0\0\u09b4\0\u32d6\0\u330c\0\u3342\0\u3378\0\u33ae"+
    "\0\u33e4\0\u341a\0\330\0\u3450\0\330\0\u3486\0\u34bc\0\u34f2"+
    "\0\u3528\0\330\0\u355e\0\u3594\0\u05b2\0\u35ca\0\u05b2\0\u3600"+
    "\0\u3636\0\u366c\0\u36a2\0\u05b2\0\u36d8\0\u370e\0\u09b4\0\u3744"+
    "\0\u09b4\0\u377a\0\u37b0\0\u37e6\0\u381c\0\u09b4\0\u3852\0\u3888"+
    "\0\u38be\0\330\0\u38f4\0\u392a\0\u3960\0\u3996\0\330\0\u39cc"+
    "\0\u05b2\0\u3a02\0\u3a38\0\u3a6e\0\u3aa4\0\u05b2\0\u3ada\0\u09b4"+
    "\0\u3b10\0\u3b46\0\u3b7c\0\u3bb2\0\u09b4\0\u3be8\0\330\0\u3c1e"+
    "\0\u3c54\0\330\0\u3c8a\0\u05b2\0\u3cc0\0\u3cf6\0\u05b2\0\u3d2c"+
    "\0\u09b4\0\u3d62\0\u3d98\0\u09b4\0\330\0\330\0\u3dce\0\u05b2"+
    "\0\u05b2\0\u3e04\0\u09b4\0\u09b4\0\u3e3a\0\330\0\u05b2\0\u09b4";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[368];
    int offset = 0;
    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackRowMap(String packed, int offset, int [] result) {
    int i = 0;  /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int high = packed.charAt(i++) << 16;
      result[j++] = high | packed.charAt(i++);
    }
    return j;
  }

  /** 
   * The transition table of the DFA
   */
  private static final int [] ZZ_TRANS = zzUnpackTrans();

  private static final String ZZ_TRANS_PACKED_0 =
    "\1\4\1\5\2\4\1\6\1\7\1\10\1\11\1\12"+
    "\1\13\1\14\1\15\1\16\1\17\1\20\1\21\1\22"+
    "\1\23\1\4\1\24\1\25\1\12\1\26\1\27\5\5"+
    "\1\30\2\5\1\31\1\32\1\5\1\33\1\5\1\34"+
    "\1\5\1\35\2\5\1\36\2\5\1\37\1\5\1\40"+
    "\3\5\1\41\1\42\1\43\1\44\1\45\1\46\1\47"+
    "\1\6\1\7\1\50\1\11\1\12\1\13\1\14\1\15"+
    "\1\16\1\17\1\20\1\21\1\22\1\51\1\44\1\52"+
    "\1\25\1\12\1\53\1\54\5\45\1\55\2\45\1\56"+
    "\1\57\1\45\1\60\1\45\1\61\1\45\1\62\2\45"+
    "\1\63\2\45\1\64\1\45\1\65\3\45\1\66\1\67"+
    "\1\70\1\44\1\71\1\46\1\47\1\6\1\7\1\50"+
    "\1\11\1\12\1\13\1\14\1\15\1\16\1\17\1\20"+
    "\1\21\1\22\1\51\1\44\1\52\1\25\1\12\1\72"+
    "\1\73\5\71\1\74\2\71\1\75\1\76\1\71\1\77"+
    "\1\71\1\100\1\71\1\101\2\71\1\102\2\71\1\103"+
    "\1\71\1\104\3\71\1\105\1\106\1\107\4\4\2\0"+
    "\1\4\12\0\4\4\1\0\41\4\3\5\2\0\1\4"+
    "\12\0\4\4\1\0\40\5\10\110\1\0\14\110\1\0"+
    "\40\110\6\0\1\111\57\0\4\4\2\0\1\4\1\112"+
    "\11\0\4\4\1\0\40\4\111\0\1\113\42\0\4\4"+
    "\2\0\1\4\12\0\1\4\1\114\2\4\1\0\44\4"+
    "\2\0\1\4\12\0\1\4\1\115\2\4\1\0\40\4"+
    "\25\25\1\0\40\25\1\4\3\5\2\0\1\4\12\0"+
    "\4\4\1\0\1\5\1\116\36\5\1\4\3\5\2\0"+
    "\1\4\12\0\4\4\1\0\4\5\1\117\33\5\1\4"+
    "\3\5\2\0\1\4\12\0\4\4\1\0\10\5\1\120"+
    "\27\5\1\4\3\5\2\0\1\4\12\0\4\4\1\0"+
    "\14\5\1\121\23\5\1\4\3\5\2\0\1\4\12\0"+
    "\4\4\1\0\1\5\1\122\36\5\1\4\3\5\2\0"+
    "\1\4\12\0\4\4\1\0\1\5\1\123\36\5\1\4"+
    "\3\5\2\0\1\4\12\0\4\4\1\0\3\5\1\124"+
    "\10\5\1\125\23\5\1\4\3\5\2\0\1\4\12\0"+
    "\4\4\1\0\6\5\1\126\31\5\1\4\3\5\2\0"+
    "\1\4\12\0\4\4\1\0\11\5\1\127\26\5\1\4"+
    "\3\5\2\0\1\4\12\0\4\4\1\0\30\5\1\130"+
    "\7\5\1\4\3\5\2\0\1\4\12\0\4\4\1\0"+
    "\4\5\1\131\33\5\1\4\3\5\2\0\1\4\12\0"+
    "\4\4\1\0\1\5\1\132\36\5\1\4\3\5\2\0"+
    "\1\4\12\0\4\4\1\0\32\5\1\133\5\5\1\4"+
    "\3\5\2\0\1\4\12\0\4\4\1\0\3\5\1\134"+
    "\34\5\1\0\3\45\22\0\40\45\2\0\2\47\71\0"+
    "\1\112\100\0\1\135\65\0\1\136\44\0\3\45\22\0"+
    "\1\45\1\137\36\45\1\0\3\45\22\0\4\45\1\140"+
    "\33\45\1\0\3\45\22\0\10\45\1\141\27\45\1\0"+
    "\3\45\22\0\14\45\1\142\23\45\1\0\3\45\22\0"+
    "\1\45\1\143\36\45\1\0\3\45\22\0\1\45\1\144"+
    "\36\45\1\0\3\45\22\0\3\45\1\145\10\45\1\146"+
    "\23\45\1\0\3\45\22\0\6\45\1\147\31\45\1\0"+
    "\3\45\22\0\11\45\1\150\26\45\1\0\3\45\22\0"+
    "\30\45\1\151\7\45\1\0\3\45\22\0\4\45\1\152"+
    "\33\45\1\0\3\45\22\0\1\45\1\153\36\45\1\0"+
    "\3\45\22\0\32\45\1\154\5\45\1\0\3\45\22\0"+
    "\3\45\1\155\34\45\1\0\3\71\22\0\40\71\1\0"+
    "\3\71\22\0\1\71\1\156\36\71\1\0\3\71\22\0"+
    "\4\71\1\157\33\71\1\0\3\71\22\0\10\71\1\160"+
    "\27\71\1\0\3\71\22\0\14\71\1\161\23\71\1\0"+
    "\3\71\22\0\1\71\1\162\36\71\1\0\3\71\22\0"+
    "\1\71\1\163\36\71\1\0\3\71\22\0\3\71\1\164"+
    "\10\71\1\165\23\71\1\0\3\71\22\0\6\71\1\166"+
    "\31\71\1\0\3\71\22\0\11\71\1\167\26\71\1\0"+
    "\3\71\22\0\30\71\1\170\7\71\1\0\3\71\22\0"+
    "\4\71\1\171\33\71\1\0\3\71\22\0\1\71\1\172"+
    "\36\71\1\0\3\71\22\0\32\71\1\173\5\71\1\0"+
    "\3\71\22\0\3\71\1\174\34\71\6\0\1\175\57\0"+
    "\1\4\3\5\2\0\1\4\12\0\4\4\1\0\2\5"+
    "\1\176\35\5\1\4\3\5\2\0\1\4\12\0\4\4"+
    "\1\0\32\5\1\177\5\5\1\4\3\5\2\0\1\4"+
    "\12\0\4\4\1\0\1\5\1\200\36\5\1\4\3\5"+
    "\2\0\1\4\12\0\4\4\1\0\5\5\1\201\32\5"+
    "\1\4\3\5\2\0\1\4\12\0\4\4\1\0\12\5"+
    "\1\202\25\5\1\4\3\5\2\0\1\4\12\0\4\4"+
    "\1\0\12\5\1\203\25\5\1\4\3\5\2\0\1\4"+
    "\12\0\4\4\1\0\30\5\1\204\7\5\1\4\3\5"+
    "\2\0\1\4\12\0\4\4\1\0\20\5\1\205\17\5"+
    "\1\4\3\5\2\0\1\4\12\0\4\4\1\0\11\5"+
    "\1\206\5\5\1\207\20\5\1\4\3\5\2\0\1\4"+
    "\12\0\4\4\1\0\6\5\1\210\31\5\1\4\3\5"+
    "\2\0\1\4\12\0\4\4\1\0\14\5\1\211\23\5"+
    "\1\4\3\5\2\0\1\4\12\0\4\4\1\0\32\5"+
    "\1\212\5\5\1\4\3\5\2\0\1\4\12\0\4\4"+
    "\1\0\32\5\1\213\5\5\1\4\3\5\2\0\1\4"+
    "\12\0\4\4\1\0\12\5\1\214\25\5\1\4\3\5"+
    "\2\0\1\4\12\0\4\4\1\0\5\5\1\215\32\5"+
    "\1\0\3\45\22\0\2\45\1\216\35\45\1\0\3\45"+
    "\22\0\32\45\1\217\5\45\1\0\3\45\22\0\1\45"+
    "\1\220\36\45\1\0\3\45\22\0\5\45\1\221\32\45"+
    "\1\0\3\45\22\0\12\45\1\222\25\45\1\0\3\45"+
    "\22\0\12\45\1\223\25\45\1\0\3\45\22\0\30\45"+
    "\1\224\7\45\1\0\3\45\22\0\20\45\1\225\17\45"+
    "\1\0\3\45\22\0\11\45\1\226\5\45\1\227\20\45"+
    "\1\0\3\45\22\0\6\45\1\230\31\45\1\0\3\45"+
    "\22\0\14\45\1\231\23\45\1\0\3\45\22\0\32\45"+
    "\1\232\5\45\1\0\3\45\22\0\32\45\1\233\5\45"+
    "\1\0\3\45\22\0\12\45\1\234\25\45\1\0\3\45"+
    "\22\0\5\45\1\235\32\45\1\0\3\71\22\0\2\71"+
    "\1\236\35\71\1\0\3\71\22\0\32\71\1\237\5\71"+
    "\1\0\3\71\22\0\1\71\1\240\36\71\1\0\3\71"+
    "\22\0\5\71\1\241\32\71\1\0\3\71\22\0\12\71"+
    "\1\242\25\71\1\0\3\71\22\0\12\71\1\243\25\71"+
    "\1\0\3\71\22\0\30\71\1\244\7\71\1\0\3\71"+
    "\22\0\20\71\1\245\17\71\1\0\3\71\22\0\11\71"+
    "\1\246\5\71\1\247\20\71\1\0\3\71\22\0\6\71"+
    "\1\250\31\71\1\0\3\71\22\0\14\71\1\251\23\71"+
    "\1\0\3\71\22\0\32\71\1\252\5\71\1\0\3\71"+
    "\22\0\32\71\1\253\5\71\1\0\3\71\22\0\12\71"+
    "\1\254\25\71\1\0\3\71\22\0\5\71\1\255\32\71"+
    "\1\4\3\5\2\0\1\4\12\0\4\4\1\0\3\5"+
    "\1\256\34\5\1\4\3\5\2\0\1\4\12\0\4\4"+
    "\1\0\6\5\1\257\31\5\1\4\3\5\2\0\1\4"+
    "\12\0\4\4\1\0\20\5\1\260\17\5\1\4\3\5"+
    "\2\0\1\4\12\0\4\4\1\0\12\5\1\261\25\5"+
    "\1\4\3\5\2\0\1\4\12\0\4\4\1\0\14\5"+
    "\1\262\23\5\1\4\3\5\2\0\1\4\12\0\4\4"+
    "\1\0\25\5\1\263\12\5\1\4\3\5\2\0\1\4"+
    "\12\0\4\4\1\0\5\5\1\264\32\5\1\4\3\5"+
    "\2\0\1\4\12\0\4\4\1\0\6\5\1\265\31\5"+
    "\1\4\3\5\2\0\1\4\12\0\4\4\1\0\3\5"+
    "\1\266\34\5\1\4\3\5\2\0\1\4\12\0\4\4"+
    "\1\0\6\5\1\267\16\5\1\270\12\5\1\4\3\5"+
    "\2\0\1\4\12\0\4\4\1\0\12\5\1\271\25\5"+
    "\1\4\3\5\2\0\1\4\12\0\4\4\1\0\33\5"+
    "\1\272\4\5\1\4\3\5\2\0\1\4\12\0\4\4"+
    "\1\0\3\5\1\273\34\5\1\4\3\5\2\0\1\4"+
    "\12\0\4\4\1\0\10\5\1\274\27\5\1\0\3\45"+
    "\22\0\3\45\1\275\34\45\1\0\3\45\22\0\6\45"+
    "\1\276\31\45\1\0\3\45\22\0\20\45\1\277\17\45"+
    "\1\0\3\45\22\0\12\45\1\300\25\45\1\0\3\45"+
    "\22\0\14\45\1\301\23\45\1\0\3\45\22\0\25\45"+
    "\1\302\12\45\1\0\3\45\22\0\5\45\1\303\32\45"+
    "\1\0\3\45\22\0\6\45\1\304\31\45\1\0\3\45"+
    "\22\0\3\45\1\305\34\45\1\0\3\45\22\0\6\45"+
    "\1\306\16\45\1\307\12\45\1\0\3\45\22\0\12\45"+
    "\1\310\25\45\1\0\3\45\22\0\33\45\1\311\4\45"+
    "\1\0\3\45\22\0\3\45\1\312\34\45\1\0\3\45"+
    "\22\0\10\45\1\313\27\45\1\0\3\71\22\0\3\71"+
    "\1\314\34\71\1\0\3\71\22\0\6\71\1\315\31\71"+
    "\1\0\3\71\22\0\20\71\1\316\17\71\1\0\3\71"+
    "\22\0\12\71\1\317\25\71\1\0\3\71\22\0\14\71"+
    "\1\320\23\71\1\0\3\71\22\0\25\71\1\321\12\71"+
    "\1\0\3\71\22\0\5\71\1\322\32\71\1\0\3\71"+
    "\22\0\6\71\1\323\31\71\1\0\3\71\22\0\3\71"+
    "\1\324\34\71\1\0\3\71\22\0\6\71\1\325\16\71"+
    "\1\326\12\71\1\0\3\71\22\0\12\71\1\327\25\71"+
    "\1\0\3\71\22\0\33\71\1\330\4\71\1\0\3\71"+
    "\22\0\3\71\1\331\34\71\1\0\3\71\22\0\10\71"+
    "\1\332\27\71\1\4\3\5\2\0\1\4\12\0\4\4"+
    "\1\0\4\5\1\333\33\5\1\4\3\5\2\0\1\4"+
    "\12\0\4\4\1\0\11\5\1\334\26\5\1\4\3\5"+
    "\2\0\1\4\12\0\4\4\1\0\10\5\1\335\27\5"+
    "\1\4\3\5\2\0\1\4\12\0\4\4\1\0\14\5"+
    "\1\336\23\5\1\4\3\5\2\0\1\4\12\0\4\4"+
    "\1\0\11\5\1\337\26\5\1\4\3\5\2\0\1\4"+
    "\12\0\4\4\1\0\6\5\1\340\31\5\1\4\3\5"+
    "\2\0\1\4\12\0\4\4\1\0\22\5\1\341\15\5"+
    "\1\4\3\5\2\0\1\4\12\0\4\4\1\0\30\5"+
    "\1\342\7\5\1\4\3\5\2\0\1\4\12\0\4\4"+
    "\1\0\2\5\1\343\35\5\1\4\3\5\2\0\1\4"+
    "\12\0\4\4\1\0\6\5\1\344\31\5\1\4\3\5"+
    "\2\0\1\4\12\0\4\4\1\0\25\5\1\345\12\5"+
    "\1\4\3\5\2\0\1\4\12\0\4\4\1\0\20\5"+
    "\1\346\17\5\1\4\3\5\2\0\1\4\12\0\4\4"+
    "\1\0\5\5\1\347\32\5\1\0\3\45\22\0\4\45"+
    "\1\350\33\45\1\0\3\45\22\0\11\45\1\351\26\45"+
    "\1\0\3\45\22\0\10\45\1\352\27\45\1\0\3\45"+
    "\22\0\14\45\1\353\23\45\1\0\3\45\22\0\11\45"+
    "\1\354\26\45\1\0\3\45\22\0\6\45\1\355\31\45"+
    "\1\0\3\45\22\0\22\45\1\356\15\45\1\0\3\45"+
    "\22\0\30\45\1\357\7\45\1\0\3\45\22\0\2\45"+
    "\1\360\35\45\1\0\3\45\22\0\6\45\1\361\31\45"+
    "\1\0\3\45\22\0\25\45\1\362\12\45\1\0\3\45"+
    "\22\0\20\45\1\363\17\45\1\0\3\45\22\0\5\45"+
    "\1\364\32\45\1\0\3\71\22\0\4\71\1\365\33\71"+
    "\1\0\3\71\22\0\11\71\1\366\26\71\1\0\3\71"+
    "\22\0\10\71\1\367\27\71\1\0\3\71\22\0\14\71"+
    "\1\370\23\71\1\0\3\71\22\0\11\71\1\371\26\71"+
    "\1\0\3\71\22\0\6\71\1\372\31\71\1\0\3\71"+
    "\22\0\22\71\1\373\15\71\1\0\3\71\22\0\30\71"+
    "\1\374\7\71\1\0\3\71\22\0\2\71\1\375\35\71"+
    "\1\0\3\71\22\0\6\71\1\376\31\71\1\0\3\71"+
    "\22\0\25\71\1\377\12\71\1\0\3\71\22\0\20\71"+
    "\1\u0100\17\71\1\0\3\71\22\0\5\71\1\u0101\32\71"+
    "\1\4\3\5\2\0\1\4\12\0\4\4\1\0\1\5"+
    "\1\u0102\1\5\1\u0103\34\5\1\4\3\5\2\0\1\4"+
    "\12\0\4\4\1\0\1\5\1\u0104\36\5\1\4\3\5"+
    "\2\0\1\4\12\0\4\4\1\0\16\5\1\u0105\21\5"+
    "\1\4\3\5\2\0\1\4\12\0\4\4\1\0\3\5"+
    "\1\u0106\34\5\1\4\3\5\2\0\1\4\12\0\4\4"+
    "\1\0\22\5\1\u0107\15\5\1\4\3\5\2\0\1\4"+
    "\12\0\4\4\1\0\25\5\1\u0108\12\5\1\4\3\5"+
    "\2\0\1\4\12\0\4\4\1\0\26\5\1\u0109\11\5"+
    "\1\4\3\5\2\0\1\4\12\0\4\4\1\0\22\5"+
    "\1\u010a\15\5\1\4\3\5\2\0\1\4\12\0\4\4"+
    "\1\0\5\5\1\u010b\32\5\1\4\3\5\2\0\1\4"+
    "\12\0\4\4\1\0\5\5\1\u010c\32\5\1\0\3\45"+
    "\22\0\1\45\1\u010d\1\45\1\u010e\34\45\1\0\3\45"+
    "\22\0\1\45\1\u010f\36\45\1\0\3\45\22\0\16\45"+
    "\1\u0110\21\45\1\0\3\45\22\0\3\45\1\u0111\34\45"+
    "\1\0\3\45\22\0\22\45\1\u0112\15\45\1\0\3\45"+
    "\22\0\25\45\1\u0113\12\45\1\0\3\45\22\0\26\45"+
    "\1\u0114\11\45\1\0\3\45\22\0\22\45\1\u0115\15\45"+
    "\1\0\3\45\22\0\5\45\1\u0116\32\45\1\0\3\45"+
    "\22\0\5\45\1\u0117\32\45\1\0\3\71\22\0\1\71"+
    "\1\u0118\1\71\1\u0119\34\71\1\0\3\71\22\0\1\71"+
    "\1\u011a\36\71\1\0\3\71\22\0\16\71\1\u011b\21\71"+
    "\1\0\3\71\22\0\3\71\1\u011c\34\71\1\0\3\71"+
    "\22\0\22\71\1\u011d\15\71\1\0\3\71\22\0\25\71"+
    "\1\u011e\12\71\1\0\3\71\22\0\26\71\1\u011f\11\71"+
    "\1\0\3\71\22\0\22\71\1\u0120\15\71\1\0\3\71"+
    "\22\0\5\71\1\u0121\32\71\1\0\3\71\22\0\5\71"+
    "\1\u0122\32\71\1\4\3\5\2\0\1\4\12\0\4\4"+
    "\1\0\32\5\1\u0123\5\5\1\4\3\5\2\0\1\4"+
    "\12\0\4\4\1\0\5\5\1\u0124\32\5\1\4\3\5"+
    "\2\0\1\4\12\0\4\4\1\0\12\5\1\u0125\25\5"+
    "\1\4\3\5\2\0\1\4\12\0\4\4\1\0\4\5"+
    "\1\u0126\33\5\1\4\3\5\2\0\1\4\12\0\4\4"+
    "\1\0\14\5\1\u0127\23\5\1\4\3\5\2\0\1\4"+
    "\12\0\4\4\1\0\6\5\1\u0128\31\5\1\4\3\5"+
    "\2\0\1\4\12\0\4\4\1\0\3\5\1\u0129\34\5"+
    "\1\4\3\5\2\0\1\4\12\0\4\4\1\0\1\5"+
    "\1\u012a\36\5\1\4\3\5\2\0\1\4\12\0\4\4"+
    "\1\0\3\5\1\u012b\34\5\1\4\3\5\2\0\1\4"+
    "\12\0\4\4\1\0\1\5\1\u012c\36\5\1\0\3\45"+
    "\22\0\32\45\1\u012d\5\45\1\0\3\45\22\0\5\45"+
    "\1\u012e\32\45\1\0\3\45\22\0\12\45\1\u012f\25\45"+
    "\1\0\3\45\22\0\4\45\1\u0130\33\45\1\0\3\45"+
    "\22\0\14\45\1\u0131\23\45\1\0\3\45\22\0\6\45"+
    "\1\u0132\31\45\1\0\3\45\22\0\3\45\1\u0133\34\45"+
    "\1\0\3\45\22\0\1\45\1\u0134\36\45\1\0\3\45"+
    "\22\0\3\45\1\u0135\34\45\1\0\3\45\22\0\1\45"+
    "\1\u0136\36\45\1\0\3\71\22\0\32\71\1\u0137\5\71"+
    "\1\0\3\71\22\0\5\71\1\u0138\32\71\1\0\3\71"+
    "\22\0\12\71\1\u0139\25\71\1\0\3\71\22\0\4\71"+
    "\1\u013a\33\71\1\0\3\71\22\0\14\71\1\u013b\23\71"+
    "\1\0\3\71\22\0\6\71\1\u013c\31\71\1\0\3\71"+
    "\22\0\3\71\1\u013d\34\71\1\0\3\71\22\0\1\71"+
    "\1\u013e\36\71\1\0\3\71\22\0\3\71\1\u013f\34\71"+
    "\1\0\3\71\22\0\1\71\1\u0140\36\71\1\4\3\5"+
    "\2\0\1\4\12\0\4\4\1\0\3\5\1\u0141\34\5"+
    "\1\4\3\5\2\0\1\4\12\0\4\4\1\0\5\5"+
    "\1\u0142\32\5\1\4\3\5\2\0\1\4\12\0\4\4"+
    "\1\0\11\5\1\u0143\26\5\1\4\3\5\2\0\1\4"+
    "\12\0\4\4\1\0\3\5\1\u0144\34\5\1\4\3\5"+
    "\2\0\1\4\12\0\4\4\1\0\5\5\1\u0145\32\5"+
    "\1\4\3\5\2\0\1\4\12\0\4\4\1\0\34\5"+
    "\1\u0146\3\5\1\4\3\5\2\0\1\4\12\0\4\4"+
    "\1\0\32\5\1\u0147\5\5\1\0\3\45\22\0\3\45"+
    "\1\u0148\34\45\1\0\3\45\22\0\5\45\1\u0149\32\45"+
    "\1\0\3\45\22\0\11\45\1\u014a\26\45\1\0\3\45"+
    "\22\0\3\45\1\u014b\34\45\1\0\3\45\22\0\5\45"+
    "\1\u014c\32\45\1\0\3\45\22\0\34\45\1\u014d\3\45"+
    "\1\0\3\45\22\0\32\45\1\u014e\5\45\1\0\3\71"+
    "\22\0\3\71\1\u014f\34\71\1\0\3\71\22\0\5\71"+
    "\1\u0150\32\71\1\0\3\71\22\0\11\71\1\u0151\26\71"+
    "\1\0\3\71\22\0\3\71\1\u0152\34\71\1\0\3\71"+
    "\22\0\5\71\1\u0153\32\71\1\0\3\71\22\0\34\71"+
    "\1\u0154\3\71\1\0\3\71\22\0\32\71\1\u0155\5\71"+
    "\1\4\3\5\2\0\1\4\12\0\4\4\1\0\6\5"+
    "\1\u0156\31\5\1\4\3\5\2\0\1\4\12\0\4\4"+
    "\1\0\23\5\1\u0157\14\5\1\4\3\5\2\0\1\4"+
    "\12\0\4\4\1\0\4\5\1\u0158\33\5\1\4\3\5"+
    "\2\0\1\4\12\0\4\4\1\0\3\5\1\u0159\34\5"+
    "\1\4\3\5\2\0\1\4\12\0\4\4\1\0\1\5"+
    "\1\u015a\36\5\1\0\3\45\22\0\6\45\1\u015b\31\45"+
    "\1\0\3\45\22\0\23\45\1\u015c\14\45\1\0\3\45"+
    "\22\0\4\45\1\u015d\33\45\1\0\3\45\22\0\3\45"+
    "\1\u015e\34\45\1\0\3\45\22\0\1\45\1\u015f\36\45"+
    "\1\0\3\71\22\0\6\71\1\u0160\31\71\1\0\3\71"+
    "\22\0\23\71\1\u0161\14\71\1\0\3\71\22\0\4\71"+
    "\1\u0162\33\71\1\0\3\71\22\0\3\71\1\u0163\34\71"+
    "\1\0\3\71\22\0\1\71\1\u0164\36\71\1\4\3\5"+
    "\2\0\1\4\12\0\4\4\1\0\4\5\1\u0165\33\5"+
    "\1\4\3\5\2\0\1\4\12\0\4\4\1\0\5\5"+
    "\1\u0166\32\5\1\4\3\5\2\0\1\4\12\0\4\4"+
    "\1\0\6\5\1\u0167\31\5\1\0\3\45\22\0\4\45"+
    "\1\u0168\33\45\1\0\3\45\22\0\5\45\1\u0169\32\45"+
    "\1\0\3\45\22\0\6\45\1\u016a\31\45\1\0\3\71"+
    "\22\0\4\71\1\u016b\33\71\1\0\3\71\22\0\5\71"+
    "\1\u016c\32\71\1\0\3\71\22\0\6\71\1\u016d\31\71"+
    "\1\4\3\5\2\0\1\4\12\0\4\4\1\0\4\5"+
    "\1\u016e\33\5\1\0\3\45\22\0\4\45\1\u016f\33\45"+
    "\1\0\3\71\22\0\4\71\1\u0170\33\71";

  private static int [] zzUnpackTrans() {
    int [] result = new int[15984];
    int offset = 0;
    offset = zzUnpackTrans(ZZ_TRANS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackTrans(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /* error codes */
  private static final int ZZ_UNKNOWN_ERROR = 0;
  private static final int ZZ_NO_MATCH = 1;
  private static final int ZZ_PUSHBACK_2BIG = 2;
  private static final char[] EMPTY_BUFFER = new char[0];
  private static final int YYEOF = -1;
  private static java.io.Reader zzReader = null; // Fake

  /* error messages for the codes above */
  private static final String ZZ_ERROR_MSG[] = {
    "Unkown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state <code>aState</code>
   */
  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
    "\3\0\5\1\7\11\1\1\2\11\21\1\1\11\1\1"+
    "\1\11\41\1\1\11\1\1\2\11\21\1\2\11\36\1"+
    "\1\11\363\1";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[368];
    int offset = 0;
    offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAttribute(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /** the current state of the DFA */
  private int zzState;

  /** the current lexical state */
  private int zzLexicalState = YYINITIAL;

  /** this buffer contains the current text to be matched and is
      the source of the yytext() string */
  private CharSequence zzBuffer = "";

  /** this buffer may contains the current text array to be matched when it is cheap to acquire it */
  private char[] zzBufferArray;

  /** the textposition at the last accepting state */
  private int zzMarkedPos;

  /** the textposition at the last state to be included in yytext */
  private int zzPushbackPos;

  /** the current text position in the buffer */
  private int zzCurrentPos;

  /** startRead marks the beginning of the yytext() string in the buffer */
  private int zzStartRead;

  /** endRead marks the last character in the buffer, that has been read
      from input */
  private int zzEndRead;

  /**
   * zzAtBOL == true <=> the scanner is currently at the beginning of a line
   */
  private boolean zzAtBOL = true;

  /** zzAtEOF == true <=> the scanner is at the EOF */
  private boolean zzAtEOF;

  /** denotes if the user-EOF-code has already been executed */
  private boolean zzEOFDone;


  /**
   * Creates a new scanner
   *
   * @param   in  the java.io.Reader to read input from.
   */
  CoqLexer(java.io.Reader in) {
    this.zzReader = in;
  }


  /** 
   * Unpacks the compressed character translation table.
   *
   * @param packed   the packed character translation table
   * @return         the unpacked character translation table
   */
  private static char [] zzUnpackCMap(String packed) {
    char [] map = new char[0x10000];
    int i = 0;  /* index in packed string  */
    int j = 0;  /* index in unpacked array */
    while (i < 162) {
      int  count = packed.charAt(i++);
      char value = packed.charAt(i++);
      do map[j++] = value; while (--count > 0);
    }
    return map;
  }

  public final int getTokenStart(){
    return zzStartRead;
  }

  public final int getTokenEnd(){
    return getTokenStart() + yylength();
  }

  public void reset(CharSequence buffer, int start, int end,int initialState){
    zzBuffer = buffer;
    zzBufferArray = com.intellij.util.text.CharArrayUtil.fromSequenceWithoutCopying(buffer);
    zzCurrentPos = zzMarkedPos = zzStartRead = start;
    zzPushbackPos = 0;
    zzAtEOF  = false;
    zzAtBOL = true;
    zzEndRead = end;
    yybegin(initialState);
  }

  /**
   * Refills the input buffer.
   *
   * @return      <code>false</code>, iff there was new input.
   *
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  private boolean zzRefill() throws java.io.IOException {
    return true;
  }


  /**
   * Returns the current lexical state.
   */
  public final int yystate() {
    return zzLexicalState;
  }


  /**
   * Enters a new lexical state
   *
   * @param newState the new lexical state
   */
  public final void yybegin(int newState) {
    zzLexicalState = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   */
  public final CharSequence yytext() {
    return zzBuffer.subSequence(zzStartRead, zzMarkedPos);
  }


  /**
   * Returns the character at position <tt>pos</tt> from the
   * matched text.
   *
   * It is equivalent to yytext().charAt(pos), but faster
   *
   * @param pos the position of the character to fetch.
   *            A value from 0 to yylength()-1.
   *
   * @return the character at position pos
   */
  public final char yycharat(int pos) {
    return zzBufferArray != null ? zzBufferArray[zzStartRead+pos]:zzBuffer.charAt(zzStartRead+pos);
  }


  /**
   * Returns the length of the matched text region.
   */
  public final int yylength() {
    return zzMarkedPos-zzStartRead;
  }


  /**
   * Reports an error that occured while scanning.
   *
   * In a wellformed scanner (no or only correct usage of
   * yypushback(int) and a match-all fallback rule) this method
   * will only be called with things that "Can't Possibly Happen".
   * If this method is called, something is seriously wrong
   * (e.g. a JFlex bug producing a faulty scanner etc.).
   *
   * Usual syntax/scanner level error handling should be done
   * in error fallback rules.
   *
   * @param   errorCode  the code of the errormessage to display
   */
  private void zzScanError(int errorCode) {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    }
    catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }

    throw new Error(message);
  }


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * They will be read again by then next call of the scanning method
   *
   * @param number  the number of characters to be read again.
   *                This number must not be greater than yylength()!
   */
  public void yypushback(int number)  {
    if ( number > yylength() )
      zzScanError(ZZ_PUSHBACK_2BIG);

    zzMarkedPos -= number;
  }


  /**
   * Contains user EOF-code, which will be executed exactly once,
   * when the end of file is reached
   */
  private void zzDoEOF() {
    if (!zzEOFDone) {
      zzEOFDone = true;
    
    }
  }


  /**
   * Resumes scanning until the next regular expression is matched,
   * the end of input is encountered or an I/O-Error occurs.
   *
   * @return      the next token
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  public IElementType advance() throws java.io.IOException {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    CharSequence zzBufferL = zzBuffer;
    char[] zzBufferArrayL = zzBufferArray;
    char [] zzCMapL = ZZ_CMAP;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;

      zzState = ZZ_LEXSTATE[zzLexicalState];


      zzForAction: {
        while (true) {

          if (zzCurrentPosL < zzEndReadL)
            zzInput = (zzBufferArrayL != null ? zzBufferArrayL[zzCurrentPosL++] : zzBufferL.charAt(zzCurrentPosL++));
          else if (zzAtEOF) {
            zzInput = YYEOF;
            break zzForAction;
          }
          else {
            // store back cached positions
            zzCurrentPos  = zzCurrentPosL;
            zzMarkedPos   = zzMarkedPosL;
            boolean eof = zzRefill();
            // get translated positions and possibly new buffer
            zzCurrentPosL  = zzCurrentPos;
            zzMarkedPosL   = zzMarkedPos;
            zzBufferL      = zzBuffer;
            zzEndReadL     = zzEndRead;
            if (eof) {
              zzInput = YYEOF;
              break zzForAction;
            }
            else {
              zzInput = (zzBufferArrayL != null ? zzBufferArrayL[zzCurrentPosL++] : zzBufferL.charAt(zzCurrentPosL++));
            }
          }
          int zzNext = zzTransL[ zzRowMapL[zzState] + zzCMapL[zzInput] ];
          if (zzNext == -1) break zzForAction;
          zzState = zzNext;

          int zzAttributes = zzAttrL[zzState];
          if ( (zzAttributes & 1) == 1 ) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ( (zzAttributes & 8) == 8 ) break zzForAction;
          }

        }
      }

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
        case 27: 
          { yybegin(YYNAME); return CoqTypes.FACT_KW;
          }
        case 43: break;
        case 11: 
          { return CoqTypes.MID;
          }
        case 44: break;
        case 21: 
          { return CoqTypes.COMCLOSE;
          }
        case 45: break;
        case 34: 
          { yybegin(YYNAME); return CoqTypes.THEOREM_KW;
          }
        case 46: break;
        case 26: 
          { return CoqTypes.QED;
          }
        case 47: break;
        case 28: 
          { return CoqTypes.WITH;
          }
        case 48: break;
        case 25: 
          { return CoqTypes.END;
          }
        case 49: break;
        case 29: 
          { return CoqTypes.MATCH;
          }
        case 50: break;
        case 2: 
          { String str = String.valueOf(yytext());
                                                    IElementType element = CoqKeywords.getIElementType(str);
                                                    if (element != null) return element;
                                                    return CoqTypes.ID;
          }
        case 51: break;
        case 7: 
          { return CoqTypes.LSQBRACK;
          }
        case 52: break;
        case 6: 
          { return TokenType.WHITE_SPACE;
          }
        case 53: break;
        case 23: 
          { return CoqTypes.ARROW;
          }
        case 54: break;
        case 13: 
          { return CoqTypes.SEMICOLON;
          }
        case 55: break;
        case 32: 
          { yybegin(YYNAME); return CoqTypes.REMARK_KW;
          }
        case 56: break;
        case 37: 
          { return CoqTypes.ADMITTED;
          }
        case 57: break;
        case 39: 
          { yybegin(YYNAME);return CoqTypes.INDUCTIVE_KW;
          }
        case 58: break;
        case 41: 
          { yybegin(YYNAME);return CoqTypes.COFIXPOINT_KW;
          }
        case 59: break;
        case 24: 
          { return CoqTypes.BIGARROW;
          }
        case 60: break;
        case 31: 
          { return CoqTypes.PROOF_KW;
          }
        case 61: break;
        case 9: 
          { return CoqTypes.LBRACK;
          }
        case 62: break;
        case 5: 
          { return CoqTypes.RPAR;
          }
        case 63: break;
        case 10: 
          { return CoqTypes.RBRACK;
          }
        case 64: break;
        case 17: 
          { return CoqTypes.NUMBER;
          }
        case 65: break;
        case 19: 
          { return CoqTypes.FDOT;
          }
        case 66: break;
        case 3: 
          { return CoqTypes.DOT;
          }
        case 67: break;
        case 14: 
          { return CoqTypes.COMMA;
          }
        case 68: break;
        case 33: 
          { return CoqTypes.DEFINED;
          }
        case 69: break;
        case 42: 
          { yybegin(YYNAME); return CoqTypes.PROPOSITION_KW;
          }
        case 70: break;
        case 40: 
          { yybegin(YYNAME); return CoqTypes.DEFINITION_KW;
          }
        case 71: break;
        case 12: 
          { return CoqTypes.COLON;
          }
        case 72: break;
        case 16: 
          { return TokenType.BAD_CHARACTER;
          }
        case 73: break;
        case 22: 
          { return CoqTypes.ASSIGN;
          }
        case 74: break;
        case 36: 
          { yybegin(YYNAME);return CoqTypes.FIXPOINT_KW;
          }
        case 75: break;
        case 8: 
          { return CoqTypes.RSQBRACK;
          }
        case 76: break;
        case 18: 
          { yybegin(YYINITIAL); return CoqTypes.ID;
          }
        case 77: break;
        case 35: 
          { yybegin(YYNAME); return CoqTypes.EXAMPLE_KW;
          }
        case 78: break;
        case 30: 
          { yybegin(YYNAME); return CoqTypes.LEMMA_KW;
          }
        case 79: break;
        case 38: 
          { yybegin(YYNAME); return CoqTypes.COROLLARY_KW;
          }
        case 80: break;
        case 20: 
          { return CoqTypes.COMOPEN;
          }
        case 81: break;
        case 1: 
          { return CoqTypes.ANYBUTDOT;
          }
        case 82: break;
        case 15: 
          { return CoqTypes.COMMENT;
          }
        case 83: break;
        case 4: 
          { return CoqTypes.LPAR;
          }
        case 84: break;
        default:
          if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
            zzAtEOF = true;
            zzDoEOF();
            return null;
          }
          else {
            zzScanError(ZZ_NO_MATCH);
          }
      }
    }
  }


}
