package com.springboot_pkce.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class ApiController {

    @GetMapping("/api/data")
    public List<Map<String, Object>> userInfo(@AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getClaim("preferred_username").toString();
        String email = jwt.getClaim("email");
        System.out.println("Authenticate user: " + username + "," + "email:" + email);
        return List.of(
                Map.of(
                        "id", 101,
                        "name", "John Doe",
                        "contact", "+977-9812345678",
                        "email", "johndoe@example.com",
                        "address", "Kathmandu, Nepal",
                        "course", "Computer Science",
                        "year", 3,
                        "gpa", 3.65
                ),
                Map.of(
                        "id", 102,
                        "name", "Sita Sharma",
                        "contact", "+977-9801122334",
                        "email", "sitasharma@example.com",
                        "address", "Pokhara, Nepal",
                        "course", "Information Technology",
                        "year", 2,
                        "gpa", 3.80
                ),
                Map.of(
                        "id", 103,
                        "name", "Ramesh Thapa",
                        "contact", "+977-9856677889",
                        "email", "rameshthapa@example.com",
                        "address", "Lalitpur, Nepal",
                        "course", "Electronics Engineering",
                        "year", 4,
                        "gpa", 3.45
                ),
                Map.of(
                        "id", 104,
                        "name", "Anjali Koirala",
                        "contact", "+977-9812233445",
                        "email", "anjalikoirala@example.com",
                        "address", "Bhaktapur, Nepal",
                        "course", "Business Administration",
                        "year", 1,
                        "gpa", 3.90
                ),
                Map.of(
                        "id", 105,
                        "name", "Prakash Yadav",
                        "contact", "+977-9867788990",
                        "email", "prakashyadav@example.com",
                        "address", "Biratnagar, Nepal",
                        "course", "Data Science",
                        "year", 3,
                        "gpa", 3.72
                )
        );
    }
}
