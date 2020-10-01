package HW2;
// Gabriello Lima, 112803276, R01
import java.util.Stack;
public class HistoryStack{
	private boolean pushed;
	Equation tempEquation;
	Equation prevEquation;
	Stack<Equation> eqStack = new Stack<Equation>();
	Stack<Equation> tempStack = new Stack<Equation>();
	boolean canUndo = false;
	boolean canRedo = false;
	int size = 0;
	/*
	 * Excess variables explanations:
	 * 
	 * @param tempEquation
	 * Used to store an equation in the case of Equation stack manipulation 
	 * 
	 * @param prevEquation
	 * Used to store the previous equation for undo and redo
	 * 
	 * @param canUndo
	 * keeps track of if we can undo this turn
	 * 
	 * @param canRedo
	 * keeps track of if we can redo this turn
	 * 
	 * @param pushed
	 * keeps track of if push() was called last
	 * 
	 */
	public HistoryStack() {
		
	}
	/*
	 * An important note for push() and pop() is that they manipulate the
	 * pushed boolean to manage undo and redo in tangent with canUndo and 
	 * canRedo
	 * 
	 */
	public void push(Equation newEquation) {
		this.size++;
		canUndo = true;
		canRedo = false;
		newEquation.setNumber(this.size); 
		eqStack.push(newEquation);
		tempEquation = newEquation;
		pushed = true;
	}
	public Equation pop() {
		this.size--;
		canUndo = true;
		canRedo = false;
		pushed = false;
		tempEquation = eqStack.pop();
		return tempEquation;
	}
	public Equation peek() {
		tempEquation = pop();
		push(tempEquation);
		return tempEquation;
	}
	public void undo() throws NothingToUndoException {
		if(canUndo==false) {
			throw new NothingToUndoException("There is nothing to Undo");
		}
		if (pushed) {
			pop();
			pushed = false;
		}
		else {
			push(tempEquation);
			pushed = true;
		}
		canUndo = false;
		canRedo = true;
	}
	public void redo() throws NothingToRedoException {
		if (canRedo) {
			if (pushed) {
				pop();
			} 
			else {
				push(tempEquation);
			}
			canRedo = false;
			canUndo = true;
		}
		else
			throw new NothingToRedoException("There is nothing to redo");
	}
	public int size() { 
		return this.size;
	}
	/*
	 * This method returns an equation that is stored at a given position by using
	 * two stacks.
	 * 
	 * @throws IndexOutOfBoundsException
	 * When the given position is not within the range of possibilities
	 * 
	 */
	public Equation getEquation(int position) throws IndexOutOfBoundsException{
		if(position <= size() + 1 && position > 0) {
			for (int i = size(); i > position; i --) {
				tempStack.push(eqStack.pop());
			}
			tempEquation = eqStack.peek();
			while (!tempStack.isEmpty()) {
				eqStack.push(tempStack.pop());
			}
			return tempEquation;
		}
		else
			throw new IndexOutOfBoundsException("Please enter a valid position");
	}
	/*
	 * This method removes an equation at a given position by using two stacks.
	 * 
	 * @throws IndexOutOfBoundsException
	 * When the given position is not within the range of possibilities
	 * 
	 */
	public Equation removeEquation(int position) throws IndexOutOfBoundsException{ 
		if(position <= size() + 1 && position > 0) {
			for (int i = size(); i > position; i --) {
				tempStack.push(eqStack.pop());
			}
			tempEquation = eqStack.pop();
			while (!tempStack.isEmpty()) {
				eqStack.push(tempStack.pop());
			}
			return tempEquation;
		}
		else
			throw new IndexOutOfBoundsException("Please enter a valid position");
	}
	public String toString(){
		return "#   Equation          Pre-Fix          Post-Fix          Answer          Binary   Hexadecimal";
	}
	public void printPrevEquation() { 
		System.out.println(toString());
		prevEquation = eqStack.peek();
		System.out.println(prevEquation.toString() +"   " + prevEquation.getEquation() +"     " +prevEquation.getPrefix() +"     " + prevEquation.getPostfix() +"     " + prevEquation.getAnswer() +"     " + prevEquation.getBinary() +"     " + prevEquation.getHex());
	}
	public void printAllEquations() {
		System.out.println(toString());
		while(!eqStack.isEmpty()) {
			tempEquation = eqStack.peek();
			tempStack.push(pop()); 
			System.out.println(tempEquation.toString() + "   "+tempEquation.getEquation() +"     " +tempEquation.getPrefix() +"     " + tempEquation.getPostfix() +"     " + tempEquation.getAnswer() +"     " + tempEquation.getBinary() +"     " + tempEquation.getHex());
		}
		while (!tempStack.isEmpty()) {
			eqStack.push(tempStack.pop());
		}
	}
	public Equation getTempEquation() {
		return tempEquation;
	}
}