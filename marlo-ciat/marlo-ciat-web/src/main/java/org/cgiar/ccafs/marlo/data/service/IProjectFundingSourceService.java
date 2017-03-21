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

import org.cgiar.ccafs.marlo.data.model.ProjectFundingSource;
import org.cgiar.ccafs.marlo.data.service.impl.ProjectFundingSourceService;

import java.util.List;

import com.google.inject.ImplementedBy;

/**
 * @author Christian Garcia
 */
@ImplementedBy(ProjectFundingSourceService.class)
public interface IProjectFundingSourceService {


  /**
   * This method removes a specific projectFundingSource value from the database.
   * 
   * @param projectFundingSourceId is the projectFundingSource identifier.
   * @return true if the projectFundingSource was successfully deleted, false otherwise.
   */
  public boolean deleteProjectFundingSource(long projectFundingSourceId);


  /**
   * This method validate if the projectFundingSource identify with the given id exists in the system.
   * 
   * @param projectFundingSourceID is a projectFundingSource identifier.
   * @return true if the projectFundingSource exists, false otherwise.
   */
  public boolean existProjectFundingSource(long projectFundingSourceID);


  /**
   * This method gets a list of projectFundingSource that are active
   * 
   * @return a list from ProjectFundingSource null if no exist records
   */
  public List<ProjectFundingSource> findAll();


  /**
   * This method gets a projectFundingSource object by a given projectFundingSource identifier.
   * 
   * @param projectFundingSourceID is the projectFundingSource identifier.
   * @return a ProjectFundingSource object.
   */
  public ProjectFundingSource getProjectFundingSourceById(long projectFundingSourceID);

  /**
   * This method gets a list of projectFundingSources belongs of the user
   * 
   * @param userId - the user id
   * @return List of ProjectFundingSources or null if the user is invalid or not have roles.
   */
  public List<ProjectFundingSource> getProjectFundingSourcesByUserId(Long userId);

  /**
   * This method saves the information of the given projectFundingSource
   * 
   * @param projectFundingSource - is the projectFundingSource object with the new information to be added/updated.
   * @return a number greater than 0 representing the new ID assigned by the database, 0 if the projectFundingSource was
   *         updated
   *         or -1 is some error occurred.
   */
  public long saveProjectFundingSource(ProjectFundingSource projectFundingSource);


}
