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

import com.google.gson.annotations.Expose;


/**
 * This class represents the Research Area (such as DAPA, AgBio, Soils, etc) in the application
 * Modified by @author nmatovu last on Oct 6, 2016
 */
public class ResearchArea implements Serializable {

  private static final long serialVersionUID = -2457377813686256015L;
  @Expose
  private Long id;
  private String name;
  private String acronym;
  private Set<ResearchProgram> researchPrograms = new HashSet<ResearchProgram>(0);
  private ResearchCenter researchCenter;


  /**
   * 
   */
  public ResearchArea() {
    super();
    // TODO Auto-generated constructor stub
  }


  /**
   * @param name
   */
  public ResearchArea(String name) {
    super();
    this.name = name;
  }


  /**
   * @param name
   * @param acronym
   * @param active
   * @param createdBy
   * @param modifiedBy
   */
  public ResearchArea(String name, String acronym, boolean active) {
    super();
    this.name = name;
    this.acronym = acronym;
  }


  /**
   * @return the acronym
   */
  public String getAcronym() {
    return acronym;
  }


  /*
   * (non-Javadoc)
   * @see org.cgiar.ccafs.marlo.data.IAuditLog#getId()
   */

  public Object getId() {
    return this.id;
  }


  /**
   * @return the name
   */
  public String getName() {
    return name;
  }


  /**
   * @return the researchCenter
   */
  public ResearchCenter getResearchCenter() {
    return researchCenter;
  }


  /**
   * @return the researchPrograms
   */
  public Set<ResearchProgram> getResearchPrograms() {
    return researchPrograms;
  }


  /**
   * @param acronym the acronym to set
   */
  public void setAcronym(String acronym) {
    this.acronym = acronym;
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
   * @param researchCenter the researchCenter to set
   */
  public void setResearchCenter(ResearchCenter researchCenter) {
    this.researchCenter = researchCenter;
  }


  /**
   * @param researchPrograms the researchPrograms to set
   */
  public void setResearchPrograms(Set<ResearchProgram> researchPrograms) {
    this.researchPrograms = researchPrograms;
  }

}
