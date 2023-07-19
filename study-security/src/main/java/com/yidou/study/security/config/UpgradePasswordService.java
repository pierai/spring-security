package com.yidou.study.security.config;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.yidou.study.security.mapper.UserMapper;
import com.yidou.study.security.userdetails.SecurityUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.stereotype.Component;

/**
 * @ClassName UpgradePasswordService
 * @Description 密码升级Service
 * @Author Lipeng5
 * @Date 2023/7/18 16:24
 * @Version 1.0
 */
@Component
public class UpgradePasswordService implements UserDetailsPasswordService {
	@Autowired
	private UserMapper userMapper;

	@Override
	public UserDetails updatePassword(UserDetails user, String newPassword) {
		SecurityUserDetails securityUser = (SecurityUserDetails) user;
		// 更新数据库密码
		LambdaUpdateWrapper<SecurityUserDetails> updateWrapper = new LambdaUpdateWrapper<>();
		updateWrapper.eq(SecurityUserDetails::getUsername, securityUser.getUsername());
		updateWrapper.set(SecurityUserDetails::getPassword, newPassword);
		userMapper.update(null, updateWrapper);

		// 更新UserDetails
		securityUser.setPassword(newPassword);
		return securityUser;
	}
}
