/**
 * Client class - Client Test class for deliverable 3
 * @date 1/27/09
 */

package deliverable3;

/**
 *
 * @author Trance
 */
public class Client {

    /**
     * Loop for a very longtime
     */
    public static void waitForMessages(){
        try{
            Thread.sleep(100000);
        }
        catch(InterruptedException ie){ }
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
        
    }

}
