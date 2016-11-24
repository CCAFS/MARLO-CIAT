package org.cgiar.ccafs.marlo.data.model;
// Generated Nov 24, 2016 11:37:14 AM by Hibernate Tools 3.4.0.CR1


import org.cgiar.ccafs.marlo.data.IAuditLog;

import java.util.Date;

import com.google.gson.annotations.Expose;

/**
 * ResearchImpactBeneficiary generated by hbm2java
 */
public class ResearchImpactBeneficiary implements java.io.Serializable, IAuditLog {

  private static final long serialVersionUID = -6926942149840809770L;

  @Expose
  private Integer id;

  @Expose
  private User modifiedBy;

  @Expose
  private User createdBy;

  @Expose
  private ResearchImpact researchImpact;

  @Expose
  private Beneficiary beneficiary;

  @Expose
  private ResearchRegion researchRegion;

  @Expose
  private boolean active;

  @Expose
  private Date activeSince;

  @Expose
  private String modificationJustification;


  public ResearchImpactBeneficiary() {
  }


  public ResearchImpactBeneficiary(Beneficiary beneficiary, ResearchRegion researchRegion, boolean active) {
    this.beneficiary = beneficiary;
    this.researchRegion = researchRegion;
    this.active = active;
  }


  public ResearchImpactBeneficiary(User modifiedBy, User createdBy, ResearchImpact researchImpact,
    Beneficiary beneficiary, ResearchRegion researchRegion, boolean active, Date activeSince,
    String modificationJustification) {
    this.modifiedBy = modifiedBy;
    this.createdBy = createdBy;
    this.researchImpact = researchImpact;
    this.beneficiary = beneficiary;
    this.researchRegion = researchRegion;
    this.active = active;
    this.activeSince = activeSince;
    this.modificationJustification = modificationJustification;
  }


  public Date getActiveSince() {
    return activeSince;
  }


  public Beneficiary getBeneficiary() {
    return beneficiary;
  }


  public User getCreatedBy() {
    return createdBy;
  }


  @Override
  public Integer getId() {
    return id;
  }


  @Override
  public String getLogDeatil() {
    StringBuilder sb = new StringBuilder();
    sb.append("Id : ").append(this.getId());
    return sb.toString();
  }


  public String getModificationJustification() {
    return modificationJustification;
  }

  @Override
  public User getModifiedBy() {
    return modifiedBy;
  }

  public ResearchImpact getResearchImpact() {
    return researchImpact;
  }

  public ResearchRegion getResearchRegion() {
    return researchRegion;
  }

  @Override
  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public void setActiveSince(Date activeSince) {
    this.activeSince = activeSince;
  }

  public void setBeneficiary(Beneficiary beneficiary) {
    this.beneficiary = beneficiary;
  }

  public void setCreatedBy(User createdBy) {
    this.createdBy = createdBy;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public void setModificationJustification(String modificationJustification) {
    this.modificationJustification = modificationJustification;
  }


  public void setModifiedBy(User modifiedBy) {
    this.modifiedBy = modifiedBy;
  }

  public void setResearchImpact(ResearchImpact researchImpact) {
    this.researchImpact = researchImpact;
  }

  public void setResearchRegion(ResearchRegion researchRegion) {
    this.researchRegion = researchRegion;
  }

}
