package com.gym.authuser.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.gym.authuser.dtos.ResponseDto;
import com.gym.authuser.dtos.UserDto;
import com.gym.authuser.enums.UserStatus;
import com.gym.authuser.enums.UserType;
import com.gym.authuser.models.UserModel;
import com.gym.authuser.publishers.UserEventPublisher;
import com.gym.authuser.repositories.UserRepository;
import com.gym.authuser.security.TokenService;
import com.gym.authuser.services.UserService;
import com.gym.authuser.specifications.SpecificationTemplate;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    UserEventPublisher userEventPublisher;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    TokenService tokenService;

    @PostMapping("/check-admin")
    public ResponseEntity<Void> checkAdmin(){
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/check-trainer")
    public ResponseEntity<Void> checkTrainer(){
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/check-customer")
    public ResponseEntity<Void> checkCustomer(){
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/login")
    public ResponseEntity<Object> loginUser (@RequestBody @Validated(UserDto.UserView.LoginPost.class)
                                             @JsonView(UserDto.UserView.LoginPost.class)UserDto userDto) {

        UserModel userModel = this.userRepository.findByEmail(userDto.getEmail()).orElseThrow(() -> new RuntimeException("User not found"));
        if(passwordEncoder.matches(userDto.getPassword(), userModel.getPassword())) {
            if (userModel.getUserStatus().toString().equals("ACTIVE")) {
                String token = this.tokenService.generateToken(userModel);
                return ResponseEntity.ok(new ResponseDto(userModel.getUsername(), token, userModel.getUserId().toString(), userModel.getUserType().toString()));
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User is not ACTIVE");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: User not found");

    }

    @PostMapping("/signup")
    public ResponseEntity<Object> registerUser (@RequestBody @Validated(UserDto.UserView.RegistrationPost.class)
                                                    @JsonView(UserDto.UserView.RegistrationPost.class) UserDto userDto) {
        if (userService.existsByEmail(userDto.getEmail())){
            //log.warn("Email {} is already in use!", userDto.getEmail());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: This email is already in use!");
        }
        if (userService.existsByCpf(userDto.getCpf())){
            //log.warn("Email {} is already in use!", userDto.getEmail());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: This cpf is already in use!");
        }
        if (userService.existsByPhoneNumber(userDto.getPhoneNumber())){
            //log.warn("Email {} is already in use!", userDto.getEmail());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: This phone number is already in use!");
        }
        //LocalDate date = LocalDate.parse(LocalDateTime.now(ZoneId.of("UTC")).toString());
        var userModel = new UserModel();
        BeanUtils.copyProperties(userDto, userModel);
        userModel.setUserType(UserType.CUSTOMER);
        userModel.setUserStatus(UserStatus.BLOCKED);
        userModel.setPassword(passwordEncoder.encode(userModel.getCpf()));
        userModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
        userService.saveUser(userModel, "userCustomer");

        String token = this.tokenService.generateToken(userModel);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @PostMapping("/register/instructor")
    public ResponseEntity<Object> registerInstructor (@RequestBody @Validated(UserDto.UserView.RegistrationPost.class)
                                                @JsonView(UserDto.UserView.RegistrationPost.class) UserDto userDto) {
        if (userService.existsByEmail(userDto.getEmail())){
            //log.warn("Email {} is already in use!", userDto.getEmail());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: This email is already in use!");
        }
        if (userService.existsByCpf(userDto.getCpf())){
            //log.warn("Email {} is already in use!", userDto.getEmail());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: This cpf is already in use!");
        }
        if (userService.existsByPhoneNumber(userDto.getPhoneNumber())){
            //log.warn("Email {} is already in use!", userDto.getEmail());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: This phone number is already in use!");
        }
        var userModel = new UserModel();
        BeanUtils.copyProperties(userDto, userModel);
        userModel.setUserType(UserType.INSTRUCTOR);
        userModel.setUserStatus(UserStatus.ACTIVE);
        userModel.setPassword(passwordEncoder.encode(userModel.getCpf()));
        userModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
        userService.saveUser(userModel, "userTrainer");
        //userModel.setExpirationDate(LocalDateTime.now(ZoneId.of("UTC")));

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/register/admin")
    public ResponseEntity<Object> registerAdmin (@RequestBody @Validated(UserDto.UserView.RegistrationPost.class)
                                                      @JsonView(UserDto.UserView.RegistrationPost.class) UserDto userDto) {
        if (userService.existsByEmail(userDto.getEmail())){
            //log.warn("Email {} is already in use!", userDto.getEmail());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: This email is already in use!");
        }
        if (userService.existsByCpf(userDto.getCpf())){
            //log.warn("Email {} is already in use!", userDto.getEmail());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: This cpf is already in use!");
        }
        if (userService.existsByPhoneNumber(userDto.getPhoneNumber())){
            //log.warn("Email {} is already in use!", userDto.getEmail());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: This phone number is already in use!");
        }
        var userModel = new UserModel();
        BeanUtils.copyProperties(userDto, userModel);
        userModel.setUserType(UserType.ADMIN);
        userModel.setUserStatus(UserStatus.ACTIVE);
        userModel.setPassword(passwordEncoder.encode(userModel.getCpf()));
        userModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
        userService.save(userModel);
        //userModel.setExpirationDate(LocalDateTime.now(ZoneId.of("UTC")));

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<Page<UserModel>> getAllUsers(SpecificationTemplate.UserSpec spec,
                                                       @PageableDefault(page = 0, size = 10, sort = "userId", direction = Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll(spec, pageable));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Object> getOneUser(@PathVariable(value = "userId") UUID userId) {
        Optional<UserModel> userModelOptional = userService.findById(userId);
        if(!userModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(userModelOptional.get());
        }
    }

    @DeleteMapping("/deletecustomer/{userId}")
    public ResponseEntity<Object> deleteUserCustomer(@PathVariable(value = "userId") UUID userId){
        //log.debug("DELETE deleteUser userId received {}", userId);
        Optional<UserModel> userModelOptional = userService.findById(userId);
        if(!userModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        if(userModelOptional.get().getUserType().toString().equals("CUSTOMER")){
            userService.deleteUser(userModelOptional.get(),"userCustomer");
            return ResponseEntity.status(HttpStatus.OK).body("User deleted succesfully");
        }
        else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User is not a customer");
        }
    }

    @DeleteMapping("/deletetrainer/{userId}")
    public ResponseEntity<Object> deleteUserTrainer(@PathVariable(value = "userId") UUID userId){
        //log.debug("DELETE deleteUser userId received {}", userId);
        Optional<UserModel> userModelOptional = userService.findById(userId);
        if(!userModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        if(userModelOptional.get().getUserType().toString().equals("INSTRUCTOR")){
            userService.deleteUser(userModelOptional.get(),"userTrainer");
            return ResponseEntity.status(HttpStatus.OK).body("User deleted succesfully");
        }
        else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User is not a trainer");
        }
    }

    @DeleteMapping("/deleteadmin/{userId}")
    public ResponseEntity<Object> deleteUserAdmin(@PathVariable(value = "userId") UUID userId){
        //log.debug("DELETE deleteUser userId received {}", userId);
        Optional<UserModel> userModelOptional = userService.findById(userId);
        if(!userModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        if(userModelOptional.get().getUserType().toString().equals("ADMIN")){
            userService.delete(userModelOptional.get());
            return ResponseEntity.status(HttpStatus.OK).body("User deleted succesfully");
        }
        else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User is not a admin");
        }
    }


    @PutMapping ("/password/{userId}")
    public ResponseEntity<Object> updatePassword(@PathVariable(value = "userId") UUID userId,
                                                 @RequestBody @Validated(UserDto.UserView.PasswordPut.class)
                                                 @JsonView(UserDto.UserView.PasswordPut.class) UserDto userDto){
        //log.debug("PUT updatePassword password received {}", userDto.getPassword());
        Optional<UserModel> userModelOptional = userService.findById(userId);
        if (!userModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        } if (!passwordEncoder.matches(userDto.getOldPassword(), userModelOptional.get().getPassword())){
            //log.warn("Missmatched old password userId {}", userDto.getUserId());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Missmatched old password!");
        }

        else {
            var userModel = userModelOptional.get();
            userModel.setPassword(passwordEncoder.encode(userDto.getPassword()));
            userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
            userService.save(userModel);
            //log.debug("PUT  updatePassword userId  {}", userModel.getUserId());
            //log.info("Password updated succesfully {}", userModel.getUserId());
            return ResponseEntity.status(HttpStatus.OK).build();
        }
    }

    @PutMapping ("/activate/year/{userId}")
    public ResponseEntity<Object> setConsumerActiveYear(@PathVariable(value = "userId") UUID userId, UserDto userDto){
        //log.debug("PUT updateUser userDto received {}", userDto.getPassword());
        Optional<UserModel> userModelOptional = userService.findById(userId);
        if (!userModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        } else {
            var userModel = userModelOptional.get();
            userModel.setUserStatus(UserStatus.ACTIVE);
            userModel.setExpirationDate(LocalDateTime.now(ZoneId.of("UTC")).plusYears(1));
            userService.updateUser(userModel, "userCustomer");
            //log.debug("PUT  updateUser userId {}", userModel.getUserId());
            //log.info("User updated succesfully userId {}", userModel.getUserId());
            return ResponseEntity.status(HttpStatus.OK).build();
        }
    }

    @PutMapping ("/activate/mounth/{userId}")
    public ResponseEntity<Object> setConsumerActiveMounth(@PathVariable(value = "userId") UUID userId, UserDto userDto){
        //log.debug("PUT updateUser userDto received {}", userDto.getPassword());
        Optional<UserModel> userModelOptional = userService.findById(userId);
        if (!userModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        } else {
            var userModel = userModelOptional.get();
            userModel.setUserStatus(UserStatus.ACTIVE);
            userModel.setExpirationDate(LocalDateTime.now(ZoneId.of("UTC")).plusMonths(1));
            userService.updateUser(userModel, "userCustomer");
            //log.debug("PUT  updateUser userId {}", userModel.getUserId());
            //log.info("User updated succesfully userId {}", userModel.getUserId());
            return ResponseEntity.status(HttpStatus.OK).build();
        }
    }

    @PutMapping ("/deactivate/{userId}")
    public ResponseEntity<Object> deactivateCustomer(@PathVariable(value = "userId") UUID userId, UserDto userDto){
        //log.debug("PUT updateUser userDto received {}", userDto.getPassword());
        Optional<UserModel> userModelOptional = userService.findById(userId);
        if (!userModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        } else {
            var userModel = userModelOptional.get();
            userModel.setUserStatus(UserStatus.BLOCKED);
            userModel.setExpirationDate(LocalDateTime.now(ZoneId.of("UTC")));
            userService.updateUser(userModel, "userCustomer");
            //log.debug("PUT  updateUser userId {}", userModel.getUserId());
            //log.info("User updated succesfully userId {}", userModel.getUserId());
            return ResponseEntity.status(HttpStatus.OK).build();
        }
    }





}
