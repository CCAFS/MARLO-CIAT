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
package org.cgiar.ccafs.marlo.data.service.impl;


import org.cgiar.ccafs.marlo.data.dao.IProjectStatusDAO;
import org.cgiar.ccafs.marlo.data.model.ProjectStatus;
import org.cgiar.ccafs.marlo.data.service.IProjectStatusService;

import java.util.List;

import com.google.inject.Inject;

/**
 * @author Christian Garcia
 */
public class ProjectStatusService implements IProjectStatusService {


  private IProjectStatusDAO projectStatusDAO;

  // Managers


  @Inject
  public ProjectStatusService(IProjectStatusDAO projectStatusDAO) {
    this.projectStatusDAO = projectStatusDAO;


  }

  @Override
  public boolean deleteProjectStatus(long projectStatusId) {

    return projectStatusDAO.deleteProjectStatus(projectStatusId);
  }

  @Override
  public boolean existProjectStatus(long projectStatusID) {

    return projectStatusDAO.existProjectStatus(projectStatusID);
  }

  @Override
  public List<ProjectStatus> findAll() {

    return projectStatusDAO.findAll();

  }

  @Override
  public ProjectStatus getProjectStatusById(long projectStatusID) {

    return projectStatusDAO.find(projectStatusID);
  }

  @Override
  public List<ProjectStatus> getProjectStatussByUserId(Long userId) {
    return projectStatusDAO.getProjectStatussByUserId(userId);
  }

  @Override
  public long saveProjectStatus(ProjectStatus projectStatus) {

    return projectStatusDAO.save(projectStatus);
  }


}
