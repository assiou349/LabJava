package com.labjava.skillguest.api.service.interfaces;

import com.labjava.skillguest.api.persistence.interfaces.IEntity;

public abstract class AbstractService<T extends IEntity> extends AbstractRawService<T> implements IService<T> {

    public AbstractService() {
        super();
    }

    // API

}
