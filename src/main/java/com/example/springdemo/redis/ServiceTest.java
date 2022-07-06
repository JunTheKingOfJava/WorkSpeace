package com.example.springdemo.redis;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
class SkillServiceTest {

    @Autowired
    SkillService service;

    @RequestMapping("/testSkillService")
    public void TestSkillService(){
        for (int i = 10; i < 60; i++) { //开50个线程
            SkillThread skillThread = new SkillThread(service, "skillThread->" + i);
            skillThread.start();
        }
    }
}

class SkillThread extends Thread {

    private SkillService skillService;

    public SkillThread(SkillService skillService, String skillThreadName) {
        super(skillThreadName);
        this.skillService = skillService;
    }

    @Override
    public void run() {
        skillService.seckill();
    }
}
