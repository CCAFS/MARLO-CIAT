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


import org.cgiar.ccafs.marlo.data.dao.IResearchMilestoneDAO;
import org.cgiar.ccafs.marlo.data.model.ResearchMilestone;
import org.cgiar.ccafs.marlo.data.service.IResearchMilestoneService;

import java.util.List;

import com.google.inject.Inject;

/**
 * @author Christian Garcia
 */
public class ResearchMilestoneService implements IResearchMilestoneService {


  private IResearchMilestoneDAO researchMilestoneDAO;

  // Managers


  @Inject
  public ResearchMilestoneService(IResearchMilestoneDAO researchMilestoneDAO) {
    this.researchMilestoneDAO = researchMilestoneDAO;


  }

  @Override
  public boolean deleteResearchMilestone(long researchMilestoneId) {

    return researchMilestoneDAO.deleteResearchMilestone(researchMilestoneId);
  }

  @Override
  public boolean existResearchMilestone(long researchMilestoneID) {

    return researchMilestoneDAO.existResearchMilestone(researchMilestoneID);
  }

  @Override
  public List<ResearchMilestone> findAll() {

    return researchMilestoneDAO.findAll();

  }

  @Override
  public ResearchMilestone getResearchMilestoneById(long researchMilestoneID) {

    return researchMilestoneDAO.find(researchMilestoneID);
  }

  @Override
  public List<ResearchMilestone> getResearchMilestonesByUserId(Long userId) {
    return researchMilestoneDAO.getResearchMilestonesByUserId(userId);
  }

  @Override
  public long saveResearchMilestone(ResearchMilestone researchMilestone) {

    return researchMilestoneDAO.save(researchMilestone);
  }


}
