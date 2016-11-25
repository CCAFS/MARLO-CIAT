package org.cgiar.ccafs.marlo.data.model;
// Generated Nov 23, 2016 10:03:29 AM by Hibernate Tools 3.4.0.CR1


import org.cgiar.ccafs.marlo.data.IAuditLog;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.google.gson.annotations.Expose;

/**
 * Institution generated by hbm2java
 */
public class Institution implements java.io.Serializable, IAuditLog {

  private static final long serialVersionUID = 5386863238801941201L;

  @Expose
  private Long id;

  @Expose
  private LocElement locElement;

  @Expose
  private InstitutionType institutionType;

  @Expose
  private Institution institution;

  @Expose
  private String name;

  @Expose
  private String acronym;

  @Expose
  private String city;

  @Expose
  private String websiteLink;

  @Expose
  private Date added;


  private Set<Institution> branches = new HashSet<Institution>(0);


  public Institution() {
  }


  public Institution(InstitutionType institutionType, String name, Date added) {
    this.institutionType = institutionType;
    this.name = name;
    this.added = added;
  }


  public Institution(LocElement locElement, InstitutionType institutionType, Institution institution, String name,
    String acronym, String city, String websiteLink, Date added, Set<Institution> branches) {
    this.locElement = locElement;
    this.institutionType = institutionType;
    this.institution = institution;
    this.name = name;
    this.acronym = acronym;
    this.city = city;
    this.websiteLink = websiteLink;
    this.added = added;
    this.branches = branches;
  }


  public String getAcronym() {
    return acronym;
  }


  public Date getAdded() {
    return added;
  }


  public Set<Institution> getBranches() {
    return branches;
  }


  public String getBranchName() {
    try {
      String composedAcronym = this.acronym != null ? this.acronym : "";
      if (this.institution == null) {
        // Verify if there exist a city to show
        if (this.city != null && this.city != "") {
          return "HQ: " + composedAcronym + " - " + this.city + ", " + this.locElement.getName();
        }
        return "HQ: " + composedAcronym + " - " + this.locElement.getName();
      } else {
        // Verify if there exist a city to show
        if (this.city != null && this.city != "") {
          return composedAcronym + " - " + this.city + ", " + this.locElement.getName();
        }
        return composedAcronym + " - " + this.locElement.getName();
      }
    } catch (Exception e) {
      return this.name;
    }

  }


  public String getCity() {
    return city;
  }


  public String getComposedName() {
    try {
      if (this.getLocElement() == null) {
        return this.getAcronym() + " - " + this.getName();
      }

      if (this.getLocElement().getName() == null) {
        this.getLocElement().setName("");
      }
      if (this.getAcronym() != null) {
        if (this.getAcronym().length() != 0) {
          try {
            return this.getAcronym() + " - " + this.getName();// + " - " + this.getLocElement().getName();
          } catch (Exception e) {
            return this.getAcronym() + " - " + this.getName();
          }

        }
      } else {
        try {
          return this.getName() + "-" + this.getLocElement().getName();
        } catch (Exception e) {
          return this.getName();
        }
      }
      return this.getName();
    } catch (Exception e) {
      return this.getName();
    }


  }


  @Override
  public Long getId() {
    return id;
  }

  public Institution getInstitution() {
    return institution;
  }

  public InstitutionType getInstitutionType() {
    return institutionType;
  }

  public LocElement getLocElement() {
    return locElement;
  }

  @Override
  public String getLogDeatil() {
    StringBuilder sb = new StringBuilder();
    sb.append("Id : ").append(this.getId());
    return sb.toString();
  }

  @Override
  public User getModifiedBy() {
    User user = new User();
    user.setId(new Long(3));
    return user;
  }

  public String getName() {
    return name;
  }

  public String getWebsiteLink() {
    return websiteLink;
  }

  @Override
  public boolean isActive() {
    return true;
  }

  public void setAcronym(String acronym) {
    this.acronym = acronym;
  }

  public void setAdded(Date added) {
    this.added = added;
  }

  public void setBranches(Set<Institution> branches) {
    this.branches = branches;
  }


  public void setCity(String city) {
    this.city = city;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setInstitution(Institution institution) {
    this.institution = institution;
  }

  public void setInstitutionType(InstitutionType institutionType) {
    this.institutionType = institutionType;
  }


  public void setLocElement(LocElement locElement) {
    this.locElement = locElement;
  }


  public void setName(String name) {
    this.name = name;
  }


  public void setWebsiteLink(String websiteLink) {
    this.websiteLink = websiteLink;
  }
}

