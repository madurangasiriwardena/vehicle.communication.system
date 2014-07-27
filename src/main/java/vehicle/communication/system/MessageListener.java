package vehicle.communication.system;

import groupsignature.client.Verifier;
import groupsignature.server.IssuingManager;
import groupsignature.server.OpeningManager;
import groupsignature.server.RevocationManager;
import groupsignature.signature.Signature;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MessageListener implements Runnable {

	RevocationManager revoc;
	IssuingManager issue;
	OpeningManager open;
	Verifier verifier;

	ServerSocket serverSocket;
	Socket socket;
	int port;

	protected MessageListener(int port) {
		this.port = port;
		FileInputStream fileIn;
		try {
			fileIn = new FileInputStream("RevocationManager");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			revoc = (RevocationManager) in.readObject();
			in.close();
			fileIn.close();

			fileIn = new FileInputStream("IssuingManager");
			in = new ObjectInputStream(fileIn);
			issue = (IssuingManager) in.readObject();
			in.close();
			fileIn.close();

			fileIn = new FileInputStream("OpeningManager");
			in = new ObjectInputStream(fileIn);
			open = (OpeningManager) in.readObject();
			in.close();
			fileIn.close();

			verifier = new Verifier(issue, open, revoc);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void receive() throws IOException, ClassNotFoundException {
		serverSocket = new ServerSocket(port);
		System.out.println("Server socket created");

		while (true) {
			socket = serverSocket.accept();
			System.out.println("Socket accepted");

			ObjectInputStream in = new ObjectInputStream(
					socket.getInputStream());
			String message = (String) in.readObject();
			Signature signature = (Signature) in.readObject();
			in.close();
			socket.close();
			boolean verified = verifier.verify(message, signature);
			if (verified) {
				System.out
						.println("#############################################################");
				System.out
						.println("--------------- Incomming message --------------------");
				System.out.println(message);
				System.out
						.println("#############################################################");
			}
		}

		// serverSocket.close();
	}

	public static void main(String args[]) throws IOException,
			ClassNotFoundException {
		MessageListener nc = new MessageListener(12345);
		nc.receive();
	}

	@Override
	public void run() {
		try {
			receive();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}

	}

}
