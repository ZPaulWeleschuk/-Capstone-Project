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
@Table(name = "cause")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cause.findAll", query = "SELECT c FROM Cause c"),
    @NamedQuery(name = "Cause.findByCauseId", query = "SELECT c FROM Cause c WHERE c.causeId = :causeId"),
    @NamedQuery(name = "Cause.findByCauseCode", query = "SELECT c FROM Cause c WHERE c.causeCode = :causeCode"),
    @NamedQuery(name = "Cause.findByCodeDescription", query = "SELECT c FROM Cause c WHERE c.codeDescription = :codeDescription")})
public class Cause implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "cause_id")
    private Integer causeId;
    @Column(name = "cause_code")
    private String causeCode;
    @Column(name = "code_description")
    private String codeDescription;
    @OneToMany(mappedBy = "causeId")
    private List<Notification> notificationList;

    public Cause() {
    }

    public Cause(Integer causeId) {
        this.causeId = causeId;
    }

    public Integer getCauseId() {
        return causeId;
    }

    public void setCauseId(Integer causeId) {
        this.causeId = causeId;
    }

    public String getCauseCode() {
        return causeCode;
    }

    public void setCauseCode(String causeCode) {
        this.causeCode = causeCode;
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
        hash += (causeId != null ? causeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cause)) {
            return false;
        }
        Cause other = (Cause) object;
        if ((this.causeId == null && other.causeId != null) || (this.causeId != null && !this.causeId.equals(other.causeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.Cause[ causeId=" + causeId + " ]";
    }
    
}
