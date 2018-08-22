# log-reader
You can build, test and run by the following command on windows:

build:
gradlew.bat build

test:
gradlew.bat test

run:
java -jar build\libs\demo-0.1.0.jar [path to the log file]
Example: java -jar build\libs\demo-0.1.0.jar samples\sample.log

Access the flagged file entry in DB using the rest end point in your browser: http://localhost:8181/read/all 
(or using curl command example: 'curl http://localhost:8181/read/all')

You should see output like this:
[{"createdDate":"2018-08-21T19:19:17.864+0000","id":"scsmbstgra","eventDuration":5,"type":null,"host":"12345","alert":true},
{"createdDate":"2018-08-21T19:19:17.895+0000","id":"scsmbstgrc","eventDuration":8,"type":null,"host":null,"alert":true}]