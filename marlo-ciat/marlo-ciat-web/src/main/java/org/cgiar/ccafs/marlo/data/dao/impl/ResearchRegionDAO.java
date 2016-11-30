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

import org.cgiar.ccafs.marlo.data.dao.IResearchRegionDAO;
import org.cgiar.ccafs.marlo.data.model.ResearchRegion;

import java.util.List;

import com.google.inject.Inject;

public class ResearchRegionDAO implements IResearchRegionDAO {

  private StandardDAO dao;

  @Inject
  public ResearchRegionDAO(StandardDAO dao) {
    this.dao = dao;
  }

  @Override
  public boolean deleteResearchRegion(long researchRegionId) {
    ResearchRegion researchRegion = this.find(researchRegionId);
    researchRegion.setActive(false);
    return this.save(researchRegion) > 0;
  }

  @Override
  public boolean existResearchRegion(long researchRegionID) {
    ResearchRegion researchRegion = this.find(researchRegionID);
    if (researchRegion == null) {
      return false;
    }
    return true;

  }

  @Override
  public ResearchRegion find(long id) {
    return dao.find(ResearchRegion.class, id);

  }

  @Override
  public List<ResearchRegion> findAll() {
    String query = "from " + ResearchRegion.class.getName();
    List<ResearchRegion> list = dao.findAll(query);
    if (list.size() > 0) {
      return list;
    }
    return null;

  }

  @Override
  public List<ResearchRegion> getResearchRegionsByUserId(long userId) {
    String query = "from " + ResearchRegion.class.getName() + " where user_id=" + userId;
    return dao.findAll(query);
  }

  @Override
  public long save(ResearchRegion researchRegion) {
    if (researchRegion.getId() == null) {
      dao.save(researchRegion);
    } else {
      dao.update(researchRegion);
    }
    return researchRegion.getId();
  }


}