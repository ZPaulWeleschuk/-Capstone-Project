package models;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "users")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Users.findAll", query = "SELECT u FROM Users u"),
    @NamedQuery(name = "Users.findByUserId", query = "SELECT u FROM Users u WHERE u.userId = :userId"),
    @NamedQuery(name = "Users.findByUserFname", query = "SELECT u FROM Users u WHERE u.userFname = :userFname"),
    @NamedQuery(name = "Users.findByUserLname", query = "SELECT u FROM Users u WHERE u.userLname = :userLname"),
    @NamedQuery(name = "Users.findByUserHash", query = "SELECT u FROM Users u WHERE u.userHash = :userHash"),
    @NamedQuery(name = "Users.findByUserRole", query = "SELECT u FROM Users u WHERE u.userRole = :userRole"),
    @NamedQuery(name = "Users.findByUserForcedReset", query = "SELECT u FROM Users u WHERE u.userForcedReset = :userForcedReset")})
public class Users implements Serializable {

    @Column(name = "user_locked")
    private Boolean userLocked;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "user_fname")
    private String userFname;
    @Column(name = "user_lname")
    private String userLname;
    @Column(name = "user_hash")
    private String userHash;
    @Column(name = "user_role")
    private Integer userRole;
    @Column(name = "user_forced_reset")
    private Boolean userForcedReset;
    @JoinColumn(name = "work_center_id", referencedColumnName = "work_center_id")
    @ManyToOne
    private WorkCenter workCenterId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "createdBy")
    private List<Notification> notificationList;
    @OneToMany(mappedBy = "personResponsible")
    private List<Operations> operationsList;

    public Users() {
    }

    public Users(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserFname() {
        return userFname;
    }

    public void setUserFname(String userFname) {
        this.userFname = userFname;
    }

    public String getUserLname() {
        return userLname;
    }

    public void setUserLname(String userLname) {
        this.userLname = userLname;
    }

    public String getUserHash() {
        return userHash;
    }

    public void setUserHash(String userHash) {
        this.userHash = userHash;
    }

    public Integer getUserRole() {
        return userRole;
    }

    public void setUserRole(Integer userRole) {
        this.userRole = userRole;
    }

    public Boolean getUserForcedReset() {
        return userForcedReset;
    }

    public void setUserForcedReset(Boolean userForcedReset) {
        this.userForcedReset = userForcedReset;
    }

    public WorkCenter getWorkCenterId() {
        return workCenterId;
    }

    public void setWorkCenterId(WorkCenter workCenterId) {
        this.workCenterId = workCenterId;
    }

    @XmlTransient
    public List<Notification> getNotificationList() {
        return notificationList;
    }

    public void setNotificationList(List<Notification> notificationList) {
        this.notificationList = notificationList;
    }

    @XmlTransient
    public List<Operations> getOperationsList() {
        return operationsList;
    }

    public void setOperationsList(List<Operations> operationsList) {
        this.operationsList = operationsList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Users)) {
            return false;
        }
        Users other = (Users) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.Users[ userId=" + userId + " ]";
    }

    public Boolean getUserLocked() {
        return userLocked;
    }

    public void setUserLocked(Boolean userLocked) {
        this.userLocked = userLocked;
    }
    
}
