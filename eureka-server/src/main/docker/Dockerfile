# 基于哪个镜像
FROM java:8
# 将本地文件夹挂载到当前容器
#VOLUME /tmp
# 拷贝文件到容器，eureka-server-0.0.1-SNAPSHOT.jar这里是maven打包后的名字
COPY eureka-server-0.0.1-SNAPSHOT.jar /eureka-server-0.0.1-SNAPSHOT.jar
EXPOSE 8761

# 配置容器启动后执行的命令
#ENTRYPOINT [ "sh", "-c", "./eureka-entrypoint.sh" ]
CMD nohup java -Xms128m -Xmx128m -jar eureka-server-0.0.1-SNAPSHOT.jar  > catalina.out & tail -f ./catalina.out
