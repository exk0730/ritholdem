/**
 * Exception thrown when the connection is lost between client and server
 */
package server;

public class NotConnectedException extends Exception {

	String msg = "Lost the connection with server.";

	@Override
	public String getMessage(){
		return msg;
	}
}
