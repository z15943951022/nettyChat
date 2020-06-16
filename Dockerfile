FROM openjdk:8u222-jre
VOLUME /tmp
ADD target/nettyPro-1.0-SNAPSHOT.jar app.jar
ENV JAVA_OPTS=""
ENV LANG C.UTF-8
ENV TZ=Asia/Shanghai
RUN sh -c 'touch /app.jar'
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /app.jar" ]