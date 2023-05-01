package com.study.board.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity     // Entity 어노테이션 == 테이블
@Data
public class Board {

    @Id     // pk임을 의미
    @GeneratedValue(strategy = GenerationType.IDENTITY)     // 마리아디비에서 사용 (enerationType.IDENTITY)
    private Integer id;

    private String title;

    private String content;

    private String filename;

    private String filepath;

}
