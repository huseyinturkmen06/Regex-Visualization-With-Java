
package OdevClassPaketi;

import java.util.Stack;     
import java.lang.Character; // concatenation birleşim veya yıldız yani &  | *


public class InfixToPostfix {         // a(a+b)*b örnek bu olsun ? yerine concetenation kullanılırsa a?(a+b)*?b bu olur
    private String infixString; // infix için string
    private static String outputString=""; // queue gibi davranıyor biz zaten stackden queue ya dönüştürüyoruz
        //a(a+b)*b
        
    public String infixToPostFix(String infixString){ //guiden infix olarak gelen String i, postfix e çeviren metod
            
            Stack<Character> stack = new Stack<Character>();
            String Garbage="";  //Parantezleri atmak için oluşturuldu
            for(int i=0;i<infixString.length();i++){ // 7 -- 2,3
                char CurrentChar=infixString.charAt(i); // a*b //infix Stringinin her karakteri üstünde tek tek geziyoruz
                if(letterOrDigit(CurrentChar)){ 
                    outputString += Character.toString(CurrentChar); // current charı string yapıp ekledik
                }
                else if(CurrentChar == '('){
                    stack.push(CurrentChar);
                }
                // stack search içinde bulursa bulduğu yeri döner yoksa -1 döner
                else if(CurrentChar == ')'){
                    while(!stack.isEmpty() && stack.peek()!='('){ // stack in ( parantez içerip içermediğine bakma --- stack in en üstteki elemanı ( olana kadar ara
                        outputString += stack.pop();              //  ) bulana kadar stack in en üstteki elemanını poplamak                
                    }
                    
                        stack.push(CurrentChar);        // ( ve şimdi gelen (döngüden çıktığım için ( parantez geldi demek) 
                    while(stack.peek()!='('){ // ( ve ) parantezi stackden atmak için 
                        Garbage+=stack.pop(); 
                        
                    }
                        if(stack.peek()=='(') Garbage+=stack.pop();
                }
                else if(isOperator(CurrentChar)){// operator olma durumu     else if(isOperator(CurrentChar)==false){       else if(isOperator(CurrentChar))
                    if(stack.isEmpty()){
                        stack.push(CurrentChar);
                        
                    }
                    else{
                        while(!stack.isEmpty() && precedence(CurrentChar) <= precedence(stack.peek())){// while ın ilk durumunu yazmaya gerek yok ama şimdilik kalsın
                            outputString+=stack.pop();
                        }
                            stack.push(CurrentChar);
                    }
                    
                }
                }
                while(!stack.isEmpty()){
                    if(stack.peek()=='(') return "this expression is invalid";
                        
                        outputString +=stack.pop();
                }
                
            
            
            return outputString; 
        }
        
        
        
        public boolean isOperator(char charac){ // charac --> character --- gelen characterin operator (* ? +) dan biri olup olmadığını anlar, değilse letter gelmiştir.
            if(charac == '+' ||charac=='|'){
            return true;
        }
            else if(charac == '-'){
            return true;
        }
            else if(charac == '/'){
            return true;
        }
            else if(charac == '*'){
            return true;
        }
            else if(charac == '?' || charac=='&'){
            return true;
        }
            else{
                return false;
        }
            }
        public static boolean letterOrDigit(char c){ // c stands for character
            if(Character.isLetter(c)){  //o anki karakterin harf olup olmadığını kontrol eder
                return true;
            }
            else{
                return false;
            }
        }
        public int precedence(char charac){ // charac --> character
            if(charac == '(' ||charac==')'){
                return 0;
            }
            if(charac == '+' ||charac == '-' ||charac=='|'){         
                return 1;
            }
            else if(charac=='?' ||charac=='&'){ // concatenation
                return 2;
            }
            else if(charac == '*' || charac == '/'){
                return 3;
            }
            else return -1; // missing return statement
        }
        
       

}