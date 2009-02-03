/**
 * @class JMSPublisher
 * @brief Provide methods to publish messages through JMS
 * @author Emilien Girault
 * @date 1/27/09
 */

package deliverable3;

import javax.jms.*;


public class JMSPublisher extends JMSLayer {

	protected TopicPublisher pub;
	

    /**
     * Constructor. Initialize the publisher.
     */
	public JMSPublisher(){

        //Call parent constructor to initialize connection
		super();

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
		} catch(JMSException je) {
			System.err.println("Unable to send the message: " + je.getMessage());
			System.exit(1);
		}
		
	}


}