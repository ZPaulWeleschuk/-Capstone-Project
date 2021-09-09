package models;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author rcgam
 */
@Entity
@Table(name = "damage")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Damage.findAll", query = "SELECT d FROM Damage d"),
    @NamedQuery(name = "Damage.findByDamageId", query = "SELECT d FROM Damage d WHERE d.damageId = :damageId"),
    @NamedQuery(name = "Damage.findByDamageCode", query = "SELECT d FROM Damage d WHERE d.damageCode = :damageCode"),
    @NamedQuery(name = "Damage.findByCodeDescription", query = "SELECT d FROM Damage d WHERE d.codeDescription = :codeDescription")})
public class Damage implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "damage_id")
    private Integer damageId;
    @Column(name = "damage_code")
    private String damageCode;
    @Column(name = "code_description")
    private String codeDescription;
    @OneToMany(mappedBy = "damageId")
    private List<Notification> notificationList;

    public Damage() {
    }

    public Damage(Integer damageId) {
        this.damageId = damageId;
    }

    public Integer getDamageId() {
        return damageId;
    }

    public void setDamageId(Integer damageId) {
        this.damageId = damageId;
    }

    public String getDamageCode() {
        return damageCode;
    }

    public void setDamageCode(String damageCode) {
        this.damageCode = damageCode;
    }

    public String getCodeDescription() {
        return codeDescription;
    }

    public void setCodeDescription(String codeDescription) {
        this.codeDescription = codeDescription;
    }

    @XmlTransient
    public List<Notification> getNotificationList() {
        return notificationList;
    }

    public void setNotificationList(List<Notification> notificationList) {
        this.notificationList = notificationList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (damageId != null ? damageId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Damage)) {
            return false;
        }
        Damage other = (Damage) object;
        if ((this.damageId == null && other.damageId != null) || (this.damageId != null && !this.damageId.equals(other.damageId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.Damage[ damageId=" + damageId + " ]";
    }
    
}
