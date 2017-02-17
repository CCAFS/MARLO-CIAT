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

import org.cgiar.ccafs.marlo.data.dao.IProjectStatusDAO;
import org.cgiar.ccafs.marlo.data.model.ProjectStatus;

import java.util.List;

import com.google.inject.Inject;

public class ProjectStatusDAO implements IProjectStatusDAO {

  private StandardDAO dao;

  @Inject
  public ProjectStatusDAO(StandardDAO dao) {
    this.dao = dao;
  }

  @Override
  public boolean deleteProjectStatus(long projectStatusId) {
    ProjectStatus projectStatus = this.find(projectStatusId);
    return dao.delete(projectStatus);
  }

  @Override
  public boolean existProjectStatus(long projectStatusID) {
    ProjectStatus projectStatus = this.find(projectStatusID);
    if (projectStatus == null) {
      return false;
    }
    return true;

  }

  @Override
  public ProjectStatus find(long id) {
    return dao.find(ProjectStatus.class, id);

  }

  @Override
  public List<ProjectStatus> findAll() {
    String query = "from " + ProjectStatus.class.getName();
    List<ProjectStatus> list = dao.findAll(query);
    if (list.size() > 0) {
      return list;
    }
    return null;

  }

  @Override
  public List<ProjectStatus> getProjectStatussByUserId(long userId) {
    String query = "from " + ProjectStatus.class.getName() + " where user_id=" + userId;
    return dao.findAll(query);
  }

  @Override
  public long save(ProjectStatus projectStatus) {
    if (projectStatus.getId() == null) {
      dao.save(projectStatus);
    } else {
      dao.update(projectStatus);
    }
    return projectStatus.getId();
  }


}