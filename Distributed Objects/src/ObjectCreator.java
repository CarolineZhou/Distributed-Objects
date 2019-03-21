import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Scanner;

public class ObjectCreator {
	Scanner input;
	private boolean doneWithObject = false;
	public static Object objects[] = new Object[10];
	
	
	public boolean displayObjectSelections(int currentObjectNumber) {
		int selection = 0;
		input = new Scanner(System.in);
		
		
		while(doneWithObject != true){
			Object object;
			Class classObject;
			if(currentObjectNumber != 1){
				System.out.println("Would you like to create another object?");
			}
			System.out.println("OBJECT #" + currentObjectNumber);
			System.out.print(
					"Please choose an object\n" +
					"1) Library Object\n" +
					"2) Book Object\n" +
					"3) Owner Object\n" +
					"4) No thanks\n" +
					"Enter the number here please : ");
				selection = input.nextInt();
				System.out.println(selection);
				input.nextLine(); // to stop input skipping
				if(selection == 1)
				{
					object = new Library();
					classObject = object.getClass();
					this.accessField(object,currentObjectNumber);
					currentObjectNumber++;
				}else if(selection == 2) {
					object = new Book();
					classObject = object.getClass();
					this.accessField(object,currentObjectNumber);
					currentObjectNumber++;
				}else if(selection == 3) {
					object = new Owner();
					classObject = object.getClass();
					this.accessField(object,currentObjectNumber);
					currentObjectNumber++;
				}else if (selection == 4){
					doneWithObject = true;
				}else {
					doneWithObject = false;
				}
				
		}
		input.close();
		
		return doneWithObject;
	}
	
	public void accessField(Object object, int currentObjectNumber)
	{
		fieldsSelections(object);
		objects[currentObjectNumber] = new Object();
		objects[currentObjectNumber] = object;
	}

	private void fieldsSelections(Object object) {
		String changeFieldValueString;
		boolean more = true;
		
		while(more == true)
		{
			System.out.println("########################################");
			System.out.println("Would you like to change object's field value?\n"
								+ "Enter \"YES\" OR \"NO\" please!");
			changeFieldValueString = input.nextLine();
			if(changeFieldValueString.equals("YES")){
				Field chosen = fieldOptions(object);
				if(chosen != null)
				{
					changeField(chosen,object);
				}
			}else if(changeFieldValueString.equals("NO")){
				more = false;
			}else {
				System.out.println("Please enter all upper case letter for \"YES\" OR \"NO\" please!");
				more = true;
			}
		}
	}
	
	
	private Field fieldOptions(Object object)
	{
		Field fieldArray[] = object.getClass().getDeclaredFields(); 
		Field chosen = null;
		int selection = -1 ;
		
		System.out.println("Please choose a field from below");
		for(int i = 0; i < fieldArray.length; i++)
		{
			System.out.println( i+1 + ") " + fieldArray[i].getName() + "\n");
		}
		
		while(selection == -1)
		{
			System.out.print("Enter the number here please : ");
			if(input.hasNextLine())
			{
				selection = input.nextInt();
				input.nextLine();
				chosen = fieldArray[selection-1];
			}
		}
		return chosen;
	}
	
	
	private void changeField(Field chosen, Object object)
	{
		String changeTo = null;
		int valueEntered = 0;
		
		while(valueEntered == 0)
		{			
				//check the kind of input
				if(chosen.getType().isPrimitive()){
					System.out.println("You choose " + chosen.getName() 
					+ "\nType: " + chosen.getType() 
					+ "\nModifier: " + Modifier.toString(chosen.getModifiers()));
					
					System.out.print("Enter new value: ");
					if(input.hasNextLine()){
						changeTo = input.nextLine();
					}
					valueEntered = 1;
					
					System.out.println("It's a interger class field");
					try {
						chosen.setAccessible(true);
						chosen.setInt(object, Integer.parseInt(changeTo));
						System.out.println("Integer value renewed");
					} catch (NumberFormatException e) {
						System.out.println("Number Format error");
						valueEntered = 0;
					} catch (IllegalArgumentException e) {
						System.out.println("Illegal Argument error");
						valueEntered = 0;
					} catch (IllegalAccessException e) {
						System.out.println("Illegal Access error");
						valueEntered = 0;
					}
					
					
				}else if(chosen.getType().isArray()){
					System.out.println("array field");
					//ARRAY
					chosen.setAccessible(true);
					try {
						System.out.println("You choose " + chosen.getName() 
						+ "\nType: " + (chosen.get(object)).getClass().getTypeName()
						+ "\nModifier: " + Modifier.toString(chosen.getModifiers()));
					} catch (IllegalArgumentException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IllegalAccessException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					Object arrObj = null;
					try {
						arrObj = chosen.get(object);
						valueEntered = 1;
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						valueEntered = 0;
						System.out.println("Illegal Argument");
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						valueEntered = 0;
						System.out.println("Illegal Access");
					}
					
					
					// Changing array element
					try {
						if((chosen.get(object)).getClass().getTypeName().compareTo("int[]") == 0){
							System.out.println("primitive int array");
							boolean arrayChangeReceived = false;
							while(arrayChangeReceived == false)
							{
								System.out.print("Index of the element you would like to change please:");
								if(input.hasNextInt())
								{
									int index = input.nextInt();
									System.out.print("New Integer Value: ");
									if(input.hasNextInt())
									{
										arrayChangeReceived = true;
										int newValue = input.nextInt();
										System.out.println("Index " + index + " is now "
												+ newValue);
										((Library) object).setDonation(index,newValue);
										System.out.println("SUCCESS! new value: "+ ((Library) object).getDonation(index));
									}
									
								}else {
									System.out.println("Enter integer only please");
								}
							}
							
						}else if((chosen.get(object)).getClass().getTypeName().compareTo("Book[]") == 0){
							System.out.println("book array");
							boolean arrayChangeReceived = false;
							while(arrayChangeReceived == false)
							{
								System.out.print("Index of the element you would like to change please:");
								if(input.hasNextInt())
								{
									int index = input.nextInt();
									arrayChangeReceived = true;
									//access the object for the specific index on array
									Book[] books = ((Library) object).getBooks();
									System.out.println("Object: " + books[index].toString() );
									
									fieldsSelections(((Library) object).getBook(index));
									
									
								}else {
									System.out.println("Enter integer only please");
								}
							}	
						}
					}catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						valueEntered = 0;
						System.out.println("Illegal Argument");
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						valueEntered = 0;
						System.out.println("Illegal Access");
					}
				}else if(Collection.class.isAssignableFrom(chosen.getType())) {
						//COLLECTION FIELD
						System.out.println("Collection Field");
						valueEntered = 1;
						boolean arrayChangeReceived = false;
						while(arrayChangeReceived == false)
						{
							System.out.print("Index of the element you would like to change please:");
							if(input.hasNextInt()){
								int index = input.nextInt();
								arrayChangeReceived = true;
								System.out.print("Please enter new name for employees: ");
								if(input.hasNextLine()){
									String name = input.nextLine();
									input.nextLine();
									((Library) object).setEmployees(index,name);
								}else {
									System.out.println("error");
									valueEntered = 0;
								}
							}else {
								System.out.println("Enter integer only please");
								valueEntered = 0;
							}
						}
						
						
				}else{
						//OBJECT FILED
						System.out.println("object field");
						try 
						{
							//Create the object field instance
							Object obj = chosen.getType().getConstructor().newInstance();
					
							//Give option to change the fields of the object
							fieldsSelections(obj);
							
							//give a random number to break from this while loop
							valueEntered = 1;
						} 
						catch (Exception e) 
						{
							e.printStackTrace();
						}	
				}
		}
	}

}
