package com.service.beauth.service;

import com.service.beauth.constant.Constant;
import com.service.beauth.dto.LoginRequest;
import com.service.beauth.dto.LoginResponse;
import com.service.beauth.dto.Response;
import com.service.beauth.dto.UserRequest;
import com.service.beauth.entity.User;
import com.service.beauth.exception.BadRequestCustomException;
import com.service.beauth.exception.DataExistException;
import com.service.beauth.exception.NotFoundException;
import com.service.beauth.repository.UserRepository;
import com.service.beauth.security.jwt.JwtUtils;
import com.service.beauth.security.service.UserDetailsImpl;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtils jwtUtils;

    @Value("${jwt.expirationMs}")
    private int jwtExpirationMs;
    @Value("${user.role}")
    private String userRole;
    @Value("${user.gender}")
    private String userGender;

    Date nowDate = new Date();

    @Transactional
    public Response<Object> register(UserRequest userRequest) {
        User user = userRepository.findByUsername(userRequest.getUsername());
        if (Objects.nonNull(user)) {
            throw new DataExistException(Constant.Message.EXIST_DATA_MESSAGE);
        }

        if (!isValid(userRole, userRequest.getRole())) {
            throw new BadRequestCustomException(Constant.Message.FORBIDDEN_REQUEST_MESSAGE.replace("{value}", "role"));
        }
        if (!isValid(userGender, userRequest.getGender())) {
            throw new BadRequestCustomException(Constant.Message.FORBIDDEN_REQUEST_MESSAGE.replace("{value}", "gender"));
        }

        User savedUser = userRepository.save(mappingUser(userRequest));
        return Response.builder()
                .responseCode(Constant.Response.SUCCESS_CODE)
                .responseMessage(Constant.Response.SUCCESS_MESSAGE)
                .data(savedUser)
                .build();

    }


    public Response<Object> login(LoginRequest loginRequest) {

        User user = userRepository.findByUsername(loginRequest.getUsername());
        if (Objects.isNull(user)) {
            throw new NotFoundException(Constant.Message.INVALID_LOGIN_MESSAGE);
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        LoginResponse response = mappingLoginResponse(user, jwt);
        return Response.builder()
                .responseCode(Constant.Response.SUCCESS_CODE)
                .responseMessage(Constant.Response.SUCCESS_MESSAGE)
                .data(response)
                .build();
    }

    public Response<Object> validateAccessToken(String accessToken) {
        boolean isValid = jwtUtils.validateJwtToken(accessToken);
        if (!isValid) {
            throw new BadRequestCustomException(Constant.Message.INVALID_TOKEN_MESSAGE);
        }
        return Response.builder()
                .responseCode(Constant.Response.SUCCESS_CODE)
                .responseMessage(Constant.Response.SUCCESS_VALID_TOKEN_MESSAGE)
                .build();
    }

    private LoginResponse mappingLoginResponse(User user,String jwt) {
        return LoginResponse.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .accessToken(jwt)
                .tokenType("Bearer")
                .expiresIn(jwtExpirationMs)
                .role(user.getRole())
                .build();
    }

    private User mappingUser(UserRequest userRequest) {
        return User.builder()
                .name(userRequest.getName())
                .phoneNumber(userRequest.getPhoneNumber())
                .address(userRequest.getAddress())
                .email(userRequest.getEmail())
                .gender(userRequest.getGender())
                .username(userRequest.getUsername())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .role(userRequest.getRole())
                .createdBy("")
                .createdDate(this.nowDate)
                .isDeleted(false)
                .build();
    }

    private boolean isValid(String value, String request) {
        String[] valueList = value.split("\\|");
        List<String> reqList = new ArrayList<>();
        for (int i=0 ; i < valueList.length; i++) {
            if (valueList[i].equals(request)) {
                reqList.add(request);
                break;
            }
        }
        if (reqList.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

}
