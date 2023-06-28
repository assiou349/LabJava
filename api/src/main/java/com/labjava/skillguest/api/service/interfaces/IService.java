package com.labjava.skillguest.api.service.interfaces;

import com.labjava.skillguest.api.persistence.interfaces.IEntity;

public interface  IService<T extends IEntity> extends IEntityOperations<T> {
}
