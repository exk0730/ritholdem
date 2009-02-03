/**
 * @class JMSAsyncSubscriber
 * @brief Provide methods to receive JMS messages asynchronously
 * @author Emilien Girault
 * @date 1/27/09
 */

package jms;

import javax.jms.*;

public class JMSAsyncSubscriber extends JMSLayer {
    
	protected TopicSubscriber sub;

    /**
     * Constructor. Initialize the subscriber and start it.
     * @param listener message listener
     */
    public JMSAsyncSubscriber(MessageListener listener){

        //Call parent constructor to initialize connection
        super();

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

        try {
            // start receiving messages
            conn.start();
        } catch(JMSException je){
            System.err.println("Unable to start the connection.");
			System.exit(1);
        }

    }

 
}