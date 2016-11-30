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

import org.cgiar.ccafs.marlo.data.dao.IResearchImpactBeneficiaryDAO;
import org.cgiar.ccafs.marlo.data.model.ResearchImpactBeneficiary;

import java.util.List;

import com.google.inject.Inject;

public class ResearchImpactBeneficiaryDAO implements IResearchImpactBeneficiaryDAO {

  private StandardDAO dao;

  @Inject
  public ResearchImpactBeneficiaryDAO(StandardDAO dao) {
    this.dao = dao;
  }

  @Override
  public boolean deleteResearchImpactBeneficiary(long researchImpactBeneficiaryId) {
    ResearchImpactBeneficiary researchImpactBeneficiary = this.find(researchImpactBeneficiaryId);
    researchImpactBeneficiary.setActive(false);
    return this.save(researchImpactBeneficiary) > 0;
  }

  @Override
  public boolean existResearchImpactBeneficiary(long researchImpactBeneficiaryID) {
    ResearchImpactBeneficiary researchImpactBeneficiary = this.find(researchImpactBeneficiaryID);
    if (researchImpactBeneficiary == null) {
      return false;
    }
    return true;

  }

  @Override
  public ResearchImpactBeneficiary find(long id) {
    return dao.find(ResearchImpactBeneficiary.class, id);

  }

  @Override
  public List<ResearchImpactBeneficiary> findAll() {
    String query = "from " + ResearchImpactBeneficiary.class.getName();
    List<ResearchImpactBeneficiary> list = dao.findAll(query);
    if (list.size() > 0) {
      return list;
    }
    return null;

  }

  @Override
  public List<ResearchImpactBeneficiary> getResearchImpactBeneficiarysByUserId(long userId) {
    String query = "from " + ResearchImpactBeneficiary.class.getName() + " where user_id=" + userId;
    return dao.findAll(query);
  }

  @Override
  public long save(ResearchImpactBeneficiary researchImpactBeneficiary) {
    if (researchImpactBeneficiary.getId() == null) {
      dao.save(researchImpactBeneficiary);
    } else {
      dao.update(researchImpactBeneficiary);
    }
    return researchImpactBeneficiary.getId();
  }


}