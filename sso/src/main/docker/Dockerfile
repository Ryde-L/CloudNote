# 基于哪个镜像
FROM java:8
COPY sso-0.0.1-SNAPSHOT.jar /
EXPOSE 9010

CMD nohup java -Xms128m -Xmx128m -jar sso-0.0.1-SNAPSHOT.jar  > catalina.out & tail -f ./catalina.out
