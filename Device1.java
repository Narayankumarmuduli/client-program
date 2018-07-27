import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
// import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
// import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;
import java.util.Scanner;
public class Device1 implements MqttCallback {

	MqttClient client;
    String broker = "tcp://localhost";
    String clientid = "001";
    String sub_topic = "device2";
    String pub_topic = "device1";
    // String tmpDir = System.getProperty("java.io.tmpdir");
    // MqttDefaultFilePersistence dataStore = new MqttDefaultFilePersistence(tmpDir);

	public Device1() {
		try {
            Scanner scan =new Scanner(System.in);
            System.out.println("broker =" + broker + "\nclientid = " + clientid);
            System.out.println("\nsub_topic = " + sub_topic + "\npub_topic = " + pub_topic);
            System.out.println("here");
            client = new MqttClient(broker, clientid);
            client.connect();
            client.setCallback(this);
			client.subscribe(sub_topic);
			MqttMessage message = new MqttMessage();
			while(true) {
				String msg=scan.nextLine();
				message.setPayload(msg.getBytes());
				client.publish(pub_topic, message);
				if(msg.equals("QUIT"))
					break;
				}
			scan.close();
			client.disconnect();
		} catch (MqttException e) {
				e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		new Device1();
    
	}

	@Override
	public void connectionLost(Throwable cause) {
	//System.out.println(cause);

	}

	@Override
	public void messageArrived(String topic, MqttMessage message)throws Exception {
		System.out.println(message);   
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		//System.out.println(token);

	}
}