/**
 * @class Client
 * @brief Client Test class for deliverable 3
 * @date 1/27/09
 */

package deliverable3;

public class Client {

    /**
     * Wait for messages
     */
    public static void waitForMessages(){
        // loop to create and send the messages
        int counter = 0;
        while(true) {
            try {
                Thread.sleep(1000);
            } catch(InterruptedException e) {}
            counter++;
            if(counter > 100) break;
        }

    }


   /**
    * Main test program for client
    * @param args
    */
    public static void main(String[] args){

        System.out.println("Starting the client...");

        JMSAsyncSubscriber jms = new JMSAsyncSubscriber(new ClientTextListener());

        System.out.println("Waiting for messages...");

        waitForMessages();

        jms.close();

    }

}
