package com.vgeorgo.projectorganizer.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
import javax.validation.constraints.Null;
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
    private Long id;

    @Column(nullable = false)
    @NotBlank
    @Getter
    @Setter
    private String name;

    @Column(nullable = false)
    @Getter
    @Setter
    private String type;

    @OneToOne
    @JoinColumn(name = "supervisor_id", referencedColumnName = "id")
    @RestResource(path = "supervisor", rel="supervisor")
    @JsonIgnoreProperties(value = {"supervisor","projects"})
    @Getter
    @Setter
    @SupervisorValidation
    private User supervisor;

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

    @ManyToMany(mappedBy = "developers", fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = {"developers","leader"})
    @RestResource(path = "projects")
    @Getter
    @Setter
    private List<Project> projects = new ArrayList<Project>();

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
