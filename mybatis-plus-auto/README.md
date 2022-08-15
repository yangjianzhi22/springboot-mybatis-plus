## 插入自增策略

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