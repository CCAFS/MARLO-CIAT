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

import org.cgiar.ccafs.marlo.data.dao.IProjectFundingSourceDAO;
import org.cgiar.ccafs.marlo.data.model.ProjectFundingSource;

import java.util.List;

import com.google.inject.Inject;

public class ProjectFundingSourceDAO implements IProjectFundingSourceDAO {

  private StandardDAO dao;

  @Inject
  public ProjectFundingSourceDAO(StandardDAO dao) {
    this.dao = dao;
  }

  @Override
  public boolean deleteProjectFundingSource(long projectFundingSourceId) {
    ProjectFundingSource projectFundingSource = this.find(projectFundingSourceId);
    projectFundingSource.setActive(false);
    return this.save(projectFundingSource) > 0;
  }

  @Override
  public boolean existProjectFundingSource(long projectFundingSourceID) {
    ProjectFundingSource projectFundingSource = this.find(projectFundingSourceID);
    if (projectFundingSource == null) {
      return false;
    }
    return true;

  }

  @Override
  public ProjectFundingSource find(long id) {
    return dao.find(ProjectFundingSource.class, id);

  }

  @Override
  public List<ProjectFundingSource> findAll() {
    String query = "from " + ProjectFundingSource.class.getName();
    List<ProjectFundingSource> list = dao.findAll(query);
    if (list.size() > 0) {
      return list;
    }
    return null;

  }

  @Override
  public List<ProjectFundingSource> getProjectFundingSourcesByUserId(long userId) {
    String query = "from " + ProjectFundingSource.class.getName() + " where user_id=" + userId;
    return dao.findAll(query);
  }

  @Override
  public long save(ProjectFundingSource projectFundingSource) {
    if (projectFundingSource.getId() == null) {
      dao.save(projectFundingSource);
    } else {
      dao.update(projectFundingSource);
    }
    return projectFundingSource.getId();
  }


}