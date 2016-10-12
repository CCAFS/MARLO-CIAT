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
 * Modified by @author nmatovu last on Sep 29, 2016
 */
public class ResearchCenter implements Serializable {


  private static final long serialVersionUID = -5311843838644643688L;

  @Expose
  private Long id;

  @Expose
  private String name;

  @Expose
  private String acronym;

  private Set<CenterUser> centerUsers = new HashSet<CenterUser>(0);

  public ResearchCenter() {
  }

  public ResearchCenter(String name) {
    this.name = name;
  }


  /**
   * @param name
   * @param acronym
   * @param roles
   */
  public ResearchCenter(String name, String acronym) {
    super();
    this.name = name;
    this.acronym = acronym;
  }

  /**
   * @param name
   * @param acronym
   * @param centerUsers
   */
  public ResearchCenter(String name, String acronym, Set<CenterUser> centerUsers) {
    super();
    this.name = name;
    this.acronym = acronym;
    this.centerUsers = centerUsers;
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
    ResearchCenter other = (ResearchCenter) obj;
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


  /**
   * @return the centerUsers
   */
  public Set<CenterUser> getCenterUsers() {
    return centerUsers;
  }


  public Long getId() {
    return this.id;
  }


  public String getName() {
    return this.name;
  }


  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }


  public void setAcronym(String acronym) {
    this.acronym = acronym;
  }


  /**
   * @param centerUsers the centerUsers to set
   */
  public void setCenterUsers(Set<CenterUser> centerUsers) {
    this.centerUsers = centerUsers;
  }


  public void setId(Long id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return id.toString();
  }

}
