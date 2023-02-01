package dev.teamtesseract.molang.namespaces;

import dev.teamtesseract.molang.MolangResult;
import net.minecraft.util.math.MathHelper;

import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class MolangMath extends MolangNamespace {

    private static final Random RNG = new Random();

    private static final Function<List<MolangResult>, Boolean> NUM_CHECK = (l) -> l.stream().allMatch(MolangResult::hasFloat);
    private static final Function<List<MolangResult>, Boolean> LERP_CHECK = (l) -> {
        if(!l.stream().allMatch(MolangResult::hasFloat))
            return false;
        float delta = l.get(2).getFloat();
        return !(delta > 1.0F) && !(delta < 0.0F);
    };

    public MolangMath() {
        super("math");

        registerFunction("cos", 1, NUM_CHECK, (l, ctx) -> MolangResult.ofFloat(MathHelper.cos(l.get(0).getFloat())));
        registerFunction("acos", 1, NUM_CHECK, (l, ctx) -> MolangResult.ofFloat(Math.acos(l.get(0).getFloat())));
        registerFunction("sin", 1, NUM_CHECK, (l, ctx) -> MolangResult.ofFloat(MathHelper.sin(l.get(0).getFloat())));
        registerFunction("asin", 1, NUM_CHECK, (l, ctx) -> MolangResult.ofFloat(Math.asin(l.get(0).getFloat())));
        registerFunction("atan", 1, NUM_CHECK, (l, ctx) -> MolangResult.ofFloat(Math.atan(l.get(0).getFloat())));
        registerFunction("atan2", 2, NUM_CHECK, (l, ctx) -> MolangResult.ofFloat(MathHelper.atan2(l.get(0).getFloat(), l.get(1).getFloat())));

        registerFunction("exp", 1, NUM_CHECK, (l, ctx) -> MolangResult.ofFloat(Math.exp(l.get(0).getFloat())));
        registerFunction("ceil", 1, NUM_CHECK, (l, ctx) -> MolangResult.ofFloat(MathHelper.ceil(l.get(0).getFloat())));
        registerFunction("floor", 1, NUM_CHECK, (l, ctx) -> MolangResult.ofFloat(MathHelper.floor(l.get(0).getFloat())));
        registerFunction("abs", 1, NUM_CHECK, (l, ctx) -> MolangResult.ofFloat(MathHelper.abs(l.get(0).getFloat())));
        registerFunction("ln", 1, NUM_CHECK, (l, ctx) -> MolangResult.ofFloat(Math.log(l.get(0).getFloat())));
        registerFunction("mod", 2, NUM_CHECK, (l, ctx) -> MolangResult.ofFloat(l.get(0).getFloat() % l.get(1).getFloat()));
        registerFunction("pow", 2, NUM_CHECK, (l, ctx) -> MolangResult.ofFloat(Math.pow(l.get(0).getFloat(), l.get(1).getFloat())));
        registerFunction("sqrt", 1, NUM_CHECK, (l, ctx) -> MolangResult.ofFloat(MathHelper.sqrt(l.get(0).getFloat())));

        registerFunction("round", 1, NUM_CHECK, (l, ctx) -> MolangResult.ofFloat(Math.round(l.get(0).getFloat())));
        registerFunction("trunc", 1, NUM_CHECK, (l, ctx) -> MolangResult.ofFloat((int)l.get(0).getFloat()));
        registerFunction("min_angle", 1, NUM_CHECK, (l, ctx) -> MolangResult.ofFloat(MathHelper.wrapDegrees(l.get(0).getFloat())));
        registerFunction("lerp", 3, LERP_CHECK, (l, ctx) -> MolangResult.ofFloat(MathHelper.lerp(l.get(2).getFloat(), l.get(0).getFloat(), l.get(1).getFloat())));
        registerFunction("lerprotate", 3, LERP_CHECK, (l, ctx) -> MolangResult.ofFloat(MathHelper.lerpAngleDegrees(l.get(2).getFloat(), l.get(0).getFloat(), l.get(1).getFloat())));
        registerFunction("clamp", 3, NUM_CHECK, (l, ctx) -> MolangResult.ofFloat(MathHelper.clamp(l.get(0).getFloat(), l.get(1).getFloat(), l.get(2).getFloat())));
        registerFunction("max", 2, NUM_CHECK, (l, ctx) -> MolangResult.ofFloat(Math.max(l.get(0).getFloat(), l.get(1).getFloat())));
        registerFunction("min", 2, NUM_CHECK, (l, ctx) -> MolangResult.ofFloat(Math.min(l.get(0).getFloat(), l.get(1).getFloat())));
        registerFunction("hermite_blend", 1, NUM_CHECK, (l, ctx) -> {
            float value = l.get(0).getFloat();
            return MolangResult.ofFloat(Math.pow(3 * value, 2) - Math.pow(2 * value, 3));
        });

        registerFunction("random", 2, NUM_CHECK, (l, ctx) -> MolangResult.ofFloat(RNG.nextFloat(l.get(0).getFloat(), l.get(1).getFloat())));
        registerFunction("random_integer", 2, NUM_CHECK, (l, ctx) -> MolangResult.ofFloat(RNG.nextFloat((int)l.get(0).getFloat(), (int)l.get(1).getFloat())));
        registerFunction("die_roll", 3, NUM_CHECK, (l, ctx) -> {
            int sums = (int)l.get(0).getFloat();
            float min = l.get(1).getFloat();
            float max = l.get(2).getFloat();
            float value = 0.0F;
            for(int i = 0; i < sums; i++)
                value += new Random().nextFloat(min, max);
            return MolangResult.ofFloat(value);
        });
        registerFunction("die_roll_integer", 3, NUM_CHECK, (l, ctx) -> {
            int sums = (int)l.get(0).getFloat();
            int min = (int)l.get(1).getFloat();
            int max = (int)l.get(2).getFloat();
            Random r = new Random();
            int value = 0;
            for(int i = 0; i < sums; i++)
                value += RNG.nextInt(min, max);
            return MolangResult.ofFloat(value);
        });

        registerFunction("pi", (l, ctx) -> MolangResult.ofFloat(Math.PI));
    }
}
