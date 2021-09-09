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
@Table(name = "order_type")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OrderType.findAll", query = "SELECT o FROM OrderType o"),
    @NamedQuery(name = "OrderType.findByTypeId", query = "SELECT o FROM OrderType o WHERE o.typeId = :typeId"),
    @NamedQuery(name = "OrderType.findByTypeDesc", query = "SELECT o FROM OrderType o WHERE o.typeDesc = :typeDesc")})
public class OrderType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "type_id")
    private String typeId;
    @Column(name = "type_desc")
    private String typeDesc;
    @OneToMany(mappedBy = "orderType")
    private List<WorkOrder> workOrderList;

    public OrderType() {
    }

    public OrderType(String typeId) {
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
    public List<WorkOrder> getWorkOrderList() {
        return workOrderList;
    }

    public void setWorkOrderList(List<WorkOrder> workOrderList) {
        this.workOrderList = workOrderList;
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
        if (!(object instanceof OrderType)) {
            return false;
        }
        OrderType other = (OrderType) object;
        if ((this.typeId == null && other.typeId != null) || (this.typeId != null && !this.typeId.equals(other.typeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.OrderType[ typeId=" + typeId + " ]";
    }
    
}
