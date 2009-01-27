package deliverable3;

import javax.naming.*;
import javax.jms.*;

public class JMSLayer {

	public static final String CONNECTION_FACTORY = "";
	public static final String TOPIC = "";
	public static final String DESTINATION = "";
	
	
	protected Context jndiContext;
	protected TopicConnectionFactory cf;
	protected Destination dest;
	


	public JMSLayer(){
		// get a JNDI naming context
		try {
			jndiContext = new InitialContext();
		}
		catch(NamingException ne){
			System.err.println("Unable to get a JNDI context for naming");
			System.exit(1);
		}
	  
		// set up a ConnectionFactory and destination
		try {
			cf = (TopicConnectionFactory)jndiContext.lookup(CONNECTION_FACTORY);
		}
		catch(Exception exc) {
			System.err.println("Unable to get a ConnectionFactory. Msg: " + exc.getMessage());
			System.exit(1);
		}
	  
		try{
			dest = (Topic)jndiContext.lookup(DESTINATION);
		}
		catch(Exception exc) {
			System.err.println("Unable to get a Destination. Msg: " + exc.getMessage());
			System.exit(1);
 		 }
	
	}


}