package org.cgiar.ccafs.marlo.data.model;
// Generated Oct 13, 2016 1:24:28 PM by Hibernate Tools 3.4.0.CR1


import java.util.Date;

import com.google.gson.annotations.Expose;


public class ResearchImpactObjective implements java.io.Serializable {


  private static final long serialVersionUID = -8979609328318401070L;


  @Expose
  private Long id;

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

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (this.getClass() != obj.getClass()) {
      return false;
    }
    ResearchImpactObjective other = (ResearchImpactObjective) obj;
    if (this.getResearchImpact() == null) {
      if (other.getResearchImpact() != null) {
        return false;
      }
    } else if (!this.getResearchImpact().equals(other.getResearchImpact())) {
      return false;
    }
    if (this.getResearchObjective() == null) {
      if (other.getResearchObjective() != null) {
        return false;
      }
    } else if (!this.getResearchObjective().getId().equals(other.getResearchObjective().getId())) {
      return false;
    }
    return true;
  }


  public Date getActiveSince() {
    return this.activeSince;
  }


  public User getCreatedBy() {
    return createdBy;
  }

  public Long getId() {
    return id;
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

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (active ? 1231 : 1237);
    result = prime * result + ((researchImpact == null) ? 0 : researchImpact.hashCode());
    result = prime * result + ((researchObjective == null) ? 0 : researchObjective.hashCode());
    return result;
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

  public void setId(Long id) {
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

