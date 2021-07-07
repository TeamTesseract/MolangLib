package dev.teamtesseract.molang.values;

import dev.teamtesseract.molang.MolangResult;
import dev.teamtesseract.molang.context.MolangContext;
import dev.teamtesseract.molang.elements.MolangValue;

public class MolangUnknown extends MolangValue<String> {

    public MolangUnknown(String value) {
        super(value, false);
    }

    @Override
    public String parse(String value) {
        return value;
    }

    @Override
    public boolean isValidToken(String s) {
        return false;
    }

    @Override
    public MolangResult process(MolangContext ctx) {
        return MolangResult.ZERO;
    }

    @Override
    public String toString() {
        return "Unknown[" + value + "]";
    }
}
