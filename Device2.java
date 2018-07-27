import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import java.util.Scanner;

public class Device2 implements MqttCallback {

    MqttClient client;
    // the broker I used is mosquitto
    String broker = "tcp://localhost";
    // to be changed accoring to ip address when running on different devices        
    String clientid = "002";
    // there could be n number of clients but their client id should be unique
    String subscribe_to_topic = "device1";
    String publish_topic = "device2";
    // both the programs are subscribed to each other
    // both are to be runned parallely
    MemoryPersistence persistence = new MemoryPersistence();

    public Device2() {
        try {
            Scanner scan = new Scanner(System.in);
            System.out.println("broker ="+broker+"\nclientid = "+clientid);
            System.out.println("subscribe_to_topic = "+ subscribe_to_topic+"\npublish_topic = "+publish_topic);
            client = new MqttClient(broker, clientid, persistence);
            client.connect();
            client.setCallback(this);
            client.subscribe(subscribe_to_topic);
            MqttMessage message = new MqttMessage();
            System.out.println("waiting for messages \n Enter any text message to send via MQTT Protocol\nEnter\"QUIT\" to Terminated");
            while (true) {
                String msg = scan.nextLine();
                message.setPayload(msg.getBytes());
                client.publish(publish_topic, message);
                if (msg.equals("QUIT"))
                    break;
            }
            scan.close();
            client.disconnect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        new Device2();

    }

    @Override
    public void connectionLost(Throwable cause) {
        System.out.println("connection lost because " + cause);

    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        System.out.println("received message ---------> " + message);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        // System.out.println(token);

    }
}