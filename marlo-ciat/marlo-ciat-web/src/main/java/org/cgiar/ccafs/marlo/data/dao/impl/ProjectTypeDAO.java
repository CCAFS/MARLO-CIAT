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


package org.cgiar.ccafs.marlo.data.dao.impl;

import org.cgiar.ccafs.marlo.data.dao.IProjectTypeDAO;
import org.cgiar.ccafs.marlo.data.model.ProjectType;

import java.util.List;

import com.google.inject.Inject;

public class ProjectTypeDAO implements IProjectTypeDAO {

  private StandardDAO dao;

  @Inject
  public ProjectTypeDAO(StandardDAO dao) {
    this.dao = dao;
  }

  @Override
  public boolean deleteProjectType(long projectTypeId) {
    ProjectType projectType = this.find(projectTypeId);
    projectType.setActive(false);
    return this.save(projectType) > 0;
  }

  @Override
  public boolean existProjectType(long projectTypeID) {
    ProjectType projectType = this.find(projectTypeID);
    if (projectType == null) {
      return false;
    }
    return true;

  }

  @Override
  public ProjectType find(long id) {
    return dao.find(ProjectType.class, id);

  }

  @Override
  public List<ProjectType> findAll() {
    String query = "from " + ProjectType.class.getName();
    List<ProjectType> list = dao.findAll(query);
    if (list.size() > 0) {
      return list;
    }
    return null;

  }

  @Override
  public List<ProjectType> getProjectTypesByUserId(long userId) {
    String query = "from " + ProjectType.class.getName() + " where user_id=" + userId;
    return dao.findAll(query);
  }

  @Override
  public long save(ProjectType projectType) {
    if (projectType.getId() == null) {
      dao.save(projectType);
    } else {
      dao.update(projectType);
    }
    return projectType.getId();
  }

  @Override
  public long save(ProjectType projectType, String actionName, List<String> relationsName) {
    if (projectType.getId() == null) {
      dao.save(projectType, actionName, relationsName);
    } else {
      dao.update(projectType, actionName, relationsName);
    }
    return projectType.getId();
  }


}