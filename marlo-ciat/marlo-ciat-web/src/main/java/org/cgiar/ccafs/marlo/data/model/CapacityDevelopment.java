/*****************************************************************
 * This file is part of Managing Agricultural Research for Learning &
 * Outcomes Platform (MiLE).
 * MiLE is free software: you can redistribute it and/or modify
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

public class CapacityDevelopment {

  private int id;
  private String title;
  private String type;
  private Date initiationDate;
  private Date terminationDate;
  private String contactPerson;
  private String country;
  private String region;
  private String researchArea;
  private String researchProgram_theme;
  private String project;
  private String crp;
  private String directedBy;


  /* Default constructor */
  public CapacityDevelopment() {
    super();
    // TODO Auto-generated constructor stub
  }


  /* minimal constructor */
  public CapacityDevelopment(int id, String title, String type ) {
    super();
    this.id = id;
    this.title = title;
    this.type = type;
    
  }


  /* full constructor */
  public CapacityDevelopment(int id, String title, String type, Date initiationDate, Date terminationDate,
    String contactPerson, String country, String region, String researchArea, String researchProgram_theme,
    String project, String crp, String directedBy) {
    super();
    this.id = id;
    this.title = title;
    this.type = type;
    this.initiationDate = initiationDate;
    this.terminationDate = terminationDate;
    this.contactPerson = contactPerson;
    this.country = country;
    this.region = region;
    this.researchArea = researchArea;
    this.researchProgram_theme = researchProgram_theme;
    this.project = project;
    this.crp = crp;
    this.directedBy = directedBy;
  }


  public String getContactPerson() {
    return contactPerson;
  }

  public String getCountry() {
    return country;
  }

  public String getCrp() {
    return crp;
  }

  public String getDirectedBy() {
    return directedBy;
  }

  public int getId() {
    return id;
  }

  public Date getInitiationDate() {
    return initiationDate;
  }

  public String getProject() {
    return project;
  }

  public String getRegion() {
    return region;
  }

  public String getResearchArea() {
    return researchArea;
  }

  public String getResearchProgram_theme() {
    return researchProgram_theme;
  }

  public Date getTerminationDate() {
    return terminationDate;
  }

  public String getTitle() {
    return title;
  }

  public String getType() {
    return type;
  }

  public void setContactPerson(String contactPerson) {
    this.contactPerson = contactPerson;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public void setCrp(String crp) {
    this.crp = crp;
  }

  public void setDirectedBy(String directedBy) {
    this.directedBy = directedBy;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setInitiationDate(Date initiationDate) {
    this.initiationDate = initiationDate;
  }

  public void setProject(String project) {
    this.project = project;
  }

  public void setRegion(String region) {
    this.region = region;
  }

  public void setResearchArea(String researchArea) {
    this.researchArea = researchArea;
  }

  public void setResearchProgram_theme(String researchProgram_theme) {
    this.researchProgram_theme = researchProgram_theme;
  }

  public void setTerminationDate(Date terminationDate) {
    this.terminationDate = terminationDate;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setType(String type) {
    this.type = type;
  }


}
