package com.authify.io;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class StudentInfo {

    private String name;

    private int age;

    private String email;

    private String rollNumber;

    private String studentClass;

    private String semester;
}
