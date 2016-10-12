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
public class ResearchTopic implements Serializable {

  private static final long serialVersionUID = 365817271325565483L;

  private Long id;
  /**
   * The research topic description text.
   */
  private String researchTopic;
  /**
   * The research program related to this research topic or flagship project.
   */
  private ResearchProgram researchProgram;

  /**
   * 
   */
  public ResearchTopic() {
    super();
    // TODO Auto-generated constructor stub
  }

  /**
   * @param researchTopic
   * @param researchProgram
   */
  public ResearchTopic(String researchTopic, ResearchProgram researchProgram) {
    super();
    this.researchTopic = researchTopic;
    this.researchProgram = researchProgram;
  }


  /**
   * @return the id
   */
  public Long getId() {
    return id;
  }


  /**
   * @return the researchProgram
   */
  public ResearchProgram getResearchProgram() {
    return researchProgram;
  }


  /**
   * @return the researchTopic
   */
  public String getResearchTopic() {
    return researchTopic;
  }


  /**
   * @param id the id to set
   */
  public void setId(Long id) {
    this.id = id;
  }


  /**
   * @param researchProgram the researchProgram to set
   */
  public void setResearchProgram(ResearchProgram researchProgram) {
    this.researchProgram = researchProgram;
  }


  /**
   * @param researchTopic the researchTopic to set
   */
  public void setResearchTopic(String researchTopic) {
    this.researchTopic = researchTopic;
  }


}