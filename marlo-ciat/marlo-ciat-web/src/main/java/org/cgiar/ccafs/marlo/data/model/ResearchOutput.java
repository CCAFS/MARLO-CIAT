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


/**
 * @author nmatovu
 * @author hjimenez
 */
public class ResearchOutput implements Serializable {


  private static final long serialVersionUID = -185814169741386135L;


  private Long id;


  private String output;
  private Date dateAdded;
  private ResearchOutcome researchOutcome;
  private Date activeSince;

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
  public ResearchOutput(String output, Date dateAdded, ResearchOutcome researchOutcome, Date activeSince) {
    super();
    this.output = output;
    this.dateAdded = dateAdded;
    this.researchOutcome = researchOutcome;
    this.activeSince = activeSince;
  }

  public Date getActiveSince() {
    return activeSince;
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
  public Long getId() {
    return id;
  }


  /**
   * @return the output
   */
  public String getOutput() {
    return output;
  }


  /**
   * @return the researchOutcome
   */
  public ResearchOutcome getResearchOutcome() {
    return researchOutcome;
  }


  public void setActiveSince(Date activeSince) {
    this.activeSince = activeSince;
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


  /**
   * @param output the output to set
   */
  public void setOutput(String output) {
    this.output = output;
  }


  /**
   * @param researchOutcome the researchOutcome to set
   */
  public void setResearchOutcome(ResearchOutcome researchOutcome) {
    this.researchOutcome = researchOutcome;
  }

}
