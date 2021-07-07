package dev.teamtesseract.molang.values;

import dev.teamtesseract.molang.MolangResult;
import dev.teamtesseract.molang.context.MolangContext;
import dev.teamtesseract.molang.elements.MolangValue;

public class MolangString extends MolangValue<String> {

    public static final MolangString DUMMY = new MolangString("DUMMY");

    public MolangString(String s) {
        super(s, false);
    }

    public MolangString(String s, boolean parse) {
        super(s, parse);
    }

    @Override
    public String parse(String value) {
        return value.substring(1, value.length() - 1);
    }

    @Override
    public boolean isValidToken(String s) {
        return s.startsWith("\"") && s.endsWith("\"") && s.length() >= 2;
    }

    @Override
    public MolangResult process(MolangContext ctx) {
        return MolangResult.ofString(this.value);
    }
}
