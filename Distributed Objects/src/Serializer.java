import org.jdom2.*;
import org.jdom2.output.XMLOutputter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Collection;


public class Serializer {
	private Class currentClass;
	private Object currentObject;
	private Element rootElement;
	private Element currentRoot;
	private Element classHolder;
	
	// SERIALIZE object passed in, and produce an XML
	public Document serialize(Object objectCreator)
	{
		Element root = new Element("serialized");
		
		rootElement = root;
		
		//Create root element first
		
		for(int i = 0; i < ObjectCreator.objects.length; i++)
		{
			if(ObjectCreator.objects[i] == null) break;
			this.accessClass(ObjectCreator.objects[i], root);
		}
		//then create object one by one
		
		
		DocType type = new DocType("Deserialized");
		Document doc = new Document(root, type);
		
		return doc;
	}
	
	public void accessClass(Object obj, Element root)
	{	
		currentClass = obj.getClass();
		currentRoot = root;
		currentObject = obj;
		
		this.serializeClass();
		
		
	}

	public void serializeClass() 
	{
		
		classHolder = new Element("object");
		
		int id = currentObject.hashCode();
		classHolder.setAttribute("id", String.valueOf(id) );
		classHolder.setAttribute("class", currentClass.getName());
		
		if(currentClass.getDeclaredFields().length > 0)
		{
			//System.out.println("Found Fields");
			this.serializeFields();
		}
		
		currentRoot.addContent(classHolder);
	}
	
	public void serializeFields()
	{
		Field[] fields = currentClass.getDeclaredFields();
		//System.out.println("This class has " + fields.length + " fields.");
		for(Field f : fields) 
		{
			//System.out.println("Adding field info");
			f.setAccessible(true);
			
			Element fieldElement = new Element("field");
			
			if(f.getType().isPrimitive())
			{
				this.serializePrimitiveField(fieldElement, f);
			}else if(f.getType().isArray())
			{
				this.serializeArrayField(fieldElement, f);
			}else if(Collection.class.isAssignableFrom(f.getType()))
			{
				this.serializeCollectionField(fieldElement, f);
			}else {
				this.serializeObjectField(fieldElement, f);
				
			}
		}
		
	}
	
	public void serializePrimitiveField(Element fieldElement, Field f)
	{
		try {
			//System.out.println("Primitive type");
			
			fieldElement.setAttribute("declaringclass", f.getDeclaringClass().getName());
			fieldElement.setAttribute("name", f.getName());
			fieldElement.addContent(new Element("value").setText(String.valueOf(f.getInt(currentObject))));
			classHolder.addContent(fieldElement);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void serializeArrayField(Element fieldElement, Field f)
	{
		//System.out.println("Array type");
		fieldElement.setAttribute("declaringClass", f.getDeclaringClass().getName());
		fieldElement.setAttribute("name", f.getName());
		//classHolder.addContent(fieldElement);
		//store current class info
		Class previousClass = currentClass;
		Object previousObject = currentObject;
		Element previousRootElement = rootElement;
		Element previousRoot = currentRoot;
		Element previousClassHolder = classHolder;
		
		//serialize array info
		Object arrObj = null;
		try {
			arrObj = f.get(currentObject);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int id = arrObj.hashCode();
		
		//Element arrayElement = new Element("object");
		
		
		if(arrObj.getClass().getTypeName().compareTo("int[]") == 0)
		{
			//System.out.println("Integer array");
			Element arrayElement;
			previousRootElement.addContent(arrayElement = new Element("object").setAttribute("length", "10").setAttribute("id", String.valueOf(id))
					.setAttribute("class", f.getType().getName()));
			for(int i = 0; i < 10; i++)
			{
				int amount = ((Library) previousObject).getDonation(i);
				Element ele;
				arrayElement.addContent(ele = new Element("value").addContent(String.valueOf(amount)));
			}
			
		}else if(arrObj.getClass().getTypeName().compareTo("Book[]") == 0)
		{
			//System.out.println("Book Object array");
			for(int i = 0; i<10;i++)
			{
				this.accessClass(((Library) previousObject).getBook(i), previousRootElement);
			}
		}
		
		// retrive previous class info
		currentClass = previousClass;
		currentObject = previousObject;
		rootElement = previousRootElement;
		currentRoot = previousRoot;
		classHolder = previousClassHolder;
		
		
	}
	
	
	public void serializeCollectionField(Element fieldElement, Field f)
	{
		System.out.println("Collection object");
		Class previousClass = currentClass;
		Object previousObject = currentObject;
		Element previousRootElement = rootElement;
		Element previousRoot = currentRoot;
		Element previousClassHolder = classHolder;
		
		Object obj = null;
		try {
			obj = f.get(previousObject);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int id = obj.hashCode();
		fieldElement.setAttribute("declaringClass", f.getDeclaringClass().getName());
		fieldElement.setAttribute("name", f.getName());

		Element collectionElement;
		//System.out.println("Collection");
		fieldElement.addContent(collectionElement = new Element("object").setAttribute("length", "5").setAttribute("id", String.valueOf(id))
					.setAttribute("class", f.getType().getName()));
		for(int i = 0; i < 5; i++)
		{
			String amount = ((Library) previousObject).getEmployees(i);
			Element ele;
			collectionElement.addContent(ele = new Element("collection").addContent(amount));
		}
		
		previousClassHolder.addContent(fieldElement);
		
		currentClass = previousClass;
		currentObject = previousObject;
		rootElement = previousRootElement;
		currentRoot = previousRoot;
		classHolder = previousClassHolder;
		
	}
	
	public void serializeObjectField(Element fieldElement, Field f)
	{
		Class previousClass = currentClass;
		Object previousObject = currentObject;
		Element previousRootElement = rootElement;
		Element previousRoot = currentRoot;
		Element previousClassHolder = classHolder;
		
		Object obj = null;
		try {
			obj = f.get(currentObject);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int id = obj.hashCode();

		//System.out.println("Field is object!");
		fieldElement.setAttribute("declaringClass", f.getDeclaringClass().getName());
		fieldElement.setAttribute("name", f.getName());
		fieldElement.addContent(new Element("reference").addContent(String.valueOf(id)));
		this.accessClass(obj, fieldElement);
		previousClassHolder.addContent(fieldElement);
		
		currentClass = previousClass;
		currentObject = previousObject;
		rootElement = previousRootElement;
		currentRoot = previousRoot;
		classHolder = previousClassHolder;
	}
	
	public Element getRootElement() {
			return rootElement;
	}
	
	public Element getClassHolder()
	{
		return classHolder;
	}
}
