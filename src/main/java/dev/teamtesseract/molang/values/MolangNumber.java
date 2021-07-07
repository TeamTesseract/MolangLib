package dev.teamtesseract.molang.values;

import dev.teamtesseract.molang.MolangResult;
import dev.teamtesseract.molang.context.MolangContext;
import dev.teamtesseract.molang.elements.MolangValue;
import org.apache.commons.lang3.math.NumberUtils;

public class MolangNumber extends MolangValue<Float> {

    public static final MolangNumber DUMMY = new MolangNumber(0.0F);

    public MolangNumber(String value) {
        super(value, true);
    }

    public MolangNumber(float value) {
        super(value, false);
    }

    @Override
    public boolean isValidToken(String s) {
        return NumberUtils.isParsable(s);
    }

    @Override
    public Float parse(String value) {
        return Float.parseFloat(value);
    }

    @Override
    public MolangResult process(MolangContext ctx) {
        return MolangResult.ofFloat(this.value);
    }
}
