package com.trainday.train.domain.models;

import java.time.LocalDate;

import org.springframework.data.mongodb.core.mapping.Document;

import com.trainday.train.domain.models.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "physical_education_professional")

public class PhysicalEducationProfessional {
    private String id;
    private String name;
    private String email;
    private LocalDate bornDate;
    private String cpf;
    private String phone;
    private String address;
    private String cref;
    private Role role;
    private String userId;
}
