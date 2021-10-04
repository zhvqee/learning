package com.test.controller;

import com.test.annotations.RegistryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/test-service/test")
@RegistryService
public class Constroller {


    @RequestMapping("/get")
    public Map<String, Object> get(String abc) {
        Map<String, Object> result = new HashMap<>();
        result.put("result", "helloWorld->" + abc);
        System.out.println(result);
        return result;
    }

    @RequestMapping("/get2")
    public Map<String, Object> get2(String abc) {
        Map<String, Object> result = new HashMap<>();
        result.put("result", "helloWorld2->" + abc);
        System.out.println(result);
        return result;
    }
}
