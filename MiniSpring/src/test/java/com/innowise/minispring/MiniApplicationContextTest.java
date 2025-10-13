package com.innowise.minispring;

import com.innowise.minispring.model.PrototypeComponentTest;
import com.innowise.minispring.service.ServiceTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

public class MiniApplicationContextTest {

    @Test
    void injectDependenciesAndCallPropertiesSet() {
        MiniApplicationContext context = new MiniApplicationContext("com.innowise.minispring");
        ServiceTest service = context.getBean(ServiceTest.class);
        assertNotNull(service);
        assertNotNull(service.getRepositoryTest());
        assertTrue(service.isInitialized());
        assertEquals("Service using data", service.serve());
    }

    @Test
    void createNewInstanceForPropertyScope() {
        MiniApplicationContext context = new MiniApplicationContext("com.innowise.minispring");
        PrototypeComponentTest prototype1 = context.getBean(PrototypeComponentTest.class);
        PrototypeComponentTest prototype2 = context.getBean(PrototypeComponentTest.class);
        assertNotSame(prototype1, prototype2);
    }
}
