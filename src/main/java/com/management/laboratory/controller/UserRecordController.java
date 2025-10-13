package com.management.laboratory.controller;

import com.management.laboratory.mapper.UserRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class UserRecordController {
    @Autowired
    UserRecordMapper userRecordMapper;

}
