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

import org.cgiar.ccafs.marlo.data.model.ResearchProgram;
import org.cgiar.ccafs.marlo.data.service.impl.ProgramService;

import java.util.List;

import com.google.inject.ImplementedBy;

/**
 * @author Christian Garcia
 */
@ImplementedBy(ProgramService.class)
public interface IProgramService {


  /**
   * This method removes a specific ResearchProgram value from the database.
   * 
   * @param programId is the ResearchProgram identifier.
   * @return true if the ResearchProgram was successfully deleted, false otherwise.
   */
  public boolean deleteProgram(long programId);


  /**
   * This method validate if the ResearchProgram identify with the given id exists in the system.
   * 
   * @param programID is a ResearchProgram identifier.
   * @return true if the ResearchProgram exists, false otherwise.
   */
  public boolean existProgram(long programID);


  /**
   * This method gets a list of ResearchProgram that are active
   * 
   * @return a list from ResearchProgram null if no exist records
   */
  public List<ResearchProgram> findAll();


  /**
   * Lists all the research programs for the research area with the given id
   * 
   * @param researchAreaId the research area id or primary key
   * @return List of research programs for the given research area id or else an empty list will be returned.
   */
  public List<ResearchProgram> findProgramsByResearchArea(Long researchAreaId);

  /**
   * This method gets a list of ResearchProgram of a specific program type
   * 
   * @return a ResearchProgram list
   */
  public List<ResearchProgram> findProgramsByType(long id, int programType);

  /**
   * This method gets a ResearchProgram object by a given ResearchProgram identifier.
   * 
   * @param programID is the crpProgram identifier.
   * @return a ResearchProgram object.
   */
  public ResearchProgram getProgramById(long programID);

  /**
   * This method saves the information of the given ResearchProgram
   * 
   * @param researchProgram - is the crpProgram object with the new information to be added/updated.
   * @return a number greater than 0 representing the new ID assigned by the database, 0 if the ResearchProgram was
   *         updated
   *         or -1 is some error occurred.
   */
  public long saveProgram(ResearchProgram researchProgram);


  /**
   * This method saves the information of the given ResearchProgram
   * 
   * @param researchProgram - is the ResearchProgram object with the new information to be added/updated.
   * @return a number greater than 0 representing the new ID assigned by the database, 0 if the ResearchProgram was
   *         updated
   *         or -1 is some error occurred.
   */
  public long saveProgram(ResearchProgram researchProgram, String actionName, List<String> relationsName);


}
