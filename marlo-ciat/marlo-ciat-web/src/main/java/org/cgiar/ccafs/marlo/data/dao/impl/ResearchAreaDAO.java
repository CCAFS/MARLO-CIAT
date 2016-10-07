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

import org.cgiar.ccafs.marlo.data.dao.IResearchAreaDAO;
import org.cgiar.ccafs.marlo.data.model.ResearchArea;

import java.util.List;

import com.google.inject.Inject;


/**
 * Modified by @author nmatovu last on Oct 7, 2016
 */
public class ResearchAreaDAO implements IResearchAreaDAO {

  private StandardDAO dao;

  @Inject
  public ResearchAreaDAO(StandardDAO dao) {
    this.dao = dao;
  }

  /*
   * (non-Javadoc)
   * @see org.cgiar.ccafs.marlo.data.dao.IResearchAreaDAO#deleteResearchArea(long)
   */
  @Override
  public boolean deleteResearchArea(long researchAreaId) {
    ResearchArea researchArea = this.find(researchAreaId);
    researchArea.setActive(false);
    return this.save(researchArea) > 0;
  }

  /*
   * (non-Javadoc)
   * @see org.cgiar.ccafs.marlo.data.dao.IResearchAreaDAO#existResearchArea(long)
   */
  @Override
  public boolean existResearchArea(long researchAreaId) {
    ResearchArea researchArea = this.find(researchAreaId);
    if (researchArea == null) {
      return false;
    }
    return true;

  }

  /*
   * (non-Javadoc)
   * @see org.cgiar.ccafs.marlo.data.dao.IResearchAreaDAO#find(long)
   */
  @Override
  public ResearchArea find(long researchId) {
    return dao.find(ResearchArea.class, researchId);
  }

  /*
   * (non-Javadoc)
   * @see org.cgiar.ccafs.marlo.data.dao.IResearchAreaDAO#findAll()
   */
  @Override
  public List<ResearchArea> findAll() {
    String query = "from " + ResearchArea.class.getName() + " where is_active=1";
    List<ResearchArea> list = dao.findAll(query);
    if (list.size() > 0) {
      return list;
    }
    return null;
  }

  /*
   * (non-Javadoc)
   * @see org.cgiar.ccafs.marlo.data.dao.IResearchAreaDAO#findResearchAreaByAcronym(java.lang.String)
   */
  @Override
  public ResearchArea findResearchAreaByAcronym(String acronym) {
    String query = "from " + ResearchArea.class.getName() + " where acronym='" + acronym + "'";
    List<ResearchArea> list = dao.findAll(query);
    if (list.size() > 0) {
      return list.get(0);
    }
    return null;
  }

  /*
   * (non-Javadoc)
   * @see org.cgiar.ccafs.marlo.data.dao.IResearchAreaDAO#save(org.cgiar.ccafs.marlo.data.model.ResearchArea)
   */
  @Override
  public long save(ResearchArea researchArea) {
    if (researchArea.getId() == null) {
      dao.save(researchArea);
    } else {
      dao.update(researchArea);
    }
    return (long) researchArea.getId();
  }

}
