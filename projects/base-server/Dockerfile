#FROM指定基础镜像
FROM java:8
#声明一个挂载点，容器内此路径会对应宿主机的某个文件夹
VOLUME /tmp
#应用构建成功后的jar文件被复制到镜像内，名字也改成了app.jar
ADD  target/base-server.jar base.jar
#暴露8080端口 EXPOSE并不会让容器的端口访问到主机。要使其可访问，需要在docker run运行容器时通过-p来发布这些端口，或通过-P参数来发布EXPOSE导出的所有端口
EXPOSE 8081
#启动容器时的进程
ENTRYPOINT [ "java", "-jar", "/base.jar","--eureka.client.service-url.defaultZone=http://localhost:8761/eureka" ]
# jar命令
#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar","--spring.config.location=/config/application.properties"]
