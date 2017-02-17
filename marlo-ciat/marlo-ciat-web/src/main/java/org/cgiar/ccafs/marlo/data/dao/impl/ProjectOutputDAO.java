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

import org.cgiar.ccafs.marlo.data.dao.IProjectOutputDAO;
import org.cgiar.ccafs.marlo.data.model.ProjectOutput;

import java.util.List;

import com.google.inject.Inject;

public class ProjectOutputDAO implements IProjectOutputDAO {

  private StandardDAO dao;

  @Inject
  public ProjectOutputDAO(StandardDAO dao) {
    this.dao = dao;
  }

  @Override
  public boolean deleteProjectOutput(long projectOutputId) {
    ProjectOutput projectOutput = this.find(projectOutputId);
    return dao.delete(projectOutput);
  }

  @Override
  public boolean existProjectOutput(long projectOutputID) {
    ProjectOutput projectOutput = this.find(projectOutputID);
    if (projectOutput == null) {
      return false;
    }
    return true;

  }

  @Override
  public ProjectOutput find(long id) {
    return dao.find(ProjectOutput.class, id);

  }

  @Override
  public List<ProjectOutput> findAll() {
    String query = "from " + ProjectOutput.class.getName();
    List<ProjectOutput> list = dao.findAll(query);
    if (list.size() > 0) {
      return list;
    }
    return null;

  }

  @Override
  public List<ProjectOutput> getProjectOutputsByUserId(long userId) {
    String query = "from " + ProjectOutput.class.getName() + " where user_id=" + userId;
    return dao.findAll(query);
  }

  @Override
  public long save(ProjectOutput projectOutput) {
    if (projectOutput.getId() == null) {
      dao.save(projectOutput);
    } else {
      dao.update(projectOutput);
    }
    return projectOutput.getId();
  }


}