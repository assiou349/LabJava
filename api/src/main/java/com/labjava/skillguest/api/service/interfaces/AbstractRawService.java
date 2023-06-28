package com.labjava.skillguest.api.service.interfaces;

import com.google.common.collect.Lists;
import com.labjava.skillguest.api.persistence.interfaces.IEntity;
import com.labjava.skillguest.api.utils.exception.MyResourceNotFoundException;
import org.assertj.core.util.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public abstract class AbstractRawService<T extends IEntity> implements IEntityOperations<T> {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    public AbstractRawService() {
        super();
    }

    // API

    // find - one

    @Override
    @Transactional(readOnly = true)
    public T findOne(final long id) {
        Optional<T> entity = getDao().findById(id);
        return entity.orElse(null);
    }

    // find - all

    @Override
    @Transactional(readOnly = true)
    public List<T> findAll() {
        return Lists.newArrayList(getDao().findAll());
    }


    // save/create/persist

    @Override
    public T create(final T entity) {
        Preconditions.checkNotNull(entity);

        final T persistedEntity = getDao().save(entity);

        return persistedEntity;
    }

    // update/merge

    @Override
    public void update(final T entity) {
        Preconditions.checkNotNull(entity);

        getDao().save(entity);
    }

    // delete

    @Override
    public void deleteAll() {
        getDao().deleteAll();
    }

    @Override
    public void delete(final long id) {
        final T entity = getDao().findById(id).orElseThrow(MyResourceNotFoundException::new);

        getDao().delete(entity);
    }


    // count

    @Override
    public long count() {
        return getDao().count();
    }

    // template method

    protected abstract JpaRepository<T, Long> getDao();


    // template

    protected final Sort constructSort(final String sortBy, final String sortOrder) {
        Sort sortInfo = Sort.unsorted();
        if (sortBy != null) {
            sortInfo = Sort.by(Sort.Direction.fromString(sortOrder), sortBy);
        }
        return sortInfo;
    }
}
