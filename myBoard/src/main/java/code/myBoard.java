package code;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/*
	@SpringBootApplication 어노테이션만 있으면 됨.
	이니셜라이저로 받은 프로젝트의 패키지를 입맛대로 바꿔라.

	만약 데이터베이스 연결 안할거면,
	@springbootapplication(exclude = DataSourceAutoConfiguration.class )
	이걸로 어노테이션을 바꿔준다.

*/

@SpringBootApplication//(exclude = DataSourceAutoConfiguration.class )
public class myBoard {

	public static void main(String[] args) {

		SpringApplication.run(myBoard.class, args);
	}

}
