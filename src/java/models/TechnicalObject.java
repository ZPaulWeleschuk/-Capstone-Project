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
@Table(name = "technical_object")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TechnicalObject.findAll", query = "SELECT t FROM TechnicalObject t"),
    @NamedQuery(name = "TechnicalObject.findByTechObjId", query = "SELECT t FROM TechnicalObject t WHERE t.techObjId = :techObjId"),
    @NamedQuery(name = "TechnicalObject.findByTechObjName", query = "SELECT t FROM TechnicalObject t WHERE t.techObjName = :techObjName"),
    @NamedQuery(name = "TechnicalObject.findByTechObjType", query = "SELECT t FROM TechnicalObject t WHERE t.techObjType = :techObjType")})
public class TechnicalObject implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "tech_obj_id")
    private Integer techObjId;
    @Column(name = "tech_obj_name")
    private String techObjName;
    @Column(name = "tech_obj_type")
    private String techObjType;
    @OneToMany(mappedBy = "techObjId")
    private List<Notification> notificationList;

    public TechnicalObject() {
    }

    public TechnicalObject(Integer techObjId) {
        this.techObjId = techObjId;
    }

    public Integer getTechObjId() {
        return techObjId;
    }

    public void setTechObjId(Integer techObjId) {
        this.techObjId = techObjId;
    }

    public String getTechObjName() {
        return techObjName;
    }

    public void setTechObjName(String techObjName) {
        this.techObjName = techObjName;
    }

    public String getTechObjType() {
        return techObjType;
    }

    public void setTechObjType(String techObjType) {
        this.techObjType = techObjType;
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
        hash += (techObjId != null ? techObjId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TechnicalObject)) {
            return false;
        }
        TechnicalObject other = (TechnicalObject) object;
        if ((this.techObjId == null && other.techObjId != null) || (this.techObjId != null && !this.techObjId.equals(other.techObjId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.TechnicalObject[ techObjId=" + techObjId + " ]";
    }
    
}
