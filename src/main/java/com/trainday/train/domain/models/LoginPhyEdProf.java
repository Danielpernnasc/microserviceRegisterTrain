package com.trainday.train.domain.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "login_phy_ed_prof")
public class LoginPhyEdProf {

    @Id
    private String id;
    @Indexed(unique = true)
    private String cref;
    @Indexed(unique = true)
    private String email;
    private String password;
    private String name;

}
