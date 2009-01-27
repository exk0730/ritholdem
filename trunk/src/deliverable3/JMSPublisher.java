package deliverable3;

import javax.jms.*;

public class JMSPublisher extends JMSLayer {

	protected TopicConnection conn;
	protected TopicSession sess;
	protected TopicPublisher pub;
	

	public JMSPublisher(){
		super();
		
		// create the connection
		conn = cf.createTopicConnection();
		  
		// create the session
		sess = conn.createTopicSession(false,Session.AUTO_ACKNOWLEDGE);
		  
		// create a producer
		pub = sess.createPublisher(dest);

	}
	

	/**
	 * 
	 */
	public void publish(String msg) {
		try {
					  
			//create a text message
			TextMessage text = sess.createTextMessage();
			text.setText(msg);
			System.out.println("Sending " + text.getText());
			pub.publish(text);
			
		}
		catch(JMSException je) {
			System.out.println("Unable to close the connection: " + je.getMessage());
			System.exit(1);
		}
		
	}
	
	/**
	 *
	 */
	public void close(){				  
		//close everything down
		if(conn != null) {
			conn.close();
		}
	}



}