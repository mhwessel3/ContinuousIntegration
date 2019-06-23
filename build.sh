#!/bin/sh
mkdir bashbuild

javac -cp src/main/resources/servlet-api-2.5.jar:src/main/resources/jetty-all-7.0.2.v20100331.jar:src/main/resources/javax.activation-1.2.0.jar:src/main/java/gson-2.6.2.jar:src/main/java/mail.jar src/main/java/ContinuousIntegrationServer.java src/main/java/MailNotification.java -d bashbuild