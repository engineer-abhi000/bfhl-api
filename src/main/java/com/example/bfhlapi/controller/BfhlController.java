package com.example.bfhlapi.controller;

import com.example.bfhlapi.dto.RequestDto;
import com.example.bfhlapi.dto.ResponseDto;
import com.example.bfhlapi.service.BfhlService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bfhl")
public class BfhlController {

    private final BfhlService bfhlService;

    public BfhlController(BfhlService bfhlService) {
        this.bfhlService = bfhlService;
    }

    @PostMapping
    public ResponseEntity<ResponseDto> process(
            @Valid @RequestBody RequestDto request,
            @RequestHeader(value = "X-Request-Id", required = false) String requestId) {

        return ResponseEntity.ok(
                bfhlService.process(request, requestId)
        );
    }
    @GetMapping("/health")
    public String health() {
        return "OK";
    }
}