package com.org.myGameServiceCenter;

<<<<<<< HEAD
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MyGameServiceCenterApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyGameServiceCenterApplication.class, args);
	}

}

=======
import com.org.myGameServiceCenter.tcp.NettyServerListener;
import de.codecentric.boot.admin.config.EnableAdminServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@Configuration
@SpringBootApplication
@ComponentScan(value = "com.org.myGameServiceCenter")
@EnableScheduling
@EnableAdminServer
@Slf4j
public class MyGameServiceCenterApplication implements CommandLineRunner {

    @Autowired
    NettyServerListener nettyServerListener;

    public static void main(String[] args) {
        SpringApplication.run(MyGameServiceCenterApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        nettyServerListener.start();
        log.info("启动成功");
    }
}
>>>>>>> first commit
