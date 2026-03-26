package com.kishansetu.kishan_setu_backend.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.kishansetu.kishan_setu_backend.config.CloudinaryConfig;
import com.kishansetu.kishan_setu_backend.model.UserModel;
import com.kishansetu.kishan_setu_backend.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.auditing.CurrentDateTimeProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepo userRepo;
    @Autowired
    private Cloudinary cloudinary;

    public List<UserModel>  getAllUsers(){
        return userRepo.findAll();
    }

    public ResponseEntity<?> saveUser(UserModel userModel){
        if (userRepo.existsByEmail(userModel.getEmail())){
            return
        ResponseEntity.status(HttpStatus.CONFLICT).body("User Exists already");
        }
        if (userModel.getEmail() == null || userModel.getEmail().trim().isEmpty()
                || userModel.getName() == null || userModel.getName().trim().isEmpty()
                || userModel.getPassword() == null || userModel.getPassword().trim().isEmpty()) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Email, Name or Password cannot be empty");
        }
        UserModel tempUser = userModel;
        tempUser.setCreatedAt(LocalDateTime.now());
        tempUser.setUpdatedAt(LocalDateTime.now());
        UserModel savedUser = userRepo.save(tempUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);

    }

    public ResponseEntity<?> updateUser(UserModel userModel, MultipartFile image) {

        // 1. Validate ID
        if (userModel.getId() == null || userModel.getId().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("User id is required");
        }

        // 2. Find user
        Optional<UserModel> optionalUser = userRepo.findById(userModel.getId());

        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User Not Found / Invalid ID");
        }

        UserModel existingUser = optionalUser.get();

        // 3. Update fields safely
        if (userModel.getName() != null && !userModel.getName().isBlank()) {
            existingUser.setName(userModel.getName());
        }

        if (userModel.getPhone() != null && !userModel.getPhone().isBlank()) {
            existingUser.setPhone(userModel.getPhone());
        }

        if (userModel.getAddress() != null && !userModel.getAddress().isBlank()) {
            existingUser.setAddress(userModel.getAddress());
        }

        if (userModel.getRole() != null && !userModel.getRole().isBlank()) {
            existingUser.setRole(userModel.getRole());
        }

        // ⚠️ password (better to hash)
        if (userModel.getPassword() != null && !userModel.getPassword().isBlank()) {
            existingUser.setPassword(userModel.getPassword());
        }

        // 4. CLOUDINARY IMAGE UPDATE 🚀
        if (image != null && !image.isEmpty()) {
            try {

                Map uploadResult = cloudinary.uploader().upload(
                        image.getBytes(),
                        ObjectUtils.emptyMap()
                );

                String imageUrl = uploadResult.get("secure_url").toString();
                existingUser.setImageUrl(imageUrl);

            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Image upload failed");
            }
        }

        // 5. Timestamp
        existingUser.setUpdatedAt(LocalDateTime.now());

        // 6. Save
        UserModel updatedUser = userRepo.save(existingUser);

        return ResponseEntity.ok(updatedUser);
    }

    public ResponseEntity<?> deleteUser(String userID){
        if (userID.isBlank()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("USER ID REQUIRED");
        }
        if (userRepo.existsById(userID)){
            userRepo.deleteById(userID);
            return ResponseEntity.status(HttpStatus.OK).body("USER DELETED");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("USER DOSE NOT EXIST");



    }

    public ResponseEntity<?> loginUser(UserModel userModel){
        if ( userModel.getPassword()==null || userModel.getEmail() == null || userModel.getPassword().isBlank() || userModel.getEmail().isBlank() ){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("EMAIL AND PASSWORD REQUIRED");
        }
        Optional<UserModel> savedUser;
        savedUser = userRepo.findByEmail(userModel.getEmail());
        if (savedUser.isPresent()){
            if (savedUser.get().getEmail().equals(userModel.getEmail()) && savedUser.get().getPassword().equals(userModel.getPassword())){
                savedUser.get().setPassword("null");
                return ResponseEntity.status(HttpStatus.OK).body(savedUser.get());
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("WRONG CREDENTIALS");
    }
}
