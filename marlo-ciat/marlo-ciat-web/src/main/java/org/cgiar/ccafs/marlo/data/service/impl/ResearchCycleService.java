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


import org.cgiar.ccafs.marlo.data.dao.IResearchCycleDAO;
import org.cgiar.ccafs.marlo.data.model.ResearchCycle;
import org.cgiar.ccafs.marlo.data.service.IResearchCycleService;

import java.util.List;

import com.google.inject.Inject;

/**
 * @author Christian Garcia
 */
public class ResearchCycleService implements IResearchCycleService {


  private IResearchCycleDAO researchCycleDAO;

  // Managers


  @Inject
  public ResearchCycleService(IResearchCycleDAO researchCycleDAO) {
    this.researchCycleDAO = researchCycleDAO;


  }

  @Override
  public boolean deleteResearchCycle(long researchCycleId) {

    return researchCycleDAO.deleteResearchCycle(researchCycleId);
  }

  @Override
  public boolean existResearchCycle(long researchCycleID) {

    return researchCycleDAO.existResearchCycle(researchCycleID);
  }

  @Override
  public List<ResearchCycle> findAll() {

    return researchCycleDAO.findAll();

  }

  @Override
  public ResearchCycle getResearchCycleById(long researchCycleID) {

    return researchCycleDAO.find(researchCycleID);
  }

  @Override
  public List<ResearchCycle> getResearchCyclesByUserId(Long userId) {
    return researchCycleDAO.getResearchCyclesByUserId(userId);
  }

  @Override
  public long saveResearchCycle(ResearchCycle researchCycle) {

    return researchCycleDAO.save(researchCycle);
  }


}
