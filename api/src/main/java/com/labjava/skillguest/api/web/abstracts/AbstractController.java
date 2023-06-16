package com.labjava.skillguest.api.web.abstracts;

import com.labjava.skillguest.api.persistence.interfaces.IEntity;
import com.labjava.skillguest.api.service.interfaces.IOperations;
import com.labjava.skillguest.api.utils.RestPreconditions;

public abstract class AbstractController<T extends IEntity>  {

    // save
    protected final T createInternal(final T resource) {
        RestPreconditions.checkRequestElementNotNull(resource);
        RestPreconditions.checkRequestState(resource.getId() == null);
        return getService().create(resource);
    }


    protected abstract IOperations<T> getService();
}
