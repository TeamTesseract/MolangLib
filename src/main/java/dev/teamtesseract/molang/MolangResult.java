package dev.teamtesseract.molang;

public class MolangResult {

    public static MolangResult ZERO = ofFloat(0.0F);

    private final float floatValue;
    private final boolean booleanValue;
    private final String stringValue;

    private MolangResult(float f, boolean b, String s) {
        this.floatValue = f;
        this.booleanValue = b;
        this.stringValue = s;
    }

    public static MolangResult ofFloat(float f) {
        return new MolangResult(f, false, null);
    }

    public static MolangResult ofBoolean(boolean b) {
        return new MolangResult(Float.NaN, b, null);
    }

    public static MolangResult ofString(String s) {
        return new MolangResult(Float.NaN, false, s);
    }

    public boolean hasFloat() {
        return !Float.isNaN(floatValue);
    }

    public boolean hasBoolean() {
        return !hasFloat() && !hasString();
    }

    public boolean hasString() {
        return stringValue != null;
    }

    public float getFloat() {
        if(hasFloat())
            return floatValue;
        else
            return booleanValue ? 1.0F : 0.0F;
    }

    public boolean getBoolean() {
        if(hasFloat())
            return floatValue > 0.0F;
        else
            return booleanValue;
    }

    public String getString() {
        if(hasString())
            return stringValue;
        else
            return "";
    }

    @Override
    public String toString() {
        return String.format("R{ F: %.2f | B: %s  | S: \"%s\" }", getFloat(), getBoolean(), getString());
    }
}
