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


import org.cgiar.ccafs.marlo.data.dao.IResearchOutputsNextUserDAO;
import org.cgiar.ccafs.marlo.data.model.ResearchOutputsNextUser;
import org.cgiar.ccafs.marlo.data.service.IResearchOutputsNextUserService;

import java.util.List;

import com.google.inject.Inject;

/**
 * @author Christian Garcia
 */
public class ResearchOutputsNextUserService implements IResearchOutputsNextUserService {


  private IResearchOutputsNextUserDAO researchOutputsNextUserDAO;

  // Managers


  @Inject
  public ResearchOutputsNextUserService(IResearchOutputsNextUserDAO researchOutputsNextUserDAO) {
    this.researchOutputsNextUserDAO = researchOutputsNextUserDAO;


  }

  @Override
  public boolean deleteResearchOutputsNextUser(long researchOutputsNextUserId) {

    return researchOutputsNextUserDAO.deleteResearchOutputsNextUser(researchOutputsNextUserId);
  }

  @Override
  public boolean existResearchOutputsNextUser(long researchOutputsNextUserID) {

    return researchOutputsNextUserDAO.existResearchOutputsNextUser(researchOutputsNextUserID);
  }

  @Override
  public List<ResearchOutputsNextUser> findAll() {

    return researchOutputsNextUserDAO.findAll();

  }

  @Override
  public ResearchOutputsNextUser getResearchOutputsNextUserById(long researchOutputsNextUserID) {

    return researchOutputsNextUserDAO.find(researchOutputsNextUserID);
  }

  @Override
  public List<ResearchOutputsNextUser> getResearchOutputsNextUsersByUserId(Long userId) {
    return researchOutputsNextUserDAO.getResearchOutputsNextUsersByUserId(userId);
  }

  @Override
  public long saveResearchOutputsNextUser(ResearchOutputsNextUser researchOutputsNextUser) {

    return researchOutputsNextUserDAO.save(researchOutputsNextUser);
  }


}
