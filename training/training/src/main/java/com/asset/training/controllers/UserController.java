package com.asset.training.controllers;

import com.asset.training.constants.ErrorCodes;
import com.asset.training.models.BaseResponse;
import com.asset.training.models.LoginModel;
import com.asset.training.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/auth/login", method = RequestMethod.POST)
    public BaseResponse<String> login(@RequestBody LoginModel loginModel){
        String token = userService.login(loginModel);
        return new BaseResponse(ErrorCodes.SUCCESS.SUCCESS, "success", token);
    }


    @RequestMapping(value = "/get-all", method = RequestMethod.GET)
    public String getAll(){
        return "Fine";
    }

    @RequestMapping(value = "/get/{username}", method = RequestMethod.GET)
    public String getByUserName(@RequestParam String username){
        userService.getByUsername(username);
        return "Fine";
    }
}
