package com.tistory.workshop6349.studyjpa.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@AllArgsConstructor @NoArgsConstructor
@Builder
@Entity
public class Account {

    @Id
    private Integer id;

    private String name;

    public void updateName(String name) {
        this.name = name;
    }

}
