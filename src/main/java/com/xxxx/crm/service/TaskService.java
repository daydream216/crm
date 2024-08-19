package com.xxxx.crm.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TaskService {
    @Resource
    private CustomerService customerService;

    @Scheduled(cron = "0/2 * * * * ?")
    public void job(){

    }
}
