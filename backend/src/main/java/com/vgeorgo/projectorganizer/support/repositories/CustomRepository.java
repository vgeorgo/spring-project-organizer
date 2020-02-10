package com.vgeorgo.projectorganizer.support.repositories;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.io.Serializable;

public class CustomRepository<T, ID extends Serializable> extends SimpleJpaRepository<T, ID>
        implements CustomRepositoryInterface<T, ID> {

    private final EntityManager entityManager;

    public CustomRepository(JpaEntityInformation entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public T refresh(T t) {
        entityManager.refresh(t);
        return t;
    }
}
