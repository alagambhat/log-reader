# log-reader

You can build, run and test by the following command on windows:

gradlew.bat build && java -jar build\libs\json-log-reader-1.0.1-SNAPSHOT.jar [path to the log file]

Access the flagged file entry in DB using the rest end point in your browser: http://localhost:8181/read/fetchall 
(or using curl command example: 'curl http://localhost:8181/read/fetchall')