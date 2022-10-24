package com.duongtai.syndiary.services;

import com.duongtai.syndiary.entities.Diary;
import com.duongtai.syndiary.entities.ResponseObject;

import java.util.List;

import org.springframework.http.ResponseEntity;

public interface DiaryService {
    Diary createDiary(Diary diary);

    Diary editDiaryById(Diary diary);

    Diary getDiaryById(Long id);

    boolean deleteDiaryById(Long id);

    List<Diary> findAllByAuthUsername();

}