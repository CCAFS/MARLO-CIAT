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


import org.cgiar.ccafs.marlo.data.dao.IResearchImpactStatementDAO;
import org.cgiar.ccafs.marlo.data.model.ResearchImpactStatement;
import org.cgiar.ccafs.marlo.data.service.IResearchImpactStatementService;

import java.util.List;

import com.google.inject.Inject;

/**
 * @author Christian Garcia
 */
public class ResearchImpactStatementService implements IResearchImpactStatementService {


  private IResearchImpactStatementDAO researchImpactStatementDAO;

  // Managers


  @Inject
  public ResearchImpactStatementService(IResearchImpactStatementDAO researchImpactStatementDAO) {
    this.researchImpactStatementDAO = researchImpactStatementDAO;


  }

  @Override
  public boolean deleteResearchImpactStatement(long researchImpactStatementId) {

    return researchImpactStatementDAO.deleteResearchImpactStatement(researchImpactStatementId);
  }

  @Override
  public boolean existResearchImpactStatement(long researchImpactStatementID) {

    return researchImpactStatementDAO.existResearchImpactStatement(researchImpactStatementID);
  }

  @Override
  public List<ResearchImpactStatement> findAll() {

    return researchImpactStatementDAO.findAll();

  }

  @Override
  public ResearchImpactStatement getResearchImpactStatementById(long researchImpactStatementID) {

    return researchImpactStatementDAO.find(researchImpactStatementID);
  }

  @Override
  public List<ResearchImpactStatement> getResearchImpactStatementsByUserId(Long userId) {
    return researchImpactStatementDAO.getResearchImpactStatementsByUserId(userId);
  }

  @Override
  public long saveResearchImpactStatement(ResearchImpactStatement researchImpactStatement) {

    return researchImpactStatementDAO.save(researchImpactStatement);
  }

  @Override
  public long saveResearchImpactStatement(ResearchImpactStatement researchImpactStatement, String actionName, List<String> relationsName) {
    return researchImpactStatementDAO.save(researchImpactStatement, actionName, relationsName);
  }


}
