/**
 * Thread called when the server is stopped
 */
package server;

import java.rmi.Naming;

public class ServerExitThread extends Thread {

	/**
	 * Called when thread starts
	 */
	@Override
	public void run(){
		try {
			Naming.unbind(Server.SERVER_NAME);
            Server.instance().updateLastServerReboot();
		} catch (Exception ex){
            System.err.println("Error unbinding server from ServerExitThread: " + ex.getMessage());
        }
	}
}
