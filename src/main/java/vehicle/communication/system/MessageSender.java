package vehicle.communication.system;

import groupsignature.client.User;
import groupsignature.server.IssuingManager;
import groupsignature.server.OpeningManager;
import groupsignature.server.RevocationManager;
import groupsignature.signature.Signature;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class MessageSender implements Runnable {
	Socket socket;
	ObjectOutputStream out;
	RevocationManager revoc;
	IssuingManager issue;
	OpeningManager open;
	User user;
	String host;
	int port;

	public MessageSender(String host, int port) {
		// Exists only to defeat instantiation.
		this.host = host;
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

			user = new User("User2", issue, open, revoc);
			user.join();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	public void connect(String host, int port) throws UnknownHostException,
			IOException {
		socket = new Socket(host, port);
		out = new ObjectOutputStream(socket.getOutputStream());
	}

	public void send(Object signature) throws IOException {
		try {

			out.writeObject(signature);
			out.flush();
		} catch (IOException e) {
			throw e;
		}

	}

	public void close() throws IOException {

		out.close();
		socket.close();
	}

	public static void main(String args[]) throws IOException {
		MessageSender nc = new MessageSender("127.0.0.1", 12346);
		nc.connect("127.0.0.1", 12346);
		Scanner keyboard = new Scanner(System.in);
		System.out.println("enter an integer");
		String myint;
		while (!(myint = keyboard.next()).equals("exit")) {
			nc.send(myint);
		}
		keyboard.close();
		nc.close();
	}

	@Override
	public void run() {
		try {
			Scanner userInput = new Scanner(System.in);
			String message;

			while (!(message = userInput.nextLine()).equalsIgnoreCase("exit")) {
				Signature signature = user.sign(message);
				connect(host, port);
				send(message);

				send(signature);
			}
			userInput.close();

			System.exit(0);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
