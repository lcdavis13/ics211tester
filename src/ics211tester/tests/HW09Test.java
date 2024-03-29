package ics211tester.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.Deque;
import java.util.Vector;
import java.util.Queue;
import java.util.ArrayDeque;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import static org.junit.jupiter.api.Assertions.*;

import edu.ics211.h09.HW09;

public class HW09Test {
    
    @Test
    @DisplayName("basic valid string not being marked valid")
    void AAtestBasicValid() {
        boolean success = false;
    
        try {
            BABtestRoundValid();
            success = true;
        } catch (AssertionError ignore) {}
        
        try {
            BBBtestSquareValid();
            success = true;
        } catch (AssertionError ignore) {}
        
        try {
            BCBtestCurlyValid();
            success = true;
        } catch (AssertionError ignore) {}
        
        try {
            BDBtestAngleValid();
            success = true;
        } catch (AssertionError ignore) {}
        
        assertTrue(success);
    }
    
    @Test
    @DisplayName("more pops than pushes is invalid")
    void ABtestanyoverpop() {
        boolean success = false;

        try {
            BACtestRoundOverpop();
            success = true;
        } catch (AssertionError ignore) {}
        
        try {
            BBCtestSquareOverpop();
            success = true;
        } catch (AssertionError ignore) {}
        
        try {
            BCCtestCurlyOverpop();
            success = true;
        } catch (AssertionError ignore) {}
        
        try {
            BDCtestAngleOverpop();
            success = true;
        } catch (AssertionError ignore) {}
        
        assertTrue(success);
    }

    @Test
    @DisplayName("more pushes than pops is invalid")
    void ACtestanyunderpop() {
        boolean success = false;

        try {
            BADtestRoundUnderpop();
            success = true;
        } catch (AssertionError ignore) {}
        
        try {
            BBDtestSquareUnderpop();
            success = true;
        } catch (AssertionError ignore) {}
        
        try {
            BCDtestCurlyUnderpop();
            success = true;
        } catch (AssertionError ignore) {}
        
        try {
            BDDtestAngleUnderpop();
            success = true;
        } catch (AssertionError ignore) {}
        
        assertTrue(success);
    }

    @Test
    @DisplayName("ROUND not working")
    void BAAtestRoundIssue() {
        BABtestRoundValid();
        BACtestRoundOverpop();
        BADtestRoundUnderpop();
    }

    @Test
    void BABtestRoundValid() {
        HW09 hw = new HW09();
        String test1 = "a(b)c";
        assertTrue(hw.validSyntax(test1));
    }

    @Test
    void BACtestRoundOverpop() {
        HW09 hw = new HW09();
        String test1 = "a(b))c";
        assertFalse(hw.validSyntax(test1));
    }

    @Test
    void BADtestRoundUnderpop() {
        HW09 hw = new HW09();
        String test1 = "a((b)c";
        assertFalse(hw.validSyntax(test1));
    }

    @Test
    @DisplayName("SQUARE not working")
    void BBAtestSquareIssue() {
        BBBtestSquareValid();
        BBCtestSquareOverpop();
        BBDtestSquareUnderpop();
    }

    @Test
    void BBBtestSquareValid() {
        HW09 hw = new HW09();
        String test1 = "a[b]c";
        assertTrue(hw.validSyntax(test1));
    }

    @Test
    void BBCtestSquareOverpop() {
        HW09 hw = new HW09();
        String test1 = "a[b]]c";
        assertFalse(hw.validSyntax(test1));
    }

    @Test
    void BBDtestSquareUnderpop() {
        HW09 hw = new HW09();
        String test1 = "a[[b]c";
        assertFalse(hw.validSyntax(test1));
    }

    @Test
    @DisplayName("CURLY not working")
    void BCAtestCurlyIssue() {
        BCBtestCurlyValid();
        BCCtestCurlyOverpop();
        BCDtestCurlyUnderpop();
    }

    @Test
    void BCBtestCurlyValid() {
        HW09 hw = new HW09();
        String test1 = "a{b}c";
        assertTrue(hw.validSyntax(test1));
    }

    @Test
    void BCCtestCurlyOverpop

() {
        HW09 hw = new HW09();
        String test1 = "a{b}}c";
        assertFalse(hw.validSyntax(test1));
    }

    @Test
    void BCDtestCurlyUnderpop() {
        HW09 hw = new HW09();
        String test1 = "a{{b}c";
        assertFalse(hw.validSyntax(test1));
    }

    @Test
    @DisplayName("ANGLE not working")
    void BDAtestAngleIssue() {
        BDBtestAngleValid();
        BDCtestAngleOverpop();
        BDDtestAngleUnderpop();
    }

    @Test
    void BDBtestAngleValid() {
        HW09 hw = new HW09();
        String test1 = "a<b>c";
        assertTrue(hw.validSyntax(test1));
    }

    @Test
    void BDCtestAngleOverpop() {
        HW09 hw = new HW09();
        String test1 = "a<b>>c";
        assertFalse(hw.validSyntax(test1));
    }

    @Test
    void BDDtestAngleUnderpop() {
        HW09 hw = new HW09();
        String test1 = "a<<b>c";
        assertFalse(hw.validSyntax(test1));
    }
    
    @Test
    @DisplayName("mismatched types of parentheses should be invalid")
    void CATestMismatchIssue() {
        CBTestMismatch12();
        CBTestMismatch13();
        CBTestMismatch14();
        CBTestMismatch21();
        CBTestMismatch23();
        CBTestMismatch24();
        CBTestMismatch31();
        CBTestMismatch32();
        CBTestMismatch34();
        CBTestMismatch41();
        CBTestMismatch42();
        CBTestMismatch43();
    }
    
    @Test
    void CBTestMismatch12() {
        HW09 hw = new HW09();
        String test = "a(b]c";
        assertFalse(hw.validSyntax(test));
    }

    @Test
    void CBTestMismatch13() {
        HW09 hw = new HW09();
        String test = "a(b}c";
        assertFalse(hw.validSyntax(test));
    }

    @Test
    void CBTestMismatch14() {
        HW09 hw = new HW09();
        String test = "a(b>c";
        assertFalse(hw.validSyntax(test));
    }

    @Test
    void CBTestMismatch21() {
        HW09 hw = new HW09();
        String test = "a[b)c";
        assertFalse(hw.validSyntax(test));
    }

    @Test
    void CBTestMismatch23() {
        HW09 hw = new HW09();
        String test = "a[b}c";
        assertFalse(hw.validSyntax(test));
    }

    @Test
    void CBTestMismatch24() {
        HW09 hw = new HW09();
        String test = "a[b>c";
        assertFalse(hw.validSyntax(test));
    }

    @Test
    void CBTestMismatch31() {
        HW09 hw = new HW09();
        String test = "a{b)c";
        assertFalse(hw.validSyntax(test));
    }

    @Test
    void CBTestMismatch32() {
        HW09 hw = new HW09();
        String test = "a{b]c";
        assertFalse(hw.validSyntax(test));
    }

    @Test
    void CBTestMismatch34() {
        HW09 hw = new HW09();
        String test = "a{b>c";
        assertFalse(hw.validSyntax(test));
    }

    @Test
    void CBTestMismatch41() {
        HW09 hw = new HW09();
        String test = "a<b)c";
        assertFalse(hw.validSyntax(test));
    }

    @Test
    void CBTestMismatch42() {
        HW09 hw = new HW09();
        String test = "a<b]c";
        assertFalse(hw.validSyntax(test));
    }

    @Test
    void CBTestMismatch43() {
        HW09 hw = new HW09();
        String test = "a<b}c";
        assertFalse(hw.validSyntax(test));
    }

    @Test
    @DisplayName("empty string not being marked valid")
    void DAtestemptyvalid() {
        HW09 hw = new HW09();
        String test1 = "";
        assertTrue(hw.validSyntax(test1));
    }

    @Test
    @DisplayName("strings with no parentheses not being marked valid")
    void DBtestnoparensvalid() {
        HW09 hw = new HW09();
        String test1 = "abc";
        assertTrue(hw.validSyntax(test1));
    }

    @Test
    @DisplayName("strings with parentheses at the start not working")
    void DCAtestfirstissue() {
        DCBtestfirstvalid();
        DCDtestfirstoverpop();
        DDDtestfirstunderpop();
    }

    @Test
    void DCBtestfirstvalid() {
        HW09 hw = new HW09();
        String test1 = "(ab)c";
        assertTrue(hw.validSyntax(test1));
    }
    
    @Test
    void DCDtestfirstoverpop() {
        HW09 hw = new HW09();
        String test1 = ")a(b)c";
        assertFalse(hw.validSyntax(test1));
    }

    @Test
    void DDDtestfirstunderpop() {
        HW09 hw = new HW09();
        String test1 = "(a(b)c";
        assertFalse(hw.validSyntax(test1));
    }

    @Test
    @DisplayName("strings with parentheses at the end not working")
    void DDAtestlastissue() {
        DDBtestlastvalid();
        DDDtestlastoverpop();
        DEDtestlastunderpop();
    }

    @Test
    void DDBtestlastvalid() {
        HW09 hw = new HW09();
        String test1 = "a(bc)";
        assertTrue(hw.validSyntax(test1));
    }

    @Test
    void DDDtestlastoverpop() {
        HW09 hw = new HW09();
        String test1 = "a(b))";
        assertFalse(hw.validSyntax(test1));
    }

    @Test
    void DEDtestlastunderpop() {
        HW09 hw = new HW09();
        String test1 = "a(b)c(";
        assertFalse(hw.validSyntax(test1));
    }

    @Test
    @DisplayName("long strings not working")
    void EAtestlongstringany() {
        EBtestlongvalid();
        ECtestlongmismatch();
        EDtestlongoverpop();
        EEtestlongunderpop();
    }

    @Test
    void EBtestlongvalid() {
        HW09 hw = new HW09();
        String test1 = "a(bcdefghijklmnopqrstuvwxy)z";
        assertTrue(hw.validSyntax(test1));
    }

    @Test
    void ECtestlongmismatch() {
        String test12 = "a(bcdefghijklmnopqrstuvwxy]z";
        String test13 = "a(bcdefghijklmnopqrstuvwxy}z";
        String test14 = "a(bcdefghijklmnopqrstuvwxy>z";
        
        String test21 = "a[bcdefghijklmnopqrstuvwxy)z";
        String test23 = "a[bcdefghijklmnopqrstuvwxy}z";
        String test24 = "a[bcdefghijklmnopqrstuvwxy>z";
        
        String test31 = "a{bcdefghijklmnopqrstuvwxy)z";
        String test32 = "a{bcdefghijklmnopqrstuvwxy]z";
        String test34 = "a{bcdefghijklmnopqrstuvwxy>z";
        
        String test41 = "a<bcdefghijklmnopqrstuvwxy)z";
        String test42 = "a<bcdefghijklmnopqrstuvwxy]z";
        String test43 = "a<bcdefghijklmnopqrstuvwxy}z";
        
        assertFalse(
            new HW09().validSyntax(test12) || new HW09().validSyntax(test13) || new HW09().validSyntax(test14) ||
            new HW09().validSyntax(test21) || new HW09().validSyntax(test23) || new HW09().validSyntax(test24) ||
            new HW09().validSyntax(test31) || new HW09().validSyntax(test32) || new HW09().validSyntax(test34) ||
            new HW09().validSyntax(test41) || new HW09().validSyntax(test42) || new HW09().validSyntax(test43)
        );
    }

    @Test
    void EDtestlongoverpop() {
        HW09 hw = new HW09();
        String test1 = "a(b)cdefghijklmnopqrstuvwxy)z";
        assertFalse(hw.validSyntax(test1));
    }

    @Test
    void EEtestlongunderpop() {
        HW09 hw = new HW09();
        String test1 = "a(b)cdefghijklmnopqrstuvwxy(z";
        assertFalse(hw.validSyntax(test1));
    }

    @Test
    @DisplayName("strings with multiple sets of parentheses such as (()) or ()() not working")
    void FAtestparallelornestedissue() {
        FBAtestparallelvalid();
        FBBtestparallelmismatch();
        FBCtestparalleloverpop();
        FBDtestparallelunderpop();
        FCAtestnestedvalid();
        FCBtestnestedmismatch();
        FCCtestnestedoverpop();
        FCDtestnestedunderpop();
        FDAtestnestparavalid();
        FDBtestnestparamismatch();
        FDCtestnestparaoverpop();
        FDDtestnestparaunderpop();
    }

    @Test
    void FBAtestparallelvalid() {
        HW09 hw = new HW09();
        String test1 = "a(b)c(d)e";
        assertTrue(hw.validSyntax(test1));
    }

    @Test
    void FBBtestparallelmismatch() {
        String test12 = "a(b)c(d]e";
        String test13 = "a(b)c(d}e";
        String test14 = "a(b)c(d>e";
        
        String test21 = "a[b]c[d)e";
        String test23 = "a[b]c[d}e";
        String test24 = "a[b]c[d>e";
        
        String test31 = "a{b}c{d)e";
        String test32 = "a{b}c{d]e";
        String test34 = "a{b}c{d>e";
        
        String test41 = "a<b>c<d)e";
        String test42 = "a<b>c<d]e";
        String test43 = "a<b>c<d}e";
        
        assertFalse(
            new HW09().validSyntax(test12) || new HW09().validSyntax(test13) || new HW09().validSyntax(test14) ||
            new HW09().validSyntax(test21) || new HW09().validSyntax(test23) || new HW09().validSyntax(test24) ||
            new HW09().validSyntax(test31) || new HW09().validSyntax(test32) || new HW09().validSyntax(test34) ||
            new HW09().validSyntax(test41) || new HW09().validSyntax(test42) || new HW09().validSyntax(test43)
        );
    }

    @Test
    void FBCtestparalleloverpop() {
        HW09 hw = new HW09();
        String test1 = "a(b)c(d))e";
        assertFalse(hw.validSyntax(test1));
    }

    @Test
    void FBDtestparallelunderpop() {
        HW09 hw = new HW09();
        String test1 = "a((b)c(d)e";
        assertFalse(hw.validSyntax(test1));
    }

    @Test
    void FCAtestnestedvalid() {
        HW09 hw = new HW09();
        String test1 = "a(b(c)d)e";
        assertTrue(hw.validSyntax(test1));
    }

    @Test
    void FCBtestnestedmismatch() {
        String test12 = "a(b(c]d)e";
        String test13 = "a(b(c}d)e";
        String test14 = "a(b(c>d)e";
        
        String test21 = "a[b[c)d]e";
        String test23 = "a[b[c}d]e";
        String test24 = "a[b[c>d]e";
        
        String test31 = "a{b{c)d}e";
        String test32 = "a{b{c]d}e";
        String test34 = "a{b{c>d}e";
        
        String test41 = "a<b<c)d>e";
        String test42 = "a<b<c]d>e";
        String test43 = "a<b<c}d>e";
        
        assertFalse(
            new HW09().validSyntax(test12) || new HW09().validSyntax(test13) || new HW09().validSyntax(test14) ||
            new HW09().validSyntax(test21) || new HW09().validSyntax(test23) || new HW09().validSyntax(test24) ||
            new HW09().validSyntax(test31) || new HW09().validSyntax(test32) || new HW09().validSyntax(test34) ||
            new HW09().validSyntax(test41) || new HW09().validSyntax(test42) || new HW09().validSyntax(test43)
        );
    }

    @Test
    void FCCtestnestedoverpop() {
        HW09 hw = new HW09();
        String test1 = "a(b(c)d)))e";
        assertFalse(hw.validSyntax(test1));
    }

    @Test
    void FCDtestnestedunderpop() {
        HW09 hw = new HW09();
        String test1 = "a((b(c)d)e";
        assertFalse(hw.validSyntax(test1));
    }

    @Test
    void FDAtestnestparavalid() {
        HW09 hw = new HW09();
        String test1 = "a(b(c)d(e)f)g";
        assertTrue(hw.validSyntax(test1));
    }

    @Test
    void FDBtestnestparamismatch() {
        String test12 = "a(b(c)b(c]d)e";
        String test13 = "a(b(c)b(c}d)e";
        String test14 = "a(b(c)b(c>d)e";
        
        String test21 = "a[b[c]b[c)d]e";
        String test23 = "a[b[c]b[c}d]e";
        String test24 = "a[b[c]b[c>d]e";
        
        String test31 = "a{b{c}b{c)d}e";
        String test32 = "a{b{c}b{c]d}e";
        String test34 = "a{b{c}b{c>d}e";
        
        String test41 = "a<b<c>b<c)d>e";
        String test42 = "a<b<c>b<c]d>e";
        String test43 = "a<b<c>b<c}d>e";
        
        assertFalse(
            new HW09().validSyntax(test12) || new HW09().validSyntax(test13) || new HW09().validSyntax(test14) ||
            new HW09().validSyntax(test21) || new HW09().validSyntax(test23) || new HW09().validSyntax(test24) ||
            new HW09().validSyntax(test31) || new HW09().validSyntax(test32) || new HW09().validSyntax(test34) ||
            new HW09().validSyntax(test41) || new HW09().validSyntax(test42) || new HW09().validSyntax(test43)
        );
    }

    @Test
    void FDCtestnestparaoverpop() {
        HW09 hw = new HW09();
        String test1 = "a(b(c)d)))e";
        assertFalse(hw.validSyntax(test1));
    }

    @Test
    void FDDtestnestparaunderpop() {
        HW09 hw = new HW09();
        String test1 = "a((b(c)d)e";
        assertFalse(hw.validSyntax(test1));
    }
    
    @Test
    @DisplayName("strings with intersecting parentheses such as ([)] should be invalid")
    void GtestIntersectingParens()
    {
        HW09 hw = new HW09();
        String test1 = "a(b[c)d]e";
        assertFalse(hw.validSyntax(test1));
    }
    
    @Test
    @DisplayName("multiple successive calls to validSyntax should not alter the results")
    void HtestRepeatedCalls()
    {
        HW09 hw = new HW09();
        
        String test1 = "a(b(c)d)e";
        String test2 = "a(b(c)de";
        String test3 = "a(b(c)d)e";
        String test4 = "a(b(c)d))e";
        String test5 = "a(b(c)d)e";

        assertTrue(hw.validSyntax(test1));
        assertFalse(hw.validSyntax(test2));
        assertTrue(hw.validSyntax(test3));
        assertFalse(hw.validSyntax(test4));
        assertTrue(hw.validSyntax(test5));
    }
    
    
    
    //test main method without args
    
    //test main method with args

    @Test
    @DisplayName("Uses java standard library class or list")
    void __testNoForbiddenDependencies() {
        Field[] fields = HW09.class.getDeclaredFields();
        assertFalse(
                isForbiddenClass(HW09.class),
                "Class HW09 extends/implements a forbidden type"
            );
        for (Field field : fields) {
            Class<?> fieldType = field.getType();
            assertFalse(
                isForbiddenClass(fieldType),
                "Field " + field.getName() + " is of a forbidden type or extends/implements a forbidden type"
            );
            System.out.println(field.getName() + " passed test and is of type " + fieldType.getName());
        }
    }

    private boolean isForbiddenClass(Class<?> clazz) {
        if (clazz == null) {
            return false;
        }
        return ArrayList.class.isAssignableFrom(clazz) ||
               LinkedList.class.isAssignableFrom(clazz) ||
               List.class.isAssignableFrom(clazz) ||
               Stack.class.isAssignableFrom(clazz) ||
               Deque.class.isAssignableFrom(clazz) ||
               Vector.class.isAssignableFrom(clazz) ||
               Queue.class.isAssignableFrom(clazz) ||
               ArrayDeque.class.isAssignableFrom(clazz) ||
               BlockingDeque.class.isAssignableFrom(clazz) ||
               BlockingQueue.class.isAssignableFrom(clazz) ||
               isForbiddenClass(clazz.getSuperclass());
    }
}
