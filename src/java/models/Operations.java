package models;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author rcgam
 */
@Entity
@Table(name = "operations")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Operations.findAll", query = "SELECT o FROM Operations o"),
    @NamedQuery(name = "Operations.findByOperationId", query = "SELECT o FROM Operations o WHERE o.operationId = :operationId"),
    @NamedQuery(name = "Operations.findByDescription", query = "SELECT o FROM Operations o WHERE o.description = :description"),
    @NamedQuery(name = "Operations.findByLongText", query = "SELECT o FROM Operations o WHERE o.longText = :longText")})
public class Operations implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "operation_id")
    private Integer operationId;
    @Column(name = "description")
    private String description;
    @Column(name = "long_text")
    private String longText;
    @JoinColumn(name = "status", referencedColumnName = "status_id")
    @ManyToOne
    private Status status;
    @JoinColumn(name = "person_responsible", referencedColumnName = "user_id")
    @ManyToOne
    private Users personResponsible;
    @JoinColumn(name = "work_order_id", referencedColumnName = "work_order_id")
    @ManyToOne(optional = false)
    private WorkOrder workOrderId;

    public Operations() {
    }

    public Operations(Integer operationId) {
        this.operationId = operationId;
    }

    public Integer getOperationId() {
        return operationId;
    }

    public void setOperationId(Integer operationId) {
        this.operationId = operationId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLongText() {
        return longText;
    }

    public void setLongText(String longText) {
        this.longText = longText;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Users getPersonResponsible() {
        return personResponsible;
    }

    public void setPersonResponsible(Users personResponsible) {
        this.personResponsible = personResponsible;
    }

    public WorkOrder getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(WorkOrder workOrderId) {
        this.workOrderId = workOrderId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (operationId != null ? operationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Operations)) {
            return false;
        }
        Operations other = (Operations) object;
        if ((this.operationId == null && other.operationId != null) || (this.operationId != null && !this.operationId.equals(other.operationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.Operations[ operationId=" + operationId + " ]";
    }
    
}
