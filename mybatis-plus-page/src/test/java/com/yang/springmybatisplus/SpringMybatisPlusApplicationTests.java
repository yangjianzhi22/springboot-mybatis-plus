package com.yang.springmybatisplus;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yang.springmybatisplus.entity.User;
import com.yang.springmybatisplus.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class SpringMybatisPlusApplicationTests {

	@Autowired
	private UserMapper userMapper;

	@Test
	void contextLoads() {
		System.out.println(("----- selectAll method test 测试查询所有用户方法 ------"));
		//selectList 的参数wrapper 是条件构造器，可以先写null
		List<User> userList = userMapper.selectList(null);
		//forEach 的参数是 Consumer类型的 语法糖
		userList.forEach(System.out::println);
	}

	//测试分页查询
	@Test
	public void testPage() {
		Page<User> page = new Page<>(2,5); //开启拦截器后，会注册一个page对象  当前页，每页条数
		//方法源码：   <P extends IPage<T>> P selectPage(P page, @Param(Constants.WRAPPER) Wrapper<T> queryWrapper);
		userMapper.selectPage(page,null); //分页查询
		page.getRecords().forEach(System.out::println); //获取分页后的数据 打印
		System.out.println(page.getTotal()); //获取记录总数
	}
}