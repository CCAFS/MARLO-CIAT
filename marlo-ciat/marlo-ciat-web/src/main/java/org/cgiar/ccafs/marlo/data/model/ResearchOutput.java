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

import org.cgiar.ccafs.marlo.data.IAuditLog;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.google.gson.annotations.Expose;


/**
 * @author nmatovu
 * @author hjimenez
 */
public class ResearchOutput implements Serializable, IAuditLog {


  private static final long serialVersionUID = -185814169741386135L;


  @Expose
  private Long id;

  @Expose
  private String title;


  @Expose
  private Date dateAdded;

  @Expose
  private ResearchOutcome researchOutcome;


  @Expose
  private Date activeSince;


  @Expose
  private boolean active;


  @Expose
  private User createdBy;


  @Expose
  private User modifiedBy;


  @Expose
  private String modificationJustification;

  /**
   * The next user of the output such as (Policy Makers, etc)
   */
  private Set<OutputNextUser> nextOutputUsers = new HashSet<OutputNextUser>(0);
  /**
   * The next sub users such as Minister of Agriculture.
   */
  private Set<OutputNextSubUser> nextOutputSubUsers = new HashSet<OutputNextSubUser>(0);

  private Set<ResearchOutputPartner> researchOutputPartners = new HashSet<ResearchOutputPartner>(0);


  /**
   * 
   */
  public ResearchOutput() {
    super();
    // TODO Auto-generated constructor stub
  }

  /**
   * @param output
   * @param dateAdded
   * @param researchOutcome
   */
  public ResearchOutput(String title, Date dateAdded, ResearchOutcome researchOutcome, Date activeSince) {
    super();
    this.title = title;
    this.dateAdded = dateAdded;
    this.researchOutcome = researchOutcome;
    this.activeSince = activeSince;
  }


  public Date getActiveSince() {
    return activeSince;
  }

  public User getCreatedBy() {
    return createdBy;
  }

  /**
   * @return the dateAdded
   */
  public Date getDateAdded() {
    return dateAdded;
  }

  /**
   * @return the id
   */
  @Override
  public Long getId() {
    return id;
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

  public Set<OutputNextSubUser> getNextOutputSubUsers() {
    return nextOutputSubUsers;
  }

  public Set<OutputNextUser> getNextOutputUsers() {
    return nextOutputUsers;
  }

  /**
   * @return the researchOutcome
   */
  public ResearchOutcome getResearchOutcome() {
    return researchOutcome;
  }

  public Set<ResearchOutputPartner> getResearchOutputPartners() {
    return researchOutputPartners;
  }


  public String getTitle() {
    return title;
  }


  @Override
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
   * @param dateAdded the dateAdded to set
   */
  public void setDateAdded(Date dateAdded) {
    this.dateAdded = dateAdded;
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


  public void setNextOutputSubUsers(Set<OutputNextSubUser> nextOutputSubUsers) {
    this.nextOutputSubUsers = nextOutputSubUsers;
  }


  public void setNextOutputUsers(Set<OutputNextUser> nextOutputUsers) {
    this.nextOutputUsers = nextOutputUsers;
  }

  /**
   * @param researchOutcome the researchOutcome to set
   */
  public void setResearchOutcome(ResearchOutcome researchOutcome) {
    this.researchOutcome = researchOutcome;
  }


  public void setResearchOutputPartners(Set<ResearchOutputPartner> researchOutputPartners) {
    this.researchOutputPartners = researchOutputPartners;
  }


  public void setTitle(String title) {
    this.title = title;
  }

}
