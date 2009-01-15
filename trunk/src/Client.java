/**
 * RMI Interface for the server
 * @author Eric Kisner, Emilien Girault
 * @date 1/12/09
 */

import java.rmi.*;

public class Client {
    
    /**
     * The server RMI name
     */
    public static String SERVER_NAME = "BlackJackServer";


    public static void main(String[] args){

        System.out.println("coucou");

        try {

        RMIInterface server = (RMIInterface) Naming.lookup(SERVER_NAME);

        } catch(Exception e){
            System.out.println("Client error: "+e.getMessage());
            e.printStackTrace();
        }
    }

}
