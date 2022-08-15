package com.yang.springmybatisplus;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.yang.springmybatisplus.mapper")
@SpringBootApplication
public class SpringMybatisPlusApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringMybatisPlusApplication.class, args);
	}

}