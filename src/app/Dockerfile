FROM openjdk:17

ENV spring_data_mongodb_uri=
ENV spring_data_mongodb_database=
ENV chumba_bot_token=
ENV chumba_bot_name=

EXPOSE 8080

ADD chumba-bot.jar chumba-bot.jar

ENTRYPOINT ["sh", "-c", "java \
    -Dspring.data.mongodb.uri=${spring_data_mongodb_uri} \
    -Dspring.data.mongodb.database=${spring_data_mongodb_database} \
    -Dchumba.bot.token=${chumba_bot_token} \
    -Dchumba.bot.name=${chumba_bot_name} \
    -jar /chumba-bot.jar"]
