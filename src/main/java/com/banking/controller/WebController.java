package com.banking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {
    
    @GetMapping("/")
    public String index() {
        return "redirect:/dashboard";
    }
    
    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }
    
    @GetMapping("/register")
    public String register() {
        return "register";
    }
    
    @GetMapping("/accounts")
    public String accounts() {
        return "accounts";
    }
    
    @GetMapping("/deposit")
    public String deposit() {
        return "deposit";
    }
    
    @GetMapping("/withdraw")
    public String withdraw() {

        return "withdraw";
    }
    
    @GetMapping("/transfer")
    public String transfer() {
        return "transfer";
    }
    
    @GetMapping("/view-data")
    public String viewData() {
        return "view-data";
    }
}