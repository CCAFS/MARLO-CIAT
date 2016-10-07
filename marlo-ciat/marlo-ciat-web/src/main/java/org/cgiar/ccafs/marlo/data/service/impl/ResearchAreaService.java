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
package org.cgiar.ccafs.marlo.data.service.impl;

import org.cgiar.ccafs.marlo.data.dao.IResearchAreaDAO;
import org.cgiar.ccafs.marlo.data.model.ResearchArea;
import org.cgiar.ccafs.marlo.data.service.IResearchAreaService;

import java.util.List;

import com.google.inject.Inject;


/**
 * Modified by @author nmatovu last on Oct 7, 2016
 */
public class ResearchAreaService implements IResearchAreaService {

  private IResearchAreaDAO researchAreaDao;

  @Inject
  public ResearchAreaService(IResearchAreaDAO researchAreaDAO) {
    this.researchAreaDao = researchAreaDAO;
  }

  /*
   * (non-Javadoc)
   * @see org.cgiar.ccafs.marlo.data.service.IResearchAreaService#deleteResearchArea(long)
   */
  @Override
  public boolean deleteResearchArea(long researchAreaId) {
    return researchAreaDao.deleteResearchArea(researchAreaId);
  }

  /*
   * (non-Javadoc)
   * @see org.cgiar.ccafs.marlo.data.service.IResearchAreaService#existResearchArea(long)
   */
  @Override
  public boolean existResearchArea(long researchAreaId) {

    return researchAreaDao.existResearchArea(researchAreaId);
  }

  /*
   * (non-Javadoc)
   * @see org.cgiar.ccafs.marlo.data.service.IResearchAreaService#find(long)
   */
  @Override
  public ResearchArea find(long researchId) {

    return researchAreaDao.find(researchId);
  }

  /*
   * (non-Javadoc)
   * @see org.cgiar.ccafs.marlo.data.service.IResearchAreaService#findAll()
   */
  @Override
  public List<ResearchArea> findAll() {

    return researchAreaDao.findAll();
  }

  /*
   * (non-Javadoc)
   * @see org.cgiar.ccafs.marlo.data.service.IResearchAreaService#findResearchAreaByAcronym(java.lang.String)
   */
  @Override
  public ResearchArea findResearchAreaByAcronym(String acronym) {

    return researchAreaDao.findResearchAreaByAcronym(acronym);
  }

  /*
   * (non-Javadoc)
   * @see org.cgiar.ccafs.marlo.data.service.IResearchAreaService#save(org.cgiar.ccafs.marlo.data.model.ResearchArea)
   */
  @Override
  public long save(ResearchArea researchArea) {

    return researchAreaDao.save(researchArea);
  }

}
