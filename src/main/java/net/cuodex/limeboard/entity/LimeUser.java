package net.cuodex.limeboard.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.*;


@RequiredArgsConstructor
@ToString
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username"})
})
@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Entity
public class LimeUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private long id;
    @Getter @Setter
    private String username;
    @Getter @Setter
    private String email;

    @Getter @Setter @JsonIgnore
    private String password;
    @Getter @Setter
    private String createdAt;
    @Getter @Setter
    private String lastSeen;

    @Getter
    @ElementCollection
    @MapKeyColumn(name="value")
    @Column(name="setting_name")
    @CollectionTable(name="user_settings", joinColumns=@JoinColumn(name="user_id"))
    private Map<String, String> settings = new HashMap<String, String>();


    public String getSetting(String key) {
        return settings.get(key);
    }

    public void removeSetting(String key)
    {
        settings.remove(key);
    }

    public void setSetting(String key, String value) {
        settings.put(key, value);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        LimeUser that = (LimeUser) o;
        if (id == 0L)
            return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
