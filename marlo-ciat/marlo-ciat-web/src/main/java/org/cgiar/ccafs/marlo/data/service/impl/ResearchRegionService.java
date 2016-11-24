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
package org.cgiar.ccafs.marlo.data.service.impl;


import org.cgiar.ccafs.marlo.data.dao.IResearchRegionDAO;
import org.cgiar.ccafs.marlo.data.model.ResearchRegion;
import org.cgiar.ccafs.marlo.data.service.IResearchRegionService;

import java.util.List;

import com.google.inject.Inject;

/**
 * @author Christian Garcia
 */
public class ResearchRegionService implements IResearchRegionService {


  private IResearchRegionDAO researchRegionDAO;

  // Managers


  @Inject
  public ResearchRegionService(IResearchRegionDAO researchRegionDAO) {
    this.researchRegionDAO = researchRegionDAO;


  }

  @Override
  public boolean deleteResearchRegion(long researchRegionId) {

    return researchRegionDAO.deleteResearchRegion(researchRegionId);
  }

  @Override
  public boolean existResearchRegion(long researchRegionID) {

    return researchRegionDAO.existResearchRegion(researchRegionID);
  }

  @Override
  public List<ResearchRegion> findAll() {

    return researchRegionDAO.findAll();

  }

  @Override
  public ResearchRegion getResearchRegionById(long researchRegionID) {

    return researchRegionDAO.find(researchRegionID);
  }

  @Override
  public List<ResearchRegion> getResearchRegionsByUserId(Long userId) {
    return researchRegionDAO.getResearchRegionsByUserId(userId);
  }

  @Override
  public long saveResearchRegion(ResearchRegion researchRegion) {

    return researchRegionDAO.save(researchRegion);
  }


}
