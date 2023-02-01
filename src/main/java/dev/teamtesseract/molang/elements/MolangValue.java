package dev.teamtesseract.molang.elements;

public abstract class MolangValue<T> implements MolangElement {

    protected final T value;

    protected MolangValue(Object s, boolean parse) {
        this.value = parse ? parse((String)s) : (T)s;
    }

    public abstract T parse(String value);

    @Override
    public String toString() {
        return value instanceof String ? String.format("[\"%s\"]", value) : String.format("[%s]", value);
    }
}
