package nl.teama.server.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import java.io.Serializable;

@Entity
@Getter
@Setter
public class Data extends BaseEntity implements Serializable {

    @OneToOne(fetch = FetchType.EAGER)
    private User user;

    private String appName;

    private Long timeUsed;

    public Data() {}

    public Data(User user, String appName, Long timeUsed) {
        this.user = user;
        this.appName = appName;
        this.timeUsed = timeUsed;
    }

}
