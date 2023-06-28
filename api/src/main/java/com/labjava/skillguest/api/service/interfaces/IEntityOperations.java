package com.labjava.skillguest.api.service.interfaces;

import com.labjava.skillguest.api.persistence.interfaces.IEntity;

import java.util.List;

public interface IEntityOperations<T extends IEntity> {


    T findOne(final long id);


    List<T> findAll();


    T create(final T resource);


    void update(final T resource);


    void delete(final long id);

    void deleteAll();


    long count();

}
