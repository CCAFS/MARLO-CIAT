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

import org.cgiar.ccafs.marlo.data.dao.IResearchObjectiveDAO;
import org.cgiar.ccafs.marlo.data.model.ResearchObjective;

import java.util.List;

import com.google.inject.Inject;

public class ResearchObjectiveDAO implements IResearchObjectiveDAO {

  private StandardDAO dao;

  @Inject
  public ResearchObjectiveDAO(StandardDAO dao) {
    this.dao = dao;
  }

  @Override
  public boolean deleteResearchObjective(long researchObjectiveId) {
    ResearchObjective researchObjective = this.find(researchObjectiveId);
    researchObjective.setActive(false);
    return this.save(researchObjective) > 0;
  }

  @Override
  public boolean existResearchObjective(long researchObjectiveID) {
    ResearchObjective researchObjective = this.find(researchObjectiveID);
    if (researchObjective == null) {
      return false;
    }
    return true;

  }

  @Override
  public ResearchObjective find(long id) {
    return dao.find(ResearchObjective.class, id);

  }

  @Override
  public List<ResearchObjective> findAll() {
    String query = "from " + ResearchObjective.class.getName();
    List<ResearchObjective> list = dao.findAll(query);
    if (list.size() > 0) {
      return list;
    }
    return null;

  }

  @Override
  public List<ResearchObjective> getResearchObjectivesByUserId(long userId) {
    String query = "from " + ResearchObjective.class.getName() + " where user_id=" + userId;
    return dao.findAll(query);
  }

  @Override
  public long save(ResearchObjective researchObjective) {
    if (researchObjective.getId() == null) {
      dao.save(researchObjective);
    } else {
      dao.update(researchObjective);
    }
    return researchObjective.getId();
  }


}