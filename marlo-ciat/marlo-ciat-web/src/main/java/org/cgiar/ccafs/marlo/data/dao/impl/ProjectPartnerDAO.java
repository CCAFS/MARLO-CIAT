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

import org.cgiar.ccafs.marlo.data.dao.IProjectPartnerDAO;
import org.cgiar.ccafs.marlo.data.model.ProjectPartner;

import java.util.List;

import com.google.inject.Inject;

public class ProjectPartnerDAO implements IProjectPartnerDAO {

  private StandardDAO dao;

  @Inject
  public ProjectPartnerDAO(StandardDAO dao) {
    this.dao = dao;
  }

  @Override
  public boolean deleteProjectPartner(long projectPartnerId) {
    ProjectPartner projectPartner = this.find(projectPartnerId);
    projectPartner.setActive(false);
    return this.save(projectPartner) > 0;
  }

  @Override
  public boolean existProjectPartner(long projectPartnerID) {
    ProjectPartner projectPartner = this.find(projectPartnerID);
    if (projectPartner == null) {
      return false;
    }
    return true;

  }

  @Override
  public ProjectPartner find(long id) {
    return dao.find(ProjectPartner.class, id);

  }

  @Override
  public List<ProjectPartner> findAll() {
    String query = "from " + ProjectPartner.class.getName();
    List<ProjectPartner> list = dao.findAll(query);
    if (list.size() > 0) {
      return list;
    }
    return null;

  }

  @Override
  public List<ProjectPartner> getProjectPartnersByUserId(long userId) {
    String query = "from " + ProjectPartner.class.getName() + " where user_id=" + userId;
    return dao.findAll(query);
  }

  @Override
  public long save(ProjectPartner projectPartner) {
    if (projectPartner.getId() == null) {
      dao.save(projectPartner);
    } else {
      dao.update(projectPartner);
    }
    return projectPartner.getId();
  }


}