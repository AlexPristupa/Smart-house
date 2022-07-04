package ru.prooftechit.smh.domain.tuple;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EntityWithMetadata<E, M> {
    private E entity;
    private M metadata;
}
