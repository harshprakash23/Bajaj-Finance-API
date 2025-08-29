package com.example.bfhl_api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
public class BfhlController {

    @PostMapping("/bfhl")
    public ResponseEntity<Object> processData(@RequestBody BfhlRequest request) {
        try {
            if (request == null || request.getData() == null) {
                throw new IllegalArgumentException("Missing 'data' in request body");
            }

            List<String> data = request.getData();
            List<String> evenNumbers = new ArrayList<>();
            List<String> oddNumbers = new ArrayList<>();
            List<String> alphabets = new ArrayList<>();
            List<String> specialCharacters = new ArrayList<>();
            long totalSum = 0;
            List<Character> allAlphaChars = new ArrayList<>();

            for (String item : data) {
                if (item == null) continue; // Skip nulls gracefully

                if (item.matches("\\d+")) { // is digit
                    long num = Long.parseLong(item); // Use long to handle large numbers
                    totalSum += num;
                    if (num % 2 == 0) {
                        evenNumbers.add(item);
                    } else {
                        oddNumbers.add(item);
                    }
                } else if (item.matches("[a-zA-Z]+")) { // is alpha
                    alphabets.add(item.toUpperCase());
                    for (char c : item.toCharArray()) {
                        allAlphaChars.add(c);
                    }
                } else {
                    specialCharacters.add(item);
                }
            }

            // Build concat_string
            Collections.reverse(allAlphaChars);
            StringBuilder concatBuilder = new StringBuilder();
            for (int i = 0; i < allAlphaChars.size(); i++) {
                char c = allAlphaChars.get(i);
                if (i % 2 == 0) {
                    concatBuilder.append(Character.toUpperCase(c));
                } else {
                    concatBuilder.append(Character.toLowerCase(c));
                }
            }

            // Build response
            BfhlResponse response = new BfhlResponse();
            response.setIsSuccess(true);
            response.setUserId("john_doe_17091999"); // Replace with your full_name_ddmmyyyy in lowercase
            response.setEmail("john@xyz.com"); // Replace with your email
            response.setRollNumber("ABCD123"); // Replace with your roll number
            response.setOddNumbers(oddNumbers);
            response.setEvenNumbers(evenNumbers);
            response.setAlphabets(alphabets);
            response.setSpecialCharacters(specialCharacters);
            response.setSum(String.valueOf(totalSum));
            response.setConcatString(concatBuilder.toString());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            BfhlErrorResponse errorResponse = new BfhlErrorResponse();
            errorResponse.setIsSuccess(false);
            errorResponse.setError(e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }
}