package com.controller;

import com.exception.CustomException;
import com.exception.CustomExceptionType;
import com.model.AjaxResponse;
import com.model.PictureVO;
import com.model.RegisterVO;
import com.model.UserVO;
import com.service.MyServiceImpl;
import com.utils.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.loadtime.Aj;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api")
public class MyController {

    @Resource
    MyServiceImpl myService;

    //获取所有图片id
    @GetMapping("/imgs")
    public @ResponseBody AjaxResponse getPicId(){
        return AjaxResponse.success(myService.getPicId());
    }
    //根据id获取图片信息
    @GetMapping("/imgs/{id}")
    public @ResponseBody AjaxResponse getPicInfo(@PathVariable Long id){
        return AjaxResponse.success(myService.getPicInfo(id));
    }
    //增加图片
    @PostMapping("/imgs")
    public @ResponseBody AjaxResponse addPic(@RequestBody PictureVO picture){
        myService.addPic(picture);
        return AjaxResponse.success();
    }
    //根据id修改图片
    @PutMapping("/imgs/{id}")
    public @ResponseBody AjaxResponse changeImg(@PathVariable Long id,@RequestBody PictureVO picture){
        myService.changeImg(id,picture);
        return AjaxResponse.success();
    }
    //根据id删除图片
    @DeleteMapping("/imgs/{id}")
    public @ResponseBody AjaxResponse deleteImg(@PathVariable Long id){
        myService.deleteImg(id);
        return AjaxResponse.success();
    }
    //登录
    @PostMapping("/user/login")
    public @ResponseBody AjaxResponse login(@RequestBody UserVO user){
        UserVO user1=myService.findByUsername(user.getUsername());
        Boolean isRight=false;

            if (user.getPasswd().equals(user1.getPasswd())){
                isRight = true;
            if (isRight) {
                //获取新token，过期时间为12h
                String token = myService.getToken(user.getUsername());

                Map map = new HashMap<String, String>();
                map.put("username", user.getUsername());
                map.put("token", token);
                return AjaxResponse.success(map);
            }
        }
        return AjaxResponse.fail(401);
    }
    //退出登录
    @GetMapping("/user/logout")
    public @ResponseBody AjaxResponse logout(){
        //让token失效
        myService.logout();
        return AjaxResponse.success();
    }
    //注册
    @PostMapping ("/user/register")
    public @ResponseBody AjaxResponse register(@RequestBody RegisterVO registerVO){
        if(!registerVO.getPassword1().equals(registerVO.getPassword2())||!registerVO.getInviteCode().equals("123"))
            return AjaxResponse.fail();
        try{
            myService.register(registerVO.getUsername(),registerVO.getPassword1());
        }catch (CustomException e){
            return AjaxResponse.error(e);
        }

        return AjaxResponse.success();
    }
}