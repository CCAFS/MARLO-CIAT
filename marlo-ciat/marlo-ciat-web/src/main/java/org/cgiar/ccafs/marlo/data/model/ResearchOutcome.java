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


/**
 * Modified by @author nmatovu last on Oct 9, 2016
 */
public class ResearchOutcome implements Serializable {


  private static final long serialVersionUID = -5206789836821862166L;
  private Long id;
  private String description;
  private Integer targetYear;
  private Long outcome;
  private ResearchImpact researchImpact;
  private boolean active;
  private boolean activeSince;
  private ResearchTopic researchTopic;

  /**
   * 
   */
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
  public ResearchOutcome(String description, Integer targetYear, Long outcome, ResearchImpact researchImpact,
    boolean active, boolean activeSince, ResearchTopic researchTopic) {
    super();
    this.description = description;
    this.targetYear = targetYear;
    this.outcome = outcome;
    this.researchImpact = researchImpact;
    this.active = active;
    this.activeSince = activeSince;
    this.researchTopic = researchTopic;
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
  public Long getId() {
    return id;
  }


  /**
   * @return the outcome
   */
  public Long getOutcome() {
    return outcome;
  }


  /**
   * @return the researchImpact
   */
  public ResearchImpact getResearchImpact() {
    return researchImpact;
  }


  /**
   * @return the researchTopic
   */
  public ResearchTopic getResearchTopic() {
    return researchTopic;
  }


  /**
   * @return the targetYear
   */
  public Integer getTargetYear() {
    return targetYear;
  }


  /**
   * @return the active
   */
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


  /**
   * @param outcome the outcome to set
   */
  public void setOutcome(Long outcome) {
    this.outcome = outcome;
  }


  /**
   * @param researchImpact the researchImpact to set
   */
  public void setResearchImpact(ResearchImpact researchImpact) {
    this.researchImpact = researchImpact;
  }


  /**
   * @param researchTopic the researchTopic to set
   */
  public void setResearchTopic(ResearchTopic researchTopic) {
    this.researchTopic = researchTopic;
  }


  /**
   * @param targetYear the targetYear to set
   */
  public void setTargetYear(Integer targetYear) {
    this.targetYear = targetYear;
  }

}
