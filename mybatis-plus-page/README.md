## 分页

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