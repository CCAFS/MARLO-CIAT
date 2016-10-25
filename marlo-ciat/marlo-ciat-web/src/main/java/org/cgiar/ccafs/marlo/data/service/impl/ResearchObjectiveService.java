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


import org.cgiar.ccafs.marlo.data.dao.IResearchObjectiveDAO;
import org.cgiar.ccafs.marlo.data.model.ResearchObjective;
import org.cgiar.ccafs.marlo.data.service.IResearchObjectiveService;

import java.util.List;

import com.google.inject.Inject;

/**
 * @author Christian Garcia
 */
public class ResearchObjectiveService implements IResearchObjectiveService {


  private IResearchObjectiveDAO researchObjectiveDAO;

  // Managers


  @Inject
  public ResearchObjectiveService(IResearchObjectiveDAO researchObjectiveDAO) {
    this.researchObjectiveDAO = researchObjectiveDAO;


  }

  @Override
  public boolean deleteResearchObjective(long researchObjectiveId) {

    return researchObjectiveDAO.deleteResearchObjective(researchObjectiveId);
  }

  @Override
  public boolean existResearchObjective(long researchObjectiveID) {

    return researchObjectiveDAO.existResearchObjective(researchObjectiveID);
  }

  @Override
  public List<ResearchObjective> findAll() {

    return researchObjectiveDAO.findAll();

  }

  @Override
  public ResearchObjective getResearchObjectiveById(long researchObjectiveID) {

    return researchObjectiveDAO.find(researchObjectiveID);
  }

  @Override
  public List<ResearchObjective> getResearchObjectivesByUserId(Long userId) {
    return researchObjectiveDAO.getResearchObjectivesByUserId(userId);
  }

  @Override
  public long saveResearchObjective(ResearchObjective researchObjective) {

    return researchObjectiveDAO.save(researchObjective);
  }


}
