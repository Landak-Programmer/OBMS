package com.obms.account.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@Entity
@Table(name = "account")
public class Account implements JpaEntity<Long> {

    // might not be a good idea???
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    // this maybe good idea to use as id?
    @Column(name = "account_number", nullable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String accountNumber;

    @Column(name = "account_owner", nullable = false)
    private String accountOwner;

    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private BigDecimal amount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @JsonIgnore
    private LocalDateTime dateCreated = LocalDateTime.now();

    @JsonIgnore
    private LocalDateTime lastUpdated = LocalDateTime.now();

    public enum Status {
        ACTIVE,
        DEACTIVATE,
    }
}
