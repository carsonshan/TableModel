package formula;

/**
 * Created by Philipp on 19.05.2017.
 */

import cells.Cell;
import cells.FormulaCell;
import cells.NumberCell;
import swingfrontend.SwingFrame;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import static utilities.R.getR;


/**
 * Makes the evaluation of the original Formula from Formulacells
 */


public class FormulaEvaluator{



    private static final ArrayList<String> FORMULA_LITERALS = new ArrayList<String>(Arrays.asList(getR().label("F_SUM"),
            getR().label("F_COUNT"), getR().label("F_MEAN"), getR().label("F_MAX") ,getR().label("F_MIN")));






    /**
     * first Method witch start running the evaluation of formula
     * @param formula
     * @return
     */

    public static double evaluateFormula(String formula) {
        try {
            return calculate(toPostfixNotation(formula));

        } catch (Exception e) {
            System.out.println("printing exception");
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            e.printStackTrace();
        }
        System.out.println("Calculation was not able to made" + formula);
        return 0;
    }



    /**
     * starts to calculate with the spacific Formulatype
     * @param formularType
     * @param left
     * @param right
     * @return
     */

    public static String makeCalculation(EFormulaType formularType, String left, String right){

        FormulaClass formula = null;

        if(formularType == EFormulaType.SUM){
            formula = new FormulaSum();
        }else if (formularType == EFormulaType.COUNT){
            formula = new FormulaCount();
        }else if(formularType == EFormulaType.MEAN){
            formula = new FormulaMean();
        }

        if(formula == null){
            return null;
        }

        return formula.run(left,right);
    }


    /**
     * enumtypes for the basic calculationtypes
     */


    private enum Operator
    {
        ADD(1), SUBTRACT(2), MULTIPLY(3), DIVIDE(4), CELL(5), BRACKET(6);
        final double precedence;
        Operator(double p) {
            precedence = p;
        }
    }


    /**
     * hashtable for the basic calculationtypes
     */

    private static final Map<String, Operator> ops = new HashMap<String, Operator>() {
        {
            put("+", Operator.ADD);
            put("-", Operator.SUBTRACT);
            put("*", Operator.MULTIPLY);
            put("/", Operator.DIVIDE);
        }
    };




    /**
     * split the original formula into usable numbers, operators, formulatyps etc and put it in a Arraylist
     * @param input
     * @return
     */

    private static String[] splitFormula(String input){

        ArrayList<String> output = new ArrayList<>();

        Map<String, Operator> delimiterSigns = new HashMap<>(ops);
        delimiterSigns.put("(", Operator.BRACKET);
        delimiterSigns.put(")", Operator.BRACKET);
        delimiterSigns.put(" ", Operator.BRACKET);
        delimiterSigns.put("=", Operator.BRACKET);
        delimiterSigns.put(":", Operator.BRACKET);
  

        String lastValue ="";

        input += " "; // this is so we have an "ignore" value at the end so any parsed values before that are added to the array

        boolean inFormula = false;
        int cellsInFormula = 0;

        for(String nextChar : input.split("")){
            if(delimiterSigns.containsKey(nextChar)){
                if(lastValue.isEmpty()){
                    if(nextChar.equals(" ") || nextChar.equals("=") || nextChar.equals(":")){
                        //ignore spaces
                    }
                    else{
                        output.add(nextChar);
                    }
                }
                else{
                    output.add(lastValue);
                    if( nextChar.equals(" ")) {
                        //ignore blanks
                    }
                    else {
                        if(FORMULA_LITERALS.contains(lastValue)){
                            //we know it´s a formula
                            //don´t add any sign after this
                            inFormula = true;
                        }
                        else if(lastValue.charAt(0) == '$' ){
                            if(inFormula){
                                // don´t add anything
                                if(cellsInFormula == 2){
                                    cellsInFormula = 0;
                                    inFormula = false;
                                }else{
                                    cellsInFormula++;
                                }
                            }
                            else{
                                output.add(nextChar);
                            }
                        }
                        else{
                            output.add(nextChar);
                        }
                    }

                    lastValue = "";
                }
            }
            else{
                lastValue += nextChar;
            }
        }

        if(output.get(output.size()-1).equals(" ")){
            output.remove(output.size()-1);
        }

        String[] outputStringArray = new String[output.size()];

        return  output.toArray(outputStringArray);
    }




    /**
     * is a helping Method for Shunting Yard algorithm
     * @param op
     * @param sub
     * @return
     */

    private static boolean isHigherPrecedence(String op, String sub)
    {
        return (ops.containsKey(sub) && ops.get(sub).precedence >= ops.get(op).precedence);
    }



    /**
     * sort the splited formula form infix notation to postfix notation with the shunting yard algorithm
     * @param infix
     * @return
     * @throws IOException
     */

    private static String toPostfixNotation(String infix) throws IOException {

        Deque<String> stack  = new LinkedList<>();

        String[] splitFormula = splitFormula(infix);

        ArrayList<String> strings = new ArrayList<>(Arrays.asList(splitFormula));
        ArrayList<String> targetStrings = new ArrayList<>(strings.size());


        for(int i = 0; i < strings.size(); i++) {

            if(strings.get(i).isEmpty()){
                //ignore this sign
            }
            else if(strings.get(i).equals("+") || strings.get(i).equals("-") || strings.get(i).equals("/") || strings.get(i).equals("*") || strings.get(i).equals("(") || strings.get(i).equals(")")){
                targetStrings.add( strings.get(i));
            }
            else  if(strings.get(i).charAt(0) == '$'){

                Cell tempCell = SwingFrame.getSwingFrame().getCellValue(strings.get(i));

                if(tempCell == null){

                    targetStrings.add("0.0");
                }
                else{
                    switch (tempCell.getCellType()){
                        case TEXT:
                            targetStrings.add("0.0");
                            break;
                        case NUMBER:
                            targetStrings.add(((NumberCell)tempCell).getCellContent() + "");
                            break;
                        case FORMULA:
                            targetStrings.add(((FormulaCell)tempCell).getContent() + "");
                            break;
                    }
                }
            }
            else if(strings.get(i).equals(getR().label("F_SUM")) ||
                    strings.get(i).equals(getR().label("F_COUNT")) ||
                    strings.get(i).equals(getR().label("F_MEAN"))){

                //select formula type
                EFormulaType requestedFormula = null;
                if (strings.get(i).equals(getR().label("F_SUM"))) {
                    requestedFormula = EFormulaType.SUM;
                } else if (strings.get(i).equals(getR().label("F_COUNT"))) {
                    requestedFormula = EFormulaType.COUNT;
                } else  if (strings.get(i).equals(getR().label("F_MEAN"))) {
                    requestedFormula = EFormulaType.MEAN;
                }

                //check and calculate formula
                if(requestedFormula != null){
                    String left = strings.get(i+1);
                    String right = strings.get(i+2);
                    String formulaResult = makeCalculation(requestedFormula, left, right);
                    if(formulaResult != null){
                        targetStrings.add(i, formulaResult);
                        i += 2;
                    }
                    else{
                        return null;
                    }
                }
            }
            else {
                targetStrings.add(strings.get(i));
            }
        }

        StringBuilder output = new StringBuilder();

        for (String token : targetStrings) {
            if (ops.containsKey(token)) {
                while ( ! stack.isEmpty() && isHigherPrecedence(token, stack.peek())){
                    output.append(stack.pop()).append(' ');
                }
                stack.push(token);

                // left parenthesis
            } else if (token.equals("(")) {
                stack.push(token);

                // right parenthesis
            } else if (token.equals(")")) {
                while ( !stack.peek().equals("(")) {
                    output.append(stack.pop()).append(' ');
                }
                stack.pop();

                // digit
            } else {
                output.append(token).append(' ');
            }
        }

        while ( !stack.isEmpty())
            output.append(stack.pop()).append(' ');

        return output.toString();
    }


//Berechnung des Prefix-Strings
    private static final String ADD = "+";
    private static final String SUB = "-";
    private static final String MUL = "*";
    private static final String DIV = "/";



    /**
     * start running the final calculation with numerical formula
     * @param input
     * @return
     */

    private static double calculate(String input) {
        FormulaCalculator<Double> stack = new FormulaCalculator<>();

        String[] inputs = input.split(" ");

        return handleCalculation(stack, inputs);
    }


    /**
     * calculate the numerical formula
     * @param stack
     * @param el
     * @return
     */

    private static double handleCalculation(FormulaCalculator<Double> stack, String[] el) {
        double operand1, operand2;

        for(int i = 0; i < el.length; i++) {
            if( el[i].equals(ADD) || el[i].equals(SUB) || el[i].equals(MUL) || el[i].equals(DIV) ) {
                operand2 = stack.pop();
                operand1 = stack.pop();
                switch(el[i]) {
                    case ADD: {
                        double local = operand1 + operand2;
                        stack.push( local);
                        break;
                    }

                    case SUB: {
                        double local = operand1 - operand2;
                        stack.push(local);
                        break;
                    }

                    case MUL: {
                        double local = operand1 * operand2;
                        stack.push( local);
                        break;
                    }

                    case DIV: {
                        double local = operand1 / operand2;
                        stack.push(local);
                        break;
                    }
                }
            } else {
                stack.push(Double.parseDouble(el[i]));
            }
        }

        return stack.pop();
    }


}
