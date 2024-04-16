package br.com.api.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRole;
    @Column(unique = true)
    private String name;

    public Long getIdRole() {
        return idRole;
    }
    public void setIdRole(Long idRole) {
        this.idRole = idRole;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public enum Values {
        ADMIN(1L),
        BASIC(2L);

        long idRole;

        Values(long idRole) {
            this.idRole = idRole;
        }

        public long getIdRole() {
            return idRole;
        }
    }
}
