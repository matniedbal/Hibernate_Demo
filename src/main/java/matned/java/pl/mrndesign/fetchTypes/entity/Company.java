package matned.java.pl.mrndesign.fetchTypes.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "company")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_company")
    private Integer idCompany;

    @Column(name = "name")
    private String name;

    @Column(name = "value")
    private Integer value;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "id_company_detail")
    private CompanyDetail companyDetail;

    @OneToMany(fetch = FetchType.LAZY  ,mappedBy = "company", cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    private List<Property> properties;

    public Company() {
    }

    public Company(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    public void addProperty(Property property){
        if(properties == null) properties = new ArrayList<Property>();
        properties.add(property);
        property.setCompany(this);
    }

    public void removeProperty(Property property){
        if(properties != null){
            if (properties.contains(property))
                properties.remove(property);
        }
    }

    public Integer getIdCompany() {
        return idCompany;
    }
    public void setIdCompany(Integer idCompany) {
        this.idCompany = idCompany;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }
    public void setValue(Integer value) {
        this.value = value;
    }

    public CompanyDetail getCompanyDetail() {
        return companyDetail;
    }
    public void setCompanyDetail(CompanyDetail companyDetail) {
        this.companyDetail = companyDetail;
    }

    public List<Property> getProperties() {
        return properties;
    }
    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

    @Override
    public String toString() {
        return "Company{" +
                "idCompany=" + idCompany +
                ", name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}
