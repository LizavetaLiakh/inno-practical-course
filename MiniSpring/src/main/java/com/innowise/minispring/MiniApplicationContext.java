package com.innowise.minispring;

import com.innowise.minispring.annotation.Autowired;
import com.innowise.minispring.annotation.Component;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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

    /**
     * Constructs an empty {@code MiniApplicationContext} with the {@code basePackage} directory.
     *
     * @param basePackage Package for scanning inside current sources root. Format: "com.example".
     */
    public MiniApplicationContext(String basePackage) {
        try {
            scanPackage(basePackage);
            injectDependencies();
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }

    /**
     * Scans a given package for classes annotated with {@code @Component}.
     *
     * @param basePackage Base package. Format: "com.example".
     * @throws Exception Emerged exception.
     */
    private void scanPackage(String basePackage) throws Exception {
        String path = basePackage.replace('.', '/');
        URL resource = Thread.currentThread().getContextClassLoader().getResource(path);

        if (resource == null) {
            throw new RuntimeException("Package " + basePackage + " not found.");
        }

        File directory = new File(resource.getFile());
        for (File file : Objects.requireNonNull(directory.listFiles())) {
            if (file.getName().endsWith(".class")) {
                String className = basePackage + "." + file.getName().replace(".class", "");
                Class<?> newClass = Class.forName(className);

                if (newClass.isAnnotationPresent(Component.class)) {
                    Object instance = newClass.getDeclaredConstructor().newInstance();
                    components.put(newClass, instance);
                    log.info("Registered component: {}", newClass.getName());
                }
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
                        throw new RuntimeException("No bean found with type " + field.getType());
                    }

                    try {
                        field.set(bean, dependency);
                        log.info("Injected dependency: {} into {}", dependency.getClass().getSimpleName(),
                                currentClass.getSimpleName() + "." + field.getName());
                    } catch (IllegalAccessException ex) {
                        throw new RuntimeException("Failed injecting dependency into " + currentClass.getName(), ex);
                    }
                }
            }
        }
    }

    /**
     * Find a suitable dependency among components.
     *
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
        Object bean = components.get(type);
        if (bean == null) {
            for (Map.Entry<Class<?>, Object> entry : components.entrySet()) {
                if (type.isAssignableFrom(entry.getKey())) {
                    return (T)entry.getValue();
                }
            }
            throw new RuntimeException("No bean found with type " + type);
        }
        return (T)bean;
    }
}
