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

public class ResearchPartner implements Serializable {


  private static final long serialVersionUID = 7774818062880949190L;
  private Long id;
  private String name;
  private EntityType partnerType;
  private String organization;

  /**
   * 
   */
  public ResearchPartner() {
    super();
    // TODO Auto-generated constructor stub
  }

  /**
   * @param name
   * @param partnerType
   * @param organization
   */
  public ResearchPartner(String name, EntityType partnerType, String organization) {
    super();
    this.name = name;
    this.partnerType = partnerType;
    this.organization = organization;
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
   * @return the organization
   */
  public String getOrganization() {
    return organization;
  }


  /**
   * @return the partnerType
   */
  public EntityType getPartnerType() {
    return partnerType;
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
   * @param organization the organization to set
   */
  public void setOrganization(String organization) {
    this.organization = organization;
  }


  /**
   * @param partnerType the partnerType to set
   */
  public void setPartnerType(EntityType partnerType) {
    this.partnerType = partnerType;
  }


}
