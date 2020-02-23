package com.vgeorgo.projectorganizer.models;

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
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"developer"})
public class User  implements Serializable {
    public static final String SUPERVISOR = "supervisor";
    public static final String DEVELOPER = "developer";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;

    @Column(nullable = false)
    @NotBlank
    @Getter
    @Setter
    private String name;

    @Column(nullable = false)
    @NotBlank
    @Getter
    @Setter
    private String type;

    @ManyToOne
    @JoinColumn(name = "supervisor_id", referencedColumnName = "id")
    @RestResource(path = "supervisor", rel="supervisor")
    @JsonIgnoreProperties(value = {"supervisor","projects","subordinates"})
    @Getter
    @Setter
    private User supervisor;

    @OneToMany(mappedBy = "supervisor")
    @RestResource(path = "subordinates", rel="subordinates")
    @JsonIgnoreProperties(value = {"supervisor","projects","subordinates"})
    @Getter
    @Setter
    private List<User> subordinates = new ArrayList<User>();

    @ManyToMany(mappedBy = "developers")
    @JsonIgnoreProperties(value = {"developers","leader"})
    @RestResource(path = "projects")
    @Getter
    @Setter
    private List<Project> projects = new ArrayList<Project>();

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

    /**
     * Set the type of the User as Supervisor
     */
    public void setAsSupervisor() {
        type = User.SUPERVISOR;
    }

    /**
     * Set the type of the User as Developer
     */
    public void setAsDeveloper() {
        type = User.DEVELOPER;
    }

    /**
     * @return True if the user is a Supervisor
     */
    public boolean isSupervisor() { return type.equals(User.SUPERVISOR); }

    /**
     * @return True if the user is a Developer
     */
    public boolean isDeveloper() { return type.equals(User.DEVELOPER); }
}
