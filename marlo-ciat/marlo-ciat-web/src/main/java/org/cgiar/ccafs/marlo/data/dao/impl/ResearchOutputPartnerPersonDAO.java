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

import org.cgiar.ccafs.marlo.data.dao.IResearchOutputPartnerPersonDAO;
import org.cgiar.ccafs.marlo.data.model.ResearchOutputPartnerPerson;

import java.util.List;

import com.google.inject.Inject;

public class ResearchOutputPartnerPersonDAO implements IResearchOutputPartnerPersonDAO {

  private StandardDAO dao;

  @Inject
  public ResearchOutputPartnerPersonDAO(StandardDAO dao) {
    this.dao = dao;
  }

  @Override
  public boolean deleteResearchOutputPartnerPerson(long researchOutputPartnerPersonId) {
    ResearchOutputPartnerPerson researchOutputPartnerPerson = this.find(researchOutputPartnerPersonId);
    researchOutputPartnerPerson.setActive(false);
    return this.save(researchOutputPartnerPerson) > 0;
  }

  @Override
  public boolean existResearchOutputPartnerPerson(long researchOutputPartnerPersonID) {
    ResearchOutputPartnerPerson researchOutputPartnerPerson = this.find(researchOutputPartnerPersonID);
    if (researchOutputPartnerPerson == null) {
      return false;
    }
    return true;

  }

  @Override
  public ResearchOutputPartnerPerson find(long id) {
    return dao.find(ResearchOutputPartnerPerson.class, id);

  }

  @Override
  public List<ResearchOutputPartnerPerson> findAll() {
    String query = "from " + ResearchOutputPartnerPerson.class.getName();
    List<ResearchOutputPartnerPerson> list = dao.findAll(query);
    if (list.size() > 0) {
      return list;
    }
    return null;

  }

  @Override
  public List<ResearchOutputPartnerPerson> getResearchOutputPartnerPersonsByUserId(long userId) {
    String query = "from " + ResearchOutputPartnerPerson.class.getName() + " where user_id=" + userId;
    return dao.findAll(query);
  }

  @Override
  public long save(ResearchOutputPartnerPerson researchOutputPartnerPerson) {
    if (researchOutputPartnerPerson.getId() == null) {
      dao.save(researchOutputPartnerPerson);
    } else {
      dao.update(researchOutputPartnerPerson);
    }
    return researchOutputPartnerPerson.getId();
  }


}