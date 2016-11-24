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


import org.cgiar.ccafs.marlo.data.dao.IResearchImpactBeneficiaryDAO;
import org.cgiar.ccafs.marlo.data.model.ResearchImpactBeneficiary;
import org.cgiar.ccafs.marlo.data.service.IResearchImpactBeneficiaryService;

import java.util.List;

import com.google.inject.Inject;

/**
 * @author Christian Garcia
 */
public class ResearchImpactBeneficiaryService implements IResearchImpactBeneficiaryService {


  private IResearchImpactBeneficiaryDAO researchImpactBeneficiaryDAO;

  // Managers


  @Inject
  public ResearchImpactBeneficiaryService(IResearchImpactBeneficiaryDAO researchImpactBeneficiaryDAO) {
    this.researchImpactBeneficiaryDAO = researchImpactBeneficiaryDAO;


  }

  @Override
  public boolean deleteResearchImpactBeneficiary(long researchImpactBeneficiaryId) {

    return researchImpactBeneficiaryDAO.deleteResearchImpactBeneficiary(researchImpactBeneficiaryId);
  }

  @Override
  public boolean existResearchImpactBeneficiary(long researchImpactBeneficiaryID) {

    return researchImpactBeneficiaryDAO.existResearchImpactBeneficiary(researchImpactBeneficiaryID);
  }

  @Override
  public List<ResearchImpactBeneficiary> findAll() {

    return researchImpactBeneficiaryDAO.findAll();

  }

  @Override
  public ResearchImpactBeneficiary getResearchImpactBeneficiaryById(long researchImpactBeneficiaryID) {

    return researchImpactBeneficiaryDAO.find(researchImpactBeneficiaryID);
  }

  @Override
  public List<ResearchImpactBeneficiary> getResearchImpactBeneficiarysByUserId(Long userId) {
    return researchImpactBeneficiaryDAO.getResearchImpactBeneficiarysByUserId(userId);
  }

  @Override
  public long saveResearchImpactBeneficiary(ResearchImpactBeneficiary researchImpactBeneficiary) {

    return researchImpactBeneficiaryDAO.save(researchImpactBeneficiary);
  }


}
