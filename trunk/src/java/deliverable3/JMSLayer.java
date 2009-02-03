/**
 * @class JMSLayer
 * @brief Provide basic access to JMS methods for Publish/Subscribe
 * @author Emilien Girault
 * @date 1/27/09
 */

package deliverable3;

import javax.naming.*;
import javax.jms.*;


public abstract class JMSLayer {

    /**
     * Glassfish Constants
     */
	public static final String CONNECTION_FACTORY = "jms/BlackjackConnectionFactory";
	public static final String TOPIC = "jms/BlackjackTopic";
	
	protected Context jndiContext;
	protected TopicConnectionFactory cf;
	protected Topic dest;
	protected TopicConnection conn;
	protected TopicSession sess;
	
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
			dest = (Topic)jndiContext.lookup(TOPIC);
		}
		catch(Exception exc) {
			System.err.println("Unable to get a Destination. Msg: " + exc.getMessage());
			System.exit(1);
 		}

        try {
            //Create the connection
            conn = cf.createTopicConnection();
           } catch(JMSException e){
            System.err.println("Unable to create a topic connection");
			System.exit(1);
        }

        try {
            //Create the session
            sess = conn.createTopicSession(false,Session.AUTO_ACKNOWLEDGE);
        } catch(JMSException e){
            System.err.println("Unable to create a topic session.");
			System.exit(1);
        }
	
	}


	/**
	 * Close the connection
	 */
	public void close(){
		//close everything down
		if(conn != null) {
			try {
                conn.close();
            } catch(JMSException je){
                System.err.println("Unable to close the connection: " + je.getMessage());
                System.exit(1);
            }
		}
	}

}