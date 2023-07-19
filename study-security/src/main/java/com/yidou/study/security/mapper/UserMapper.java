package com.yidou.study.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yidou.study.security.userdetails.SecurityUserDetails;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<SecurityUserDetails> {
}
