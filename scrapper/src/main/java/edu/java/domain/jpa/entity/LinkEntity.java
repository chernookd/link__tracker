package edu.java.domain.jpa.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.net.URI;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "LINK_TABLE")
public class LinkEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @URL
    @Column(name = "url", nullable = false, updatable = false, unique = true)
    private String url;

    @Column(name = "update_time", nullable = false, updatable = false)
    private OffsetDateTime updateTime;


    public LinkEntity(String url) {
        this.url = url;
    }

    public LinkEntity(URI url) {
        this.url = url.toString();
    }

    @PrePersist
    private void beforeInit() {
        if (updateTime == null) {
            updateTime = OffsetDateTime.now().withNano(0);
        }
    }
}
