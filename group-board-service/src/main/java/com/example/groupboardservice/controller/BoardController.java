package com.example.groupboardservice.controller;

import com.example.groupboardservice.data.response.ResponseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping
@RequiredArgsConstructor
@Tag(name = "Board", description = "게시판 API")
public class BoardController {

    // 게시판 등록
    @PostMapping("/board")
    public ResponseDto createBoard() {
        log.info(this.getClass().getName() + ".createBoard start");

        log.info(this.getClass().getName() + ".createBoard end");
        return new ResponseDto();
    }

    // 게시판 조회

    // 게시판 수정

    // 게시판 삭제
}
