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

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.gson.annotations.Expose;


/**
 * Modified by @author nmatovu last on Oct 9, 2016
 */
public class ResearchImpact implements Serializable {


  private static final long serialVersionUID = -5150082139088832748L;


  @Expose
  private Long id;


  /**
   * The impact description text.
   */
  @Expose
  private String description;


  /**
   * The research impact target year.
   */
  @Expose
  private Integer targetYear;

  /**
   * The research Program to which this research impact relates to.
   */
  @Expose
  private boolean active;


  @Expose
  private Date activeSince;

  @Expose
  private User createdBy;


  @Expose
  private User modifiedBy;


  @Expose
  private String modificationJustification;


  @Expose
  private ResearchProgram researchProgram;


  private Set<ResearchOutcome> researchOutcomes = new HashSet<ResearchOutcome>(0);


  private List<ResearchObjective> objectives;


  private String objectiveValue;

  /**
   * 
   */
  public ResearchImpact() {
    super();
    // TODO Auto-generated constructor stub
  }


  /**
   * @param impact
   * @param targetYear
   * @param researchProgram
   */
  public ResearchImpact(String description, Integer targetYear, ResearchProgram researchProgram) {
    super();
    this.description = description;
    this.targetYear = targetYear;
    this.researchProgram = researchProgram;
  }

  public Date getActiveSince() {
    return activeSince;
  }

  public User getCreatedBy() {
    return createdBy;
  }


  public String getDescription() {
    return description;
  }

  /**
   * @return the id
   */
  public Long getId() {
    return id;
  }

  public String getModificationJustification() {
    return modificationJustification;
  }

  public User getModifiedBy() {
    return modifiedBy;
  }

  public List<ResearchObjective> getObjectives() {
    return objectives;
  }

  public String getObjectiveValue() {
    return objectiveValue;
  }


  /**
   * @return the researchOutcomes
   */
  public Set<ResearchOutcome> getResearchOutcomes() {
    return researchOutcomes;
  }


  /**
   * @return the researchProgram
   */
  public ResearchProgram getResearchProgram() {
    return researchProgram;
  }

  /**
   * @return the targetYear
   */
  public Integer getTargetYear() {
    return targetYear;
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

  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * @param id the id to set
   */
  public void setId(Long id) {
    this.id = id;
  }

  public void setModificationJustification(String modificationJustification) {
    this.modificationJustification = modificationJustification;
  }

  public void setModifiedBy(User modifiedBy) {
    this.modifiedBy = modifiedBy;
  }

  public void setObjectives(List<ResearchObjective> objectives) {
    this.objectives = objectives;
  }

  public void setObjectiveValue(String objectiveValue) {
    this.objectiveValue = objectiveValue;
  }

  /**
   * @param researchOutcomes the researchOutcomes to set
   */
  public void setResearchOutcomes(Set<ResearchOutcome> researchOutcomes) {
    this.researchOutcomes = researchOutcomes;
  }

  /**
   * @param researchProgram the researchProgram to set
   */
  public void setResearchProgram(ResearchProgram researchProgram) {
    this.researchProgram = researchProgram;
  }

  /**
   * @param targetYear the targetYear to set
   */
  public void setTargetYear(Integer targetYear) {
    this.targetYear = targetYear;
  }


}
