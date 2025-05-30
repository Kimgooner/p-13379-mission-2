package com.back;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Calc {
    public static int run(String expression) {
        List<String> tokens = tokenize(expression);
        List<String> postFix = inFix2postFix(tokens);
        int result = calcPostFix(postFix);
        System.out.println(tokens);
        System.out.println(postFix);
        System.out.println(result);

        return result;
    }

    public static List<String> tokenize(String expression){
        List<String> tokens = new ArrayList<>();
        StringBuilder num = new StringBuilder();

        for(char c : expression.toCharArray()){
            if(Character.isWhitespace(c)) continue;
            if(Character.isDigit(c)){
                num.append(c);
            }
            else{
                if(!num.isEmpty()) tokens.add(num.toString());
                tokens.add(Character.toString(c));
                num.setLength(0);
            }
        }
        if(!num.isEmpty()) tokens.add(num.toString());
        return tokens;
    }

    public static List<String> inFix2postFix(List<String> tokens){
        List<String> postFix = new ArrayList<>();
        Stack<String> st = new Stack<>();
        boolean isPrevOp = false;
        boolean makeMinus = false;
        boolean makeMinusBundle = false;
        for(String s : tokens){
            switch (s){
                case "(":
                    st.push(s);
                    if(makeMinus){
                        makeMinusBundle = true;
                        makeMinus = false;
                    }
                    break;
                case ")":
                    String cur = st.pop();
                    while (!cur.equals("(")) {
                        postFix.add(cur);
                        cur = st.pop();
                    }
                    if(makeMinusBundle){
                        postFix.add("*");
                        postFix.add("-1");
                        makeMinusBundle = false;
                    }
                    break;
                case "+": case "-": case "*": case "/":
                    if(isPrevOp && s.equals("-")){
                        makeMinus = true;
                        continue;
                    }
                    while (!st.empty() && (priority(s) <= priority(st.peek()))) postFix.add(st.pop());
                    st.push(s);
                    isPrevOp = true;
                    break;
                default:
                    if(makeMinus){
                        postFix.add("-" + s);
                        makeMinus = false;
                        continue;
                    }
                    postFix.add(s);
                    isPrevOp = false;
                    break;
            }
        }
        while(!st.empty()) postFix.add(st.pop());
        return postFix;
    }

    public static int priority(String s){
        switch (s){
            case "+": case "-": return 1;
            case "*": case "/": return 2;
        }
        return -1;
    }

    public static int calcPostFix(List<String> postFix){
        Stack<Integer> result = new Stack<>();
        for(String s : postFix){
            if("+=-*/".contains(s)){
                if(result.size() >= 2){
                    int num2 = result.pop();
                    int num1 = result.pop();
                    switch (s) {
                        case "+":
                            result.push(num1 + num2);
                            break;
                        case "-":
                            result.push(num1 - num2);
                            break;
                        case "*":
                            result.push(num1 * num2);
                            break;
                        case "/":
                            result.push(num1 / num2);
                            break;
                    }
                }
                else{
                    if(s.equals("-")) result.push(result.pop() * -1);
                }
            }
            else{
                result.push(Integer.parseInt(s));
            }
        }
        return result.pop();
    }
}