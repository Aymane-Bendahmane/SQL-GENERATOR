package com.sqlgenerator;

import com.sqlgenerator.services.SqlService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication(exclude =
		{HibernateJpaAutoConfiguration.class,
				JpaRepositoriesAutoConfiguration.class,
				DataSourceAutoConfiguration.class,
		})
public class SqlGeneratorApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SqlGeneratorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		SqlService.sqlGenerator();
	}
}
