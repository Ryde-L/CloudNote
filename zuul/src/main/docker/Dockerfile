# 基于哪个镜像
FROM java:8
COPY zuul-0.0.1-SNAPSHOT.jar /
EXPOSE 8800

CMD nohup java -Xms128m -Xmx128m -jar zuul-0.0.1-SNAPSHOT.jar  > catalina.out & tail -f ./catalina.out
