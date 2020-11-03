# 基于哪个镜像
FROM java:8
COPY note-0.0.1-SNAPSHOT.jar /
EXPOSE 9001

CMD nohup java -Xms128m -Xmx128m -jar note-0.0.1-SNAPSHOT.jar  > catalina.out & tail -f ./catalina.out
