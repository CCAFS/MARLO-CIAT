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

import org.cgiar.ccafs.marlo.data.model.ResearchOutcome;
import org.cgiar.ccafs.marlo.data.service.impl.ResearchOutcomeService;

import java.util.List;

import com.google.inject.ImplementedBy;

/**
 * @author Christian Garcia
 */
@ImplementedBy(ResearchOutcomeService.class)
public interface IResearchOutcomeService {


  /**
   * This method removes a specific ResearchOutcome value from the database.
   * 
   * @param researchOutcomeId is the ResearchOutcome identifier.
   * @return true if the ResearchOutcome was successfully deleted, false otherwise.
   */
  public boolean deleteResearchOutcome(long researchOutcomeId);


  /**
   * This method validate if the ResearchOutcome identify with the given id exists in the system.
   * 
   * @param researchOutcomeId is a ResearchOutcome identifier.
   * @return true if the ResearchOutcome exists, false otherwise.
   */
  public boolean existResearchOutcome(long researchOutcomeId);


  /**
   * This method gets a list of ResearchOutcome that are active
   * 
   * @return a list from ResearchOutcome null if no exist records
   */
  public List<ResearchOutcome> findAll();


  /**
   * This method gets a ResearchOutcome object by a given crpProgramOutcome identifier.
   * 
   * @param researchOutcomeId is the ResearchOutcome identifier.
   * @return a ResearchOutcome object.
   */
  public ResearchOutcome getResearchOutcomeById(long researchOutcomeId);

  /**
   * This method saves the information of the given crpProgramOutcome
   * 
   * @param researchOutcome - is the ResearchOutcome object with the new information to be added/updated.
   * @return a number greater than 0 representing the new ID assigned by the database, 0 if the ResearchOutcome was
   *         updated
   *         or -1 is some error occurred.
   */
  public long saveResearchOutcome(ResearchOutcome researchOutcome);


}
