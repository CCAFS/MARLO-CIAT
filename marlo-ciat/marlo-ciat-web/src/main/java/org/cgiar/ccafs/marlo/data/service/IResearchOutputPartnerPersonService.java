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

import org.cgiar.ccafs.marlo.data.model.ResearchOutputPartnerPerson;
import org.cgiar.ccafs.marlo.data.service.impl.ResearchOutputPartnerPersonService;

import java.util.List;

import com.google.inject.ImplementedBy;

/**
 * @author Christian Garcia
 */
@ImplementedBy(ResearchOutputPartnerPersonService.class)
public interface IResearchOutputPartnerPersonService {


  /**
   * This method removes a specific researchOutputPartnerPerson value from the database.
   * 
   * @param researchOutputPartnerPersonId is the researchOutputPartnerPerson identifier.
   * @return true if the researchOutputPartnerPerson was successfully deleted, false otherwise.
   */
  public boolean deleteResearchOutputPartnerPerson(long researchOutputPartnerPersonId);


  /**
   * This method validate if the researchOutputPartnerPerson identify with the given id exists in the system.
   * 
   * @param researchOutputPartnerPersonID is a researchOutputPartnerPerson identifier.
   * @return true if the researchOutputPartnerPerson exists, false otherwise.
   */
  public boolean existResearchOutputPartnerPerson(long researchOutputPartnerPersonID);


  /**
   * This method gets a list of researchOutputPartnerPerson that are active
   * 
   * @return a list from ResearchOutputPartnerPerson null if no exist records
   */
  public List<ResearchOutputPartnerPerson> findAll();


  /**
   * This method gets a researchOutputPartnerPerson object by a given researchOutputPartnerPerson identifier.
   * 
   * @param researchOutputPartnerPersonID is the researchOutputPartnerPerson identifier.
   * @return a ResearchOutputPartnerPerson object.
   */
  public ResearchOutputPartnerPerson getResearchOutputPartnerPersonById(long researchOutputPartnerPersonID);

  /**
   * This method gets a list of researchOutputPartnerPersons belongs of the user
   * 
   * @param userId - the user id
   * @return List of ResearchOutputPartnerPersons or null if the user is invalid or not have roles.
   */
  public List<ResearchOutputPartnerPerson> getResearchOutputPartnerPersonsByUserId(Long userId);

  /**
   * This method saves the information of the given researchOutputPartnerPerson
   * 
   * @param researchOutputPartnerPerson - is the researchOutputPartnerPerson object with the new information to be added/updated.
   * @return a number greater than 0 representing the new ID assigned by the database, 0 if the researchOutputPartnerPerson was
   *         updated
   *         or -1 is some error occurred.
   */
  public long saveResearchOutputPartnerPerson(ResearchOutputPartnerPerson researchOutputPartnerPerson);


}
