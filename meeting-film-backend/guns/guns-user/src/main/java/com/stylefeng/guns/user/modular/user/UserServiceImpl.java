package com.stylefeng.guns.user.modular.user;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.api.user.UserService;
import com.stylefeng.guns.api.user.UserInfoModel;
import com.stylefeng.guns.api.user.UserModel;
import com.stylefeng.guns.core.util.MD5Util;
import com.stylefeng.guns.user.persistence.dao.UserMapper;
import com.stylefeng.guns.user.persistence.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Service(interfaceClass = UserService.class, loadbalance = "roundrobin")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public int login(String username, String password) {
        User user = new User();
        user.setUserName(username);
        User result = userMapper.selectOne(user);
        if (result == null || result.getUuid() == 0) {
            return 0;
        }

        return result.getUserPwd().equals(MD5Util.encrypt(password)) ? result.getUuid() : 0;
    }

    @Override
    public boolean register(UserModel userModel) {
        User user = new User();
        user.setUserName(userModel.getUsername());
        user.setUserPwd(MD5Util.encrypt(userModel.getPassword()));
        user.setEmail(userModel.getEmail());
        user.setAddress(userModel.getAddress());
        user.setUserPhone(userModel.getPhone());
        int affected = userMapper.insert(user);

        return affected > 0;
    }

    @Override
    public boolean checkUsername(String username) {
        EntityWrapper<User> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("user_name", username);
        int count = userMapper.selectCount(entityWrapper);

        return count == 0;
    }

    @Override
    public UserInfoModel getUserInfo(int uuid) {
        User user = userMapper.selectById(uuid);
        return user == null ? null : transformUserInfo(user);
    }

    @Override
    public UserInfoModel updateUserInfo(UserInfoModel userInfoModel) {
        User user = new User();
        user.setUuid(userInfoModel.getUuid());
        user.setNickName(userInfoModel.getNickname());
        user.setEmail(userInfoModel.getEmail());
        user.setAddress(userInfoModel.getAddress());
        user.setUserPhone(userInfoModel.getPhone());
        user.setUserSex(userInfoModel.getSex());
        user.setLifeState(Integer.parseInt(userInfoModel.getLifeState()));
        user.setBirthday(userInfoModel.getBirthday());
        user.setBiography(userInfoModel.getBiography());
        user.setHeadUrl(userInfoModel.getHeadAddress());
        user.setUpdateTime(new Date(System.currentTimeMillis()));

        int affected = userMapper.updateById(user);

        return affected > 0 ? transformUserInfo(userMapper.selectById(user.getUuid())) : null;
    }

    private UserInfoModel transformUserInfo(User user) {
        UserInfoModel userInfoModel = new UserInfoModel();
        userInfoModel.setUuid(user.getUuid());
        userInfoModel.setUsername(user.getUserName());
        userInfoModel.setAddress(user.getAddress());
        userInfoModel.setEmail(user.getEmail());
        userInfoModel.setHeadAddress(user.getHeadUrl());
        userInfoModel.setNickname(user.getNickName());
        userInfoModel.setLifeState(String.valueOf(user.getLifeState()));
        userInfoModel.setPhone(user.getUserPhone());
        userInfoModel.setSex(user.getUserSex() == null ? 0 : user.getUserSex());
        userInfoModel.setBirthday(user.getBirthday());
        userInfoModel.setBeginTime(user.getBeginTime().getTime());
        userInfoModel.setUpdateTime(user.getUpdateTime().getTime());

        return userInfoModel;
    }
}
