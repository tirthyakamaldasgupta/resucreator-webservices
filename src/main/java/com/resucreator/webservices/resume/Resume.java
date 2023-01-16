package com.resucreator.webservices.resume;

import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.resucreator.webservices.user.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document("resumes")
public class Resume {
    @Id
    private String id;

    @NotBlank
    private String resumeTitle;

    @JsonIgnore
    @DBRef
    private User user;
}
