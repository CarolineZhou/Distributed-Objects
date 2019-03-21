import static java.lang.System.out;

import org.jdom2.*;
import java.lang.reflect.*;
import java.util.*;
public class Deserializer {
	private Class currentClass;
	private Object currentObject;
	private Element currentRoot;
	private Element classHolder;
	private HashMap<Integer,Object> hashMap = new HashMap<Integer,Object>();

	public Object Deserialize(Document doc) throws NoSuchMethodException, SecurityException {
		currentRoot = doc.getRootElement();
		List<Element> elements = currentRoot.getChildren();
		
		//get the class of the object
		for(int i = 0; i < elements.size(); i++)
		{
			classHolder = elements.get(i);
			Attribute objAttr = classHolder.getAttribute("class");
			String objAttrName = classHolder.getAttributeValue(objAttr.getName());
			System.out.println("Attribute: "+ objAttr);
			System.out.println("Attribute name: " + objAttrName);
			
			try {
				currentClass = Class.forName(objAttrName);
			} catch(ClassNotFoundException e) {
				e.printStackTrace();
			}
			
			listChildren(currentRoot, 0);
			
			//createInstance(currentClass);
			
			//get hashcode and put this object in to the hashtable
			int id = currentObject.hashCode();
			hashMap.put(id, currentObject);
			
			//When there is a field. 
			
		}
		return hashMap;
	}
	
	
	
	public static void listChildren(Element current, int depth) {

		printSpaces(depth);
		System.out.println(current.getName());
		List children = current.getChildren();
		Iterator iterator = children.iterator();
		while (iterator.hasNext()) {
			Element child = (Element) iterator.next();
			listChildren(child, depth+1);
		}
	}
	
	private static void printSpaces(int n) {

		for (int i = 0; i < n; i++) {
			System.out.print(' '); 
		}

	}
	
}
