package com.test.productmanagement.service;

import com.test.productmanagement.model.Role;
import com.test.productmanagement.model.User;
import com.test.productmanagement.model.enums.ERole;
import com.test.productmanagement.repository.RoleRepository;
import com.test.productmanagement.repository.UserRepository;
import com.test.productmanagement.security.jwt.JwtUtils;
import com.test.productmanagement.security.payload.request.LoginRequest;
import com.test.productmanagement.security.payload.request.SignupRequest;
import com.test.productmanagement.security.payload.response.JwtResponse;
import com.test.productmanagement.security.payload.response.MessageResponse;
import com.test.productmanagement.security.service.UserDetailsImpl;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;

    @Autowired
    public UserService(AuthenticationManager authenticationManager,
                       JwtUtils jwtUtils,
                       UserRepository userRepository,
                       RoleRepository roleRepository,
                       PasswordEncoder encoder) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils              = jwtUtils;
        this.userRepository        = userRepository;
        this.roleRepository        = roleRepository;
        this.encoder               = encoder;

    }

    public ResponseEntity<?> authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
            userDetails.getId(),
            userDetails.getUsername(),
            userDetails.getEmail(),
            roles));
    }

    public ResponseEntity<?> registerUser(SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Error: Username is already taken!"));
        }

        User user = new User(
            signUpRequest.getUsername(),
            encoder.encode(signUpRequest.getPassword()));

        Set<Role> roles = new HashSet<>();

        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(adminRole);

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
