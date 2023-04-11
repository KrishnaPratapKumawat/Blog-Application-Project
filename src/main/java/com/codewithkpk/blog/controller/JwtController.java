package com.codewithkpk.blog.controller;

import com.codewithkpk.blog.entity.JwtModelRequest;
import com.codewithkpk.blog.entity.JwtResponse;
import com.codewithkpk.blog.payloads.JwtUtils;
import com.codewithkpk.blog.services.impl.CustomerUserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/JwtToken")
public class JwtController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private CustomerUserDetailServiceImpl customerUserDetailService;
    @Autowired
    private JwtUtils jwtUtils;
    @PostMapping("/token")
    public ResponseEntity<JwtResponse> createToke(@RequestBody JwtModelRequest jwtModelRequest) throws Exception {
        try {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtModelRequest.getUsername(),jwtModelRequest.getPassword()));
        }catch (Exception e){
            throw new Exception("Bad Credential"+e);
        }
        UserDetails userDetails = this.customerUserDetailService.loadUserByUsername(jwtModelRequest.getUsername());
        String token = jwtUtils.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }
}
