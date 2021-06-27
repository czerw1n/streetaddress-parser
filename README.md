FRIDAY interview task - Street Address parser.

## Requirements

For building and running the application you need:

- JDK 11
- Maven

## Building and running the application locally
### build:
```shell
mvn clean package
```
### run:
application takes String as an input argument and prints out result as a JSON to console

fe. for address line: Musterstrasse 45  
```shell
mvn exec:java -Dexec.mainClass=com.czerw1n.friday.StreetAddressParserApp -Dexec.args="'Musterstrasse 45'"
```

### unit tests:

running unit test will result in printing test cases out to console
unit cases can be found in: [data.csv](src/test/resources/data.csv) 
```shell
mvn test
```
