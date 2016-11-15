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

import org.cgiar.ccafs.marlo.data.dao.IResearchMilestoneDAO;
import org.cgiar.ccafs.marlo.data.model.ResearchMilestone;

import java.util.List;

import com.google.inject.Inject;

public class ResearchMilestoneDAO implements IResearchMilestoneDAO {

  private StandardDAO dao;

  @Inject
  public ResearchMilestoneDAO(StandardDAO dao) {
    this.dao = dao;
  }

  @Override
  public boolean deleteResearchMilestone(long researchMilestoneId) {
    ResearchMilestone researchMilestone = this.find(researchMilestoneId);
    researchMilestone.setActive(false);
    return this.save(researchMilestone) > 0;
  }

  @Override
  public boolean existResearchMilestone(long researchMilestoneID) {
    ResearchMilestone researchMilestone = this.find(researchMilestoneID);
    if (researchMilestone == null) {
      return false;
    }
    return true;

  }

  @Override
  public ResearchMilestone find(long id) {
    return dao.find(ResearchMilestone.class, id);

  }

  @Override
  public List<ResearchMilestone> findAll() {
    String query = "from " + ResearchMilestone.class.getName();
    List<ResearchMilestone> list = dao.findAll(query);
    if (list.size() > 0) {
      return list;
    }
    return null;

  }

  @Override
  public List<ResearchMilestone> getResearchMilestonesByUserId(long userId) {
    String query = "from " + ResearchMilestone.class.getName() + " where user_id=" + userId;
    return dao.findAll(query);
  }

  @Override
  public long save(ResearchMilestone researchMilestone) {
    if (researchMilestone.getId() == null) {
      dao.save(researchMilestone);
    } else {
      dao.update(researchMilestone);
    }
    return researchMilestone.getId();
  }


}