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

import org.cgiar.ccafs.marlo.data.model.OutputNextUser;
import org.cgiar.ccafs.marlo.data.service.impl.NextUserTypeService;

import java.util.List;

import com.google.inject.ImplementedBy;

/**
 * @author Christian Garcia
 */
@ImplementedBy(NextUserTypeService.class)
public interface INextUserTypeService {


  /**
   * This method removes a specific nextUserType value from the database.
   * 
   * @param nextUserTypeId is the nextUserType identifier.
   * @return true if the nextUserType was successfully deleted, false otherwise.
   */
  public boolean deleteNextUserType(long nextUserTypeId);


  /**
   * This method validate if the nextUserType identify with the given id exists in the system.
   * 
   * @param nextUserTypeID is a nextUserType identifier.
   * @return true if the nextUserType exists, false otherwise.
   */
  public boolean existNextUserType(long nextUserTypeID);


  /**
   * This method gets a list of nextUserType that are active
   * 
   * @return a list from NextUserType null if no exist records
   */
  public List<OutputNextUser> findAll();


  /**
   * This method gets a nextUserType object by a given nextUserType identifier.
   * 
   * @param nextUserTypeID is the nextUserType identifier.
   * @return a NextUserType object.
   */
  public OutputNextUser getNextUserTypeById(long nextUserTypeID);

  /**
   * This method gets a list of nextUserTypes belongs of the user
   * 
   * @param userId - the user id
   * @return List of NextUserTypes or null if the user is invalid or not have roles.
   */
  public List<OutputNextUser> getNextUserTypesByUserId(Long userId);

  /**
   * This method saves the information of the given nextUserType
   * 
   * @param nextUserType - is the nextUserType object with the new information to be added/updated.
   * @return a number greater than 0 representing the new ID assigned by the database, 0 if the nextUserType was
   *         updated
   *         or -1 is some error occurred.
   */
  public long saveNextUserType(OutputNextUser nextUserType);


}
