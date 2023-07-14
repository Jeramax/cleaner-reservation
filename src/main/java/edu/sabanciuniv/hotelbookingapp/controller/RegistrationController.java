package edu.sabanciuniv.hotelbookingapp.controller;

import edu.sabanciuniv.hotelbookingapp.model.User;
import edu.sabanciuniv.hotelbookingapp.model.dto.UserRegistrationDTO;
import edu.sabanciuniv.hotelbookingapp.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class RegistrationController {

    private final UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm(@ModelAttribute("user") UserRegistrationDTO registrationDTO) {
        return "register";
    }

    @PostMapping("/register/save")
    public String registerUserAccount(@Valid @ModelAttribute("user") UserRegistrationDTO registrationDTO, BindingResult result) {
        Optional<User> existingUser = userService.findByUsername(registrationDTO.getUsername());

        if (existingUser.isPresent()) {
            result.rejectValue("username", "user.exists", "This username is already registered!");
            return "register";
        }

        if (result.hasErrors()) {
            return "register";
        }

        userService.save(registrationDTO);
        // redirect link needs to be changed
        return "redirect:/register?success";
    }

}