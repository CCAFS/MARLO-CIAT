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
 * This class represents the Research Area (such as DAPA, AgBio, Soils, etc) in the application
 * Modified by @author nmatovu last on Oct 6, 2016
 */
public class ResearchArea implements Serializable, IAuditLog {


  private static final long serialVersionUID = -2457377813686256015L;


  @Expose
  private Long id;


  @Expose
  private String name;


  @Expose
  private String acronym;

  @Expose
  private ResearchCenter researchCenter;


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
  private String color;


  private Set<ResearchProgram> researchPrograms = new HashSet<ResearchProgram>(0);


  private Set<ResearchLeader> researchLeaders = new HashSet<ResearchLeader>(0);


  private List<ResearchProgram> programs;


  private List<ResearchLeader> leaders;


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
    ResearchArea other = (ResearchArea) obj;
    if (id == null) {
      if (other.id != null) {
        return false;
      }
    } else if (!id.equals(other.id)) {
      return false;
    }
    return true;
  }


  /**
   * @return the acronym
   */
  public String getAcronym() {
    return acronym;
  }

  public Date getActiveSince() {
    return activeSince;
  }


  public String getColor() {
    return color;
  }


  public User getCreatedBy() {
    return createdBy;
  }

  @Override
  public Long getId() {
    return this.id;
  }


  public List<ResearchLeader> getLeaders() {
    return leaders;
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
   * @return the name
   */
  public String getName() {
    return name;
  }

  public List<ResearchProgram> getPrograms() {
    return programs;
  }

  /**
   * @return the researchCenter
   */
  public ResearchCenter getResearchCenter() {
    return researchCenter;
  }

  public Set<ResearchLeader> getResearchLeaders() {
    return researchLeaders;
  }

  /**
   * @return the researchPrograms
   */
  public Set<ResearchProgram> getResearchPrograms() {
    return researchPrograms;
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

  /**
   * @param acronym the acronym to set
   */
  public void setAcronym(String acronym) {
    this.acronym = acronym;
  }


  public void setActive(boolean active) {
    this.active = active;
  }


  public void setActiveSince(Date activeSince) {
    this.activeSince = activeSince;
  }


  public void setColor(String color) {
    this.color = color;
  }


  /*
   * (non-Javadoc)
   * @see org.cgiar.ccafs.marlo.data.IAuditLog#getId()
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


  public void setLeaders(List<ResearchLeader> leaders) {
    this.leaders = leaders;
  }


  public void setModificationJustification(String modificationJustification) {
    this.modificationJustification = modificationJustification;
  }


  public void setModifiedBy(User modifiedBy) {
    this.modifiedBy = modifiedBy;
  }


  /**
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
  }


  public void setPrograms(List<ResearchProgram> programs) {
    this.programs = programs;
  }


  /**
   * @param researchCenter the researchCenter to set
   */
  public void setResearchCenter(ResearchCenter researchCenter) {
    this.researchCenter = researchCenter;
  }


  public void setResearchLeaders(Set<ResearchLeader> researchLeaders) {
    this.researchLeaders = researchLeaders;
  }

  /**
   * @param researchPrograms the researchPrograms to set
   */
  public void setResearchPrograms(Set<ResearchProgram> researchPrograms) {
    this.researchPrograms = researchPrograms;
  }

}
