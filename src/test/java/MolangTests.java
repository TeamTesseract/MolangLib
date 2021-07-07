import dev.teamtesseract.molang.MolangParser;
import dev.teamtesseract.molang.elements.MolangExpression;
import org.junit.jupiter.api.Test;

public class MolangTests {

    private static final String TEST_STRING = "math.abs(q.anim_time / 38.0 + 44 - 11.926) * (variable.rotation_scale + v.x) * query.life_time + query.head_rotation('weird_af string')";

    @Test
    public void testMolangParser() {
        MolangParser.init();

        MolangExpression test = new MolangExpression(MolangParser.parseLine(TEST_STRING));
        System.out.println(test);
    }
}
