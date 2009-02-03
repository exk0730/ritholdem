/**
 * @class TextListener
 * @brief Basic text message listener (from Day 9 Sample Code)
 * @author Emilien Girault
 * @date 1/27/09
 */

package jms;

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
        try {
            if(message instanceof TextMessage){
              String msg = ((TextMessage)message).getText();

              //TODO this is just for deliv 3
              if(msg.equals("END")) {
                  System.exit(0);
              }
              
              System.out.println("Received: " + msg);
            }
        }
        catch(JMSException jmse){
            System.err.println("Error when receiving the message: " + jmse.getMessage());
        }
    }

}
