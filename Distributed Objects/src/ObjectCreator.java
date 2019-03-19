import java.util.Scanner;

public class ObjectCreator {
	private Scanner input = new Scanner(System.in);
	Object obj[];
	Class classObject[];
	
	public boolean displayObjectSelections(int currentObjectNumber) {
		int selection = 0;
		boolean doneWithObject = false;
		
		while(doneWithObject != true){
			System.out.println("OBJECT #" + currentObjectNumber);
			if(currentObjectNumber != 1){
				System.out.println("Would you like to create another object?");
			}
			System.out.print(
					"Please choose an object\n" +
					"1) Library Object\n" +
					"2) Book Object\n" +
					"3) Owner Object\n" +
					"Enter the number here please : ");
				selection = input.nextInt();
				System.out.println(selection);
				//input.nextLine(); // to stop input skipping
				if(selection == 1)
				{
					doneWithObject = fieldsSelections();
				}else if(selection == 2) {
					doneWithObject = fieldsSelections();
				}else if(selection == 3) {
					doneWithObject = fieldsSelections();
				}else {
					doneWithObject = false;
				}
				
		}
		
		return doneWithObject;
	}


	private boolean fieldsSelections() {
		boolean doneWithFields = false;
		boolean changeFieldValue = false;
		int chosenField;
		String changeFieldValueString;
		
		while(doneWithFields == false) {
			Scanner change = new Scanner(System.in);
			System.out.println("Would you like to change any of the object's field value?"
								+ "Enter \"YES\" OR \"NO\" please!");
			changeFieldValueString = change.nextLine();
			if(changeFieldValueString.equals("YES")){
				doneWithFields = true;
			}else if(changeFieldValueString.equals("NO")){
				doneWithFields = true;
			}else {
				System.out.println("Please enter all upper case letter for \"YES\" OR \"NO\" please!");
				doneWithFields = false;
			}
			change.close();
		}
		
		return doneWithFields;
	}
	
	

}
