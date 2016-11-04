package org.cgiar.ccafs.marlo.data.model;
// Generated Nov 4, 2016 2:51:12 PM by Hibernate Tools 3.4.0.CR1


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * TargetUnit generated by hbm2java
 */
public class TargetUnit implements java.io.Serializable {

  private static final long serialVersionUID = 4442022448817199007L;

  private Long id;
  private User modifiedBy;
  private User createdBy;
  private String name;
  private boolean active;
  private Date activeSince;
  private String modificationJustification;
  private Set<ResearchMilestone> researchMilestones = new HashSet<ResearchMilestone>(0);
  private Set<ResearchOutcome> researchOutcomes = new HashSet<ResearchOutcome>(0);

  public TargetUnit() {
  }

  public TargetUnit(User modifiedBy, String name, boolean active, Date activeSince, String modificationJustification) {
    this.modifiedBy = modifiedBy;
    this.name = name;
    this.active = active;
    this.activeSince = activeSince;
    this.modificationJustification = modificationJustification;
  }


  public TargetUnit(User modifiedBy, User createdBy, String name, boolean active, Date activeSince,
    String modificationJustification, Set<ResearchMilestone> researchMilestones,
    Set<ResearchOutcome> researchOutcomes) {
    this.modifiedBy = modifiedBy;
    this.createdBy = createdBy;
    this.name = name;
    this.active = active;
    this.activeSince = activeSince;
    this.modificationJustification = modificationJustification;
    this.researchMilestones = researchMilestones;
    this.researchOutcomes = researchOutcomes;
  }


  public Date getActiveSince() {
    return activeSince;
  }


  public User getCreatedBy() {
    return createdBy;
  }


  public Long getId() {
    return id;
  }


  public String getModificationJustification() {
    return modificationJustification;
  }


  public User getModifiedBy() {
    return modifiedBy;
  }


  public String getName() {
    return name;
  }

  public Set<ResearchMilestone> getResearchMilestones() {
    return researchMilestones;
  }

  public Set<ResearchOutcome> getResearchOutcomes() {
    return researchOutcomes;
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

  public void setName(String name) {
    this.name = name;
  }


  public void setResearchMilestones(Set<ResearchMilestone> researchMilestones) {
    this.researchMilestones = researchMilestones;
  }

  public void setResearchOutcomes(Set<ResearchOutcome> researchOutcomes) {
    this.researchOutcomes = researchOutcomes;
  }


}

