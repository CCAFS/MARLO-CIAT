package org.cgiar.ccafs.marlo.data.model;
// Generated Oct 13, 2016 1:24:28 PM by Hibernate Tools 3.4.0.CR1


import java.util.Date;

import com.google.gson.annotations.Expose;


public class ResearchImpactObjective implements java.io.Serializable {

  private static final long serialVersionUID = -8979609328318401070L;

  @Expose
  private Integer id;

  @Expose
  private User modifiedBy;

  @Expose
  private ResearchObjective researchObjective;

  @Expose
  private User createdBy;

  @Expose
  private ResearchImpact researchImpact;

  @Expose
  private boolean active;

  @Expose
  private Date activeSince;

  @Expose
  private String modificationJustification;


  public ResearchImpactObjective() {
  }


  public ResearchImpactObjective(ResearchObjective researchObjective, ResearchImpact researchImpact, boolean active) {
    this.researchObjective = researchObjective;
    this.researchImpact = researchImpact;
    this.active = active;
  }

  public ResearchImpactObjective(User modifiedBy, ResearchObjective researchObjective, User createdBy,
    ResearchImpact researchImpact, boolean active, Date activeSince, String modificationJustification) {
    this.modifiedBy = modifiedBy;
    this.researchObjective = researchObjective;
    this.createdBy = createdBy;
    this.researchImpact = researchImpact;
    this.active = active;
    this.activeSince = activeSince;
    this.modificationJustification = modificationJustification;
  }

  public Date getActiveSince() {
    return this.activeSince;
  }

  public User getCreatedBy() {
    return createdBy;
  }

  public Integer getId() {
    return this.id;
  }

  public String getModificationJustification() {
    return this.modificationJustification;
  }

  public User getModifiedBy() {
    return modifiedBy;
  }

  public ResearchImpact getResearchImpact() {
    return researchImpact;
  }

  public ResearchObjective getResearchObjective() {
    return researchObjective;
  }

  public boolean isActive() {
    return active;
  }


  public void setActive(boolean active) {
    this.active = active;
  }

  public void setActiveSince(Date activeSince) {
    this.activeSince = activeSince;
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

  public void setResearchObjective(ResearchObjective researchObjective) {
    this.researchObjective = researchObjective;
  }


}

