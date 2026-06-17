package com.example.bfhlapi.service;

import com.example.bfhlapi.dto.RequestDto;
import com.example.bfhlapi.dto.ResponseDto;

public interface BfhlService {

    ResponseDto process(RequestDto request, String requestId);
}