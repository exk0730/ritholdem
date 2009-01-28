/**
 * JMSAsyncSubscriber class - Provide methods to receive JMS messages asynchronously
 * @author Emilien Girault
 * @date 1/27/09
 */

package deliverable3;

import javax.jms.*;

public class JMSAsyncSubscriber extends JMSLayer {
    
    protected TopicConnection conn;
	protected TopicSession sess;
	protected TopicSubscriber sub;


    public JMSAsyncSubscriber(MessageListener listener){

        //Call parent constructor to initialize connection
        super();

        try {
            //Create the connection
            conn = cf.createTopicConnection();
        } catch(JMSException e){
            System.err.println("Unable to create a topic connection.");
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
            sub = sess.createSubscriber(dest);
        } catch(JMSException e){
            System.err.println("Unable to create a subscriber.");
			System.exit(1);
        }

        try {
            //Set the message listener
            sub.setMessageListener(listener);
        } catch(JMSException je){
            System.err.println("Unable to set the message listener.");
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