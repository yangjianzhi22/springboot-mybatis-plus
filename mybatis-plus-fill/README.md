## 自动填充 (创建时间、更新时间)

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
