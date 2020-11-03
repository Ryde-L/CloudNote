# 基于哪个镜像
FROM java:8
COPY recycle-bin-0.0.1-SNAPSHOT.jar /
EXPOSE 9003

CMD nohup java -Xms128m -Xmx128m -jar recycle-bin-0.0.1-SNAPSHOT.jar  > catalina.out & tail -f ./catalina.out
