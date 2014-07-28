package vehicle.communication.system;

import groupsignature.client.User;
import groupsignature.server.IssuingManager;
import groupsignature.server.OpeningManager;
import groupsignature.server.RevocationManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class Initializer {
	public static void main(String args[]) {

		RevocationManager revoc = new RevocationManager();
		IssuingManager issue = new IssuingManager(revoc);
		OpeningManager open = new OpeningManager(issue, revoc);		
		
		FileOutputStream fileOut;
		try {
//			File dir = new File("src/init-data");
//			dir.mkdir();
			fileOut = new FileOutputStream("RevocationManager");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(revoc);
			out.close();
			fileOut.close();
			
			fileOut = new FileOutputStream("IssuingManager");
			out = new ObjectOutputStream(fileOut);
			out.writeObject(issue);
			out.close();
			fileOut.close();	

			fileOut = new FileOutputStream("OpeningManager");
			out = new ObjectOutputStream(fileOut);
			out.writeObject(open);
			out.close();
			fileOut.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
