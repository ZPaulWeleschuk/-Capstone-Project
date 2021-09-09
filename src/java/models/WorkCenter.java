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
@Table(name = "work_center")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "WorkCenter.findAll", query = "SELECT w FROM WorkCenter w"),
    @NamedQuery(name = "WorkCenter.findByWorkCenterId", query = "SELECT w FROM WorkCenter w WHERE w.workCenterId = :workCenterId"),
    @NamedQuery(name = "WorkCenter.findByWorkCenterName", query = "SELECT w FROM WorkCenter w WHERE w.workCenterName = :workCenterName")})
public class WorkCenter implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "work_center_id")
    private String workCenterId;
    @Column(name = "work_center_name")
    private String workCenterName;
    @OneToMany(mappedBy = "workCenterId")
    private List<Users> usersList;
    @OneToMany(mappedBy = "workCenterId")
    private List<Notification> notificationList;

    public WorkCenter() {
    }

    public WorkCenter(String workCenterId) {
        this.workCenterId = workCenterId;
    }

    public String getWorkCenterId() {
        return workCenterId;
    }

    public void setWorkCenterId(String workCenterId) {
        this.workCenterId = workCenterId;
    }

    public String getWorkCenterName() {
        return workCenterName;
    }

    public void setWorkCenterName(String workCenterName) {
        this.workCenterName = workCenterName;
    }

    @XmlTransient
    public List<Users> getUsersList() {
        return usersList;
    }

    public void setUsersList(List<Users> usersList) {
        this.usersList = usersList;
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
        hash += (workCenterId != null ? workCenterId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WorkCenter)) {
            return false;
        }
        WorkCenter other = (WorkCenter) object;
        if ((this.workCenterId == null && other.workCenterId != null) || (this.workCenterId != null && !this.workCenterId.equals(other.workCenterId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.WorkCenter[ workCenterId=" + workCenterId + " ]";
    }
    
}