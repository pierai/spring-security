package com.yidou.study.security.config;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yidou.study.security.mapper.UserMapper;
import com.yidou.study.security.userdetails.SecurityUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private UserMapper userMapper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		LambdaQueryWrapper<SecurityUserDetails> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(SecurityUserDetails::getUsername, username);
		return userMapper.selectOne(queryWrapper);
	}
}
