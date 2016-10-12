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
import java.util.HashSet;
import java.util.Set;


/**
 * Modified by @author nmatovu last on Oct 9, 2016
 */
public class ResearchImpact implements Serializable {

  private static final long serialVersionUID = -5150082139088832748L;

  private Long id;
  /**
   * The impact description text.
   */
  private String impact;
  /**
   * The research impact target year.
   */
  private Integer targetYear;
  /**
   * The research Program to which this research impact relates to.
   */
  private ResearchProgram researchProgram;
  private Set<ResearchOutcome> researchOutcomes = new HashSet<ResearchOutcome>(0);

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
  public ResearchImpact(String impact, Integer targetYear, ResearchProgram researchProgram) {
    super();
    this.impact = impact;
    this.targetYear = targetYear;
    this.researchProgram = researchProgram;
  }

  /**
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * @return the impact
   */
  public String getImpact() {
    return impact;
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

  /**
   * @param id the id to set
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * @param impact the impact to set
   */
  public void setImpact(String impact) {
    this.impact = impact;
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

  /**
   * @return the researchOutcomes
   */
  public Set<ResearchOutcome> getResearchOutcomes() {
    return researchOutcomes;
  }

  /**
   * @param researchOutcomes the researchOutcomes to set
   */
  public void setResearchOutcomes(Set<ResearchOutcome> researchOutcomes) {
    this.researchOutcomes = researchOutcomes;
  }


}
