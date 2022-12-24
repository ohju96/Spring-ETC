package com.example.groupboardservice.data.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Data
public class RedisDto {
    private String name = "";
    private String email = "";
    private String addr = "";
    private String test_text = "";
}
