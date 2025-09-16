package com.mini.mini_2.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.annotation.Rollback;

import com.mini.mini_2.user.repository.UserRepository;


@SpringBootTest
@Transactional
@Rollback
public class UserTest {
    
    @Value(value = "${JWT_SECRET_KEY}")
    private String envString;
        
    @Autowired
    private UserRepository userRepository;
    
    @Test
    public void insertUser() {
        
        // System.out.println("[ENV TEST] string : " + envString);
        
        // UserRequestDTO request = UserRequestDTO.builder()
        //                                     .userEmail("minu@naa.com")
        //                                     .userNickName("holy")
        //                                     .password("1234")
        //                                     .build();

        // UserEntity entity = userRepository.save(request.toEntity());
        // UserResponseDTO response = UserResponseDTO.fromEntity(entity);       
        
        // System.out.println("entity : " +  entity);
        // System.out.println("dto : " +  response);
        
    }
}
