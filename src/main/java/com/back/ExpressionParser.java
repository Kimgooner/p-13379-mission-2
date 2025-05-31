package com.back;
import java.util.List;

class ExpressionParser {
    private final List<String> tokens;
    private int index = 0;

    public ExpressionParser(List<String> tokens) {
        this.tokens = tokens;
    }

    public int parseExpression() {
        int result = parseTerm();
        while (hasNext()) {
            String op = peek();
            if (op.equals("+") || op.equals("-")) {
                next();
                int right = parseTerm();
                if(op.equals("+")) result += right;
                else result -= right;
            } else {
                break;
            }
        }
        return result;
    }

    private int parseTerm() {
        int result = parseFactor();
        while (hasNext()) {
            String op = peek();
            if (op.equals("*") || op.equals("/")) {
                next();
                int right = parseFactor();
                if(op.equals("*")) result *= right;
                else result /= right;
            } else {
                break;
            }
        }
        return result;
    }

    private int parseFactor() {
        String current = peek();
        if (current.equals("(")) {
            next(); // "(" 제거
            int value = parseExpression();
            next(); // ")" 제거
            return value;
        } else if (current.equals("-")) {
            next();
            return -parseFactor();
        } else {
            return Integer.parseInt(next());
        }
    }

    private boolean hasNext() {
        return index < tokens.size();
    }

    private String peek() {
        return tokens.get(index);
    }

    private String next() {
        return tokens.get(index++);
    }
}
