package com.healthy_plate.ingredient.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RegisterId {

    @Column(name = "registeredId")
    private Long value;

    private RegisterId(final Long value) {
        this.value = value;
    }

    public static RegisterId of(final Long value) {
        return new RegisterId(value);
    }
}
