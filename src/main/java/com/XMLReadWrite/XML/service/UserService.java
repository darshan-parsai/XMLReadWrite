package com.XMLReadWrite.XML.service;

import com.XMLReadWrite.XML.entity.UserEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface UserService {
    UserEntity saveUser(UserEntity userEntity);

    List<UserEntity> getAllUser();

    void saveUsersFromXml(MultipartFile xmlFile) throws IOException;
}
