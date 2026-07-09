package com.trainday.train.domain.models;

import com.trainday.train.domain.models.enums.Role;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "Role cannot be null")
    private Role role;

}
