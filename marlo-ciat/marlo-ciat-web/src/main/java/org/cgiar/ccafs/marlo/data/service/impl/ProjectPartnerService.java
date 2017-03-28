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


import org.cgiar.ccafs.marlo.data.dao.IProjectPartnerDAO;
import org.cgiar.ccafs.marlo.data.model.ProjectPartner;
import org.cgiar.ccafs.marlo.data.service.IProjectPartnerService;

import java.util.List;

import com.google.inject.Inject;

/**
 * @author Christian Garcia
 */
public class ProjectPartnerService implements IProjectPartnerService {


  private IProjectPartnerDAO projectPartnerDAO;

  // Managers


  @Inject
  public ProjectPartnerService(IProjectPartnerDAO projectPartnerDAO) {
    this.projectPartnerDAO = projectPartnerDAO;


  }

  @Override
  public boolean deleteProjectPartner(long projectPartnerId) {

    return projectPartnerDAO.deleteProjectPartner(projectPartnerId);
  }

  @Override
  public boolean existProjectPartner(long projectPartnerID) {

    return projectPartnerDAO.existProjectPartner(projectPartnerID);
  }

  @Override
  public List<ProjectPartner> findAll() {

    return projectPartnerDAO.findAll();

  }

  @Override
  public ProjectPartner getProjectPartnerById(long projectPartnerID) {

    return projectPartnerDAO.find(projectPartnerID);
  }

  @Override
  public List<ProjectPartner> getProjectPartnersByUserId(Long userId) {
    return projectPartnerDAO.getProjectPartnersByUserId(userId);
  }

  @Override
  public long saveProjectPartner(ProjectPartner projectPartner) {

    return projectPartnerDAO.save(projectPartner);
  }


}
