package com.stylefeng.guns.gateway.modular.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.api.user.UserInfoModel;
import com.stylefeng.guns.api.user.UserModel;
import com.stylefeng.guns.api.user.UserService;
import com.stylefeng.guns.gateway.common.CurrentUser;
import com.stylefeng.guns.gateway.modular.vo.ResponseVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/user")
@RestController
public class UserController {
    @Reference(interfaceClass = UserService.class, check = false)
    private UserService userService;

    @PostMapping("/register")
    public ResponseVO register(UserModel userModel) {
        if (userModel.getUsername() == null || userModel.getUsername().trim().length() == 0) {
            return ResponseVO.serviceFail("用户名不能为空");
        }

        if (userModel.getPassword() == null || userModel.getPassword().trim().length() == 0) {
            return ResponseVO.serviceFail("密码不能为空");
        }

        boolean succeed = userService.register(userModel);
        return succeed ? ResponseVO.success("注册成功") : ResponseVO.serviceFail("注册失败");
    }

    @PostMapping("/check")
    public ResponseVO check(String username) {
        if (username == null || username.trim().length() == 0) {
            return ResponseVO.serviceFail("用户名不能为空");
        }

        boolean notExists = userService.checkUsername(username);
        return notExists ? ResponseVO.success("用户名不存在") : ResponseVO.serviceFail("用户名已存在");
    }

    @GetMapping("/logout")
    public ResponseVO logout() {
        return ResponseVO.success("用户退出成功");
    }

    @GetMapping("/getUserInfo")
    public ResponseVO getUserInfo() {
        String uuid = CurrentUser.getCurrentUserId();
        if (uuid == null || uuid.trim().length() == 0) {
            return ResponseVO.serviceFail("用户未登录");
        }

        UserInfoModel userInfoModel = userService.getUserInfo(Integer.parseInt(uuid));
        return userInfoModel == null ?
                ResponseVO.systemFail("用户信息查询失败") :
                ResponseVO.success(userInfoModel);
    }

    @PostMapping("/updateUserInfo")
    public ResponseVO updateUserInfo(UserInfoModel userInfoModel) {
        String uuid = CurrentUser.getCurrentUserId();
        if (uuid == null || uuid.trim().length() == 0) {
            return ResponseVO.serviceFail("用户为登录");
        }

        if (Integer.parseInt(uuid) != userInfoModel.getUuid()) {
            return ResponseVO.serviceFail("请修改您个人的信息");
        }

        UserInfoModel result = userService.updateUserInfo(userInfoModel);
        return result == null ?
                ResponseVO.systemFail("用户信息修改失败") :
                ResponseVO.success(result);
    }
}
