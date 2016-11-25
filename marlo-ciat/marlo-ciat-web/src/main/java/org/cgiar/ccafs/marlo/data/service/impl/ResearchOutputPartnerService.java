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


import org.cgiar.ccafs.marlo.data.dao.IResearchOutputPartnerDAO;
import org.cgiar.ccafs.marlo.data.model.ResearchOutputPartner;
import org.cgiar.ccafs.marlo.data.service.IResearchOutputPartnerService;

import java.util.List;

import com.google.inject.Inject;

/**
 * @author Christian Garcia
 */
public class ResearchOutputPartnerService implements IResearchOutputPartnerService {


  private IResearchOutputPartnerDAO researchOutputPartnerDAO;

  // Managers


  @Inject
  public ResearchOutputPartnerService(IResearchOutputPartnerDAO researchOutputPartnerDAO) {
    this.researchOutputPartnerDAO = researchOutputPartnerDAO;


  }

  @Override
  public boolean deleteResearchOutputPartner(long researchOutputPartnerId) {

    return researchOutputPartnerDAO.deleteResearchOutputPartner(researchOutputPartnerId);
  }

  @Override
  public boolean existResearchOutputPartner(long researchOutputPartnerID) {

    return researchOutputPartnerDAO.existResearchOutputPartner(researchOutputPartnerID);
  }

  @Override
  public List<ResearchOutputPartner> findAll() {

    return researchOutputPartnerDAO.findAll();

  }

  @Override
  public ResearchOutputPartner getResearchOutputPartnerById(long researchOutputPartnerID) {

    return researchOutputPartnerDAO.find(researchOutputPartnerID);
  }

  @Override
  public List<ResearchOutputPartner> getResearchOutputPartnersByUserId(Long userId) {
    return researchOutputPartnerDAO.getResearchOutputPartnersByUserId(userId);
  }

  @Override
  public long saveResearchOutputPartner(ResearchOutputPartner researchOutputPartner) {

    return researchOutputPartnerDAO.save(researchOutputPartner);
  }


}
