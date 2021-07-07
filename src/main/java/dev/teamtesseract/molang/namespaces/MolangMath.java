package dev.teamtesseract.molang.namespaces;

import dev.teamtesseract.molang.MolangResult;
import dev.teamtesseract.molang.values.MolangNumber;

public class MolangMath extends MolangNamespace {

    public MolangMath() {
        super("math");
        registerFunction("abs", 1, (l) -> l.get(0) instanceof MolangNumber, (l, ctx) -> MolangResult.ofFloat(Math.abs(l.get(0).process(ctx).getFloat())));
    }
}
