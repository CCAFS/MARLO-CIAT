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


import org.cgiar.ccafs.marlo.data.dao.IProjectTypeDAO;
import org.cgiar.ccafs.marlo.data.model.ProjectType;
import org.cgiar.ccafs.marlo.data.service.IProjectTypeService;

import java.util.List;

import com.google.inject.Inject;

/**
 * @author Christian Garcia
 */
public class ProjectTypeService implements IProjectTypeService {


  private IProjectTypeDAO projectTypeDAO;

  // Managers


  @Inject
  public ProjectTypeService(IProjectTypeDAO projectTypeDAO) {
    this.projectTypeDAO = projectTypeDAO;


  }

  @Override
  public boolean deleteProjectType(long projectTypeId) {

    return projectTypeDAO.deleteProjectType(projectTypeId);
  }

  @Override
  public boolean existProjectType(long projectTypeID) {

    return projectTypeDAO.existProjectType(projectTypeID);
  }

  @Override
  public List<ProjectType> findAll() {

    return projectTypeDAO.findAll();

  }

  @Override
  public ProjectType getProjectTypeById(long projectTypeID) {

    return projectTypeDAO.find(projectTypeID);
  }

  @Override
  public List<ProjectType> getProjectTypesByUserId(Long userId) {
    return projectTypeDAO.getProjectTypesByUserId(userId);
  }

  @Override
  public long saveProjectType(ProjectType projectType) {

    return projectTypeDAO.save(projectType);
  }

  @Override
  public long saveProjectType(ProjectType projectType, String actionName, List<String> relationsName) {
    return projectTypeDAO.save(projectType, actionName, relationsName);
  }


}
