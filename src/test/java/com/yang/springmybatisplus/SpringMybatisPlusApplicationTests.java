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

	//测试插入
	@Test
	public void testInsert(){
		User user = new User();
		user.setName("小蒋");
		user.setAge(3);
		user.setEmail("2474950959@qq.com");
		//没有设置ID却自动生成的ID
		int result = userMapper.insert(user);
		System.out.println("result = " + result);
		System.out.println("user = " + user);
	}

	//更新测试
	@Test
	public void testUpdateByID() {
		User user = new User();
		user.setId(6L);
		user.setName("小小");
		user.setAge(18);//这一行后加
		int i = userMapper.updateById(user);//受影响的行数,参数是一个user不是id,点击看源码
		System.out.println("i = " + i);
	}

	//测试分页查询
	@Test
	public void testPage() {
		Page<User> page = new Page<>(1,5); //开启拦截器后，会注册一个page对象  当前页，每页条数
		//方法源码：   <P extends IPage<T>> P selectPage(P page, @Param(Constants.WRAPPER) Wrapper<T> queryWrapper);
		userMapper.selectPage(page,null); //分页查询
		page.getRecords().forEach(System.out::println); //获取分页后的数据 打印
		System.out.println(page.getTotal()); //获取记录总数
	}
}