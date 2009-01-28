/**
 * @class Server
 * @brief Server Test class for deliverable 3
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

        System.out.println("Sending...");

        //TODO we should find something else more interesting to send...
        for(int i = 1; i < 10; i++){
            jms.publish("Hello client, this is message "+i);
            try{
                Thread.sleep(1000);
            } catch(InterruptedException e) {}
        }
        jms.publish("END");

        jms.close();

    }

}