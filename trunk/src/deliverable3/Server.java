/**
 * Client class - Client Test class for deliverable 3
 * @date 1/27/09
 */

package deliverable3;

public class Server {

    /**
     * Main test program for server
     * @param args
     */
    public static void main(String[] args){

        System.out.println("Starting the server...");
        JMSPublisher jms = new JMSPublisher();

        System.out.println("Sending \"Hello client\"...");
        jms.publish("Hello client!");

    }

}