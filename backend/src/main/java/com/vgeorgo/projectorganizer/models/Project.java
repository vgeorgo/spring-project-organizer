package com.vgeorgo.projectorganizer.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vgeorgo.projectorganizer.validators.user.SupervisorValidation;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "projects")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"}, allowGetters = true)
public class Project  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Column(nullable = false)
    @NotBlank
    @Getter
    @Setter
    private String name;

    @OneToOne
    @JoinColumn(name = "leader_id", referencedColumnName = "id")
    @RestResource(path = "leader", rel="leader")
    @JsonIgnoreProperties(value = {"supervisor","projects"})
    @Getter
    @Setter
    private User leader;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    @Getter
    private Date createdAt;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    @Getter
    private Date updatedAt;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = {"projects","supervisor"})
    @RestResource(path = "developers")
    @JoinTable(name = "user_project",
            joinColumns = @JoinColumn(name = "project_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            uniqueConstraints={@UniqueConstraint(columnNames={"project_id", "user_id"})})
    @Getter
    @Setter
    private List<User> developers = new ArrayList<User>();

    public void addDeveloper(User dev) {
        for(User user : developers) {
            if(user.getId().equals(dev.getId()))
                return;
        }
        developers.add(dev);
    }

    public void deleteDeveloper(User dev) {
        developers.removeIf(u -> u.getId().equals(dev.getId()));
    }
}