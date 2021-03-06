package com.shop.service;

import com.shop.domain.dto.UserDto;
import com.shop.domain.entity.Role;
import com.shop.domain.entity.User;
import com.shop.repository.RoleRepository;
import com.shop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class UserService {

    private static String UPLOAD_DIR = System.getProperty("user.dir")+File.separator+"images"+File.separator+"user"+File.separator;

    private final JdbcTemplate jdbcTemplate;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //@Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    //@Override
    public void saveUser(User user, MultipartFile multipartFiles) {

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1);
        Role userRole = roleRepository.findByRole("CLIENT");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));

        String filename;
        try {
            byte[] bytes = multipartFiles.getBytes();

            filename = multipartFiles.getOriginalFilename();

            Path path = Paths.get(UPLOAD_DIR + File.separator+filename);
            Files.write(path,bytes);

            user.setImageUrl(filename);

            userRepository.save(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<UserDto> getAllUsers() {
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(
                "SELECT u.user_id,u.name,u.last_name,u.email,r.role FROM user AS u " +
                        "INNER JOIN user_role AS ur on (u.user_id = ur.user_id) " +
                        "INNER JOIN role AS r ON (ur.role_id = r.role_id)");

        List<UserDto> userDtos = new ArrayList<>();
        Integer userId;
        String userName;
        String userLastName;
        String userEmail;
        String userRole;

        for (Map<String, Object> map : maps) {
            userId = (Integer) map.get("user_id");
            userName = (String) map.get("name");
            userLastName = (String) map.get("last_name");
            userEmail = (String) map.get("email");
            userRole = (String) map.get("role");
            userDtos.add(new UserDto(userId, userName, userLastName, userEmail, userRole));
        }
        return userDtos;
    }

    @Transactional
    public void deleteUserById(int userId) {
        userRepository.deleteUserById(userId);
    }

    public void updatePassword(User user) {
        String password = (bCryptPasswordEncoder.encode(user.getPassword()));
        jdbcTemplate.update("UPDATE user SET password = ? WHERE user_id = ?", password, user.getId());
    }

    public void updateUser(User user, MultipartFile multipartFiles) {

        String filename;
        try {
            byte[] bytes = multipartFiles.getBytes();

            filename = multipartFiles.getOriginalFilename();

            Path path = Paths.get(UPLOAD_DIR + File.separator+filename);
            Files.write(path,bytes);

            user.setImageUrl(filename);

        } catch (IOException e) {
            e.printStackTrace();
        }
        jdbcTemplate.update("UPDATE user SET name = ?, last_name = ?, phone_number = ?, image_url = ? WHERE user_id = ?\n"
                ,user.getName(),user.getLastName(),user.getPhoneNumber(),user.getImageUrl(), user.getId());
    }
}
