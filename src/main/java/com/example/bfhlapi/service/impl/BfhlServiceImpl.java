package com.example.bfhlapi.service.impl;

import com.example.bfhlapi.dto.RequestDto;
import com.example.bfhlapi.dto.ResponseDto;
import com.example.bfhlapi.service.BfhlService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BfhlServiceImpl implements BfhlService {

    @Override
    public ResponseDto process(RequestDto request, String requestId) {

        long startTime = System.currentTimeMillis();

        List<String> oddNumbers = new ArrayList<>();
        List<String> evenNumbers = new ArrayList<>();
        List<String> alphabets = new ArrayList<>();
        List<String> specialCharacters = new ArrayList<>();

        Set<String> uniqueValues = new LinkedHashSet<>();

        boolean containsDuplicates = false;

        for (String value : request.getData()) {

            if (value == null || value.trim().isEmpty()) {
                continue;
            }

            if (!uniqueValues.add(value)) {
                containsDuplicates = true;
            }
        }

        double sum = 0;
        Double largest = null;
        Double smallest = null;

        int alphabetCount = 0;
        int numberCount = 0;
        int specialCount = 0;

        for (String value : uniqueValues) {

            if (value.matches("-?\\d+(\\.\\d+)?")) {

                double num = Double.parseDouble(value);

                sum += num;

                if (largest == null || num > largest)
                    largest = num;

                if (smallest == null || num < smallest)
                    smallest = num;

                numberCount++;

                if (num % 2 == 0)
                    evenNumbers.add(value);
                else
                    oddNumbers.add(value);

            }

            else if(value.matches(".*\\d.*") && value.matches(".*[a-zA-Z].*")) {

                String letters = value.replaceAll("[^a-zA-Z]", "");
                String numbers = value.replaceAll("[^0-9.-]", "");

                for(char c : letters.toUpperCase().toCharArray()) {

                    alphabets.add(String.valueOf(c));
                    alphabetCount++;
                }

                if(!numbers.isEmpty()) {

                    double num = Double.parseDouble(numbers);

                    sum += num;

                    if(largest == null || num > largest)
                        largest = num;

                    if(smallest == null || num < smallest)
                        smallest = num;

                    numberCount++;

                    if(num % 2 == 0)
                        evenNumbers.add(numbers);
                    else
                        oddNumbers.add(numbers);
                }
            }

            else if (value.matches("[a-zA-Z]+")) {

                for(char c : value.toUpperCase().toCharArray()) {

                    alphabets.add(String.valueOf(c));
                    alphabetCount++;
                }
            }

            else if (value.matches("[^a-zA-Z0-9]+")) {

                specialCharacters.add(value);
                specialCount++;

            }
        }

        ResponseDto response = new ResponseDto();

        response.setIs_success(true);
        response.setRequest_id(requestId);

        response.setOdd_numbers(oddNumbers);
        response.setEven_numbers(evenNumbers);
        response.setAlphabets(alphabets);
        response.setSpecial_characters(specialCharacters);

        response.setSum(String.valueOf(sum));

        response.setLargest_number(
                largest == null ? "" : String.valueOf(largest));

        response.setSmallest_number(
                smallest == null ? "" : String.valueOf(smallest));

        response.setAlphabet_count(alphabetCount);
        response.setNumber_count(numberCount);
        response.setSpecial_character_count(specialCount);

        response.setContains_duplicates(containsDuplicates);

        response.setUnique_element_count(uniqueValues.size());

        response.setProcessing_time_ms(
                System.currentTimeMillis() - startTime);

        return response;
    }
}