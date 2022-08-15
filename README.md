# springboot-mybatis-plus

springboot+mybatis-plus整合应用

> 参考: [https://blog.csdn.net/CodeInCoke/article/details/121030290](https://blog.csdn.net/CodeInCoke/article/details/121030290)

## mybatis-plus 

MyBatis-Plus (opens new window)（简称 MP）是一个 MyBatis (opens new window)的增强工具，在 MyBatis 的基础上只做增强不做改变，为简化开发、提高效率而生。

> 特性 (官网提供)

- **无侵入**：只做增强不做改变，引入它不会对现有工程产生影响，如丝般顺滑
- **损耗小**：启动即会自动注入基本 CURD，性能基本无损耗，直接面向对象操作，BaseMapper
- **强大的 CRUD 操作**：内置通用 Mapper、通用 Service，仅仅通过少量配置即可实现单表大部分 CRUD 操作，更有强大的条件构造器，满足各类使用需求，简单的CRUD操作不用自己编写。
- **支持 Lambda 形式调用**：通过 Lambda 表达式，方便的编写各类查询条件，无需再担心字段写错
- **支持主键自动生成**：支持多达 4 种主键策略（内含分布式唯一 ID 生成器 - Sequence），可自由配置，完美解决主键问题
- **支持 ActiveRecord 模式**：支持 ActiveRecord 形式调用，实体类只需继承 Model 类即可进行强大的 CRUD 操作
- **支持自定义全局通用操作**：支持全局通用方法注入（ Write once, use anywhere ）
- **内置代码生成器**：采用代码或者 Maven 插件可快速生成 Mapper 、 Model 、 Service 、 Controller 层代码，支持模板引擎，更有超多自定义配置等您来使用（自动生成代码）
- **内置分页插件**：基于 MyBatis 物理分页，开发者无需关心具体操作，配置好插件之后，写分页等同于普通 List 查询
- **分页插件支持多种数据库**：支持 MySQL、MariaDB、Oracle、DB2、H2、HSQL、SQLite、Postgre、SQLServer 等多种数据库
- **内置性能分析插件**：可输出 SQL 语句以及其执行时间，建议开发测试时启用该功能，能快速揪出慢查询
- **内置全局拦截插件**：提供全表 delete 、 update 操作智能分析阻断，也可自定义拦截规则，预防误操作

## [快速入门](/mybatis-plus-quickstart)

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

## 场景

### 1. [日志配置](/mybatis-plus-log)

- 使用yml添加日志配置项

```
mybatis-plus:
  configuration:
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
```

执行上面的查询所有用户的方法查看日志

![结果2](/docs/2.jpg)

### 2. [插入自增策略](/mybatis-plus-auto)

- 主键自增策略

```
@TableId(type = IdType.AUTO)
private Long id;
```

- 插入测试

```
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
```

查看日志

![结果3](/docs/3.jpg)

- 其他策略

```
public enum IdType {
    /**
     * 数据库ID自增
     * <p>该类型请确保数据库设置了 ID自增 否则无效</p>
     */
    AUTO(0),
    /**
     * 该类型为未设置主键类型(注解里等于跟随全局,全局里约等于 INPUT)
     */
    NONE(1),
    /**
     * 用户输入ID
     * <p>该类型可以通过自己注册自动填充插件进行填充</p>
     */
    INPUT(2),

    /* 以下3种类型、只有当插入对象ID 为空，才自动填充。 */
    /**
     * 分配ID (主键类型为number或string）,
     * 默认实现类 {@link com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator}(雪花算法)
     *
     * @since 3.3.0
     */
    ASSIGN_ID(3),
    /**
     * 分配UUID (主键类型为 string)
     * 默认实现类 {@link com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator}(UUID.replace("-",""))
     */
    ASSIGN_UUID(4);

    private final int key;

    IdType(int key) {
        this.key = key;
    }
}
```

### 3. [自动填充 (创建时间、更新时间)](/mybatis-plus-fill)

- 在实体类的成员变量上添加注解@TableField

```
//字段添加填充内容
@TableField(fill = FieldFill.INSERT ,value = "create_time")
private LocalDateTime createTime;
@TableField(fill = FieldFill.INSERT_UPDATE ,value = "update_time")
private LocalDateTime updateTime;
```

- 编写处理器来处理这个注解

```
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    //插入时启动  第三个参数 LocalDateTime 一定要和 createTime成员变量的值的类型一致，不然是null 如果是date就都设置date
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill ....");
        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now()); // 起始版本 3.3.0(推荐使用)
        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now()); // 起始版本 3.3.0(推荐)
    }

    //更新时候启动
    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill ....");
        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now()); // 起始版本 3.3.0(推荐)
    }
}
```

- 执行上面插入代码

![结果5](/docs/5.jpg)

### 4. [分页查询](/mybatis-plus-page)

- 配置拦截器

```
@Component
public class MybatisPlusConfig {
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        mybatisPlusInterceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());//创建乐观锁拦截器 OptimisticLockerInnerInterceptor
        mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL)); //插件分页拦截器，我的是mysql
        return mybatisPlusInterceptor;
    }
}
```

- 使用page对象即可

```
//测试分页查询
@Test
public void testPage() {
    Page<User> page = new Page<>(1,5); //开启拦截器后，会注册一个page对象  当前页，每页条数
    //方法源码：   <P extends IPage<T>> P selectPage(P page, @Param(Constants.WRAPPER) Wrapper<T> queryWrapper);
    userMapper.selectPage(page,null); //分页查询
    page.getRecords().forEach(System.out::println); //获取分页后的数据 打印
    System.out.println(page.getTotal()); //获取记录总数
}
```

![结果6](/docs/6.jpg)

## 其他

略, 如果需要深入使用时，查看参考链接

- 乐观锁
- 删除测试
- 性能分析插件
- 条件构造器wrapper(重点)
- 代码自动生成器(重点)