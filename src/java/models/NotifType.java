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
@Table(name = "notif_type")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NotifType.findAll", query = "SELECT n FROM NotifType n"),
    @NamedQuery(name = "NotifType.findByTypeId", query = "SELECT n FROM NotifType n WHERE n.typeId = :typeId"),
    @NamedQuery(name = "NotifType.findByTypeDesc", query = "SELECT n FROM NotifType n WHERE n.typeDesc = :typeDesc")})
public class NotifType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "type_id")
    private String typeId;
    @Column(name = "type_desc")
    private String typeDesc;
    @OneToMany(mappedBy = "notificationType")
    private List<Notification> notificationList;

    public NotifType() {
    }

    public NotifType(String typeId) {
        this.typeId = typeId;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getTypeDesc() {
        return typeDesc;
    }

    public void setTypeDesc(String typeDesc) {
        this.typeDesc = typeDesc;
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
        hash += (typeId != null ? typeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NotifType)) {
            return false;
        }
        NotifType other = (NotifType) object;
        if ((this.typeId == null && other.typeId != null) || (this.typeId != null && !this.typeId.equals(other.typeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.NotifType[ typeId=" + typeId + " ]";
    }
    
}
