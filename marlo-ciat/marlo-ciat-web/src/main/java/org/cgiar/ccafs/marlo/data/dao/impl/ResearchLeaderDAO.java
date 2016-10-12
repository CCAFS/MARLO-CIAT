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

/**
 * 
 */
package org.cgiar.ccafs.marlo.data.dao.impl;

import org.cgiar.ccafs.marlo.data.dao.IResearchLeaderDAO;
import org.cgiar.ccafs.marlo.data.model.ResearchLeader;

import java.util.List;

import com.google.inject.Inject;


/**
 * Modified by @author nmatovu last on Oct 10, 2016
 */
public class ResearchLeaderDAO implements IResearchLeaderDAO {

  private StandardDAO dao;

  @Inject
  public ResearchLeaderDAO(StandardDAO dao) {
    this.dao = dao;
  }


  @Override
  public boolean deleteResearchLeader(long researchLeaderId) {
    ResearchLeader researchLeader = this.find(researchLeaderId);
    // researchLeader.setActive(false);
    return this.save(researchLeader) > 0;
  }


  @Override
  public boolean existResearchLeader(long researchLeaderId) {
    ResearchLeader researchLeader = this.find(researchLeaderId);
    if (researchLeader == null) {
      return false;
    }
    return true;
  }


  @Override
  public ResearchLeader find(long researchLeaderId) {
    return dao.find(ResearchLeader.class, researchLeaderId);
  }

  @Override
  public List<ResearchLeader> findAll() {
    String query = "from " + ResearchLeader.class.getName() + " where is_active=1";
    List<ResearchLeader> list = dao.findAll(query);
    if (list.size() > 0) {
      return list;
    }
    return null;
  }

  @Override
  public long save(ResearchLeader researchLeader) {
    if (researchLeader.getId() == null) {
      dao.save(researchLeader);
    } else {
      dao.update(researchLeader);
    }
    return researchLeader.getId();
  }

}
