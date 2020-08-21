package com.service;

import com.model.PictureVO;
import com.model.UserVO;

import java.util.List;

public interface MyService {
    public List<Long> getPicId();
    public PictureVO getPicInfo(Long id);
    public void addPic(PictureVO picture);
    public void changeImg(Long id, PictureVO picture);
    public void deleteImg(Long id);
    public UserVO findByUsername(String name);
    public void logout();
    public void register(String username, String passwd);

    public String getToken(String username);
}
