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

import org.cgiar.ccafs.marlo.data.dao.IProjectLocationDAO;
import org.cgiar.ccafs.marlo.data.model.ProjectLocation;

import java.util.List;

import com.google.inject.Inject;

public class ProjectLocationDAO implements IProjectLocationDAO {

  private StandardDAO dao;

  @Inject
  public ProjectLocationDAO(StandardDAO dao) {
    this.dao = dao;
  }

  @Override
  public boolean deleteProjectLocation(long projectLocationId) {
    ProjectLocation projectLocation = this.find(projectLocationId);
    projectLocation.setActive(false);
    return this.save(projectLocation) > 0;
  }

  @Override
  public boolean existProjectLocation(long projectLocationID) {
    ProjectLocation projectLocation = this.find(projectLocationID);
    if (projectLocation == null) {
      return false;
    }
    return true;

  }

  @Override
  public ProjectLocation find(long id) {
    return dao.find(ProjectLocation.class, id);

  }

  @Override
  public List<ProjectLocation> findAll() {
    String query = "from " + ProjectLocation.class.getName();
    List<ProjectLocation> list = dao.findAll(query);
    if (list.size() > 0) {
      return list;
    }
    return null;

  }

  @Override
  public List<ProjectLocation> getProjectLocationsByUserId(long userId) {
    String query = "from " + ProjectLocation.class.getName() + " where user_id=" + userId;
    return dao.findAll(query);
  }

  @Override
  public long save(ProjectLocation projectLocation) {
    if (projectLocation.getId() == null) {
      dao.save(projectLocation);
    } else {
      dao.update(projectLocation);
    }
    return projectLocation.getId();
  }

  @Override
  public long save(ProjectLocation projectLocation, String actionName, List<String> relationsName) {
    if (projectLocation.getId() == null) {
      dao.save(projectLocation, actionName, relationsName);
    } else {
      dao.update(projectLocation, actionName, relationsName);
    }
    return projectLocation.getId();
  }


}