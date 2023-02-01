package dev.teamtesseract.molang;

import dev.teamtesseract.molang.context.DummyContext;
import dev.teamtesseract.molang.elements.MolangExpression;
import org.junit.jupiter.api.Test;

public class MolangTests {

    private static final String TEST_STRING = "math.abs(q.anim_time / 38.0 + 44 - 11.926) * (variable.rotation_scale + v.x) * query.life_time + query.head_rotation('weird_af string')";

    public MolangTests() {
        MolangParser.init();
    }

    @Test
    public void testMolangParser() {
        MolangExpression test = new MolangExpression(MolangParser.parseLine(TEST_STRING));
        System.out.println(test);
    }

    @Test
    public void operatorTest() {
        test("(12 + 18 - 15) * 3 / 5", 9.0F, true, "");
        test("1 - 2", -1.0F, false, "");
        //test("math.pow((2 - 3 + 5) / 2, 2) + 12 * 4 - 2", 50.0F, true, "");
    }

    private void test(String expression, float expectedFloat, boolean expectedBool, String expectedString) {
        MolangExpression test = new MolangExpression(MolangParser.parseLine(expression));
        System.out.println("---+=== [ " + expression + " ] ===+---");
        System.out.println(test);

        MolangResult result = test.process(new DummyContext());
        System.out.println(result);

        if(expectedFloat == result.getFloat() && result.getBoolean() == expectedBool && result.getString().equals(expectedString))
            System.out.printf("---+=== Correct! %.2f | %s | \"%s\" ===+---%n", result.getFloat(), result.getBoolean() ? "True" : "False", result.getString());
        else {
            if(expectedFloat != result.getFloat())
                System.out.printf("---+=== Float value does not match expectation [%.2f != %.2f] ===+---%n", result.getFloat(), expectedFloat);
            if(expectedBool != result.getBoolean())
                System.out.printf("---+=== Boolean value does not match expectation [%s != %s] ===+---%n", result.getBoolean(), expectedBool);
            if(!expectedString.equals(result.getString()))
                System.out.printf("---+=== String value does not match expectation [%s != %s] ===+---%n", result.getString(), expectedString);
        }
        System.out.println();
    }
}
