package vehicle.communication.system;

public class Main {
	public static void main(String args[]) {
		int send;
		int receive;
		if (args.length == 2) {
			send = Integer.parseInt(args[0]);
			receive = Integer.parseInt(args[1]);

			MessageSender ms = new MessageSender("127.0.0.1", send);
			MessageListener ml = new MessageListener(receive);

			Thread tms = new Thread(ms);
			Thread tml = new Thread(ml);

			tms.start();
			tml.start();
		}

	}
}
