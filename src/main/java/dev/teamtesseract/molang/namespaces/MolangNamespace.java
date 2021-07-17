package dev.teamtesseract.molang.namespaces;

import dev.teamtesseract.molang.MolangResult;
import dev.teamtesseract.molang.context.MolangContext;
import dev.teamtesseract.molang.exceptions.MolangNamespaceException;

import java.util.HashMap;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public abstract class MolangNamespace {

    private final String namespace;
    private final HashMap<String, MolangFunction> functions;

    public MolangNamespace(String functionName) {
        this.namespace = functionName;
        this.functions = new HashMap<>();
    }

    public void registerFunction(String name, int argumentNumber, Function<List<MolangResult>, Boolean> arguments, BiFunction<List<MolangResult>, MolangContext, MolangResult> processing) {
        functions.put(name, new MolangFunction(name, argumentNumber, arguments, processing));
    }

    public void registerFunction(String name, BiFunction<List<MolangResult>, MolangContext, MolangResult> processing) {
        functions.put(name, new MolangFunction(name, 0, null, processing));
    }

    public boolean isValidCall(String full) {
        if(!full.contains(".") || full.endsWith("."))
            return false;
        String[] parts = full.split("\\.");
        boolean namespaceMatch = parts[0].equals(this.namespace) || (parts[0].length() == 1 && this.namespace.startsWith(parts[0]));
        boolean functionMatch = functions.keySet().stream().anyMatch(s -> s.equals(parts[1]));
        return namespaceMatch && functionMatch;
    }

    public MolangFunction getFunction(String name) {
        return functions.get(name);
    }

    @Override
    public String toString() {
        return namespace;
    }

    public static class MolangFunction {

        private final String name;
        private final int argumentCount;
        private final Function<List<MolangResult>, Boolean> argumentCheck;
        private final BiFunction<List<MolangResult>, MolangContext, MolangResult> function;

        public MolangFunction(String name, int argumentCount, Function<List<MolangResult>, Boolean> argumentCheck, BiFunction<List<MolangResult>, MolangContext, MolangResult> processing) {
            this.name = name;
            this.argumentCount = argumentCount;
            this.argumentCheck = argumentCheck;
            this.function = processing;
        }

        public String getName() {
            return name;
        }

        public MolangResult process(List<MolangResult> elements, MolangContext ctx) throws MolangNamespaceException {
            try {
                verifyArguments(elements);
                return function.apply(elements, ctx);
            } catch(MolangNamespaceException e) {
                return MolangResult.ZERO;
            }
        }

        private void verifyArguments(List<MolangResult> arguments) throws MolangNamespaceException {
            if(arguments.size() > this.argumentCount)
                throw new MolangNamespaceException(String.format("Too many arguments for function '%s'! Given: %d | Expected: %d", this.name, arguments.size(), this.argumentCount));
            if(arguments.size() < this.argumentCount)
                throw new MolangNamespaceException(String.format("Too few arguments for function '%s'! Given: %d | Expected: %d", this.name, arguments.size(), this.argumentCount));

            if(this.argumentCheck != null && !this.argumentCheck.apply(arguments))
                throw new MolangNamespaceException(String.format("Failed to evaluate arguments for function '%s'!", this.name));
        }
    }
}
