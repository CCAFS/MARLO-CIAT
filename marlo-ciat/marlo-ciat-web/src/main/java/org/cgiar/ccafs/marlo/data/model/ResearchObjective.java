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
import java.util.Date;

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

  /**
   * 
   */
  public ResearchObjective() {
    super();
    // TODO Auto-generated constructor stub
  }

  /**
   * @param name
   * @param researchCenter
   * @param entityType
   */
  public ResearchObjective(String name, ResearchCenter researchCenter) {
    super();
    this.name = name;
    this.researchCenter = researchCenter;
  }

  public Date getActiveSince() {
    return activeSince;
  }

  public User getCreatedBy() {
    return createdBy;
  }

  /**
   * @return the id
   */
  public Long getId() {
    return id;
  }

  public String getModificationJustification() {
    return modificationJustification;
  }

  public User getModifiedBy() {
    return modifiedBy;
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

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public void setActiveSince(Date activeSince) {
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


}
