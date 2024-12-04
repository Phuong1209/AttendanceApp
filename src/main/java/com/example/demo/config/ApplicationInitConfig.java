//package com.example.demo.config;
//
//import com.example.demo.repository.IPositionRepository;
//import lombok.AccessLevel;
//import lombok.RequiredArgsConstructor;
//import lombok.experimental.FieldDefaults;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import com.example.demo.model.User;
//import com.example.demo.model.Position;
////import com.example.demo.enums.Position;
//import com.example.demo.repository.IUserRepository;
//
//import java.util.HashSet;
//import java.util.Set;
//
//@Configuration
//@RequiredArgsConstructor
//@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
//public class ApplicationInitConfig {
//    @Autowired
//    PasswordEncoder passwordEncoder;
//    @Autowired
//    IPositionRepository positionRepository;
//    @Bean
//    ApplicationRunner applicationRunner(IUserRepository iUserRepository) {
//        return args -> {
//            if(iUserRepository.findByUserName("admin").isEmpty()){
////                var position = new HashSet<String>();
////                position.add(Position.ADMIN.name());
//                Position adminPosition = positionRepository.findByName("ADMIN");
//                if (adminPosition==null){
//                    adminPosition=positionRepository.save(new Position("ADMIN"));
//                }
//                User user = User.builder()
//                        .userName("admin")
//                        .password(passwordEncoder.encode("admin"))
////                        .positions()
//                        .build();
//
//                iUserRepository.save(user);
//            }
//        };
//    }
//}
//
