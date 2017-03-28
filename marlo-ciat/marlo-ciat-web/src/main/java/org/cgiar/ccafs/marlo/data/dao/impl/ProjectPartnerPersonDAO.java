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

import org.cgiar.ccafs.marlo.data.dao.IProjectPartnerPersonDAO;
import org.cgiar.ccafs.marlo.data.model.ProjectPartnerPerson;

import java.util.List;

import com.google.inject.Inject;

public class ProjectPartnerPersonDAO implements IProjectPartnerPersonDAO {

  private StandardDAO dao;

  @Inject
  public ProjectPartnerPersonDAO(StandardDAO dao) {
    this.dao = dao;
  }

  @Override
  public boolean deleteProjectPartnerPerson(long projectPartnerPersonId) {
    ProjectPartnerPerson projectPartnerPerson = this.find(projectPartnerPersonId);
    projectPartnerPerson.setActive(false);
    return this.save(projectPartnerPerson) > 0;
  }

  @Override
  public boolean existProjectPartnerPerson(long projectPartnerPersonID) {
    ProjectPartnerPerson projectPartnerPerson = this.find(projectPartnerPersonID);
    if (projectPartnerPerson == null) {
      return false;
    }
    return true;

  }

  @Override
  public ProjectPartnerPerson find(long id) {
    return dao.find(ProjectPartnerPerson.class, id);

  }

  @Override
  public List<ProjectPartnerPerson> findAll() {
    String query = "from " + ProjectPartnerPerson.class.getName();
    List<ProjectPartnerPerson> list = dao.findAll(query);
    if (list.size() > 0) {
      return list;
    }
    return null;

  }

  @Override
  public List<ProjectPartnerPerson> getProjectPartnerPersonsByUserId(long userId) {
    String query = "from " + ProjectPartnerPerson.class.getName() + " where user_id=" + userId;
    return dao.findAll(query);
  }

  @Override
  public long save(ProjectPartnerPerson projectPartnerPerson) {
    if (projectPartnerPerson.getId() == null) {
      dao.save(projectPartnerPerson);
    } else {
      dao.update(projectPartnerPerson);
    }
    return projectPartnerPerson.getId();
  }


}