package com.innowise.minispring.service;

import com.innowise.minispring.annotation.Autowired;
import com.innowise.minispring.annotation.Component;
import com.innowise.minispring.lifecycle.InitializingBean;
import com.innowise.minispring.repository.RepositoryTest;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ServiceTest implements InitializingBean {

    @Getter
    @Autowired
    private RepositoryTest repositoryTest;

    @Getter
    private boolean initialized = false;

    public String serve() {
        return "Service using " + repositoryTest.getData();
    }

    @Override
    public void afterPropertiesSet() {
        initialized = true;
        log.info("Method afterPropertiesSet() called in ServiceTest.");
    }
}
