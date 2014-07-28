Building and running the code
Go to the project folder
run the command
mvn clean install
It will create a jar file vehicle.communication.system-1.0-SNAPSHOT-jar-with-dependencies.jar in the target folder.
To run the below commands IssuingManager, OpeningManager and RevocationManager files will have to be in the same directory as the command is running.
To save the IssuingManager, OpeningManager and RevocationManager objects to files run the command (this will create 3 folders with the class names of the objects in the root directory of the project)
java -jar vehicle.communication.system-1.0-SNAPSHOT-jar-with-dependencies.jar init

To create a user run the command
java -jar vehicle.communication.system-1.0-SNAPSHOT-jar-with-dependencies.jar <user id> <sending port> <listening port>
To demonstrate the system, to users will have to be created using interchanged listening and sending ports.


Group signature implementation is taken from
https://code.google.com/p/group-signature-java/
