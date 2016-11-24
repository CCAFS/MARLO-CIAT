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

import java.io.Serializable;
import java.util.List;


public class OutputNextSubUser implements Serializable {

  private static final long serialVersionUID = -6178819795093572087L;
  private Long id;
  /**
   * The name of the next user type e.g (Minister of Agriculture, etc)
   */
  private String name;
  /**
   * The next user of the output such as (Policy Makers, etc)
   */
  private OutputNextUser nextOutputUser;
  private List<ResearchOutput> researchOutputs;


  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }


  public OutputNextUser getNextOutputUser() {
    return nextOutputUser;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setNextOutputUser(OutputNextUser nextOutputUser) {
    this.nextOutputUser = nextOutputUser;
  }

  public List<ResearchOutput> getResearchOutputs() {
    return researchOutputs;
  }

  public void setResearchOutputs(List<ResearchOutput> researchOutputs) {
    this.researchOutputs = researchOutputs;
  }


}
