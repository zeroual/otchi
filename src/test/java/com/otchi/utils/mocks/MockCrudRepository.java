package com.otchi.utils.mocks;

import org.assertj.core.util.Iterables;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MockCrudRepository<T, ID extends String> implements CrudRepository<T, ID> {

    private static final Map<Class<?>, Set<Object>> objectRecords = new HashMap<>();

    private final Field idField;
    private final List<Field> relations;
    private Class<T> clazz;

    protected MockCrudRepository(Class<T> clazz) {
        this.clazz = clazz;
        Field[] declaredFields = clazz.getDeclaredFields();
        Optional<Field> idField = Arrays.stream(declaredFields).filter(f -> f.isAnnotationPresent(Id.class))
                .findFirst();
        this.idField = idField
                .orElseThrow(() -> new IllegalArgumentException("Class " + clazz.getSimpleName() + " is not an entity"));
        this.idField.setAccessible(true);
        relations = Arrays.stream(declaredFields).filter(MockCrudRepository::isRelation).peek(f -> f.setAccessible(true)).collect(Collectors.toList());
    }

    public static void clearDatabase() {
        objectRecords.clear();
    }

    private static boolean isRelation(Field field) {
        return field.isAnnotationPresent(OneToMany.class) || field.isAnnotationPresent(ManyToOne.class);
    }

    public Set<T> getRecords() {
        objectRecords.putIfAbsent(clazz, new HashSet<>());
        return (Set<T>) objectRecords.get(clazz);
    }

    @Override
    public <S extends T> S save(S entity) {
        if (entity == null)
            return null;
        if (getRecords().add(entity) && getId(entity) == null) {
            this.setId(entity, (long) getRecords().size());
            relations.forEach(r -> {
                try {
                    if (r.isAnnotationPresent(OneToMany.class)) {
                        Class<Object> type = (Class<Object>) ((ParameterizedType) r.getGenericType()).getActualTypeArguments()[0];
                        MockCrudRepository mockCrudRepository = new MockCrudRepository(type);
                        Collection<Object> value = (Collection<Object>) r.get(entity);
                        value.forEach(mockCrudRepository::save);
                    } else {
                        Class<Object> type = (Class<Object>) r.getType();
                        MockCrudRepository mockCrudRepository = new MockCrudRepository(type);
                        Object value = r.get(entity);
                        mockCrudRepository.save(value);
                    }

                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        return entity;
    }

    @Override
    public <S extends T> Iterable<S> save(Iterable<S> entities) {
        entities.forEach(this::save);
        return entities;
    }

    @Override
    public T findOne(ID id) {
        return findFirst(id).orElse(null);
    }

    @Override
    public boolean exists(ID id) {
        return findFirst(id).isPresent();
    }

    @Override
    public Iterable<T> findAll() {
        return new HashSet<>(getRecords());
    }

    @Override
    public Iterable<T> findAll(Iterable<ID> ids) {
        List<ID> idList = Arrays.asList(Iterables.toArray(ids));
        return () -> getRecords().stream().filter(e -> idList.contains(this.getId(e))).iterator();
    }

    @Override
    public long count() {
        return getRecords().size();
    }

    @Override
    public void delete(ID id) {
        findFirst(id).ifPresent(getRecords()::remove);
    }

    @Override
    public void delete(T entity) {
        getRecords().remove(entity);
    }

    @Override
    public void delete(Iterable<? extends T> entities) {
        entities.forEach(getRecords()::remove);
    }

    @Override
    public void deleteAll() {
        getRecords().clear();
    }

    protected Optional<T> findFirst(ID id) {
        return findFirst(e -> this.getId(e).equals(id));
    }

    protected Optional<T> findFirst(Predicate<T> predicate) {
        return getRecords().stream().filter(predicate).findFirst();
    }

    protected Collection<T> findAll(Predicate<T> predicate) {
        return getRecords().stream().filter(predicate).collect(ArrayList::new, Collection::add, Collection::addAll);
    }

    @SuppressWarnings("unchecked")
    protected ID getId(T element) {
        try {
            return (ID) idField.get(element);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    protected void setId(T element, Long id) {
        try {
            idField.set(element, id);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
