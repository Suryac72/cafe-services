package com.cafe.java.cafebackend.impl.user;

import java.security.Key;
import java.time.LocalDate;
import java.util.*;

import com.cafe.java.cafebackend.impl.password.EncryptionService;
import com.cafe.java.cafebackend.models.Password;
import com.cafe.java.cafebackend.repo.PasswordRepository;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.cafe.java.cafebackend.constants.CafeConstants;
import com.cafe.java.cafebackend.dto.LoginDTO;
import com.cafe.java.cafebackend.mappers.UserProfileMapper;
import com.cafe.java.cafebackend.models.UserProfile;
import com.cafe.java.cafebackend.repo.UserRepository;
import com.cafe.java.cafebackend.services.user.UserService;
import com.cafe.java.cafebackend.services.auth.CustomerDetailsUserService;
import com.cafe.java.cafebackend.services.auth.JwtFilter;
import com.cafe.java.cafebackend.services.auth.JwtUtil;
import com.cafe.java.cafebackend.utils.CafeUtils;
import com.cafe.java.cafebackend.utils.EmailUtils;
import com.cafe.java.cafebackend.wrappers.UserWrapper;

import jakarta.validation.Valid;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserProfileMapper mapper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomerDetailsUserService customerDetailsUserService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    EmailUtils emailUtils;

    @Autowired
    EncryptionService encryptionService;

    @Autowired
    PasswordRepository passwordRepository;

    @Override
    public ResponseEntity<String> signUp(@Valid UserProfile userProfile, BindingResult result) {

        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(128); // or 192 or 256 bits
            SecretKey generatedKey = keyGenerator.generateKey();

            System.out.println("Generated Key Algorithm: " + generatedKey.getAlgorithm());
            System.out.println("Generated Key Size (bits): " + keyGenerator.generateKey());
            if (userProfile.getUserName() == null && userProfile.getUserEmail() == null
                    && userProfile.getPassword() == null && userProfile.getUserPhoneNo() == null) {
                return CafeUtils.getResponseEntity(CafeConstants.INVALID_REQUEST_PAYLOAD,
                        HttpStatus.INTERNAL_SERVER_ERROR);
            }
            if (result.hasErrors()) {
                return CafeUtils.handleValidationErrors(result);
            }
            UserProfile user = this.userRepository.findByUserEmail(userProfile.getUserEmail());
            if (Objects.isNull(user)) {
                Password password = new Password();
                password.setUserHashedPassword(passwordEncoder.encode(userProfile.getPassword()));
                password.setUserEncryptedPassword(encryptionService.encrypt(userProfile.getPassword()));
                password.setUserEmail(userProfile.getUserEmail());
                passwordRepository.save(password);
                userProfile.setPassword(passwordEncoder.encode(userProfile.getPassword()));
                userProfile.setCreatedAt(LocalDate.now().toString());
                userProfile.setRole("USER");
                userProfile.setStatus("false");
                this.userRepository.save(userProfile);
                return CafeUtils.getResponseEntity(CafeConstants.USER_REGISTER_SUCCESSFULLY, HttpStatus.OK);
            } else {
                return CafeUtils.getResponseEntity(CafeConstants.EMAIL_ALREADY_EXISTS,
                        HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<String> login(@Valid LoginDTO login, BindingResult result) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(login.getUserEmail(), login.getPassword()));

            if (auth.isAuthenticated()) {
                if (customerDetailsUserService.getUserProfile().getStatus().equalsIgnoreCase("true")) {
                    return new ResponseEntity<String>(
                            "{\"token\":\""
                                    + jwtUtil.generateToken(customerDetailsUserService.getUserProfile().getUserEmail(),
                                            customerDetailsUserService.getUserProfile().getRole())
                                    + "\"}",
                            HttpStatus.OK);
                } else {
                    return CafeUtils.getResponseEntity("Wait for admin approval", HttpStatus.BAD_REQUEST);
                }
            } else {
                return CafeUtils.getResponseEntity("Unauthorized Access", HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return CafeUtils.getResponseEntity("Unauthorized Access", HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public ResponseEntity<List<UserWrapper>> getAllUsers() {
        try {
            if (!jwtFilter.isAdmin()) {
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
            }
            List<UserProfile> users = userRepository.getAllUsers();
            List<UserWrapper> result = mapper.userProfileMapper(users);
            return new ResponseEntity<List<UserWrapper>>(result, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public  ResponseEntity<String> updateUser(String userId,Map<String,String> requestMap) {
        try {
            if (requestMap.size() == 0) {
                return CafeUtils.getResponseEntity(CafeConstants.INVALID_REQUEST_PAYLOAD,
                        HttpStatus.INTERNAL_SERVER_ERROR);
            }
            else if(jwtFilter.isAdmin()){
                 UserProfile user = userRepository.findByUserEmail(userId);
                 if(Objects.isNull(user)){
                    return CafeUtils.getResponseEntity(CafeConstants.USER_NOT_FOUND,HttpStatus.NOT_FOUND);
                 }
                 else {
                    userRepository.updateStatus(requestMap.get("status"),LocalDate.now().toString(),userId);
                    sendMailToAllAdmin(requestMap.get("status"),user.getUserEmail(),userRepository.getAllAdmins());
                    return CafeUtils.getResponseEntity(CafeConstants.USER_STATUS_UPDATED_SUCCESSFULLY, HttpStatus.OK);
                 }
                 
            }
            else {
                return CafeUtils.getResponseEntity(CafeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    private void sendMailToAllAdmin(String status, String user, List<String> allAdmins) {
        allAdmins.remove(jwtFilter.getCurrentUser());
        if(status != null && status.equalsIgnoreCase("true")){
            //send mail to admin that a user has been activated
            emailUtils.sendSimpleMessage(jwtFilter.getCurrentUser(),"Account Approved","USER:- " + user +"\n is approved by \nADMIN:- "+jwtFilter.getCurrentUser(),allAdmins);
        }
        else {
              emailUtils.sendSimpleMessage(jwtFilter.getCurrentUser(),"Account Disabled","USER:- " + user +"\n is disabled by \nADMIN:- "+jwtFilter.getCurrentUser(),allAdmins);
        }
    }

    @Override
    public ResponseEntity<String> checkToken() {
        return CafeUtils.getResponseEntity("true",HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> changePassword(Map<String, String> requestMap) {
        try {
            UserProfile user = userRepository.findByUserEmail(jwtFilter.getCurrentUser());
            if(!user.equals(null)){
                String oldPassword = requestMap.get("oldPassword");
                String newPassword = requestMap.get("newPassword");
                if(oldPassword.equals(newPassword)){
                    return CafeUtils.getResponseEntity(CafeConstants.OLD_PASSWORD_CANNOT_BE_SAME_AS_NEW_ONE,HttpStatus.BAD_REQUEST);
                }
                if(passwordEncoder.matches(oldPassword,user.getPassword())){
                    Password password = new Password();
                    password.setUserHashedPassword(passwordEncoder.encode(newPassword));
                    password.setUserEncryptedPassword(encryptionService.encrypt(newPassword));
                    password.setUserEmail(user.getUserEmail());
                    user.setPassword(passwordEncoder.encode(requestMap.get("newPassword")));
                    userRepository.save(user);
                    passwordRepository.save(password);
                    return CafeUtils.getResponseEntity(CafeConstants.PASSWORD_UPDATED_SUCCESSFULLY,HttpStatus.OK);
                }
                return CafeUtils.getResponseEntity(CafeConstants.INCORRECT_OLD_PASSWORD,HttpStatus.BAD_REQUEST);
            }
            return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> forgotPassword(Map<String, String> requestMap) {
        try {
            UserProfile user = userRepository.findByUserEmail(requestMap.get("userEmail"));
            Password password = passwordRepository.findPasswordByUserEmail(requestMap.get("userEmail"));
            String credPassword = encryptionService.decrypt(password.getUserEncryptedPassword());
            if(!Objects.isNull(user) && !Strings.isNullOrEmpty(user.getUserEmail())){
                emailUtils.forgotMail(user.getUserEmail(),"Credentials by Cafe Services",credPassword);
            }
            return CafeUtils.getResponseEntity(CafeConstants.CHECK_YOUR_MAIL_FOR_CREDENTIALS,HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

    }

}
