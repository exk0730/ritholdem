/**
 * JMSLayer class - Provide basic access to JMS methods
 * @author Emilien Girault
 * @date 1/27/09
 */

package deliverable3;

import javax.naming.*;
import javax.jms.*;


public abstract class JMSLayer {

    /**
     * TODO Constants you need to change
     */
	public static final String CONNECTION_FACTORY = "";
	public static final String TOPIC = "";
	public static final String DESTINATION = "";
	
	
	protected Context jndiContext;
	protected TopicConnectionFactory cf;
	protected Topic dest;
	
    /**
     * Constructor. Initialize the connection to Glassfish.
     */
	public JMSLayer(){
		//Get a JNDI naming context
		try {
			jndiContext = new InitialContext();
		}
		catch(NamingException ne){
			System.err.println("Unable to get a JNDI context for naming");
			System.exit(1);
		}
	  
		//Set up a ConnectionFactory and destination
		try {
			cf = (TopicConnectionFactory)jndiContext.lookup(CONNECTION_FACTORY);
		}
		catch(Exception exc) {
			System.err.println("Unable to get a ConnectionFactory. Msg: " + exc.getMessage());
			System.exit(1);
		}

        //Lookup the topic
		try{
			dest = (Topic)jndiContext.lookup(DESTINATION);
		}
		catch(Exception exc) {
			System.err.println("Unable to get a Destination. Msg: " + exc.getMessage());
			System.exit(1);
 		}
	
	}


}