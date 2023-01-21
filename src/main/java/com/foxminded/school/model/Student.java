package com.foxminded.school.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    private Integer id;
    private Integer groupId;
    private String firstName;
    private String lastName;
}
