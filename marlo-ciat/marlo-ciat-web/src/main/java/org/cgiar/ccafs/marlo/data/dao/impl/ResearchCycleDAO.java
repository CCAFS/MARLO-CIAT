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

import org.cgiar.ccafs.marlo.data.dao.IResearchCycleDAO;
import org.cgiar.ccafs.marlo.data.model.ResearchCycle;

import java.util.List;

import com.google.inject.Inject;

public class ResearchCycleDAO implements IResearchCycleDAO {

  private StandardDAO dao;

  @Inject
  public ResearchCycleDAO(StandardDAO dao) {
    this.dao = dao;
  }

  @Override
  public boolean deleteResearchCycle(long researchCycleId) {
    ResearchCycle researchCycle = this.find(researchCycleId);
    return dao.delete(researchCycle);
  }

  @Override
  public boolean existResearchCycle(long researchCycleID) {
    ResearchCycle researchCycle = this.find(researchCycleID);
    if (researchCycle == null) {
      return false;
    }
    return true;

  }

  @Override
  public ResearchCycle find(long id) {
    return dao.find(ResearchCycle.class, id);

  }

  @Override
  public List<ResearchCycle> findAll() {
    String query = "from " + ResearchCycle.class.getName();
    List<ResearchCycle> list = dao.findAll(query);
    if (list.size() > 0) {
      return list;
    }
    return null;

  }

  @Override
  public List<ResearchCycle> getResearchCyclesByUserId(long userId) {
    String query = "from " + ResearchCycle.class.getName() + " where user_id=" + userId;
    return dao.findAll(query);
  }

  @Override
  public long save(ResearchCycle researchCycle) {
    if (researchCycle.getId() == null) {
      dao.save(researchCycle);
    } else {
      dao.update(researchCycle);
    }
    return researchCycle.getId();
  }


}