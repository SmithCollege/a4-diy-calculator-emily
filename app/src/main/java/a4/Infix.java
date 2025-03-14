package a4;

import java.util.ArrayDeque;

public class Infix {
    public static int getPrecedence(Character token){
        switch(token){
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            default:
                return 0;
        }
    }

    public static Double infixToPostfix(ArrayDeque<Object> tokens) {
        ArrayDeque<Object> stack = new ArrayDeque<>();
        ArrayDeque<Object> queue = new ArrayDeque<>();

        while (!tokens.isEmpty()) {
            Object token = tokens.removeFirst();

            ArrayDeque<Object> parsedTokens = Tokenizer.readTokens(token.toString());
            Object parsedToken = parsedTokens.removeFirst();

            if (parsedToken instanceof Double) {
                queue.add(parsedToken);
            }else if(parsedToken instanceof Character){
                
                if ((Character) parsedToken =='(') {
                    stack.push(parsedToken);
                }else if ((Character) parsedToken == ')') {
                    while ((Character) stack.peek() != '(') {
                        queue.add(stack.pop());
                    }
                    if (stack.isEmpty()) {
                        throw new IllegalArgumentException("There are missmatched parenthesis");
                    }else{
                        stack.pop();
                    }
                }else{
                    while (stack.peek() instanceof Character){
                        if(getPrecedence((Character) stack.peek()) >= getPrecedence((Character) parsedToken)) {
                        queue.add(stack.pop());
                        }else{
                            break;
                        }
                    }
                    stack.push(parsedToken);
                } 
                
            }
        }
        while (!stack.isEmpty()) {
            if (stack.peek().equals('(')) {
                throw new IllegalArgumentException("Mismatched parentheses");
            }else if (stack.element() instanceof Character) {
                queue.add(stack.pop());
            }
        }
        return Postfix.postfix(queue);
    }

}

