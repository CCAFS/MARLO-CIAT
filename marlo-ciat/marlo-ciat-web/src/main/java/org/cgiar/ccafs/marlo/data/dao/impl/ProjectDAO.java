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

import org.cgiar.ccafs.marlo.data.dao.IProjectDAO;
import org.cgiar.ccafs.marlo.data.model.Project;

import java.util.List;

import com.google.inject.Inject;

public class ProjectDAO implements IProjectDAO {

  private StandardDAO dao;

  @Inject
  public ProjectDAO(StandardDAO dao) {
    this.dao = dao;
  }

  @Override
  public boolean deleteProject(long projectId) {
    Project project = this.find(projectId);
    return dao.delete(project);
  }

  @Override
  public boolean existProject(long projectID) {
    Project project = this.find(projectID);
    if (project == null) {
      return false;
    }
    return true;

  }

  @Override
  public Project find(long id) {
    return dao.find(Project.class, id);

  }

  @Override
  public List<Project> findAll() {
    String query = "from " + Project.class.getName();
    List<Project> list = dao.findAll(query);
    if (list.size() > 0) {
      return list;
    }
    return null;

  }

  @Override
  public List<Project> getProjectsByUserId(long userId) {
    String query = "from " + Project.class.getName() + " where user_id=" + userId;
    return dao.findAll(query);
  }

  @Override
  public long save(Project project) {
    if (project.getId() == null) {
      dao.save(project);
    } else {
      dao.update(project);
    }
    return project.getId();
  }


}