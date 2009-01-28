/**
 * TextListener class - Basic text message listener (from Day 9 Sample Code)
 * @author Emilien Girault
 * @date 1/27/09
 */

package deliverable3;

import javax.jms.*;

public class ClientTextListener implements MessageListener {

    /**
     * Constructor
     */
    public ClientTextListener() {
    }

    /**
     * Called when a message is received
     * TODO will have to change this for future deliverables - print the message on the GUI for example...
     * @param message
     */
    @Override
    public void onMessage(Message message) {
        TextMessage msg = null;
        try {
            if(message instanceof TextMessage){
              msg = (TextMessage)message;
              System.out.println("Received: " + msg.getText());
            }
        }
        catch(JMSException jmse){
            System.err.println("Error when receiving the message: " + jmse.getMessage());
        }
    }

}
