package hello.login;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ItemServiceApplication {

	// 항상 단방향 의존관계 설계
	public static void main(String[] args) {
		SpringApplication.run(ItemServiceApplication.class, args);
	}

}
