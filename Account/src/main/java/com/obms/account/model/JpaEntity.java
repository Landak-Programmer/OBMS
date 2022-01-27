package com.obms.account.model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Make into abstract if needed
 *
 * todo: Move it to core?
 * @param <ID>
 */
public interface JpaEntity<ID extends Serializable> {

    ID getId();

    void setId(ID id);

    LocalDateTime getDateCreated();

    void setDateCreated(final LocalDateTime dateCreated);

    LocalDateTime getLastUpdated();

    void setLastUpdated(final LocalDateTime dateCreated);
}

