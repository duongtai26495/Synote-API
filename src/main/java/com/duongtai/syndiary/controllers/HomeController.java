package com.duongtai.syndiary.controllers;

import com.duongtai.syndiary.configs.Snippets;
import com.duongtai.syndiary.entities.ResponseObject;
import com.duongtai.syndiary.services.impl.StorageServiceImpl;
import com.duongtai.syndiary.services.impl.UserServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class HomeController {

    @Autowired
    private StorageServiceImpl storageService;
    
    @Autowired
    private UserServiceImpl userService;

    @GetMapping("")
    public ResponseEntity<ResponseObject> pingBE (){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(Snippets.SUCCESS, Snippets.PING_SUCCESS,null));
    }

    @GetMapping("images/{fileName:.+}")
    public ResponseEntity<byte[]> readFile (@PathVariable String fileName){
        return storageService.readFile(fileName);
    }

    @PostMapping("forgot_password")
    public void forgot_password(@RequestParam("email") String email){
        if(userService.findByEmail(email) != null){
            userService.send_email_reset_password(email);
        }
    }
}
