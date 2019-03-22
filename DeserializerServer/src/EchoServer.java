import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;

public class EchoServer 
{
	private ServerSocket serverSocket;
	private Socket clientSocket;
	private PrintWriter out;
	private BufferedReader in;
	
	public String start( int port ) throws IOException 
	{
		serverSocket = new ServerSocket( port );
		clientSocket = serverSocket.accept();
		out = new PrintWriter( clientSocket.getOutputStream(), true );
		in = new BufferedReader( new InputStreamReader( clientSocket.getInputStream() ) );
	 
		String inputLine;
		StringBuilder builder = new StringBuilder();
		while ( ( inputLine = in.readLine() ) != null ) 
		{
			if( "transfer ended 4545".equals( inputLine ) )
			{
				break;
			}
			else
			{
				builder.append( inputLine );
			}
		}
		
		return builder.toString();
	}
	
	public static void main( String[] args ) throws IOException 
	{
	    EchoServer server=new EchoServer();
	    String xml = server.start( 4444 );
	    
		XMLOutputter serializerr = new XMLOutputter();
		SAXBuilder builder = new SAXBuilder();
		Document doc = null;
		try {
			doc = builder.build(new ByteArrayInputStream(xml.getBytes("UTF-8")));
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    Deserializer deserializer = new Deserializer();

	  	HashMap<Integer, Object> hashMap = null;
		try {
			hashMap = (HashMap<Integer, Object>) deserializer.Deserialize(doc);
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		Inspector inspector = new Inspector();
	  	// 2. For-each Loop
	  	for (Integer key : hashMap.keySet()) {
	  		inspector.inspect(hashMap.get(key),false);
	  	}
	}
}
