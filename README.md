# log-reader

You can build, run and test by the following command on windows:

gradlew.bat build && java -jar build\libs\json-log-reader-1.0.1-SNAPSHOT.jar [path to the log file]

Access the flagged file entry in DB using the rest end point in your browser: http://localhost:8181/read/all 
(or using curl command example: 'curl http://localhost:8181/read/all')

You should see output like this:
[{"createdDate":"2018-08-21T19:19:17.864+0000","id":"scsmbstgra","eventDuration":5,"type":null,"host":"12345","alert":true},
{"createdDate":"2018-08-21T19:19:17.895+0000","id":"scsmbstgrc","eventDuration":8,"type":null,"host":null,"alert":true}]