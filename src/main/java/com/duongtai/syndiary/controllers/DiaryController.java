package com.duongtai.syndiary.controllers;

import com.duongtai.syndiary.configs.Snippets;
import com.duongtai.syndiary.entities.Diary;
import com.duongtai.syndiary.entities.ResponseObject;
import com.duongtai.syndiary.entities.User;
import com.duongtai.syndiary.services.impl.DiaryServiceImpl;
import com.duongtai.syndiary.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.duongtai.syndiary.configs.MyUserDetail.getUsernameLogin;

@RestController
@CrossOrigin
@RequestMapping("/diary/")
public class DiaryController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private DiaryServiceImpl diaryService;

    @PostMapping("create")
    public ResponseEntity<ResponseObject> createNew(@RequestBody Diary diary){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(Snippets.SUCCESS, Snippets.DIARY_CREATE_SUCCESS, diaryService.createDiary(diary)));
    }

    @GetMapping("/{username}/{id}")
    public ResponseEntity<ResponseObject> getDiaryById(@PathVariable Long id, @PathVariable String username){

        Diary diary = diaryService.getDiaryById(id);
        if(diary != null) {
            if(username.equalsIgnoreCase(diary.getAuthor().getUsername())){
                    return ResponseEntity.status(HttpStatus.OK).body(
                            new ResponseObject(Snippets.SUCCESS, Snippets.DIARY_FOUND,diary));
                }

                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject(Snippets.FAILED, Snippets.YOU_DONT_HAVE_PERMISSION,null));
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(Snippets.FAILED, Snippets.DIARY_NOT_FOUND,null));
    }

    @GetMapping("all")
    public ResponseEntity<ResponseObject> getAllByAuth(){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(Snippets.SUCCESS, String.format(Snippets.LIST_DIARY_BY,getUsernameLogin()),diaryService.findAllByAuthUsername()));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<ResponseObject> deleteById(@PathVariable Long id){
        if(diaryService.deleteDiaryById(id)) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(Snippets.SUCCESS, Snippets.DIARY_DELETED, null));
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(Snippets.SUCCESS, Snippets.DIARY_FOUND,null));
    }

    @PutMapping("edit/{id}")
    public ResponseEntity<ResponseObject> editById(@PathVariable Long id, @RequestBody Diary diary){
        diary.setId(id);
        if(diaryService.getDiaryById(id) != null) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(Snippets.SUCCESS, Snippets.DIARY_EDITED,diaryService.editDiaryById(diary)));
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(Snippets.FAILED, Snippets.DIARY_NOT_FOUND,null));
    }

}
