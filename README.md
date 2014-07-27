First run the jar file with the argument "init"
It will create RevocationManager, IssuingManager, OpeningManager files in the project root folder.

Then place those files in the same directory as of the vehicle.communication.system-1.0-SNAPSHOT-jar-with-dependencies.jar and then run the jar with the sending port and listening port as the arguments.

Group signature implementation is taken from
https://code.google.com/p/group-signature-java/
