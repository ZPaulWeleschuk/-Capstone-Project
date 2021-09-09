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
@Table(name = "notification")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Notification.findAll", query = "SELECT n FROM Notification n"),
    @NamedQuery(name = "Notification.findByNotificationId", query = "SELECT n FROM Notification n WHERE n.notificationId = :notificationId")})
public class Notification implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "notification_id")
    private Integer notificationId;
    @JoinColumn(name = "cause_id", referencedColumnName = "cause_id")
    @ManyToOne
    private Cause causeId;
    @JoinColumn(name = "damage_id", referencedColumnName = "damage_id")
    @ManyToOne
    private Damage damageId;
    @JoinColumn(name = "notification_type", referencedColumnName = "type_id")
    @ManyToOne
    private NotifType notificationType;
    @JoinColumn(name = "plant_id", referencedColumnName = "plant_id")
    @ManyToOne
    private Plant plantId;
    @JoinColumn(name = "status", referencedColumnName = "status_id")
    @ManyToOne
    private Status status;
    @JoinColumn(name = "tech_obj_id", referencedColumnName = "tech_obj_id")
    @ManyToOne
    private TechnicalObject techObjId;
    @JoinColumn(name = "created_by", referencedColumnName = "user_id")
    @ManyToOne(optional = false)
    private Users createdBy;
    @JoinColumn(name = "work_center_id", referencedColumnName = "work_center_id")
    @ManyToOne
    private WorkCenter workCenterId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "notificationId")
    private List<WorkOrder> workOrderList;

    public Notification() {
    }

    public Notification(Integer notificationId) {
        this.notificationId = notificationId;
    }

    public Integer getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Integer notificationId) {
        this.notificationId = notificationId;
    }

    public Cause getCauseId() {
        return causeId;
    }

    public void setCauseId(Cause causeId) {
        this.causeId = causeId;
    }

    public Damage getDamageId() {
        return damageId;
    }

    public void setDamageId(Damage damageId) {
        this.damageId = damageId;
    }

    public NotifType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotifType notificationType) {
        this.notificationType = notificationType;
    }

    public Plant getPlantId() {
        return plantId;
    }

    public void setPlantId(Plant plantId) {
        this.plantId = plantId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public TechnicalObject getTechObjId() {
        return techObjId;
    }

    public void setTechObjId(TechnicalObject techObjId) {
        this.techObjId = techObjId;
    }

    public Users getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Users createdBy) {
        this.createdBy = createdBy;
    }

    public WorkCenter getWorkCenterId() {
        return workCenterId;
    }

    public void setWorkCenterId(WorkCenter workCenterId) {
        this.workCenterId = workCenterId;
    }

    @XmlTransient
    public List<WorkOrder> getWorkOrderList() {
        return workOrderList;
    }

    public void setWorkOrderList(List<WorkOrder> workOrderList) {
        this.workOrderList = workOrderList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (notificationId != null ? notificationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Notification)) {
            return false;
        }
        Notification other = (Notification) object;
        if ((this.notificationId == null && other.notificationId != null) || (this.notificationId != null && !this.notificationId.equals(other.notificationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.Notification[ notificationId=" + notificationId + " ]";
    }
    
}
