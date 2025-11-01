package com.kenji.web_order.configuration;


import com.kenji.web_order.entity.Role;
import com.kenji.web_order.entity.User;
import com.kenji.web_order.enums.RoleEnum;
import com.kenji.web_order.exception.AppException;
import com.kenji.web_order.exception.ErrorCode;
import com.kenji.web_order.repository.RoleRepository;
import com.kenji.web_order.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

    @Bean
    @Order(1)
    ApplicationRunner initDefaultRole(RoleRepository roleRepository){
        return args -> {
            for(RoleEnum roleEnum : RoleEnum.values()){
                if(!roleRepository.existsById(roleEnum.name())){
                    roleRepository.save(
                            Role.builder()
                                    .name(roleEnum.name())
                                    .build()
                    );
                    log.info("Role {} has been created", roleEnum.name());
                }
            }
        };
    }

    @Bean
    @Order(2)
    ApplicationRunner applicationRunner(UserRepository userRepository, RoleRepository roleRepository){
        return args -> {
          if(userRepository.findByUsername("admin").isEmpty()){

              var user = User.builder()
                      .username("admin")
                      .password(passwordEncoder.encode("admin"))
                      .role(roleRepository.findById(RoleEnum.ADMIN.toString()).orElseThrow(() ->
                              new AppException(ErrorCode.ROLE_NOT_EXISTED)))
                      .build();

              userRepository.save(user);
              log.info("Admin user has been created with default username and password");
          }
        };
    }


}
