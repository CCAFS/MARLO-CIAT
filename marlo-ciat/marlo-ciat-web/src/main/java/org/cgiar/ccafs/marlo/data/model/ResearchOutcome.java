/*****************************************************************
 * This file is part of Managing Agricultural Research for Learning &
 * Outcomes Platform (MARLO).
 * MARLO is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * at your option) any later version.
 * MARLO is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with MARLO. If not, see <http://www.gnu.org/licenses/>.
 *****************************************************************/

/**
 * 
 */
package org.cgiar.ccafs.marlo.data.model;

import org.cgiar.ccafs.marlo.data.IAuditLog;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.gson.annotations.Expose;


/**
 * Modified by @author nmatovu last on Oct 9, 2016
 */
public class ResearchOutcome implements Serializable, IAuditLog {


  private static final long serialVersionUID = -5206789836821862166L;


  @Expose
  private Long id;

  @Expose
  private String description;


  @Expose
  private Integer targetYear;

  @Expose
  private BigDecimal value;


  @Expose
  private ResearchImpact researchImpact;

  @Expose
  private boolean active;


  @Expose
  private Date activeSince;


  @Expose
  private ResearchTopic researchTopic;


  @Expose
  private User createdBy;

  @Expose
  private User modifiedBy;


  @Expose
  private TargetUnit targetUnit;

  @Expose
  private String modificationJustification;


  @Expose
  private boolean impactPathway;

  @Expose
  private BigDecimal baseline;


  @Expose
  private String shortName;

  private Set<ResearchMilestone> researchMilestones = new HashSet<ResearchMilestone>(0);


  private Set<ResearchOutput> researchOutputs = new HashSet<ResearchOutput>(0);

  private Set<SectionStatus> sectionStatuses = new HashSet<SectionStatus>(0);

  private Set<MonitoringOutcome> monitoringOutcomes = new HashSet<MonitoringOutcome>(0);


  private List<ResearchMilestone> milestones;

  private List<MonitoringOutcome> monitorings;

  public ResearchOutcome() {
    super();
    // TODO Auto-generated constructor stub
  }

  /**
   * @param description
   * @param targetYear
   * @param outcome
   * @param researchImpact
   * @param active
   * @param activeSince
   * @param researchTopic
   */
  public ResearchOutcome(String description, Integer targetYear, BigDecimal value, ResearchImpact researchImpact,
    boolean active, Date activeSince, ResearchTopic researchTopic) {
    super();
    this.description = description;
    this.targetYear = targetYear;
    this.researchImpact = researchImpact;
    this.active = active;
    this.activeSince = activeSince;
    this.researchTopic = researchTopic;
    this.value = value;
  }

  public Date getActiveSince() {
    return activeSince;
  }

  public BigDecimal getBaseline() {
    return baseline;
  }

  public User getCreatedBy() {
    return createdBy;
  }


  /**
   * @return the description
   */
  public String getDescription() {
    return description;
  }

  /**
   * @return the id
   */
  @Override
  public Long getId() {
    return id;
  }

  @Override
  public String getLogDeatil() {
    StringBuilder sb = new StringBuilder();
    sb.append("Id : ").append(this.getId());
    return sb.toString();
  }


  public List<ResearchMilestone> getMilestones() {
    return milestones;
  }

  public String getModificationJustification() {
    return modificationJustification;
  }

  @Override
  public User getModifiedBy() {
    return modifiedBy;
  }

  public Set<MonitoringOutcome> getMonitoringOutcomes() {
    return monitoringOutcomes;
  }

  public List<MonitoringOutcome> getMonitorings() {
    return monitorings;
  }

  /**
   * @return the researchImpact
   */
  public ResearchImpact getResearchImpact() {
    return researchImpact;
  }

  public Set<ResearchMilestone> getResearchMilestones() {
    return researchMilestones;
  }

  public Set<ResearchOutput> getResearchOutputs() {
    return researchOutputs;
  }

  /**
   * @return the researchTopic
   */
  public ResearchTopic getResearchTopic() {
    return researchTopic;
  }

  public Set<SectionStatus> getSectionStatuses() {
    return sectionStatuses;
  }

  public String getShortName() {
    return shortName;
  }

  public TargetUnit getTargetUnit() {
    return targetUnit;
  }

  /**
   * @return the targetYear
   */
  public Integer getTargetYear() {
    return targetYear;
  }

  public BigDecimal getValue() {
    return value;
  }

  /**
   * @return the active
   */
  @Override
  public boolean isActive() {
    return active;
  }

  public boolean isImpactPathway() {
    return impactPathway;
  }

  /**
   * @param active the active to set
   */
  public void setActive(boolean active) {
    this.active = active;
  }

  public void setActiveSince(Date activeSince) {
    this.activeSince = activeSince;
  }

  public void setBaseline(BigDecimal baseline) {
    this.baseline = baseline;
  }


  public void setCreatedBy(User createdBy) {
    this.createdBy = createdBy;
  }


  /**
   * @param description the description to set
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * @param id the id to set
   */
  public void setId(Long id) {
    this.id = id;
  }

  public void setImpactPathway(boolean impactPathway) {
    this.impactPathway = impactPathway;
  }

  public void setMilestones(List<ResearchMilestone> milestones) {
    this.milestones = milestones;
  }


  public void setModificationJustification(String modificationJustification) {
    this.modificationJustification = modificationJustification;
  }


  public void setModifiedBy(User modifiedBy) {
    this.modifiedBy = modifiedBy;
  }


  public void setMonitoringOutcomes(Set<MonitoringOutcome> monitoringOutcomes) {
    this.monitoringOutcomes = monitoringOutcomes;
  }


  public void setMonitorings(List<MonitoringOutcome> monitorings) {
    this.monitorings = monitorings;
  }


  /**
   * @param researchImpact the researchImpact to set
   */
  public void setResearchImpact(ResearchImpact researchImpact) {
    this.researchImpact = researchImpact;
  }


  public void setResearchMilestones(Set<ResearchMilestone> researchMilestones) {
    this.researchMilestones = researchMilestones;
  }


  public void setResearchOutputs(Set<ResearchOutput> researchOutputs) {
    this.researchOutputs = researchOutputs;
  }

  /**
   * @param researchTopic the researchTopic to set
   */
  public void setResearchTopic(ResearchTopic researchTopic) {
    this.researchTopic = researchTopic;
  }


  public void setSectionStatuses(Set<SectionStatus> sectionStatuses) {
    this.sectionStatuses = sectionStatuses;
  }


  public void setShortName(String shortName) {
    this.shortName = shortName;
  }

  public void setTargetUnit(TargetUnit targetUnit) {
    this.targetUnit = targetUnit;
  }


  /**
   * @param targetYear the targetYear to set
   */
  public void setTargetYear(Integer targetYear) {
    this.targetYear = targetYear;
  }

  public void setValue(BigDecimal value) {
    this.value = value;
  }

}
