package com.innowise.minispring.model;

import com.innowise.minispring.annotation.Component;
import com.innowise.minispring.annotation.Scope;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Scope("prototype")
public class PrototypeComponentTest {
    public PrototypeComponentTest() {
        log.info("PrototypeComponent created.");
    }
}
