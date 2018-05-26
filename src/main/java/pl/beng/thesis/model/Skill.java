package pl.beng.thesis.model;

import pl.beng.thesis.model.Enum.SkillLevelEnum;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
public class Skill implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "skill_id", nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false)
    @Size(min = 3, max = 40)
    private String name;

    @Enumerated(EnumType.STRING)
    private SkillLevelEnum level;

    @Version
    @Column(name = "version", columnDefinition = "integer DEFAULT 0", nullable = false)
    private long version = 0L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "developer_id")
    private Developer developer;

    public Skill() {
    }

    public Skill(String name, SkillLevelEnum level) {
        this.name = name;
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SkillLevelEnum getLevel() {
        return level;
    }

    public void setLevel(SkillLevelEnum level) {
        this.level = level;
    }

    public Long getId() {
        return id;
    }

    public long getVersion() {
        return version;
    }

    public Developer getDeveloper() {
        return developer;
    }

    public void setDeveloper(Developer developer) {
        this.developer = developer;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Skill)) {
            return false;
        }
        Skill other = (Skill) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }
}
