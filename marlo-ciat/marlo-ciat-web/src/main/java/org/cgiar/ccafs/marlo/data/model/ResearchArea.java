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

import java.util.HashSet;
import java.util.Set;

import com.google.gson.annotations.Expose;


/**
 * This class represents the Research Area (such as DAPA, AgBio, Soils, etc) in the application
 * Modified by @author nmatovu last on Oct 6, 2016
 */
public class ResearchArea implements IAuditLog {

  private static final long serialVersionUID = -2457377813686256015L;
  @Expose
  private Long id;
  @Expose
  private boolean active;
  @Expose
  private User createdBy;
  @Expose
  private User modifiedBy;
  private String name;
  private String acronym;
  private Set<CrpProgram> researchPrograms = new HashSet<CrpProgram>(0);


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
  public ResearchArea(String name, String acronym, boolean active, User createdBy, User modifiedBy) {
    super();
    this.name = name;
    this.acronym = acronym;
    this.active = active;
    this.createdBy = createdBy;
    this.modifiedBy = modifiedBy;
  }


  /**
   * @return the acronym
   */
  public String getAcronym() {
    return acronym;
  }

  /**
   * @return the createdBy
   */
  public User getCreatedBy() {
    return createdBy;
  }

  /*
   * (non-Javadoc)
   * @see org.cgiar.ccafs.marlo.data.IAuditLog#getId()
   */
  @Override
  public Object getId() {
    return this.id;
  }

  /*
   * (non-Javadoc)
   * @see org.cgiar.ccafs.marlo.data.IAuditLog#getLogDeatil()
   */
  @Override
  public String getLogDeatil() {
    StringBuilder sb = new StringBuilder();

    sb.append("Id : ").append(this.getId());


    return sb.toString();
  }


  /*
   * (non-Javadoc)
   * @see org.cgiar.ccafs.marlo.data.IAuditLog#getModifiedBy()
   */
  @Override
  public User getModifiedBy() {
    return this.modifiedBy;
  }


  /**
   * @return the name
   */
  public String getName() {
    return name;
  }


  /**
   * @return the researchPrograms
   */
  public Set<CrpProgram> getResearchPrograms() {
    return researchPrograms;
  }


  /*
   * (non-Javadoc)
   * @see org.cgiar.ccafs.marlo.data.IAuditLog#isActive()
   */
  @Override
  public boolean isActive() {

    return active;
  }


  /**
   * @param acronym the acronym to set
   */
  public void setAcronym(String acronym) {
    this.acronym = acronym;
  }


  /**
   * @param active the active to set
   */
  public void setActive(boolean active) {
    this.active = active;
  }


  /**
   * @param createdBy the createdBy to set
   */
  public void setCreatedBy(User createdBy) {
    this.createdBy = createdBy;
  }


  /**
   * @param id the id to set
   */
  public void setId(Long id) {
    this.id = id;
  }


  /**
   * @param modifiedBy the modifiedBy to set
   */
  public void setModifiedBy(User modifiedBy) {
    this.modifiedBy = modifiedBy;
  }

  /**
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @param researchPrograms the researchPrograms to set
   */
  public void setResearchPrograms(Set<CrpProgram> researchPrograms) {
    this.researchPrograms = researchPrograms;
  }

}
