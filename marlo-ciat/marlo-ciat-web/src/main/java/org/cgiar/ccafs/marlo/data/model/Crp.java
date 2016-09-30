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
import java.util.Set;

import com.google.gson.annotations.Expose;

/**
 * Modified by @author nmatovu last on Sep 29, 2016
 */
public class Crp implements IAuditLog, Serializable {

  private static final long serialVersionUID = -3829009505143795994L;

  @Expose
  private Long id;

  @Expose
  private String name;

  @Expose
  private String acronym;

  private Set<CrpUser> crpUsers = new HashSet<CrpUser>(0);

  private Set<Role> roles = new HashSet<Role>(0);
  private Set<CrpParameter> crpParameters = new HashSet<CrpParameter>(0);
  @Expose
  private boolean active;

  @Expose
  private boolean marlo;

  @Expose
  private User createdBy;
  @Expose
  private Date activeSince;

  @Expose
  private User modifiedBy;

  @Expose
  private String modificationJustification;


  private boolean hasRegions;

  public Crp() {
  }

  public Crp(String name) {
    this.name = name;
  }

  /**
   * @param name
   * @param acronym
   * @param crpUsers
   * @param roles
   * @param crpParameters
   */
  public Crp(String name, String acronym, Set<CrpUser> crpUsers, Set<Role> roles, Set<CrpParameter> crpParameters) {
    super();
    this.name = name;
    this.acronym = acronym;
    this.crpUsers = crpUsers;
    this.roles = roles;
    this.crpParameters = crpParameters;
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
    Crp other = (Crp) obj;
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
    return acronym;
  }

  public Date getActiveSince() {
    return activeSince;
  }

  public User getCreatedBy() {
    return createdBy;
  }

  /**
   * @return the crpParameters
   */
  public Set<CrpParameter> getCrpParameters() {
    return crpParameters;
  }

  public Set<CrpUser> getCrpUsers() {
    return this.crpUsers;
  }

  @Override
  public Long getId() {
    return this.id;
  }

  @Override
  public String getLogDeatil() {
    StringBuilder sb = new StringBuilder();
    sb.append("Id : ").append(this.getId()).append("Name : ").append(this.getName());
    return sb.toString();
  }

  public String getModificationJustification() {
    return modificationJustification;
  }

  @Override
  public User getModifiedBy() {
    return modifiedBy;
  }

  public String getName() {
    return this.name;
  }

  public Set<Role> getRoles() {
    return this.roles;
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

  public boolean isHasRegions() {
    return hasRegions;
  }

  public boolean isMarlo() {
    return marlo;
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

  public void setCreatedBy(User createdBy) {
    this.createdBy = createdBy;
  }

  /**
   * @param crpParameters the crpParameters to set
   */
  public void setCrpParameters(Set<CrpParameter> crpParameters) {
    this.crpParameters = crpParameters;
  }

  public void setCrpUsers(Set<CrpUser> crpUsers) {
    this.crpUsers = crpUsers;
  }

  public void setHasRegions(boolean hasRegions) {
    this.hasRegions = hasRegions;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setMarlo(boolean marlo) {
    this.marlo = marlo;
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


  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }


  @Override
  public String toString() {
    return id.toString();
  }

}
