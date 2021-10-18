package com.qee.autoconfigure.models;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class KClass {

    private Long clzzId;

    private List<Student> studentList;

}
