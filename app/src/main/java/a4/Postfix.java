package a4;

import java.util.ArrayDeque;
/*
 * This class reads a postfix expression and is able to compute the result of a given expression
 * For example: 
 *          Input: "3 2 + 5 *"
 *	        Answer: 25
 */
public class Postfix {

    /* 
     * @param tokens   array of tokens that represent an input expression
     * @return the result of the given experession as a Double 
    */
    public static Double postfix(ArrayDeque<Object> tokens) {
        Double x1;
        Double x2;
        Character operator;
        ArrayDeque<Object> stack = new ArrayDeque<>();

        while(!tokens.isEmpty()){
            Object token = tokens.removeFirst();

            if(token instanceof Double){
                stack.push(token);
            }else if(token instanceof Character){
                if (stack.size()<2) {
                    throw new IllegalArgumentException("Not enough operands");
                }else{
                    //pop two numbers off the stack
                    x1 = (Double) stack.pop();  
                    x2 = (Double) stack.pop();
                    operator = (Character) token; 
                    Double result; 
                    //combine them using the operator 
                    switch (operator) {
                        case '+':
                            result = x2 + x1;
                            break;
                        case '-':
                            result = x2 - x1;
                            break;
                        case '*':
                            result = x2 * x1;
                            break;
                        case '/':
                            if(x1 == 0.0){
                                throw new RuntimeException("Cannot divide by zero");
                            }else{
                                result = x2/x1;
                            }
                            break;
                        case '^':
                            result = Math.pow(x2, x1);
                            break;
                        default:
                            throw new IllegalArgumentException("Invalid Operator"); 
                    }
                    //push the result onto the stack 
                    stack.push(result);
                }

            }else{
                throw new IllegalArgumentException();
            }

        };
        //if stack has too many elements at the end
        if (stack.size()>1) {
            throw new IllegalArgumentException("Too many elements in stack");
        }else{
            return (Double) stack.pop(); 
        }
    }
    
}