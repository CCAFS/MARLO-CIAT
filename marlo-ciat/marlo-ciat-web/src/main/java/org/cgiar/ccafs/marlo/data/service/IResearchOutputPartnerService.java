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

import org.cgiar.ccafs.marlo.data.model.ResearchOutputPartner;
import org.cgiar.ccafs.marlo.data.service.impl.ResearchOutputPartnerService;

import java.util.List;

import com.google.inject.ImplementedBy;

/**
 * @author Christian Garcia
 */
@ImplementedBy(ResearchOutputPartnerService.class)
public interface IResearchOutputPartnerService {


  /**
   * This method removes a specific researchOutputPartner value from the database.
   * 
   * @param researchOutputPartnerId is the researchOutputPartner identifier.
   * @return true if the researchOutputPartner was successfully deleted, false otherwise.
   */
  public boolean deleteResearchOutputPartner(long researchOutputPartnerId);


  /**
   * This method validate if the researchOutputPartner identify with the given id exists in the system.
   * 
   * @param researchOutputPartnerID is a researchOutputPartner identifier.
   * @return true if the researchOutputPartner exists, false otherwise.
   */
  public boolean existResearchOutputPartner(long researchOutputPartnerID);


  /**
   * This method gets a list of researchOutputPartner that are active
   * 
   * @return a list from ResearchOutputPartner null if no exist records
   */
  public List<ResearchOutputPartner> findAll();


  /**
   * This method gets a researchOutputPartner object by a given researchOutputPartner identifier.
   * 
   * @param researchOutputPartnerID is the researchOutputPartner identifier.
   * @return a ResearchOutputPartner object.
   */
  public ResearchOutputPartner getResearchOutputPartnerById(long researchOutputPartnerID);

  /**
   * This method gets a list of researchOutputPartners belongs of the user
   * 
   * @param userId - the user id
   * @return List of ResearchOutputPartners or null if the user is invalid or not have roles.
   */
  public List<ResearchOutputPartner> getResearchOutputPartnersByUserId(Long userId);

  /**
   * This method saves the information of the given researchOutputPartner
   * 
   * @param researchOutputPartner - is the researchOutputPartner object with the new information to be added/updated.
   * @return a number greater than 0 representing the new ID assigned by the database, 0 if the researchOutputPartner was
   *         updated
   *         or -1 is some error occurred.
   */
  public long saveResearchOutputPartner(ResearchOutputPartner researchOutputPartner);


}
