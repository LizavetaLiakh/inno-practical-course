package com.innowise.minispring;

import com.innowise.minispring.annotation.Autowired;
import com.innowise.minispring.annotation.Component;
import com.innowise.minispring.annotation.Scope;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * A simplified version of the Spring Framework.<p>
 * A lightweight container for dependency injection, capable for scanning packages for annotated components,
 * instantiating beans, injecting dependencies.
 */
@Slf4j
public class MiniApplicationContext {

    /**
     * A map responsible for storing beans.
     */
    private final Map<Class<?>, Object> components = new HashMap<>();
    private static final String CURRENT_BASIC_SCOPE = "singleton";

    /**
     * Constructs an empty {@code MiniApplicationContext} with the {@code basePackage} directory.
     * @param basePackage Package for scanning inside current sources root. Format: "com.example".
     */
    public MiniApplicationContext(String basePackage) {
        try {
            scanPackage(basePackage);
            injectDependencies();
            initializeBeans();
        } catch (Exception ex) {
            log.error("Failed initializing MiniApplicationContext", ex);
            throw new RuntimeException("Failed initializing MiniApplicationContext", ex);
        }
    }

    /**
     * Scans a given package for classes annotated with {@code @Component}.
     * @param basePackage Base package. Format: "com.example".
     * @throws Exception Emerged exception.
     */
    private void scanPackage(String basePackage) throws Exception {
        Reflections reflections = new Reflections(basePackage, Scanners.TypesAnnotated);
        Set<Class<?>> annotatedClasses = reflections.getTypesAnnotatedWith(Component.class);

        for (Class<?> currentClass : annotatedClasses) {
            String scope = CURRENT_BASIC_SCOPE;
            if (currentClass.isAnnotationPresent(Scope.class)) {
                scope = currentClass.getAnnotation(Scope.class).value();
            }

            if (scope.equals(CURRENT_BASIC_SCOPE)) {
                try {
                    Object instance = currentClass.getDeclaredConstructor().newInstance();
                    components.put(currentClass, instance);
                    log.info("Registered singleton: {}", currentClass.getName());
                } catch (Exception ex) {
                    log.error("Failed to instantiate component {}", currentClass.getName(), ex);
                }
            } else if (scope.equals("prototype")) {
                log.info("Registered prototype: {}", currentClass.getName());
            }
        }
    }

    /**
     * Injects dependencies into beans' fields.
     */
    private void injectDependencies() {
        for (Object bean : components.values()) {
            Class<?> currentClass = bean.getClass();

            for (Field field : currentClass.getDeclaredFields()) {
                if (field.isAnnotationPresent(Autowired.class)) {
                    field.setAccessible(true);

                    Object dependency = findDependency(field.getType());
                    if (dependency == null) {
                        logTypeError(field.getType());
                        throw new RuntimeException("No bean found with type " + field.getType());
                    }

                    try {
                        field.set(bean, dependency);
                        log.info("Injected dependency: {} into {}", dependency.getClass().getSimpleName(),
                                currentClass.getSimpleName() + "." + field.getName());
                    } catch (IllegalAccessException ex) {
                        log.error("Failed injecting dependency into {}", currentClass.getName(), ex);
                        throw new RuntimeException("Failed injecting dependency into " + currentClass.getName());
                    }
                }
            }
        }
    }

    /**
     * Find a suitable dependency among components.
     * @param type Type of the sought-for dependency.
     * @return Value of the found dependency or {@code null} if there is no suitable dependency.
     */
    private Object findDependency(Class<?> type) {
        for (Map.Entry<Class<?>, Object> entry : components.entrySet()) {
            if (type.isAssignableFrom(entry.getKey())) {
                return entry.getValue();
            }
        }
        return null;
    }

    /**
     * Returns a found bean.
     * @param type Type of the sought-for bean.
     * @return Value of the found bean or {@code null} if there is no suitable bean.
     * @param <T> Needed type of the bean.
     */
    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> type) {
        Object singletonBean = components.get(type);
        if (singletonBean != null) {
            return (T)singletonBean;
        }

        try {
            T instance = type.getDeclaredConstructor().newInstance();
            injectBeanDependencies(instance);
            if (instance instanceof com.innowise.minispring.lifecycle.InitializingBean initializingBean) {
                initializingBean.afterPropertiesSet();
            }
            log.info("Create a new prototype instance of {}", type.getName());
            return instance;
        } catch (Exception ex) {
            log.error("Failed creating a prototype with type {}", type.getName());
        }
        return null;
    }

    /**
     * Injects dependencies into a certain bean.
     * @param bean A bean for injecting dependencies into.
     */
    private void injectBeanDependencies(Object bean) {
        Class<?> currentClass = bean.getClass();
        for (Field field : currentClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(Autowired.class)) {
                field.setAccessible(true);
                Object dependency = findDependency(field.getType());
                if (dependency == null) {
                    logTypeError(field.getType());
                    throw new RuntimeException("No bean found with type " + field.getType());
                }

                try {
                    field.set(bean, dependency);
                    log.info("Injected dependency {} into {}.", dependency.getClass().getSimpleName(),
                            currentClass.getSimpleName() + "." + field.getName());
                } catch (IllegalAccessException ex) {
                    log.error("Failed injecting dependecy into {}", currentClass.getName(), ex);
                    throw new RuntimeException("Failed injecting dependecy into " + currentClass.getName(), ex);
                }
            }
        }
    }

    /**
     * Shows type error with logging.
     * @param type Type of the error object.
     */
    private void logTypeError(Class<?> type) {
        log.error("No bean found with type {}",  type);
    }

    /**
     * Initializes beans.
     */
    private void initializeBeans() {
        for (Object bean : components.values()) {
            if (bean instanceof com.innowise.minispring.lifecycle.InitializingBean initializingBean) {
                try {
                    initializingBean.afterPropertiesSet();
                    log.info("Call afterPropertiesSet() for {}", bean.getClass().getSimpleName());
                } catch (Exception ex) {
                    log.error("Error while afterPropertiesSet() worked for {}", bean.getClass().getSimpleName(), ex);
                }
            }
        }
    }
}
