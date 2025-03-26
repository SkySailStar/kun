package cn.kun.dispatch;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import java.net.Inet4Address;
import java.net.UnknownHostException;

/**
 * 调度服务-启动类
 *
 * @author 廖航
 */
@MapperScan("cn.kun.**.mapper")
@EnableDubbo
@EnableTransactionManagement
@EnableDiscoveryClient
@SpringBootApplication
public class KunDispatchApplication {

    public static void main(String[] args) throws UnknownHostException {
        
        ConfigurableApplicationContext context = SpringApplication.run(KunDispatchApplication.class, args);
        Environment environment = context.getBean(Environment.class);
        String ip = Inet4Address.getLocalHost().getHostAddress();
        String port = environment.getProperty("server.port");
        System.out.println(
                " ██                         \n" +
                "░██                         \n" +
                "░██  ██   ██   ██   ███████ \n" +
                "░██ ██   ░██  ░██  ░░██░░░██\n" +
                "░████    ░██  ░██   ░██  ░██\n" +
                "░██░██   ░██  ░██   ░██  ░██\n" +
                "░██░░██  ░░██████   ███  ░██\n" +
                "░░  ░░    ░░░░░░   ░░░   ░░ \n" +
                "调度服务-启动成功！接口文档：http://" + ip + ":" + port + "/doc.html"
        );
    }
    
}
