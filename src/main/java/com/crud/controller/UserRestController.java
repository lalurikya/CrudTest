package com.crud.controller;

import com.crud.component.JwtManager;
import com.crud.dto.account.RequestTokenDTO;
import com.crud.dto.account.ResponseTokenDTO;
import com.crud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/users")
public class UserRestController {

    @Autowired
    private JwtManager jwtManager;

    @Autowired
    private UserService userService;

    @PostMapping("/authenticate")
    public ResponseEntity<Object> post(@RequestBody RequestTokenDTO requestDTO){
        //1. check apakah auth
        var isAuthenticated = userService.checkUsernamePassword(requestDTO.getUsername(), requestDTO.getPassword());
        if(isAuthenticated){
            var token = jwtManager.generateToken(
                    requestDTO.getUsername(),
                    requestDTO.getSubject(),
                    requestDTO.getAudience(),
                    requestDTO.getSecretKey()
            );
            var responseDTO = new ResponseTokenDTO(requestDTO.getUsername(), token);
            return ResponseEntity.status(200).body(responseDTO);
        }
        return ResponseEntity.status(401).body("Username dan password salah");
    }
}
