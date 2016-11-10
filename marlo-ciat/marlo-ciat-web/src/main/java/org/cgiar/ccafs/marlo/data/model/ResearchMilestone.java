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

import com.google.gson.annotations.Expose;


/**
 * Modified by @author nmatovu last on Oct 9, 2016
 */
public class ResearchMilestone implements Serializable, IAuditLog {


  private static final long serialVersionUID = -338676464720877306L;


  @Expose
  private Long id;

  @Expose
  private String title;


  @Expose
  private Integer targetYear;


  @Expose
  private BigDecimal value;


  @Expose
  private boolean active;


  @Expose
  private boolean activeSince;


  @Expose
  private User createdBy;


  @Expose
  private User modifiedBy;


  @Expose
  private String modificationJustification;


  @Expose
  private ResearchOutcome researchOutcome;
  @Expose
  private TargetUnit targetUnit;

  public ResearchMilestone() {
    super();
  }

  /**
   * @param title
   * @param targetYear
   * @param milestone
   * @param active
   * @param activeSince
   * @param impactOutcome
   */
  public ResearchMilestone(String title, Integer targetYear, BigDecimal value, boolean active, boolean activeSince,
    ResearchOutcome researchOutcome) {
    super();
    this.title = title;
    this.targetYear = targetYear;
    this.active = active;
    this.activeSince = activeSince;
    this.researchOutcome = researchOutcome;
    this.value = value;
  }

  public User getCreatedBy() {
    return createdBy;
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

  /**
   * @return the impactOutcome
   */
  public ResearchOutcome getResearchOutcome() {
    return researchOutcome;
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

  /**
   * @return the title
   */
  public String getTitle() {
    return title;
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


  /**
   * @return the activeSince
   */
  public boolean isActiveSince() {
    return activeSince;
  }


  /**
   * @param active the active to set
   */
  public void setActive(boolean active) {
    this.active = active;
  }


  /**
   * @param activeSince the activeSince to set
   */
  public void setActiveSince(boolean activeSince) {
    this.activeSince = activeSince;
  }


  public void setCreatedBy(User createdBy) {
    this.createdBy = createdBy;
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


  /**
   * @param impactOutcome the impactOutcome to set
   */
  public void setResearchOutcome(ResearchOutcome researchOutcome) {
    this.researchOutcome = researchOutcome;
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


  /**
   * @param title the title to set
   */
  public void setTitle(String title) {
    this.title = title;
  }

  public void setValue(BigDecimal value) {
    this.value = value;
  }


}
