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

import com.google.gson.annotations.Expose;


/**
 * Represents the research objective in the systems for the research area/research program.
 * Modified by @author nmatovu last on Oct 7, 2016
 */
public class ResearchObjective implements Serializable {

  private static final long serialVersionUID = -3618614156720044325L;
  /**
   * The id or identifier for the research objective.
   */
  private Long id;
  /**
   * The research objective
   */
  private String name;

  @Expose
  private ResearchArea researchArea;
  @Expose
  private ResearchCenter researchCenter;
  private EntityType objectiveType;

  /**
   * 
   */
  public ResearchObjective() {
    super();
    // TODO Auto-generated constructor stub
  }

  /**
   * @param name
   * @param researchArea
   * @param researchCenter
   * @param entityType
   */
  public ResearchObjective(String name, ResearchArea researchArea, ResearchCenter researchCenter,
    EntityType objectiveType) {
    super();
    this.name = name;
    this.researchArea = researchArea;
    this.researchCenter = researchCenter;
    this.objectiveType = objectiveType;
  }

  /**
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }


  /**
   * @return the objectiveType
   */
  public EntityType getObjectiveType() {
    return objectiveType;
  }


  /**
   * @return the researchArea
   */
  public ResearchArea getResearchArea() {
    return researchArea;
  }


  /**
   * @return the researchCenter
   */
  public ResearchCenter getResearchCenter() {
    return researchCenter;
  }


  /**
   * @param id the id to set
   */
  public void setId(Long id) {
    this.id = id;
  }


  /**
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
  }


  /**
   * @param objectiveType the objectiveType to set
   */
  public void setObjectiveType(EntityType objectiveType) {
    this.objectiveType = objectiveType;
  }

  /**
   * @param researchArea the researchArea to set
   */
  public void setResearchArea(ResearchArea researchArea) {
    this.researchArea = researchArea;
  }

  /**
   * @param researchCenter the researchCenter to set
   */
  public void setResearchCenter(ResearchCenter researchCenter) {
    this.researchCenter = researchCenter;
  }


}