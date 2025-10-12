package com.innowise.minispring.lifecycle;

/**
 * Interface that represents a method that should be called once dependencies are injected.
 */
public interface InitializingBean {
    /**
     * Called once dependency injection is complete.
     */
    void afterPropertiesSet();
}
