package com.service;

import com.exception.CustomException;
import com.exception.CustomExceptionType;
import com.exception.NotFoundException;
import com.jpa.Picture;
import com.jpa.PictureRepository;
import com.jpa.User;
import com.jpa.UserRepository;
import com.model.PictureVO;
import com.model.UserVO;
import com.utils.DozerUtils;
import com.utils.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.dozer.Mapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Slf4j
@Service
public class MyServiceImpl implements MyService {

    @Resource
    private UserRepository userRepository;
    @Resource
    private PictureRepository pictureRepository;
    @Resource
    private Mapper dozerMapper;

    @Override
    public List<Long> getPicId(){
        List<Picture> pictureLis = pictureRepository.findAll();
        List<PictureVO>  pictures=DozerUtils.mapList(pictureLis, PictureVO.class);
        List<Long> idList=new ArrayList();
        for (int i = 0; i < pictures.size(); i++) {
            idList.add(pictures.get(i).getId());
        }
        return idList;
    }
    @Override
    public PictureVO getPicInfo(Long id){
        if(picExist(id)==false)
            throw new CustomException(CustomExceptionType.NOT_FOUND_ERROR,"图片id不存在");
        Optional<Picture> picture = pictureRepository.findById(id);
        PictureVO pictureVO = dozerMapper.map(picture.get(), PictureVO.class);
        return pictureVO;

    }
    @Override
    public void addPic(PictureVO picture){
        //Picture picturePO = dozerMapper.map(picture,Picture.class);
        Picture picturePO=new Picture(null,picture.getName(),picture.getUrl(),picture.getAuthor(),picture.getCategory(),picture.getGetTime());
        pictureRepository.save(picturePO);
    }
    @Override

    public void changeImg(Long id, PictureVO picture){
        if(picExist(id)==false)
            throw new CustomException(CustomExceptionType.NOT_FOUND_ERROR,"图片id不存在");
        picture.setId(id);
        Picture picturePO = dozerMapper.map(picture, Picture.class);
        pictureRepository.save(picturePO);
    }
    @Override
    public void deleteImg(Long id){
        if(picExist(id)==false)
            throw new CustomException(CustomExceptionType.NOT_FOUND_ERROR,"id不存在");
        pictureRepository.deleteById(id);
    }
    @Override
    public UserVO findByUsername(String name){
        User user = userRepository.findByUsername(name);
        UserVO userVO = dozerMapper.map(user, UserVO.class);
        return userVO;
    }
    @Override
    public void logout(){
        JWTUtils.JWT_SECRET=JWTUtils.JWT_SECRET+ Calendar.getInstance().getTime();
    }

    @Override
    public void register(String username,String passwd){
        UserVO user=new UserVO(username,passwd);
        User userPO = dozerMapper.map(user, User.class);
        User isExist=userRepository.findByUsername(username);
        System.out.println(isExist);
        if(isExist!=null){
            System.out.println("存在");
            throw new CustomException(CustomExceptionType.FORBID_WRITE,"该用户名已存在");
        }
        else{
            System.out.println("来添加了");
            userRepository.save(userPO);
        }
    }

    @Override
    public String getToken(String username) {

        //存入JWT的payload中生成token
        Map claims = new HashMap<String,Integer>();
        claims.put("admin_username",username);
        String subject = "admin";
        String token = null;
        try {
            //该token过期时间为12h
            token = JWTUtils.createJWT(claims, subject, 1000*60*60*12 );
        } catch (Exception e) {
            throw new RuntimeException("创建Token失败");
        }

        System.out.println("token:"+token);
        return token;
    }

    public Boolean picExist(Long id){
        if(pictureRepository.existsById(id)==true){
            return true;
        }
        else{
            return false;
        }

    }
}
