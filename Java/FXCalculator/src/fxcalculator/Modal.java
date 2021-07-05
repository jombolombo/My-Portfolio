
package fxcalculator;


public class Modal {
   
    public float calculate(long number1, long number2, String oparator){
        switch(oparator){
            case "+": 
                return number1+ number2 ;
            case "-":
                return number1- number2 ;
            case "/": 
                if(number2==0){
                    return 0;
                }
                return number1/ number2 ;
            case "*": 
                return number1* number2 ;
            default:
                return 0;
                     
        }
       
    }
}
