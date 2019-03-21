import java.util.ArrayList;

public class Library {
	private Owner owner;
	private Book[] books;
	private int[] donations;
	private ArrayList<String> employees;
	
	public Library()
	{
		owner = new Owner();
		books = new Book[10];
		donations = new int[10];
		
		
		employees = new ArrayList<String>(5);
		for(int i = 0; i < 10; i++)
		{
			donations[i] = 0;
			books[i] = new Book();
			if (i<5)
			{
				employees.add(" ");
			}
		}
	}
	
	
	public void setEmployees(int index,String name)
	{
		employees.set(index, name);;
	}
	
	public String getEmployees(int index)
	{
		return employees.get(index);
	}
	
	public void donationAmountChange(int index, int amount)
	{
		if(index < donations.length)
		{
			donations[index] = amount;
		}else {
			System.out.println("NO SUCH DONATION");
		}
	}
	
	public void setDonation(int index, int amount)
	{
		donations[index] = amount;
	}
	
	public int getDonation(int index)
	{
		return donations[index];
	}
	
	public Book getBook(int index)
	{
		return books[index];
	}
	
	public Book[] getBooks()
	{
		return books;
	}
}
