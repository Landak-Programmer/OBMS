package com.obms.account.service;

import com.obms.account.model.JpaEntity;

import java.io.Serializable;

public interface IService<E extends JpaEntity<ID>, ID extends Serializable> {

    E create(final E entity);

    E update(final ID id, final E changeSet);

    E delete(final E entity);

    E getById(final ID id);
}
