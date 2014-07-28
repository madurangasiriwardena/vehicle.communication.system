package vehicle.communication.system;

public class Main {
	public static void main(String args[]) {
		int send;
		int receive;
		String user;
		if(args.length == 1 && args[0].equalsIgnoreCase("init")){
			Initializer.main(null);
		}
		else if (args.length == 3) {
			user = args[0];
			send = Integer.parseInt(args[1]);
			receive = Integer.parseInt(args[2]);

			MessageSender ms = new MessageSender("127.0.0.1", send, user);
			MessageListener ml = new MessageListener(receive);

			Thread tms = new Thread(ms);
			Thread tml = new Thread(ml);

			tms.start();
			tml.start();
		}

	}
}
