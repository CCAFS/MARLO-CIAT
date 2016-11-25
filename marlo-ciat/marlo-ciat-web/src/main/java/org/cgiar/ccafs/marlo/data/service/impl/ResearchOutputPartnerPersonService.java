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


import org.cgiar.ccafs.marlo.data.dao.IResearchOutputPartnerPersonDAO;
import org.cgiar.ccafs.marlo.data.model.ResearchOutputPartnerPerson;
import org.cgiar.ccafs.marlo.data.service.IResearchOutputPartnerPersonService;

import java.util.List;

import com.google.inject.Inject;

/**
 * @author Christian Garcia
 */
public class ResearchOutputPartnerPersonService implements IResearchOutputPartnerPersonService {


  private IResearchOutputPartnerPersonDAO researchOutputPartnerPersonDAO;

  // Managers


  @Inject
  public ResearchOutputPartnerPersonService(IResearchOutputPartnerPersonDAO researchOutputPartnerPersonDAO) {
    this.researchOutputPartnerPersonDAO = researchOutputPartnerPersonDAO;


  }

  @Override
  public boolean deleteResearchOutputPartnerPerson(long researchOutputPartnerPersonId) {

    return researchOutputPartnerPersonDAO.deleteResearchOutputPartnerPerson(researchOutputPartnerPersonId);
  }

  @Override
  public boolean existResearchOutputPartnerPerson(long researchOutputPartnerPersonID) {

    return researchOutputPartnerPersonDAO.existResearchOutputPartnerPerson(researchOutputPartnerPersonID);
  }

  @Override
  public List<ResearchOutputPartnerPerson> findAll() {

    return researchOutputPartnerPersonDAO.findAll();

  }

  @Override
  public ResearchOutputPartnerPerson getResearchOutputPartnerPersonById(long researchOutputPartnerPersonID) {

    return researchOutputPartnerPersonDAO.find(researchOutputPartnerPersonID);
  }

  @Override
  public List<ResearchOutputPartnerPerson> getResearchOutputPartnerPersonsByUserId(Long userId) {
    return researchOutputPartnerPersonDAO.getResearchOutputPartnerPersonsByUserId(userId);
  }

  @Override
  public long saveResearchOutputPartnerPerson(ResearchOutputPartnerPerson researchOutputPartnerPerson) {

    return researchOutputPartnerPersonDAO.save(researchOutputPartnerPerson);
  }


}
