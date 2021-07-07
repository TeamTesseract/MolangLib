package dev.teamtesseract.molang.elements;

import dev.teamtesseract.molang.MolangParser;
import dev.teamtesseract.molang.MolangResult;
import dev.teamtesseract.molang.context.MolangContext;

import java.util.function.BiFunction;

public class MolangOperator implements MolangElement {

    private final String character;
    private final BiFunction<MolangResult, MolangResult, MolangResult> eval;

    public MolangOperator(String character, BiFunction<MolangResult, MolangResult, MolangResult> eval) {
        this.character = character;
        this.eval = eval;
    }

    public boolean isValidToken(String s) {
        return this.character.equals(s);
    }

    @Override
    public MolangResult process(MolangContext ctx) {
        return null;
    }

    @Override
    public String toString() {
        return "[" + character + "]";
    }

    public static final MolangOperator MULTIPLY = new MolangOperator("*", (l, r) -> MolangResult.ofFloat(l.getFloat() * r.getFloat()));
    public static final MolangOperator DIVIDE = new MolangOperator("/", (l, r) -> MolangResult.ofFloat(l.getFloat() / r.getFloat()));
    public static final MolangOperator ADD = new MolangOperator("+",(l, r) -> MolangResult.ofFloat(l.getFloat() + r.getFloat()));
    public static final MolangOperator SUBTRACT = new MolangOperator("-", (l, r) -> MolangResult.ofFloat(l.getFloat() - r.getFloat()));

    public static final MolangOperator INVERT = new MolangOperator("!", (l, r) -> MolangResult.ofBoolean(!l.getBoolean()));
    public static final MolangOperator AND = new MolangOperator("&&", (l, r) -> MolangResult.ofBoolean(l.getBoolean() && r.getBoolean()));
    public static final MolangOperator OR = new MolangOperator("||", (l, r) -> MolangResult.ofBoolean(l.getBoolean() || r.getBoolean()));
    public static final MolangOperator SMALLER = new MolangOperator("<", (l, r) -> MolangResult.ofBoolean(l.getFloat() < r.getFloat()));
    public static final MolangOperator LARGER = new MolangOperator(">", (l, r) -> MolangResult.ofBoolean(l.getFloat() > r.getFloat()));
    public static final MolangOperator SMALLER_EQ = new MolangOperator("<=", (l, r) -> MolangResult.ofBoolean(l.getFloat() <= r.getFloat()));
    public static final MolangOperator LARGER_EQ = new MolangOperator(">=", (l, r) -> MolangResult.ofBoolean(l.getFloat() >= r.getFloat()));
    public static final MolangOperator EQUAL = new MolangOperator("==", (l, r) -> {
        if(l.hasString() && r.hasString())
            return MolangResult.ofBoolean(l.getString().equals(r.getString()));
        else
            return MolangResult.ofBoolean(l.getFloat() == r.getFloat());
    });
    public static final MolangOperator NOT_EQUAL = new MolangOperator("!=", (l, r) -> {
        if(l.hasString() && r.hasString())
            return MolangResult.ofBoolean(!l.getString().equals(r.getString()));
        else
            return MolangResult.ofBoolean(l.getFloat() != r.getFloat());
    });

    public static void registerOperators() {
        MolangParser.registerToken(MULTIPLY, s -> MULTIPLY); MolangParser.registerToken(DIVIDE, s -> DIVIDE);
        MolangParser.registerToken(ADD, s -> ADD); MolangParser.registerToken(SUBTRACT, s -> SUBTRACT);

        MolangParser.registerToken(INVERT, s -> INVERT);
        MolangParser.registerToken(AND, s -> AND); MolangParser.registerToken(OR, s -> OR);
        MolangParser.registerToken(SMALLER, s -> SMALLER); MolangParser.registerToken(SMALLER_EQ, s -> SMALLER_EQ);
        MolangParser.registerToken(LARGER, s -> LARGER); MolangParser.registerToken(LARGER_EQ, s -> LARGER_EQ);
        MolangParser.registerToken(EQUAL, s -> EQUAL); MolangParser.registerToken(NOT_EQUAL, s -> NOT_EQUAL);
    }
}
