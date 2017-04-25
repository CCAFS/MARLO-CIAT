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


import org.cgiar.ccafs.marlo.data.dao.IProjectDAO;
import org.cgiar.ccafs.marlo.data.model.Project;
import org.cgiar.ccafs.marlo.data.service.IProjectService;

import java.util.List;

import com.google.inject.Inject;

/**
 * @author Christian Garcia
 */
public class ProjectService implements IProjectService {


  private IProjectDAO projectDAO;

  // Managers


  @Inject
  public ProjectService(IProjectDAO projectDAO) {
    this.projectDAO = projectDAO;


  }

  @Override
  public boolean deleteProject(long projectId) {

    return projectDAO.deleteProject(projectId);
  }

  @Override
  public boolean existProject(long projectID) {

    return projectDAO.existProject(projectID);
  }

  @Override
  public List<Project> findAll() {

    return projectDAO.findAll();

  }

  @Override
  public Project getProjectById(long projectID) {

    return projectDAO.find(projectID);
  }

  @Override
  public List<Project> getProjectsByUserId(Long userId) {
    return projectDAO.getProjectsByUserId(userId);
  }

  @Override
  public long saveProject(Project project) {

    return projectDAO.save(project);
  }

  @Override
  public long saveProject(Project project, String actionName, List<String> relationsName) {
    return projectDAO.save(project, actionName, relationsName);
  }


}
