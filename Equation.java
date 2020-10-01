package HW2;
// Gabriello Lima, 112803276, R01

public class Equation extends HistoryStack {
	private String equation;
	private String prefix = "N/A";
	private String postfix = "N/A";
	private double answer;
	private String binary;
	private String hex;
	private int eqnNumber;
	EquationStack eqSObj = new EquationStack();
	/*
	 * Excess variable explanations:
	 * 
	 * @param eqnNumber
	 * Keeps track of this equation's equation number
	 * @param eqSObj
	 * An object of the EquationStack, used to work with the String Stack
	 * 
	 */
	public Equation() {

	}
/*
 * This constructor is essentially the brains of this class. Equation is set
 * with a string input and then all other variables, prefix, postfix, binary,
 *  and hex are filled in accordingly by calling their respective methods.
 * 
 * @throws NotBalancedException
 * When infToPos() or postToPre() returns that the equation is not balanced
 */
	public Equation(String newEq) throws NotBalancedException {
		equation = newEq;
		infToPos();
		answer = postFixEvaluate();
		answer = Math.round(answer * 100.0)/100.0;
		postToPre();
		decToBin((int)Math.round(answer));
		decToHex((int)Math.round(answer));
	}
	/*
	 * This method determines whether or not an equation is balanced
	* 
	 * @param tempLeft 
	 * keeps track of the number of left parenthesis
	 * 
	 * @param tempRight
	 * keeps track of the number of right parenthesis
	 * 
	 * @return
	 * returns true if the number of left and right parenthesis are equal
	 */
	public boolean isBalanced() {
		int tempLeft = 0;
		int tempRight = 0;
		for (int i = 0; i < equation.length(); i++) {
			char c = equation.charAt(i);
			if (c == '(')
				tempLeft++;
			else if (c == ')')
				tempRight++;
			if (tempRight > tempLeft)
				return false;
		}
		return (tempLeft == tempRight);
	}
	/*
	 * This method converts from decimal to binary and is used in tangent with
	 * the reverseString() method
	* 
	 * @param binString
	 * Keeps track of the binary string in reverse order, which is then reversed
	 * 
	 * @param tempInt
	 * keeps track of the binary value in 1 or 0, concatenated. to binString
	 * 
	 * @throws NotBalancedException
	 * When equation is not balanced
	 */
	public String decToBin(int number) throws NotBalancedException {
		if (!isBalanced()) {
			throw new NotBalancedException("Not Balanced");
		}
		String binString = "";
		int tempInt;
		while (number > 0) {
			tempInt = number % 2;
			number /= 2;
			binString += tempInt;
		}
		binString = reverseString(binString);
		binary = binString;
		return binString;
	}
	/*
	 * This method converts from decimal to hex and is used in tangent with
	 * the reverseString() method
	* 
	 * @param hexString
	 * keeps track of the hex String in reverse order, which is then reversed
	 * 
	 * @param tempInt
	 * keeps track of the hex value
	 * @throws NotBalancedException
	 * When equation is not balanced
	 */
	public String decToHex(int number) throws NotBalancedException {
		if (!isBalanced()) {
			throw new NotBalancedException("Not Balanced");
		}
		String hexString = "";
		int tempInt;
		while (number > 0) {
			tempInt = number % 16;
			number /= 16;
			if (tempInt < 10) {
				hexString += tempInt;
			} else {
				tempInt -= 10;
				hexString += (char) (tempInt + (int) 'A');
			}
		}
		hexString = reverseString(hexString);
		hex = hexString;
		return hexString;
	}
	/*
	 *Returns this equations number 
	 */
	public String toString() {
		return "" + eqnNumber;
	}
	public void setNumber(int number) {
		eqnNumber = number;
	}
	/*
	 * This method reverses a string input s
	 */
	public String reverseString(String s) {
		String newString = "";
		for (int i = s.length() - 1; i >= 0; i--) {
			newString += s.charAt(i);
		}
		return newString;
	}

	/*
 	* This method converts postfix to prefix using the following logic:
    * Step 1: Reverse the infix expression 
    * Step 2: Obtain the postfix expression of the reversed infix expression.
    * Step 3: Reverse the postfix expression. 
   * 
 	* I feel it is important to note that I made a second infToPos() method
 	* that takes a string input in order to easily accommodate this method 
 	* 
 	* @throws NotBalancedException
	* When equation is not balanced
 	*/
    public String postToPre() throws NotBalancedException 
    { 
    	String temp = "";
    	String tempString = this.equation;
    	tempString = reverseString(tempString);
    	for (int i = 0; i < tempString.length(); i ++) {
    		if (tempString.charAt(i) == ')') {
    			temp += '(';
    		}
    		else if (tempString.charAt(i) == '(') {
    			temp += ')';
    		}
    		else
    			temp += tempString.charAt(i);
    	}
    	temp = infToPos(temp);
    	prefix = reverseString(temp);
    	return "This is prefix: " + prefix; 
    } 
    /*
     * This method converts an infix expression to postfix
     * 
     * @param poppedChar
     * A character thats sole purpose was to keep track of the popped character
     * from the stack
     * 
     * @param thisChar
     * A character that keeps track of the character at I, used in tangent with
     * the string temp to easily manipulate the String stack
     * 
     * @param temp
     * A string that keeps track of the characters at I (ideally multi-digited 
     * characters) in tangent with thisChar
     * 
     * @throws NotBalancedException
	 * When equation is not balanced
     * 
     */
	public String infToPos() throws NotBalancedException {
		if (!isBalanced()) {
			throw new NotBalancedException("Not Balanced");
		}
		else {
			postfix = "";
			String equation = "(" + this.equation + ")";
			EquationStack eqSObj = new EquationStack();
	        char poppedChar;

	        for (int i = 0; i < equation.length(); i++) {
	        	String temp = "";
	            char thisChar = equation.charAt(i);
	            temp += thisChar;
	            if(thisChar == ' ') {
	            	continue;
	            }
	            if (!isOperator(thisChar)) {
	            	if(isOperator(equation.charAt(i-1)) && i != 0){
	            		postfix+= " ";
	            	}
	            	postfix += thisChar;
	            	if (i != equation.length() - 1 && isOperator(equation.charAt(i+1)))
	            		postfix += ' ';
	            }

	            else if (thisChar == ')')
	                while ((poppedChar = eqSObj.pop().charAt(0)) != '(') {
	                	postfix += " ";
	                	postfix += poppedChar;
	                	postfix += " ";
	                }
	            else {
	                while (!eqSObj.isEmpty() && thisChar != '(' && precedence(eqSObj.peek().charAt(0)) >= precedence(thisChar)) {
	                	postfix += " ";
	                	postfix += eqSObj.pop();
	                }

	                eqSObj.push(temp);
	            }
	        }
	        while (!eqSObj.isEmpty())
	            postfix += eqSObj.pop();
	        return postfix;

		}
			
			}
	private boolean isOperator(char get) { //used to be static
		return precedence(get) > 0;
	}
	/*
	 * This method is exactly the same as the other infToPos method except it 
	 * takes a string input rather than relying on the variable equation. Please
	 * read the other methods comments.
	 * 
	 * 
	 */
	public String infToPos(String s) throws NotBalancedException {
		//System.out.println("This is the equation: " + equation);
		if (!isBalanced()) {
			throw new NotBalancedException("Not Balanced");
		}
		else {
			String tempVal = equation;
			equation = s;
			String postfix = "";
			EquationStack eqSObj = new EquationStack();
	        char popped;
	
	        for (int i = 0; i < equation.length(); i++) {
	        	String temp = "";
	            char get = equation.charAt(i);
	            temp += get;
	            if(get == ' ') {
	            	continue;
	            }
	            if (!isOperator(get)) {
	            	postfix += get;
	            	if (isOperator(equation.charAt(i+1)) && i != equation.length() - 1)
	            		postfix += ' ';
	            }
	
	            else if (get == ')')
	                while ((popped = eqSObj.pop().charAt(0)) != '(') {
	                	//postfix += " ";
	                	postfix += popped;
	                	postfix += " ";
	                }
	            else {
	                while (!eqSObj.isEmpty() && get != '(' && precedence(eqSObj.peek().charAt(0)) >= precedence(get)) {
	                	//if(postfix.charAt(i - 1) != ' ') {
	                	//	postfix += ' ';
	                	//}
	                	//postfix += " ";
	                	postfix += eqSObj.pop();
	                	//postfix += " ";
	                }
	
	                eqSObj.push(temp);
	            }
	        }
	        equation = tempVal;
	        while (!eqSObj.isEmpty())
	            postfix += eqSObj.pop();
	        return postfix;
		}
	}
	/*
	 * This method evaluates a postfix expression using the String stack in 
	 * EquationStack.
	 * 
	 * @throws NumberFormatException
	 *  If postfix is somehow incorrect
	 * @throws NotBalancedException
	 * When equation is not balanced
	 */
	public double postFixEvaluate() throws NumberFormatException, NotBalancedException {
		EquationStack stack = new EquationStack();
		int size = postfix.length();
		for(int i = 0; i < size; i ++) {
			String temp = "";
			if (Character.isDigit(postfix.charAt(i))) {
				while(Character.isDigit(postfix.charAt(i))) {
					temp += postfix.charAt(i);
					i++;
				}
				stack.push(temp);
			}
			else if(!isOperator(postfix.charAt(i)) && !Character.isDigit(postfix.charAt(i))) {
				continue;
			}
			else
			{
				double num2 = Double.parseDouble(stack.pop());
				double num1 = Double.parseDouble(stack.pop());
				if(postfix.charAt(i) == '+') {
					temp += (num1+num2);
					stack.push(temp);
				}
				else if(postfix.charAt(i) == '-') {
					temp += (num1-num2);
					stack.push(temp);
				}
				else if(postfix.charAt(i) == '*') {
					temp += (num1*num2);
					stack.push(temp);
				}
				else if(postfix.charAt(i) == '/') {
					temp += (num1/num2);
					stack.push(temp);
				}
				else if(postfix.charAt(i) == '%') {
					temp += (num1%num2);
					stack.push(temp);
				}
				else if(postfix.charAt(i) == '^') {
					temp += (Math.pow(num1, num2));
					stack.push(temp);
				}

			}
		}
		return (Double.parseDouble(stack.pop()));
	}

	public double getAnswer() {
		return answer;
	}
	public String getEquation() {
		return equation;
	}
	public String getPrefix() {
		return prefix;
	}
	public String getPostfix() {
		return postfix;
	}
	public String getBinary() {
		return binary;
	}
	public String getHex() {
		return hex;
	}
	/*
	 * This method gives precedence to the operators, which is then used in 
	 * tangent with other methods such as infToPos, postToPre() and isOperator()
	 * 
	 * Precedence is given in standard pemdas order, aside from parenthesis.
	 * Generally, the higher the precedence, the more important it is.
	 * 
	 * 
	 */
	private int precedence(char i) {
		if (i == '(' || i == ')')
			return 1;
		if (i == '-' || i == '+')
			return 2;
		else if (i == '*' || i == '/' || i == '%')
			return 3;
		else if (i =='^')
			return 4;
		else
			return 0;
	}

	public void setEquation(String s) throws NotBalancedException {
		equation = s;
		infToPos();
		answer = postFixEvaluate();
		answer = Math.round(answer * 100.0)/100.0;
		postToPre();
		decToBin((int)Math.round(answer));
		decToHex((int)Math.round(answer));
	}

}