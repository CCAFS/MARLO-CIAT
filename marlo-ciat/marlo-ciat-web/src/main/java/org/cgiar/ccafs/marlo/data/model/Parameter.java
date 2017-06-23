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

// Generated May 26, 2016 9:42:28 AM by Hibernate Tools 4.3.1.Final

import java.io.Serializable;
import java.util.Date;

import com.google.gson.annotations.Expose;

/**
 * CrpParameter generated by hbm2java
 */
public class Parameter implements Serializable {

  private static final long serialVersionUID = -5502989917819467607L;
  @Expose
  private Long id;
  @Expose
  private Center researchCenter;
  @Expose
  private String key;
  @Expose
  private String value;
  @Expose
  private boolean active;
  @Expose
  private Date activeSince;

  public Parameter() {
  }

  public Parameter(Center researchCenter, String key, String value) {
    this.researchCenter = researchCenter;
    this.key = key;
    this.value = value;
  }

  /**
   * @param researchCenter
   * @param key
   * @param value
   * @param active
   * @param activeSince
   */
  public Parameter(Center researchCenter, String key, String value, boolean active, Date activeSince) {
    super();
    this.researchCenter = researchCenter;
    this.key = key;
    this.value = value;
    this.active = active;
    this.activeSince = activeSince;
  }

  public Date getActiveSince() {
    return activeSince;
  }


  public Long getId() {
    return this.id;
  }

  public String getKey() {
    return this.key;
  }


  public Center getResearchCenter() {
    return this.researchCenter;
  }

  public String getValue() {
    return this.value;
  }

  /**
   * @return the active
   */
  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }


  public void setActiveSince(Date activeSince) {
    this.activeSince = activeSince;
  }

  public void setCrp(Center researchCenter) {
    this.researchCenter = researchCenter;
  }

  public void setId(Long id) {
    this.id = id;
  }


  public void setKey(String key) {
    this.key = key;
  }

  /**
   * @param researchCenter the researchCenter to set
   */
  public void setResearchCenter(Center researchCenter) {
    this.researchCenter = researchCenter;
  }


  public void setValue(String value) {
    this.value = value;
  }


  @Override
  public String toString() {
    return id.toString();
  }


}
