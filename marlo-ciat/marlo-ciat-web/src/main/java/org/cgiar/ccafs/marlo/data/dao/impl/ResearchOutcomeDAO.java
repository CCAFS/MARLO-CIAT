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

import org.cgiar.ccafs.marlo.data.dao.IResearchOutcomeDAO;
import org.cgiar.ccafs.marlo.data.model.ResearchOutcome;

import java.util.List;

import com.google.inject.Inject;

public class ResearchOutcomeDAO implements IResearchOutcomeDAO {

  private StandardDAO dao;

  @Inject
  public ResearchOutcomeDAO(StandardDAO dao) {
    this.dao = dao;
  }

  @Override
  public boolean deleteResearchOutcome(long researchOutcomeId) {
    ResearchOutcome researchOutcome = this.find(researchOutcomeId);
    return dao.delete(researchOutcome);
  }

  @Override
  public boolean existResearchOutcome(long researchOutcomeID) {
    ResearchOutcome researchOutcome = this.find(researchOutcomeID);
    if (researchOutcome == null) {
      return false;
    }
    return true;

  }

  @Override
  public ResearchOutcome find(long id) {
    return dao.find(ResearchOutcome.class, id);

  }

  @Override
  public List<ResearchOutcome> findAll() {
    String query = "from " + ResearchOutcome.class.getName();
    List<ResearchOutcome> list = dao.findAll(query);
    if (list.size() > 0) {
      return list;
    }
    return null;

  }

  @Override
  public List<ResearchOutcome> getResearchOutcomesByUserId(long userId) {
    String query = "from " + ResearchOutcome.class.getName() + " where user_id=" + userId;
    return dao.findAll(query);
  }

  @Override
  public long save(ResearchOutcome researchOutcome) {
    if (researchOutcome.getId() == null) {
      dao.save(researchOutcome);
    } else {
      dao.update(researchOutcome);
    }
    return researchOutcome.getId();
  }


}