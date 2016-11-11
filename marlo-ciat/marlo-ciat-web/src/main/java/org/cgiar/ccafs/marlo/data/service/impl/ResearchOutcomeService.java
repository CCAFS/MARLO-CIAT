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


import org.cgiar.ccafs.marlo.data.dao.IResearchOutcomeDAO;
import org.cgiar.ccafs.marlo.data.model.ResearchOutcome;
import org.cgiar.ccafs.marlo.data.service.IResearchOutcomeService;

import java.util.List;

import com.google.inject.Inject;

/**
 * @author Christian Garcia
 */
public class ResearchOutcomeService implements IResearchOutcomeService {


  private IResearchOutcomeDAO researchOutcomeDAO;

  // Managers


  @Inject
  public ResearchOutcomeService(IResearchOutcomeDAO researchOutcomeDAO) {
    this.researchOutcomeDAO = researchOutcomeDAO;


  }

  @Override
  public boolean deleteResearchOutcome(long researchOutcomeId) {

    return researchOutcomeDAO.deleteResearchOutcome(researchOutcomeId);
  }

  @Override
  public boolean existResearchOutcome(long researchOutcomeID) {

    return researchOutcomeDAO.existResearchOutcome(researchOutcomeID);
  }

  @Override
  public List<ResearchOutcome> findAll() {

    return researchOutcomeDAO.findAll();

  }

  @Override
  public ResearchOutcome getResearchOutcomeById(long researchOutcomeID) {

    return researchOutcomeDAO.find(researchOutcomeID);
  }

  @Override
  public List<ResearchOutcome> getResearchOutcomesByUserId(Long userId) {
    return researchOutcomeDAO.getResearchOutcomesByUserId(userId);
  }

  @Override
  public long saveResearchOutcome(ResearchOutcome researchOutcome) {

    return researchOutcomeDAO.save(researchOutcome);
  }

  @Override
  public long saveResearchOutcome(ResearchOutcome outcome, String actionName, List<String> relationsName) {
    return researchOutcomeDAO.save(outcome, actionName, relationsName);
  }


}
