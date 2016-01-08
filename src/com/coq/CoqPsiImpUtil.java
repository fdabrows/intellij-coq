package com.coq;

import com.coq.psi.*;
import com.intellij.lang.ASTNode;

/**
 * Created by dabrowski on 27/12/2015.
 */
public class CoqPsiImpUtil {


    public static String getKey(CoqFile element) {
        return element.getName();
    }

    public static String getKey(CoqAssertion element) {
        ASTNode valueNode = element.getNode().findChildByType(CoqTypes.ASSERTION_KEYWORD);
        if (valueNode != null) {
            return valueNode.getText();
        } else {
            return null;
        }
    }

    public static String getValue(CoqAssertion element) {
        ASTNode valueNode = element.getNode().findChildByType(CoqTypes.ID);
        if (valueNode != null) {
            return valueNode.getText();
        } else {
            return null;
        }
    }

    public static String getName(CoqFixBody element) {
        ASTNode valueNode = element.getNode().findChildByType(CoqTypes.ID);
        if (valueNode != null) {
            return valueNode.getText();
        } else {
            return null;
        }
    }


    public static String getName(CoqIndBody element) {
        ASTNode valueNode = element.getNode().findChildByType(CoqTypes.ID);
        if (valueNode != null) {
            return valueNode.getText();
        } else {
            return null;
        }
    }

    public static String getName(CoqCofixpoint element) {
        ASTNode valueNode = element.getNode().findChildByType(CoqTypes.ID);
        if (valueNode != null) {
            return valueNode.getText();
        } else {
            return null;
        }
    }

    public static String getName(CoqDefinition element) {
        ASTNode valueNode = element.getNode().findChildByType(CoqTypes.ID);
        if (valueNode != null) {
            return valueNode.getText();
        } else {
            return null;
        }
    }

    public static String getValue(CoqFile element) {
        ASTNode valueNode = element.getNode().findChildByType(CoqTypes.ID);
        if (valueNode != null) {
            return valueNode.getText();
        } else {
            return null;
        }
    }
}
