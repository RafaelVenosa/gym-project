package com.gym.exercises.controllers;


import com.gym.exercises.dtos.TrainDto;
import com.gym.exercises.dtos.UserTrainDto;
import com.gym.exercises.enums.TrainStatus;
import com.gym.exercises.models.TrainModel;
import com.gym.exercises.models.TrainerModel;
import com.gym.exercises.models.UserModel;
import com.gym.exercises.models.UserTrainModel;
import com.gym.exercises.services.TrainService;
import com.gym.exercises.services.TrainerService;
import com.gym.exercises.services.UserService;
import com.gym.exercises.services.UserTrainService;
import com.gym.exercises.specifications.SpecificationTemplate;
import com.gym.exercises.specifications.TrainSpecifications;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/train")
public class TrainController {

    @Autowired
    TrainService trainService;

    @Autowired
    TrainerService trainerService;

    @Autowired
    UserTrainService userTrainService;

    @Autowired
    UserService userService;

    @PostMapping("/create/{trainerId}")
    public ResponseEntity<Object> createTrain (@PathVariable(value = "trainerId") UUID trainerId,
                                                @RequestBody @Valid TrainDto trainDto) {
        var trainModel = new TrainModel();
        var trainerModel = new TrainerModel();
        BeanUtils.copyProperties(trainDto, trainModel);
        trainModel.setTrainerId(trainerId);
        trainModel.setTrainStatus(TrainStatus.ACTIVE);
        trainModel.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        trainModel.setExpirationDate(LocalDateTime.now(ZoneId.of("UTC")).plusMonths(3));
        trainModel.setTrainCreator(trainerService.findNameById(trainerId));
        trainService.save(trainModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(trainModel);
    }

    @PostMapping("/register/user/{userId}")
    public ResponseEntity<Object> registerUserInTrain (@PathVariable(value = "userId") UUID userId,
                                                @RequestBody @Valid UserTrainDto userTrainDto) {
        var userTrainModel = new UserTrainModel();
        BeanUtils.copyProperties(userTrainDto, userTrainModel);
        userTrainModel.setUserId(userId);
        userTrainService.save(userTrainModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(userTrainModel);
    }

    @GetMapping("/trainer/{trainerId}")
    public ResponseEntity<Page<TrainModel>> getAllTrainsFromOneTrainer(
                                                        @PageableDefault(page = 0, size = 10, sort = "trainerId", direction = Sort.Direction.DESC) Pageable pageable,
                                                        @PathVariable(value = "trainerId") UUID trainerId) {

        Specification<TrainModel> spec = TrainSpecifications.trainsByTrainerId(trainerId);

        return ResponseEntity.status(HttpStatus.OK).body(trainService.findAll(spec, pageable));
    }

    @GetMapping("/{trainerId}/expiring-in-10-days")
    public ResponseEntity<Page<TrainModel>> getTrainsExpiringIn10Days(
                                                @PageableDefault(page = 0, size = 10, sort = "expirationDate", direction = Sort.Direction.ASC) Pageable pageable,
                                                @PathVariable(value = "trainerId") UUID trainerId) {

        Specification<TrainModel> spec = TrainSpecifications.trainsExpiringIn10Days(trainerId);
        return ResponseEntity.status(HttpStatus.OK).body(trainService.findAll(spec, pageable));
    }

    @GetMapping("/{trainerId}/expired")
    public ResponseEntity<Page<TrainModel>> getExpiredTrains(
            @PageableDefault(page = 0, size = 10, sort = "expirationDate", direction = Sort.Direction.ASC) Pageable pageable,
            @PathVariable(value = "trainerId") UUID trainerId) {

        Specification<TrainModel> spec = TrainSpecifications.trainsExpired(trainerId);
        return ResponseEntity.status(HttpStatus.OK).body(trainService.findAll(spec, pageable));
    }

    @GetMapping
    public ResponseEntity<Page<UserModel>> getAllUsers(SpecificationTemplate.UserSpec spec,
                                                       @PageableDefault(page = 0, size = 10, sort = "userId", direction = Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll(spec, pageable));
    }

    @GetMapping("/{trainId}")
    public ResponseEntity<Object> getOneTrain(@PathVariable(value = "trainId") UUID trainId) {
        Optional<TrainModel> trainModelOptional = trainService.findById(trainId);
        if(!trainModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Train not found.");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(trainModelOptional.get());
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Object> getAllTrainsFromOneUser(@PathVariable(value = "userId") UUID userId) {
        List<UserTrainModel> userTrains = userTrainService.findByUserId(userId);
        if (userTrains.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        List<UUID> trainIds = userTrains.stream()
                .map(UserTrainModel::getTrainId)
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(trainService.findByIdIn(trainIds));
    }

    @GetMapping("/users-affiliated/{trainId}")
    public ResponseEntity<Object> getAllUsersFromOneTrain(@PathVariable(value = "trainId") UUID trainId) {
        List<UserTrainModel> usersTrain = userTrainService.findByTrainIdList(trainId);
        if (usersTrain.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        List<UUID> userIds = usersTrain.stream()
                .map(UserTrainModel::getUserId)
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(userService.findByIdIn(userIds));
    }



    @DeleteMapping("/delete/{trainId}")
    public ResponseEntity<Object> deleteTrain(@PathVariable(value = "trainId") UUID trainId){
        //log.debug("DELETE deleteUser userId received {}", userId);
        Optional<TrainModel> trainModelOptional = trainService.findById(trainId);
        if(!trainModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        else {
            trainService.delete(trainModelOptional.get());
            Optional<UserTrainModel> userTrainModelOptional = userTrainService.findByTrainId(trainId);
            if(userTrainModelOptional.isPresent()){
                userTrainService.delete(userTrainModelOptional.get());
            }
            return ResponseEntity.status(HttpStatus.OK).body("Train deleted succesfully");
        }
    }

    @PutMapping ("/update/{trainId}")
    public ResponseEntity<Object> updateTrain(@PathVariable(value = "trainId") UUID trainId,
                                              @RequestBody TrainDto trainDto){
        Optional<TrainModel> trainModelOptional = trainService.findById(trainId);
        if (!trainModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Train not found");
        } else {
            var trainModel = trainModelOptional.get();
            BeanUtils.copyProperties(trainDto, trainModel, trainService.getNullPropertyNames(trainDto));
            trainService.save(trainModel);
            return ResponseEntity.status(HttpStatus.OK).body(trainModel);
        }
    }

    @PutMapping ("/extend/{trainId}")
    public ResponseEntity<Object> extendTrain(@PathVariable(value = "trainId") UUID trainId){
        Optional<TrainModel> trainModelOptional = trainService.findById(trainId);
        if (!trainModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Train not found");
        } else {
            var trainModel = trainModelOptional.get();
            trainModel.setTrainStatus(TrainStatus.ACTIVE);
            trainModel.setExpirationDate(LocalDateTime.now(ZoneId.of("UTC")).plusMonths(2));
            trainService.save(trainModel);
            return ResponseEntity.status(HttpStatus.OK).body(trainModel);
        }
    }

    @PutMapping ("/deactivate/{trainId}")
    public ResponseEntity<Object> deactivateTrain(@PathVariable(value = "trainId") UUID trainId){
        Optional<TrainModel> trainModelOptional = trainService.findById(trainId);
        if (!trainModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Train not found");
        } else {
            var trainModel = trainModelOptional.get();
            trainModel.setTrainStatus(TrainStatus.BLOCKED);
            trainModel.setExpirationDate(LocalDateTime.now(ZoneId.of("UTC")));
            trainService.save(trainModel);
            return ResponseEntity.status(HttpStatus.OK).body(trainModel);
        }
    }

}
