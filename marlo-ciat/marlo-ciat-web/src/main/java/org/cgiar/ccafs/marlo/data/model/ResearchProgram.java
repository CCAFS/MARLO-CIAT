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
package org.cgiar.ccafs.marlo.data.model;

import java.util.Date;

// Generated May 26, 2016 9:42:28 AM by Hibernate Tools 4.3.1.Final

import java.util.List;

import com.google.gson.annotations.Expose;

/**
 * CrpProgram generated by hbm2java
 */
public class ResearchProgram implements java.io.Serializable {


  private static final long serialVersionUID = -7720585854691931655L;


  @Expose
  private Long id;


  @Expose
  private ResearchArea researchArea;


  @Expose
  private String name;


  @Expose
  private String acronym;


  @Expose
  private EntityType programType;


  private List<ResearchLeader> researchLeaders;


  @Expose
  private String color;

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

  public ResearchProgram() {
  }

  /**
   * @param name
   * @param acronym
   * @param programType
   * @param active
   * @param color
   * @param researchCenter
   * @param researchArea
   * @param researchLeaders
   */
  public ResearchProgram(String name, String acronym, EntityType programType, boolean active, String color,
    ResearchArea researchArea, List<ResearchLeader> researchLeaders) {
    super();
    this.name = name;
    this.acronym = acronym;
    this.programType = programType;
    this.active = active;
    this.color = color;
    this.researchArea = researchArea;
    this.researchLeaders = researchLeaders;
  }

  public ResearchProgram(String name, String acronym, EntityType programType, ResearchArea researchArea) {
    this.name = name;
    this.acronym = acronym;
    this.programType = programType;
    this.researchArea = researchArea;
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
    ResearchProgram other = (ResearchProgram) obj;
    if (id == null) {
      if (other.id != null) {
        return false;
      }
    } else if (!id.equals(other.id)) {
      return false;
    }
    return true;
  }

  public String getAcronym() {
    return this.acronym;
  }

  public Date getActiveSince() {
    return activeSince;
  }

  public String getColor() {
    return color;
  }

  public String getComposedName() {
    return this.acronym + ": " + this.name;
  }


  public User getCreatedBy() {
    return createdBy;
  }

  public Long getId() {
    return this.id;
  }


  public String getModificationJustification() {
    return modificationJustification;
  }

  public User getModifiedBy() {
    return modifiedBy;
  }


  public String getName() {
    return this.name;
  }


  /**
   * @return the programType
   */
  public EntityType getProgramType() {
    return programType;
  }

  /**
   * @return the researchArea
   */
  public ResearchArea getResearchArea() {
    return researchArea;
  }


  /**
   * @return the researchLeaders
   */
  public List<ResearchLeader> getResearchLeaders() {
    return researchLeaders;
  }


  public boolean isActive() {
    return active;
  }


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


  public void setCreatedBy(User createdBy) {
    this.createdBy = createdBy;
  }


  public void setId(Long id) {
    this.id = id;
  }


  public void setModificationJustification(String modificationJustification) {
    this.modificationJustification = modificationJustification;
  }


  public void setModifiedBy(User modifiedBy) {
    this.modifiedBy = modifiedBy;
  }


  public void setName(String name) {
    this.name = name;
  }


  /**
   * @param programType the programType to set
   */
  public void setProgramType(EntityType programType) {
    this.programType = programType;
  }


  /**
   * @param researchArea the researchArea to set
   */
  public void setResearchArea(ResearchArea researchArea) {
    this.researchArea = researchArea;
  }


  /**
   * @param researchLeaders the researchLeaders to set
   */
  public void setResearchLeaders(List<ResearchLeader> researchLeaders) {
    this.researchLeaders = researchLeaders;
  }

  @Override
  public String toString() {
    return id.toString();
  }


}
