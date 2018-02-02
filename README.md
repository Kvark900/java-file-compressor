# java-file-compressor
Spring MVC web app for file compression/decompression  with Java API

## Requirements

* JDK 6+

  Java 6 is required, go to [Oracle Java website](http://java.oracle.com) to download it and install into your system. 
 
  Optionally, you can set **JAVA\_HOME** environment variable and add *&lt;JDK installation dir>/bin* in your **PATH** environment variable.

* Apache Maven

  Download the latest Apache Maven from [http://maven.apache.org](http://maven.apache.org), and uncompress it into your local system. 

  Optionally, you can set **M2\_HOME** environment varible, and also do not forget to append *&lt;Maven Installation dir>/bin* your **PATH** environment variable.  
  
## Running the project
Use the maven-embedded-glassfish-plugin: `mvn clean install`

## Viewing the running application
To view the running application, visit [http://localhost:8080/file-compressor](http://localhost:8080/file-compressor) in your browser
