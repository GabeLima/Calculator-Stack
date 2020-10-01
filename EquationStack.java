package HW2;
// Gabriello Lima, 112803276, R01
import java.util.Stack;
public class EquationStack extends Stack {
	/*
	 * This class is fairly simple, all methods are doing essentially what their
	 * method names are implying. Hence, push pushes, pop pops, etc.
	 */
    public EquationStack() {
    }

    public void push(String s) {
        super.push(s);
    }

    public String pop() {
        return super.pop().toString();
    }

    public String peek() {
        return super.peek().toString();
    }

    public boolean isEmpty() {
        return super.isEmpty();
    }

    public int size() {
        return super.size();
    }

}