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

import org.cgiar.ccafs.marlo.data.dao.IProjectCrosscutingThemeDAO;
import org.cgiar.ccafs.marlo.data.model.ProjectCrosscutingTheme;

import java.util.List;

import com.google.inject.Inject;

public class ProjectCrosscutingThemeDAO implements IProjectCrosscutingThemeDAO {

  private StandardDAO dao;

  @Inject
  public ProjectCrosscutingThemeDAO(StandardDAO dao) {
    this.dao = dao;
  }

  @Override
  public boolean deleteProjectCrosscutingTheme(long projectCrosscutingThemeId) {
    ProjectCrosscutingTheme projectCrosscutingTheme = this.find(projectCrosscutingThemeId);
    projectCrosscutingTheme.setActive(false);
    return this.save(projectCrosscutingTheme) > 0;
  }

  @Override
  public boolean existProjectCrosscutingTheme(long projectCrosscutingThemeID) {
    ProjectCrosscutingTheme projectCrosscutingTheme = this.find(projectCrosscutingThemeID);
    if (projectCrosscutingTheme == null) {
      return false;
    }
    return true;

  }

  @Override
  public ProjectCrosscutingTheme find(long id) {
    return dao.find(ProjectCrosscutingTheme.class, id);

  }

  @Override
  public List<ProjectCrosscutingTheme> findAll() {
    String query = "from " + ProjectCrosscutingTheme.class.getName();
    List<ProjectCrosscutingTheme> list = dao.findAll(query);
    if (list.size() > 0) {
      return list;
    }
    return null;

  }

  @Override
  public List<ProjectCrosscutingTheme> getProjectCrosscutingThemesByUserId(long userId) {
    String query = "from " + ProjectCrosscutingTheme.class.getName() + " where user_id=" + userId;
    return dao.findAll(query);
  }

  @Override
  public long save(ProjectCrosscutingTheme projectCrosscutingTheme) {
    if (projectCrosscutingTheme.getId() == null) {
      dao.save(projectCrosscutingTheme);
    } else {
      dao.update(projectCrosscutingTheme);
    }
    return projectCrosscutingTheme.getId();
  }


}