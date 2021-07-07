package dev.teamtesseract.molang.elements;

import com.mojang.datafixers.util.Either;
import dev.teamtesseract.molang.MolangResult;
import dev.teamtesseract.molang.context.MolangContext;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MolangExpression implements MolangElement {

    private final LinkedList<MolangElement> elements;

    public MolangExpression(List<MolangElement> elements) {
        this.elements = new LinkedList<>(elements);
    }

    @Override
    public boolean isValidToken(String s) {
        return false;
    }

    @Override
    public MolangResult process(MolangContext ctx) {
        Map<Integer, Either<MolangOperator, MolangResult>> results = new HashMap<>();
        for(int i = 0; i < elements.size(); i++) {
            MolangElement e = elements.get(i);
            if(e instanceof MolangOperator)
                results.put(i, Either.left((MolangOperator)e));
            else
                results.put(i, Either.right(e.process(ctx)));
        }


        for(int i = 0; i < results.size(); i++) {

        }

        return null;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("M{");
        for (int i = 0; i < elements.size() - 1; i++) {
            builder.append(elements.get(i)).append(" ");
        };
        builder.append(elements.getLast()).append("}");
        return builder.toString();
    }
}
