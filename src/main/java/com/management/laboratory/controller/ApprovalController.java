package com.management.laboratory.controller;

import com.management.laboratory.entity.Approval;
import com.management.laboratory.mapper.ApprovalMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
public class ApprovalController {
    @Autowired
    ApprovalMapper approvalMapper;

    /**
     * 获取所有审批信息
     * @return 审批信息列表
     */
    @RequestMapping("/getAllApprovals")
    public List<Approval> getAllApprovals() {
        return approvalMapper.selectAllApprovals();
    }

    /**
     * 根据学生ID获取该学生的所有审批信息
     * @param studentInfo 学生信息
     * @return 该学生的审批信息列表
     */
    @RequestMapping("/getApprovalsByStudentId")
    public List<Approval> getApprovalsByStudentId(Map<String, String> studentInfo) {
        return approvalMapper.selectApprovalsByStudentId(Integer.parseInt(studentInfo.get("studentId")));
    }
}

