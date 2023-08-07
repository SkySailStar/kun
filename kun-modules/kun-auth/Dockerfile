# 基础镜像
FROM swr.cn-north-1.myhuaweicloud.com/sevnce/jdk:17.0.5

# 将工作目录设置为 /myapps
WORKDIR /usr/local/myapps

# 添加
ADD target/sevnce-auth-*.jar sevnce-auth.jar

# 指定运行环境
SHELL ["/bin/bash", "-c"]

# 运行的命令
RUN rm -rf /var/lib/apt/lists/* \
        && localedef -i zh_CN -c -f UTF-8 -A /usr/share/locale/locale.alias zh_CN.UTF-8 \
        && touch appstart.sh \
        && chmod +x appstart.sh \
        && mkdir /logs \
        && echo 'nohup java -Djava.security.egd=file:/dev/./urandom \
                 -Dspring.config.location=/usr/local/config/ \
                 -jar /usr/local/myapps/sevnce-auth.jar > /logs/startLog.file 2>&1 &' > appstart.sh

# 设置编码
ENV LANG zh_CN.UTF-8
ENV LANGUAGE zh_CN:zh
ENV LC_ALL zh_CN.UTF-8
# 设置时区
ENV TZ=Asia/Shanghai

# 启动时运行的命令
ENTRYPOINT /usr/local/myapps/appstart.sh && tail -F /logs/startLog.file