package models;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author rcgam
 */
@Entity
@Table(name = "work_order")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "WorkOrder.findAll", query = "SELECT w FROM WorkOrder w"),
    @NamedQuery(name = "WorkOrder.findByWorkOrderId", query = "SELECT w FROM WorkOrder w WHERE w.workOrderId = :workOrderId"),
    @NamedQuery(name = "WorkOrder.findByDescription", query = "SELECT w FROM WorkOrder w WHERE w.description = :description"),
    @NamedQuery(name = "WorkOrder.findByRequiredStartDate", query = "SELECT w FROM WorkOrder w WHERE w.requiredStartDate = :requiredStartDate"),
    @NamedQuery(name = "WorkOrder.findByRequiredEndDate", query = "SELECT w FROM WorkOrder w WHERE w.requiredEndDate = :requiredEndDate"),
    @NamedQuery(name = "WorkOrder.findByNotes", query = "SELECT w FROM WorkOrder w WHERE w.notes = :notes")})
public class WorkOrder implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "work_order_id")
    private Integer workOrderId;
    @Column(name = "description")
    private String description;
    @Column(name = "required_start_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date requiredStartDate;
    @Column(name = "required_end_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date requiredEndDate;
    @Column(name = "notes")
    private String notes;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "workOrderId")
    private List<Operations> operationsList;
    @JoinColumn(name = "notification_id", referencedColumnName = "notification_id")
    @ManyToOne(optional = false)
    private Notification notificationId;
    @JoinColumn(name = "order_type", referencedColumnName = "type_id")
    @ManyToOne
    private OrderType orderType;
    @JoinColumn(name = "status", referencedColumnName = "status_id")
    @ManyToOne
    private Status status;

    public WorkOrder() {
    }

    public WorkOrder(Integer workOrderId) {
        this.workOrderId = workOrderId;
    }

    public Integer getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(Integer workOrderId) {
        this.workOrderId = workOrderId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getRequiredStartDate() {
        return requiredStartDate;
    }

    public void setRequiredStartDate(Date requiredStartDate) {
        this.requiredStartDate = requiredStartDate;
    }

    public Date getRequiredEndDate() {
        return requiredEndDate;
    }

    public void setRequiredEndDate(Date requiredEndDate) {
        this.requiredEndDate = requiredEndDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @XmlTransient
    public List<Operations> getOperationsList() {
        return operationsList;
    }

    public void setOperationsList(List<Operations> operationsList) {
        this.operationsList = operationsList;
    }

    public Notification getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Notification notificationId) {
        this.notificationId = notificationId;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (workOrderId != null ? workOrderId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WorkOrder)) {
            return false;
        }
        WorkOrder other = (WorkOrder) object;
        if ((this.workOrderId == null && other.workOrderId != null) || (this.workOrderId != null && !this.workOrderId.equals(other.workOrderId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.WorkOrder[ workOrderId=" + workOrderId + " ]";
    }
    
}
