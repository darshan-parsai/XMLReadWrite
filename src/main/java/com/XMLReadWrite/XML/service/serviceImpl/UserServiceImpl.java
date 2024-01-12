package com.XMLReadWrite.XML.service.serviceImpl;

import com.XMLReadWrite.XML.entity.UserEntity;
import com.XMLReadWrite.XML.repository.UserRepository;
import com.XMLReadWrite.XML.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserEntity saveUser(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    @Override
    public List<UserEntity> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public void saveUsersFromXml(MultipartFile xmlFile) throws IOException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder;
        List<UserEntity> userEntities = new ArrayList<>();
        File file = multipartToFile(xmlFile, xmlFile.getOriginalFilename());
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(file);
            document.getDocumentElement().normalize();
            NodeList nodeList = document.getElementsByTagName("User");
            for (int i =0; i< nodeList.getLength();i++){
                userEntities.add(getUser(nodeList.item(i)));
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new RuntimeException(e);
        }
        userRepository.saveAll(userEntities);
    }

    private static UserEntity getUser(Node item) {
        UserEntity user = null;
        if (item.getNodeType() == Node.ELEMENT_NODE){
            Element element = (Element) item;
            user = UserEntity.builder().id(Long.parseLong(getTagValue("id", element))).
                    address(getTagValue("address", element))
                    .name(getTagValue("name", element))
                    .contact(getTagValue("contact", element)).build();
        }
        return user;
    }

    private static String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return node.getNodeValue();
    }

    public  static File multipartToFile(MultipartFile multipart, String fileName) throws IllegalStateException, IOException {
        File convFile = new File(System.getProperty("java.io.tmpdir")+"/"+fileName);
        multipart.transferTo(convFile);
        return convFile;
    }
}
