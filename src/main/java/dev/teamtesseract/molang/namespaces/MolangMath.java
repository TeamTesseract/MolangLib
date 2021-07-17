package dev.teamtesseract.molang.namespaces;

import dev.teamtesseract.molang.MolangResult;
import net.minecraft.util.math.MathHelper;

import java.util.List;
import java.util.function.Function;

public class MolangMath extends MolangNamespace {

    private static final Function<List<MolangResult>, Boolean> NUM_CHECK = (l) -> l.stream().allMatch(MolangResult::hasFloat);

    public MolangMath() {
        super("math");

        registerFunction("abs", 1, NUM_CHECK, (l, ctx) -> MolangResult.ofFloat(MathHelper.abs(l.get(0).getFloat())));
        registerFunction("acos", 1, NUM_CHECK, (l, ctx) -> MolangResult.ofFloat((float)Math.acos(l.get(0).getFloat())));
        registerFunction("asin", 1, NUM_CHECK, (l, ctx) -> MolangResult.ofFloat((float)Math.asin(l.get(0).getFloat())));
        registerFunction("atan", 1, NUM_CHECK, (l, ctx) -> MolangResult.ofFloat((float)Math.atan(l.get(0).getFloat())));
        registerFunction("atan2", 2, NUM_CHECK, (l, ctx) -> MolangResult.ofFloat((float)MathHelper.atan2(l.get(0).getFloat(), l.get(1).getFloat())));
        registerFunction("ceil", 1, NUM_CHECK, (l, ctx) -> MolangResult.ofFloat(MathHelper.ceil(l.get(0).getFloat())));
        registerFunction("clamp", 3, NUM_CHECK, (l, ctx) -> MolangResult.ofFloat(MathHelper.clamp(l.get(0).getFloat(), l.get(1).getFloat(), l.get(2).getFloat())));


        registerFunction("pi", (l, ctx) -> MolangResult.ofFloat((float)Math.PI));
    }
}
