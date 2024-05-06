FROM openjdk:17

ENV chumba_bot_name=chumbaBot

EXPOSE 8080

# Add  JAR file from the source directory to the container
ADD src/chumba-bot.jar chumba-bot.jar

# Set the entry point for the container, including the VM options
ENTRYPOINT ["java", "-Dchumba.bot.name=${chumba_bot_name}", "-jar", "/chumba-bot.jar"]
