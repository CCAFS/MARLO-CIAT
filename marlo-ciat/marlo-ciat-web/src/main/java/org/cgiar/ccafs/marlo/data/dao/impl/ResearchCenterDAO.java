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

import org.cgiar.ccafs.marlo.data.dao.IResearchCenterDAO;
import org.cgiar.ccafs.marlo.data.model.ResearchCenter;

import java.util.List;

import com.google.inject.Inject;

/**
 * @author Hermes Jiménez - CIAT/CCAFS
 * @author Christian Garcia - CIAT/CCAFS
 */
public class ResearchCenterDAO implements IResearchCenterDAO {

  private StandardDAO dao;

  @Inject
  public ResearchCenterDAO(StandardDAO dao) {
    this.dao = dao;
  }

  @Override
  public boolean deleteCrp(long crpId) {
    ResearchCenter crp = this.find(crpId);
    return this.save(crp) > 0;
  }

  @Override
  public boolean existCrp(long crpID) {
    ResearchCenter crp = this.find(crpID);
    if (crp == null) {
      return false;
    }
    return true;

  }

  @Override
  public ResearchCenter find(long id) {
    return dao.find(ResearchCenter.class, id);

  }

  @Override
  public List<ResearchCenter> findAll() {
    String query = "from " + ResearchCenter.class.getName() + " where is_active=1";
    List<ResearchCenter> list = dao.findAll(query);
    if (list.size() > 0) {
      return list;
    }
    return null;

  }

  @Override
  public ResearchCenter findCrpByAcronym(String acronym) {
    String query = "from " + ResearchCenter.class.getName() + " where acronym='" + acronym + "'";
    List<ResearchCenter> list = dao.findAll(query);
    if (list.size() > 0) {
      return list.get(0);
    }
    return null;
  }

  @Override
  public long save(ResearchCenter crp) {
    if (crp.getId() == null) {
      dao.save(crp);
    } else {
      dao.update(crp);
    }
    return crp.getId();
  }
}
