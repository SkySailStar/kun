# 基础镜像
FROM swr.cn-north-1.myhuaweicloud.com/sevnce/jdk:17.0.5

# 设置语言编码
ENV LANG en_US.UTF-8
ENV LANGUAGE en_US:en
ENV LC_ALL en_US.UTF-8
# 设置默认时区
ENV TZ=Asia/Shanghai

# 将工作目录设置为 /myapps
WORKDIR /usr/local/myapps

# 添加
ADD target/sevnce-auth-*.jar sevnce-auth.jar

# 指定运行环境
SHELL ["/bin/bash", "-c"]

# 运行的命令
RUN rm -rf /var/lib/apt/lists/* \
        && localedef -i en_US -c -f UTF-8 -A /usr/share/locale/locale.alias en_US.UTF-8 \
        && touch appstart.sh \
        && chmod +x appstart.sh \
        && mkdir /logs\
        && echo 'nohup java -Djava.security.egd=file:/dev/./urandom \
                 -Dspring.config.location=/usr/local/config/ \
                 -jar /usr/local/myapps/sevnce-auth.jar > /logs/startLog.file 2>&1 &' > appstart.sh

# 启动时运行的命令
ENTRYPOINT /usr/local/myapps/appstart.sh && tail -F /logs/startLog.file