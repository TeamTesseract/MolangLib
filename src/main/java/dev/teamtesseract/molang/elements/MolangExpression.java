package dev.teamtesseract.molang.elements;

import dev.teamtesseract.molang.MolangResult;
import dev.teamtesseract.molang.context.MolangContext;

import java.util.*;

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
        LinkedHashMap<MolangElement, MolangResult> evaluated = new LinkedHashMap<>();
        elements.forEach(e -> evaluated.put(e, e instanceof MolangOperator ? null : e.process(ctx)));

        List<Integer> indices = determineOperatorIndices(evaluated);
        while(!indices.isEmpty()) {
            List<MolangElement> keys = new ArrayList<>(evaluated.keySet());
            int operatorIndex = indices.get(0);
            MolangElement leftKey = keys.get(operatorIndex - 1);
            MolangElement rightKey = keys.get(operatorIndex + 1);
            MolangOperator operator = (MolangOperator)keys.get(operatorIndex);

            MolangResult result = operator.evaluate(evaluated.get(leftKey), evaluated.get(rightKey));

            evaluated.replace(operator, result);
            evaluated.remove(leftKey);
            evaluated.remove(rightKey);

            indices = determineOperatorIndices(evaluated);
        }

        return evaluated.getOrDefault(new ArrayList<>(evaluated.keySet()).get(0), MolangResult.ofFloat(0.0F));
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

    private List<Integer> determineOperatorIndices(LinkedHashMap<MolangElement, MolangResult> results) {
        List<Integer> operatorIndices = new ArrayList<>();
        results.forEach((k, v) -> {
            if(v == null)
                operatorIndices.add(new ArrayList<>(results.keySet()).indexOf(k));
        });

        operatorIndices.sort(Comparator.comparingInt(o -> ((MolangOperator)elements.get(o)).priority()));

        return operatorIndices;
    }
}
