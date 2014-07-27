package vehicle.communication.system;

import groupsignature.client.User;
import groupsignature.client.Verifier;
import groupsignature.server.IssuingManager;
import groupsignature.server.OpeningManager;
import groupsignature.server.RevocationManager;
import groupsignature.signature.Signature;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Verify {
	public static void main(String args[]) {

		RevocationManager revoc;
		IssuingManager issue;
		OpeningManager open;

		FileInputStream fileIn;
		try {
			fileIn = new FileInputStream("obj/RevocationManager");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			revoc = (RevocationManager) in.readObject();
			in.close();
			fileIn.close();

			fileIn = new FileInputStream("obj/IssuingManager");
			in = new ObjectInputStream(fileIn);
			issue = (IssuingManager) in.readObject();
			in.close();
			fileIn.close();

			fileIn = new FileInputStream("obj/OpeningManager");
			in = new ObjectInputStream(fileIn);
			open = (OpeningManager) in.readObject();
			in.close();
			fileIn.close();

			Signature signature;
			String message = "Groupe signature";

			fileIn = new FileInputStream("obj/Signature");
			in = new ObjectInputStream(fileIn);
			signature = (Signature) in.readObject();
			in.close();
			fileIn.close();

			Verifier verifier = new Verifier(issue, open, revoc);
			verifier.verify(message, signature);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}
}
