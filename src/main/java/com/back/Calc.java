package com.back;

import java.util.ArrayList;
import java.util.List;

public class Calc {
    public static int run(String expression) {
        List<String> tokens = tokenize(expression);
        ExpressionParser parser = new ExpressionParser(tokens);
        int result = parser.parseExpression();
        System.out.println(tokens);
        System.out.println(result);
        return result;
    }

    public static List<String> tokenize(String expression) {
        List<String> tokens = new ArrayList<>();
        StringBuilder num = new StringBuilder();

        for (char c : expression.toCharArray()) {
            if (Character.isWhitespace(c)) continue;
            if (Character.isDigit(c)) {
                num.append(c);
            } else {
                if (!num.isEmpty()) tokens.add(num.toString());
                tokens.add(Character.toString(c));
                num.setLength(0);
            }
        }
        if (!num.isEmpty()) tokens.add(num.toString());
        return tokens;
    }
}

