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

import org.cgiar.ccafs.marlo.data.dao.IResearchOutputPartnerDAO;
import org.cgiar.ccafs.marlo.data.model.ResearchOutputPartner;

import java.util.List;

import com.google.inject.Inject;

public class ResearchOutputPartnerDAO implements IResearchOutputPartnerDAO {

  private StandardDAO dao;

  @Inject
  public ResearchOutputPartnerDAO(StandardDAO dao) {
    this.dao = dao;
  }

  @Override
  public boolean deleteResearchOutputPartner(long researchOutputPartnerId) {
    ResearchOutputPartner researchOutputPartner = this.find(researchOutputPartnerId);
    return dao.delete(researchOutputPartner);
  }

  @Override
  public boolean existResearchOutputPartner(long researchOutputPartnerID) {
    ResearchOutputPartner researchOutputPartner = this.find(researchOutputPartnerID);
    if (researchOutputPartner == null) {
      return false;
    }
    return true;

  }

  @Override
  public ResearchOutputPartner find(long id) {
    return dao.find(ResearchOutputPartner.class, id);

  }

  @Override
  public List<ResearchOutputPartner> findAll() {
    String query = "from " + ResearchOutputPartner.class.getName();
    List<ResearchOutputPartner> list = dao.findAll(query);
    if (list.size() > 0) {
      return list;
    }
    return null;

  }

  @Override
  public List<ResearchOutputPartner> getResearchOutputPartnersByUserId(long userId) {
    String query = "from " + ResearchOutputPartner.class.getName() + " where user_id=" + userId;
    return dao.findAll(query);
  }

  @Override
  public long save(ResearchOutputPartner researchOutputPartner) {
    if (researchOutputPartner.getId() == null) {
      dao.save(researchOutputPartner);
    } else {
      dao.update(researchOutputPartner);
    }
    return researchOutputPartner.getId();
  }


}