package com.yang.springmybatisplus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yang.springmybatisplus.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends BaseMapper<User> {
}
