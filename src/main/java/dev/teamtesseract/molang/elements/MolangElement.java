package dev.teamtesseract.molang.elements;

import dev.teamtesseract.molang.MolangResult;
import dev.teamtesseract.molang.context.MolangContext;

public interface MolangElement {

    boolean isValidToken(String s);

    MolangResult process(MolangContext ctx);

    interface Factory<T extends MolangElement> {
        T create(String value);
    }
}
