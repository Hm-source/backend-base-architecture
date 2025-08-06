package org.example.basic.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Category extends AggregateRoot {

    private Long id;
    private String name;
}
