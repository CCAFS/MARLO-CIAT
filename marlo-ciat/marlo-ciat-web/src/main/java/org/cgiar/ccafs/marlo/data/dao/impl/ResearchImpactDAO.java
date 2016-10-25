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

import org.cgiar.ccafs.marlo.data.dao.IResearchImpactDAO;
import org.cgiar.ccafs.marlo.data.model.ResearchImpact;

import java.util.List;

import com.google.inject.Inject;

public class ResearchImpactDAO implements IResearchImpactDAO {

  private StandardDAO dao;

  @Inject
  public ResearchImpactDAO(StandardDAO dao) {
    this.dao = dao;
  }

  @Override
  public boolean deleteResearchImpact(long researchImpactId) {
    ResearchImpact researchImpact = this.find(researchImpactId);
    return dao.delete(researchImpact);
  }

  @Override
  public boolean existResearchImpact(long researchImpactID) {
    ResearchImpact researchImpact = this.find(researchImpactID);
    if (researchImpact == null) {
      return false;
    }
    return true;

  }

  @Override
  public ResearchImpact find(long id) {
    return dao.find(ResearchImpact.class, id);

  }

  @Override
  public List<ResearchImpact> findAll() {
    String query = "from " + ResearchImpact.class.getName();
    List<ResearchImpact> list = dao.findAll(query);
    if (list.size() > 0) {
      return list;
    }
    return null;

  }

  @Override
  public List<ResearchImpact> getResearchImpactsByUserId(long userId) {
    String query = "from " + ResearchImpact.class.getName() + " where user_id=" + userId;
    return dao.findAll(query);
  }

  @Override
  public long save(ResearchImpact researchImpact) {
    if (researchImpact.getId() == null) {
      dao.save(researchImpact);
    } else {
      dao.update(researchImpact);
    }
    return researchImpact.getId();
  }


}