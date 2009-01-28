/**
 * JMSPublisher class - Provide methods to publish messages through JMS
 * @author Emilien Girault
 * @date 1/27/09
 */

package deliverable3;

import javax.jms.*;


public class JMSPublisher extends JMSLayer {

	protected TopicConnection conn;
	protected TopicSession sess;
	protected TopicPublisher pub;
	

    /**
     * Constructor
     */
	public JMSPublisher(){

        //Call parent constructor to initialize connection
		super();

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

        try {
            //Create a producer
            pub = sess.createPublisher(dest);
        } catch(Exception e){
            System.err.println("Unable to create a publisher.");
			System.exit(1);
        }
	}
	

	/**
	 * Publish a text message
     * @param msg message to send
	 */
	public void publish(String msg) {
		try {		  
			//create a text message
			TextMessage text = sess.createTextMessage();
			text.setText(msg);
			pub.publish(text);
			
		}
		catch(JMSException je) {
			System.err.println("Unable to send the message: " + je.getMessage());
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