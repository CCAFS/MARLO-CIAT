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
package org.cgiar.ccafs.marlo.data.service;

import org.cgiar.ccafs.marlo.data.model.ResearchLeader;
import org.cgiar.ccafs.marlo.data.service.impl.ResearchLeaderService;

import java.util.List;

import com.google.inject.ImplementedBy;

/**
 * @author Christian Garcia
 */
@ImplementedBy(ResearchLeaderService.class)
public interface IResearchLeaderService {


  /**
   * This method removes a specific ResearchLeader value from the database.
   * 
   * @param researchLeaderId is the ResearchLeader identifier.
   * @return true if the ResearchLeader was successfully deleted, false otherwise.
   */
  public boolean deleteResearchLeader(long researchLeaderId);


  /**
   * This method validate if the ResearchLeader identify with the given id exists in the system.
   * 
   * @param researchLeaderId is a ResearchLeader identifier.
   * @return true if the crpProgramLeader exists, false otherwise.
   */
  public boolean existResearchLeader(long researchLeaderId);


  /**
   * This method gets a list of ResearchLeader that are active
   * 
   * @return a list from ResearchLeader null if no exist records
   */
  public List<ResearchLeader> findAll();


  /**
   * This method gets a ResearchLeader object by a given ResearchLeader identifier.
   * 
   * @param researchLeaderId is the ResearchLeader identifier.
   * @return a ResearchLeader object.
   */
  public ResearchLeader getResearchLeaderById(long researchLeaderId);

  /**
   * This method saves the information of the given ResearchLeader
   * 
   * @param researchLeader - is the ResearchLeader object with the new information to be added/updated.
   * @return a number greater than 0 representing the new ID assigned by the database, 0 if the crpProgramLeader was
   *         updated
   *         or -1 is some error occurred.
   */
  public long saveResearchLeader(ResearchLeader researchLeader);


}
