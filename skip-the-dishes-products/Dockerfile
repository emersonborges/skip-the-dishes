FROM openjdk:8-jdk-slim

ENV APP_TARGET target
ENV APP skip-the-dishes-products.jar

RUN mkdir -p /opt

COPY ${APP_TARGET}/${APP} /opt

CMD java -XX:+PrintFlagsFinal -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap ${JAVA_OPTS} -jar /opt/${APP} -XX:NewRatio=${NEW_RATIO:-2}