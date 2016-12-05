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
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.gson.annotations.Expose;


/**
 * Modified by @author nmatovu last on Oct 9, 2016
 */
public class ResearchImpact implements Serializable, IAuditLog {


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

  @Expose
  private String color;


  private Set<ResearchOutcome> researchOutcomes = new HashSet<ResearchOutcome>(0);

  private Set<ResearchImpactObjective> researchImpactObjectives = new HashSet<>(0);


  private Set<ResearchImpactBeneficiary> researchImpactBeneficiaries = new HashSet<ResearchImpactBeneficiary>(0);


  private List<ResearchObjective> objectives;

  private List<ResearchImpactBeneficiary> beneficiaries;

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
    ResearchImpact other = (ResearchImpact) obj;
    if (id == null) {
      if (other.id != null) {
        return false;
      }
    } else if (!id.equals(other.id)) {
      return false;
    }
    return true;
  }


  public Date getActiveSince() {
    return activeSince;
  }

  public List<ResearchImpactBeneficiary> getBeneficiaries() {
    return beneficiaries;
  }

  public String getColor() {
    return color;
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


  public String getModificationJustification() {
    return modificationJustification;
  }

  @Override
  public User getModifiedBy() {
    return modifiedBy;
  }


  public List<ResearchObjective> getObjectives() {
    return objectives;
  }

  /**
   * @return an array of integers.
   */
  public long[] getObjectivesIds() {

    List<ResearchObjective> researchObjectives = this.getObjectives();
    if (researchObjectives != null) {
      long[] ids = new long[researchObjectives.size()];
      for (int i = 0; i < ids.length; i++) {
        ids[i] = researchObjectives.get(i).getId();
      }
      return ids;
    }
    return null;
  }

  public String getObjectiveValue() {
    return objectiveValue;
  }


  public Set<ResearchImpactBeneficiary> getResearchImpactBeneficiaries() {
    return researchImpactBeneficiaries;
  }

  public Set<ResearchImpactObjective> getResearchImpactObjectives() {
    return researchImpactObjectives;
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

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
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

  public void setBeneficiaries(List<ResearchImpactBeneficiary> beneficiaries) {
    this.beneficiaries = beneficiaries;
  }

  public void setColor(String color) {
    this.color = color;
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

  public void setResearchImpactBeneficiaries(Set<ResearchImpactBeneficiary> researchImpactBeneficiaries) {
    this.researchImpactBeneficiaries = researchImpactBeneficiaries;
  }

  public void setResearchImpactObjectives(Set<ResearchImpactObjective> researchImpactObjectives) {
    this.researchImpactObjectives = researchImpactObjectives;
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

  @Override
  public String toString() {
    return id.toString();
  }


}
