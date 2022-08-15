## 快速入门

#### 1. 创建数据库&表

```
DROP TABLE IF EXISTS user;

CREATE TABLE user
(
	id BIGINT(20) NOT NULL COMMENT '主键ID',
	name VARCHAR(30) NULL DEFAULT NULL COMMENT '姓名',
	age INT(11) NULL DEFAULT NULL COMMENT '年龄',
	email VARCHAR(50) NULL DEFAULT NULL COMMENT '邮箱',
	PRIMARY KEY (id)
);
-- 真实开发中，version(乐观锁)、deleted(逻辑删除)、gmt_create(创建时间)、gmt_modified(修改时间)

INSERT INTO user (id, name, age, email) VALUES
(1, 'Jone', 18, 'test1@baomidou.com'),
(2, 'Jack', 20, 'test2@baomidou.com'),
(3, 'Tom', 28, 'test3@baomidou.com'),
(4, 'Sandy', 21, 'test4@baomidou.com'),
(5, 'Billie', 24, 'test5@baomidou.com');
```

#### 2. 添加依赖

```
<!-- mybatis-plus -->
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus-boot-starter</artifactId>
    <version>3.4.3.1</version>
</dependency>
```

_注意：使用mybatis-plus可以节省大量代码，不要同时导入mybatis和mybatis-plus，可能存在版本冲突_

#### 3. 使用mybatis-plus

- 在启动类加上@mapper注解,扫描mapper文件夹

```
@MapperScan("com.yang.springmybatisplus.mapper")
@SpringBootApplication
public class SpringMybatisPlusApplication 
```

- 创建pojo类

```
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long id;
    private String name;
    private Integer age;
    private String email;
}
```

- mapper接口

```
@Repository
public interface UserMapper extends BaseMapper<User> {
}
```

mybatis-plus已经配置完成，可以直接使用，CRUD

- 使用测试类@Test测试

```
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
```

运行contextLoads, 查看结果

![结果1](/docs/1.jpg)