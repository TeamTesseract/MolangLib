package dev.teamtesseract.molang.elements;

import dev.teamtesseract.molang.MolangResult;
import dev.teamtesseract.molang.context.MolangContext;
import dev.teamtesseract.molang.namespaces.MolangNamespace;

import java.util.LinkedList;
import java.util.List;

public class MolangMethod implements MolangElement {

    private final MolangNamespace namespace;
    private final String function;
    private LinkedList<MolangElement> args;

    public MolangMethod(MolangNamespace namespace) {
        this(namespace, "");
    }

    public MolangMethod(MolangNamespace namespace, String function) {
        this.namespace = namespace;
        this.function = function.equals("") ? "" : function;
    }

    public void setArgs(List<MolangElement> args) {
        this.args = new LinkedList<>(args);
    }

    @Override
    public boolean isValidToken(String s) {
        return namespace.isValidCall(s);
    }

    @Override
    public MolangResult process(MolangContext ctx) {
        return namespace.getFunction(function).process(this.args, ctx);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(namespace + "." + function + "{");
        for (int i = 0; i < args.size() - 1; i++) {
            builder.append(args.get(i)).append(", ");
        };
        builder.append(args.getLast()).append("}");
        return builder.toString();
    }
}
