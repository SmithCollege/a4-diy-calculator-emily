package a4;

import java.util.ArrayDeque;
/*
 * This class reads an infix expression and is able to compute the result of a given expression, 
 *  this class is also in charge of determining the precedence of an operator and the right- vs left-associativity of an expression
 *  For example: 
 *                  Input: "(3+2)*5"
 *                  Answer: 25
 *                  or Input "2^1^3" is determined as 2^(1^3)
 */
public class Infix {
    /* Checks the precedence of the current operator token 
     * @param token the current operator token
     * @return the evaluated precedence as an integer
     */
    public static int getPrecedence(Character token){
        switch(token){
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            case '^':
                return 3;
            default:
                return 0;
        }
    }

    /*
     * @param tokens  a array of tokens that represent the input 
     * @return  the result of the expression as a Double 
     */
    public static Double infixToPostfix(ArrayDeque<Object> tokens) {
        ArrayDeque<Object> stack = new ArrayDeque<>();
        ArrayDeque<Object> queue = new ArrayDeque<>();

        while (!tokens.isEmpty()) {
            Object token = tokens.removeFirst();

            ArrayDeque<Object> parsedTokens = Tokenizer.readTokens(token.toString());  //read the token with the tokenizer
            Object parsedToken = parsedTokens.removeFirst();

            if (parsedToken instanceof Double) {
                queue.add(parsedToken);
            }else if(parsedToken instanceof Character){
                //if the token is a left parenthesis 
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
                    //check wheather there is right or left assosiativity 
                    while (!stack.isEmpty()){
                        if (getPrecedence((Character) stack.peek()) > getPrecedence((Character) parsedToken) || 
                            (getPrecedence((Character) stack.peek()) == getPrecedence((Character) parsedToken) && (Character) stack.peek() != '^') 
                            && (Character) stack.peek() != '(') {
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

