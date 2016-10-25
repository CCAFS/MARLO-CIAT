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

import org.cgiar.ccafs.marlo.data.dao.IResearchImpactObjectiveDAO;
import org.cgiar.ccafs.marlo.data.model.ResearchImpactObjective;

import java.util.List;

import com.google.inject.Inject;

public class ResearchImpactObjectiveDAO implements IResearchImpactObjectiveDAO {

  private StandardDAO dao;

  @Inject
  public ResearchImpactObjectiveDAO(StandardDAO dao) {
    this.dao = dao;
  }

  @Override
  public boolean deleteResearchImpactObjective(long researchImpactObjectiveId) {
    ResearchImpactObjective researchImpactObjective = this.find(researchImpactObjectiveId);
    researchImpactObjective.setActive(false);
    return this.save(researchImpactObjective) > 0;
  }

  @Override
  public boolean existResearchImpactObjective(long researchImpactObjectiveID) {
    ResearchImpactObjective researchImpactObjective = this.find(researchImpactObjectiveID);
    if (researchImpactObjective == null) {
      return false;
    }
    return true;

  }

  @Override
  public ResearchImpactObjective find(long id) {
    return dao.find(ResearchImpactObjective.class, id);

  }

  @Override
  public List<ResearchImpactObjective> findAll() {
    String query = "from " + ResearchImpactObjective.class.getName();
    List<ResearchImpactObjective> list = dao.findAll(query);
    if (list.size() > 0) {
      return list;
    }
    return null;

  }

  @Override
  public List<ResearchImpactObjective> getResearchImpactObjectivesByUserId(long userId) {
    String query = "from " + ResearchImpactObjective.class.getName() + " where user_id=" + userId;
    return dao.findAll(query);
  }

  @Override
  public long save(ResearchImpactObjective researchImpactObjective) {
    if (researchImpactObjective.getId() == null) {
      dao.save(researchImpactObjective);
    } else {
      dao.update(researchImpactObjective);
    }
    return researchImpactObjective.getId();
  }


}