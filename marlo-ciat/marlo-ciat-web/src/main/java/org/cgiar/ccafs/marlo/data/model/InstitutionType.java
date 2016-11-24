package org.cgiar.ccafs.marlo.data.model;
// Generated Nov 23, 2016 10:03:29 AM by Hibernate Tools 3.4.0.CR1


import java.util.HashSet;
import java.util.Set;

import com.google.gson.annotations.Expose;

/**
 * InstitutionType generated by hbm2java
 */
public class InstitutionType implements java.io.Serializable {


  private static final long serialVersionUID = 5232565592153730397L;

  @Expose
  private Long id;
  @Expose
  private String name;
  @Expose
  private String acronym;
  private Set<Institution> institutions = new HashSet<Institution>(0);

  public InstitutionType() {
  }

  public InstitutionType(String name) {
    this.name = name;
  }

  public InstitutionType(String name, String acronym, Set<Institution> institutions) {
    this.name = name;
    this.acronym = acronym;
    this.institutions = institutions;
  }


  public String getAcronym() {
    return this.acronym;
  }

  public Long getId() {
    return this.id;
  }

  public Set<Institution> getInstitutions() {
    return institutions;
  }

  public String getName() {
    return this.name;
  }

  public void setAcronym(String acronym) {
    this.acronym = acronym;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setInstitutions(Set<Institution> institutions) {
    this.institutions = institutions;
  }

  public void setName(String name) {
    this.name = name;
  }


}
