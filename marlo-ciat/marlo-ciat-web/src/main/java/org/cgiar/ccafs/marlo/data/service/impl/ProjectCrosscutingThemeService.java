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


import org.cgiar.ccafs.marlo.data.dao.IProjectCrosscutingThemeDAO;
import org.cgiar.ccafs.marlo.data.model.ProjectCrosscutingTheme;
import org.cgiar.ccafs.marlo.data.service.IProjectCrosscutingThemeService;

import java.util.List;

import com.google.inject.Inject;

/**
 * @author Christian Garcia
 */
public class ProjectCrosscutingThemeService implements IProjectCrosscutingThemeService {


  private IProjectCrosscutingThemeDAO projectCrosscutingThemeDAO;

  // Managers


  @Inject
  public ProjectCrosscutingThemeService(IProjectCrosscutingThemeDAO projectCrosscutingThemeDAO) {
    this.projectCrosscutingThemeDAO = projectCrosscutingThemeDAO;


  }

  @Override
  public boolean deleteProjectCrosscutingTheme(long projectCrosscutingThemeId) {

    return projectCrosscutingThemeDAO.deleteProjectCrosscutingTheme(projectCrosscutingThemeId);
  }

  @Override
  public boolean existProjectCrosscutingTheme(long projectCrosscutingThemeID) {

    return projectCrosscutingThemeDAO.existProjectCrosscutingTheme(projectCrosscutingThemeID);
  }

  @Override
  public List<ProjectCrosscutingTheme> findAll() {

    return projectCrosscutingThemeDAO.findAll();

  }

  @Override
  public ProjectCrosscutingTheme getProjectCrosscutingThemeById(long projectCrosscutingThemeID) {

    return projectCrosscutingThemeDAO.find(projectCrosscutingThemeID);
  }

  @Override
  public List<ProjectCrosscutingTheme> getProjectCrosscutingThemesByUserId(Long userId) {
    return projectCrosscutingThemeDAO.getProjectCrosscutingThemesByUserId(userId);
  }

  @Override
  public long saveProjectCrosscutingTheme(ProjectCrosscutingTheme projectCrosscutingTheme) {

    return projectCrosscutingThemeDAO.save(projectCrosscutingTheme);
  }


}
