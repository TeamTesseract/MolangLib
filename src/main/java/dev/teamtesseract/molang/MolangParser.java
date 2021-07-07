package dev.teamtesseract.molang;

import dev.teamtesseract.molang.elements.MolangElement;
import dev.teamtesseract.molang.elements.MolangExpression;
import dev.teamtesseract.molang.elements.MolangMethod;
import dev.teamtesseract.molang.elements.MolangOperator;
import dev.teamtesseract.molang.namespaces.MolangMath;
import dev.teamtesseract.molang.namespaces.MolangNamespace;
import dev.teamtesseract.molang.values.MolangNumber;
import dev.teamtesseract.molang.values.MolangString;
import dev.teamtesseract.molang.values.MolangUnknown;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MolangParser {

    private static final Map<MolangElement, MolangElement.Factory<?>> ELEMENTS = new HashMap<>();
    private static final String TEST_STRING = "math.abs(q.anim_time / 38.0 + 44 - 11.926) * (variable.rotation_scale + v.x) * query.life_time + query.head_rotation('weird_af string')";

    public static <T extends MolangElement> void registerToken(T element, MolangElement.Factory<T> factory) {
        ELEMENTS.put(element, factory);
    }

    public static void registerNamespace(MolangNamespace namespace) {
        MolangMethod getter = new MolangMethod(namespace);
        registerToken(getter, (s) -> new MolangMethod(namespace, s.split("\\.")[1]));
    }

    public static void init() {
        MolangOperator.registerOperators();
        registerToken(MolangString.DUMMY, (s) -> new MolangString(s, true));
        registerToken(MolangNumber.DUMMY, MolangNumber::new);

        registerNamespace(new MolangMath());

        MolangExpression test = new MolangExpression(parseLine(TEST_STRING));
        System.out.println(test);
    }

    public static List<MolangElement> parseLine(String line) {
        List<MolangElement> tokens = new ArrayList<>();
        char[] characters = line.toCharArray();

        StringBuilder token = new StringBuilder();
        boolean isInString = false;

        for(int i = 0; i < characters.length; i++) {
            char c = characters[i];

            System.out.println(token);

            if(c == '(') {
                StringBuilder b = new StringBuilder();
                char next = c;
                int level = 1;
                while(next != ')' && level != 0) {
                    next = characters[++i];
                    if(next == '(')
                        level++;
                    if(next == ')') {
                        if(level == 1)
                            break;
                        else
                            level--;
                    }
                    b.append(next);
                }

                if(!tokens.isEmpty() && tokens.get(tokens.size() - 1) instanceof MolangMethod) {
                    String[] values = b.toString().split(",");
                    List<MolangElement> elements = new ArrayList<>();
                    for(String s : values)
                        elements.add(new MolangExpression(parseLine(s)));
                    ((MolangMethod) tokens.get(tokens.size() - 1)).setArgs(elements);
                } else
                    tokens.add(new MolangExpression(parseLine(b.toString())));

                token = new StringBuilder();
                continue;
            }

            if((c == ' ' || c == ';' || c == ',') && !isInString) {
                System.out.println(token);
                if(token.length() > 0)
                    tokens.add(parseToken(token.toString()));
                token = new StringBuilder();
                continue;
            }

            token.append(c);

            if(c == '"') {
                if(isInString) {
                    tokens.add(parseToken(token.toString()));
                    token = new StringBuilder();
                    System.out.println("Reset! Reason: String finished.");
                }
                isInString = !isInString;
            }

            if(Character.isDigit(c) && i + 1 < characters.length) {
                while(i < characters.length - 1) {
                    char n = characters[i + 1];
                    System.out.println(token);
                    if(Character.isDigit(n) || n == '.') {
                        i++;
                        token.append(n);
                    } else
                        break;
                }
            }

            if((isValidToken(token.toString()) || i == characters.length - 1 || characters[i + 1] == '(') && token.length() > 0) {
                tokens.add(parseToken(token.toString()));
                token = new StringBuilder();
                System.out.println("Reset! Reason: Parsed.");
            }
        }

        return tokens;
    }

    private static boolean isValidToken(String s) {
        return ELEMENTS.keySet().stream().anyMatch(e -> e.isValidToken(s));
    }

    public static MolangElement parseToken(String token) {
        List<MolangElement> validElements = new ArrayList<>(ELEMENTS.keySet()).stream().filter(e -> e.isValidToken(token)).collect(Collectors.toList());
        return validElements.isEmpty() ? new MolangUnknown(token) : ELEMENTS.get(validElements.get(0)).create(token);
    }
}
