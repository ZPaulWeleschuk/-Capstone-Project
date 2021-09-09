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
@Table(name = "plant")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Plant.findAll", query = "SELECT p FROM Plant p"),
    @NamedQuery(name = "Plant.findByPlantId", query = "SELECT p FROM Plant p WHERE p.plantId = :plantId"),
    @NamedQuery(name = "Plant.findByPlantName", query = "SELECT p FROM Plant p WHERE p.plantName = :plantName")})
public class Plant implements Serializable {

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "longitude")
    private Float longitude;
    @Column(name = "latitude")
    private Float latitude;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "plant_id")
    private Integer plantId;
    @Column(name = "plant_name")
    private String plantName;
    @OneToMany(mappedBy = "plantId")
    private List<Notification> notificationList;
    @Column(name = "location")
    private String location;
    
    public Plant() {
    }

    public Plant(Integer plantId) {
        this.plantId = plantId;
    }

    public Integer getPlantId() {
        return plantId;
    }

    public void setPlantId(Integer plantId) {
        this.plantId = plantId;
    }

    public String getPlantName() {
        return plantName;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }
    
    
    public String getLocation() {
        return location;
    }
    
    public void setLocation(String location) {
        this.location = location;
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
        hash += (plantId != null ? plantId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Plant)) {
            return false;
        }
        Plant other = (Plant) object;
        if ((this.plantId == null && other.plantId != null) || (this.plantId != null && !this.plantId.equals(other.plantId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.Plant[ plantId=" + plantId + " ]";
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }
    
}
