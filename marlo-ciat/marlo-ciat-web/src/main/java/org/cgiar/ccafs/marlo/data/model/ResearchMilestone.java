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
public class ResearchMilestone implements Serializable {

  private static final long serialVersionUID = -338676464720877306L;
  private Long id;
  private String title;
  private Integer targetYear;
  private Long milestone;
  private boolean active;
  private boolean activeSince;
  private ResearchOutcome researchOutcome;

  /**
   * 
   */
  public ResearchMilestone() {
    super();
    // TODO Auto-generated constructor stub
  }

  /**
   * @param title
   * @param targetYear
   * @param milestone
   * @param active
   * @param activeSince
   * @param impactOutcome
   */
  public ResearchMilestone(String title, Integer targetYear, Long milestone, boolean active, boolean activeSince,
    ResearchOutcome researchOutcome) {
    super();
    this.title = title;
    this.targetYear = targetYear;
    this.milestone = milestone;
    this.active = active;
    this.activeSince = activeSince;
    this.researchOutcome = researchOutcome;
  }


  /**
   * @return the id
   */
  public Long getId() {
    return id;
  }


  /**
   * @return the milestone
   */
  public Long getMilestone() {
    return milestone;
  }


  /**
   * @return the impactOutcome
   */
  public ResearchOutcome getResearchOutcome() {
    return researchOutcome;
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
   * @param id the id to set
   */
  public void setId(Long id) {
    this.id = id;
  }


  /**
   * @param milestone the milestone to set
   */
  public void setMilestone(Long milestone) {
    this.milestone = milestone;
  }


  /**
   * @param impactOutcome the impactOutcome to set
   */
  public void setResearchOutcome(ResearchOutcome researchOutcome) {
    this.researchOutcome = researchOutcome;
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


}
