package com.obms.account.service;

import com.obms.account.model.JpaEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import static java.lang.String.format;

/**
 * We should move this to core
 *
 * @param <E>
 * @param <ID>
 */
public abstract class BaseEntityService<E extends JpaEntity<ID>, ID extends Serializable> implements IService<E, ID> {

    protected abstract PagingAndSortingRepository<E, ID> getRepository();

    @Override
    @Transactional
    public E create(final E entity) {
        preCreate(entity);
        getRepository().save(entity);
        return entity;
    }

    protected abstract void preCreate(final E entity);

    @Override
    @Transactional
    public E update(final ID id, final E changeSet) {
        changeSet.setId(id);
        final E pEntity = this.getById(id);
        preUpdate(pEntity, changeSet);
        this.getRepository().save(pEntity);
        return pEntity;
    }

    protected abstract void preUpdate(final E pEntity, final E changeSet);

    @Override
    public E delete(final E entity) {
        throw new UnsupportedOperationException("Delete are not supported yet!");
    }

    // protected abstract void preDelete();

    @Override
    @Transactional(readOnly = true)
    public E getById(final ID id) {
        return failIfNotFound(id);
    }

    @Transactional(readOnly = true)
    protected E failIfNotFound(final ID id) {
        return this
                .getRepository()
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(format("%s (ID %s) not found", this.getClass().getSimpleName(), id)));
    }

    protected boolean isChanged(final Object persistentObj, final Object incoming) {
        return isChanged(persistentObj, incoming, false);
    }

    protected boolean isChanged(final Object persistentObj, final Object incoming, final Boolean allowBlankField) {

        if (!allowBlankField) {
            if (incoming instanceof String && StringUtils.isBlank((String) incoming)) {
                return false;
            }
        }

        if (persistentObj == null && incoming == null) {
            return false;
        }
        if (persistentObj == null) {
            return true;
        }

        if (incoming == null) {
            return false;
        }

        if (persistentObj
                .getClass()
                .equals(incoming.getClass())) {
            if (persistentObj instanceof JpaEntity) {
                return ((JpaEntity<?>) persistentObj).getId() != ((JpaEntity<?>) incoming).getId();
            } else {
                return !persistentObj.equals(incoming);
            }
        }
        // FIXME: Hackish way to deal with util.date vs. mysql.timestamp
        else if (incoming instanceof Date) {
            return !(((Timestamp) persistentObj).getTime() == ((Date) incoming).getTime());
        } else {
            // TODO: maybe returning false?
            throw new UnsupportedOperationException();
        }
    }

    protected void failIfBlank(final String value, final String args) {
        if (StringUtils.isBlank(value)) {
            throw new IllegalArgumentException(String.format("%s cannot be null/blank", args));
        }
    }
}
