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
import org.cgiar.ccafs.marlo.data.service.impl.OutputNextUserService;

import java.util.List;

import com.google.inject.ImplementedBy;

/**
 * @author Christian Garcia
 */
@ImplementedBy(OutputNextUserService.class)
public interface IOutputNextUserService {


  /**
   * This method removes a specific outputNextUser value from the database.
   * 
   * @param outputNextUserId is the outputNextUser identifier.
   * @return true if the outputNextUser was successfully deleted, false otherwise.
   */
  public boolean deleteOutputNextUser(long outputNextUserId);


  /**
   * This method validate if the outputNextUser identify with the given id exists in the system.
   * 
   * @param outputNextUserID is a outputNextUser identifier.
   * @return true if the outputNextUser exists, false otherwise.
   */
  public boolean existOutputNextUser(long outputNextUserID);


  /**
   * This method gets a list of outputNextUser that are active
   * 
   * @return a list from OutputNextUser null if no exist records
   */
  public List<OutputNextUser> findAll();


  /**
   * This method gets a outputNextUser object by a given outputNextUser identifier.
   * 
   * @param outputNextUserID is the outputNextUser identifier.
   * @return a OutputNextUser object.
   */
  public OutputNextUser getOutputNextUserById(long outputNextUserID);

  /**
   * This method gets a list of outputNextUsers belongs of the user
   * 
   * @param userId - the user id
   * @return List of OutputNextUsers or null if the user is invalid or not have roles.
   */
  public List<OutputNextUser> getOutputNextUsersByUserId(Long userId);

  /**
   * This method saves the information of the given outputNextUser
   * 
   * @param outputNextUser - is the outputNextUser object with the new information to be added/updated.
   * @return a number greater than 0 representing the new ID assigned by the database, 0 if the outputNextUser was
   *         updated
   *         or -1 is some error occurred.
   */
  public long saveOutputNextUser(OutputNextUser outputNextUser);


}
