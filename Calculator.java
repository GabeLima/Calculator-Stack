package HW2;
// Gabriello Lima, 112803276, R01
import java.util.EmptyStackException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Calculator{
	public static void main(String[] args) {
		Equation eqObj = new Equation();
		boolean quitFlag = false;
		Scanner stdin = new Scanner(System.in);
		HistoryStack hsObj = new HistoryStack();
		while (!quitFlag) {
			System.out.println("Welcome to calculat0r.\r\n" + 
					"\r\n" + 
					"[A] Add new equation\r\n" + 
					"[F] Change equation from history\r\n" + 
					"[B] Print previous equation\r\n" + 
					"[P] Print full history\r\n" + 
					"[U] Undo\r\n" + 
					"[R] Redo\r\n" + 
					"[C] Clear history\r\n" + 
					"[Q] Quit ");
			System.out.println("Select an option: ");
			/*
			 * 
			 * @param posChange
			 * references the position the user wishes to change
			 * 
			 * @param secondQuitFlag
			 * references whether or not the user wants to quit altering
			 * equations under the case("f")
			 * 
			 * @change
			 * references what the actual change the user is requesting
			 * 
			 */
			try {
				String selection = stdin.next().toLowerCase();
				switch(selection) {
					case("a"):
						System.out.print("Please enter an equation "
						  + "(in-fix notation):");
						stdin.nextLine();
						String newEq = stdin.nextLine();
						Equation thisEq;
						hsObj.push(thisEq = new Equation(newEq));
						if(thisEq.isBalanced()) {
							System.out.println("The equation is balanced and"
							  + " the answer is " + thisEq.getAnswer());
						}
						else
							System.out.println("This equation is not balanced");
						break;
					case("f"):
						int posChange = 0;
						boolean secondQuitFlag = false;
						System.out.print("Which equation would you like to "
						  + "change? ");
						int change = stdin.nextInt();
						System.out.println("Equation at position " + change + 
						  ": " + hsObj.getEquation(change).getEquation());
						do {
							System.out.println("What would you like to do to "
							  + "the equation (Replace / remove / add)?");
							stdin.nextLine();
							String answer = stdin.nextLine();
							if (answer.toLowerCase().equals("replace")) {
								System.out.println("What position would you "
								  + "like to change? ");
								posChange = stdin.nextInt();
								String tempString = "";
								String equation = hsObj.getEquation(change).getEquation();
								System.out.println("What position would you "
								  + "like to replace it with? ");
								stdin.nextLine();
								String replacement = stdin.nextLine();
								for (int i = 0; i < equation.length(); i ++){
									if(i == posChange - 1){
										tempString += "" + replacement;
									}
									else
										tempString += equation.charAt(i);
								}
								hsObj.getEquation(change).setEquation(tempString);
							}
							else if (answer.toLowerCase().equals("remove")) {
								System.out.println("What position would you "
								  + "like to remove? ");
								posChange = stdin.nextInt();
								String tempString = "";
								String equation = hsObj.getEquation(change).getEquation();
								for (int i = 0; i < equation.length(); i ++){
									if(i == posChange - 1){
										continue;
									}
									else
										tempString += equation.charAt(i);
								}
								hsObj.getEquation(change).setEquation(tempString);
							}else if (answer.toLowerCase().equals("add")) {
								System.out.println("What position would you like"
								  + " to add something? ");
								posChange = stdin.nextInt();
								System.out.println("What position would you"
								  + " like to add? ");
								stdin.nextLine();
								String add = stdin.nextLine();
								String tempString = "";
								String equation = hsObj.getEquation(change).getEquation();
								for (int i = 0; i < equation.length(); i ++){
									if(i == posChange - 1){
										tempString+= add;
										tempString += equation.charAt(i);
									}
									else
										tempString += equation.charAt(i);
								}
								hsObj.getEquation(change).setEquation(tempString);
							}
							System.out.println("Equation: " + 
							  hsObj.getEquation(change).getEquation());
							System.out.println("Would you like to make any "
							  + "more changes?");
							if (stdin.nextLine().toLowerCase().charAt(0) == 'n') {
								secondQuitFlag = true;
							}
						}while(secondQuitFlag == false);
						break;
					case("b"):
						hsObj.printPrevEquation();
						break;
					case("p"):
						hsObj.printAllEquations();
						break;
					case("u"):
						hsObj.undo();
						System.out.println("The equation " + 
						  hsObj.getTempEquation().getEquation() +
						  " has been successfully undone");
						break;
					case("r"):
						hsObj.redo();
					System.out.println("The equation " +
					  hsObj.getTempEquation().getEquation() + 
					  " has been successfully redone");
						break;
					case("c"):
						System.out.println("Resetting Calculator...");
						for (int i = hsObj.size() - 1; i >=0; i --) {
							hsObj.removeEquation(i + 1);
						}
						break;
					case("q"):
						quitFlag = true;
						break;
				}
			}catch(NotBalancedException E) {
				System.out.println(E.getMessage());
			}catch(NothingToRedoException E) {
				System.out.println(E.getMessage());
			}catch(NothingToUndoException E) {
				System.out.println(E.getMessage());
			}catch(InputMismatchException E) {
				System.out.println("Please make sure you enter Input correctly");
			}catch(StringIndexOutOfBoundsException E) {
				System.out.println("Make sure to add parenthesis accordingly");
			}catch(EmptyStackException E) {
				System.out.println("Make sure you enter the equation in proper "
				  + "format");
			}catch(Exception E) {
				System.out.println("Congrats you broke the program in a way I "
				  + "couldn't imagine.");
			}
			
			
			
			
			
			
		}
		stdin.close();
	}
}