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


import org.cgiar.ccafs.marlo.data.dao.IResearchOutputDAO;
import org.cgiar.ccafs.marlo.data.model.ResearchOutput;
import org.cgiar.ccafs.marlo.data.service.IResearchOutputService;

import java.util.List;

import com.google.inject.Inject;

/**
 * @author Christian Garcia
 */
public class ResearchOutputService implements IResearchOutputService {


  private IResearchOutputDAO researchOutputDAO;

  // Managers


  @Inject
  public ResearchOutputService(IResearchOutputDAO researchOutputDAO) {
    this.researchOutputDAO = researchOutputDAO;


  }

  @Override
  public boolean deleteResearchOutput(long researchOutputId) {

    return researchOutputDAO.deleteResearchOutput(researchOutputId);
  }

  @Override
  public boolean existResearchOutput(long researchOutputID) {

    return researchOutputDAO.existResearchOutput(researchOutputID);
  }

  @Override
  public List<ResearchOutput> findAll() {

    return researchOutputDAO.findAll();

  }

  @Override
  public ResearchOutput getResearchOutputById(long researchOutputID) {

    return researchOutputDAO.find(researchOutputID);
  }

  @Override
  public List<ResearchOutput> getResearchOutputsByUserId(Long userId) {
    return researchOutputDAO.getResearchOutputsByUserId(userId);
  }

  @Override
  public long saveResearchOutput(ResearchOutput researchOutput) {

    return researchOutputDAO.save(researchOutput);
  }

  @Override
  public long saveResearchOutput(ResearchOutput output, String actionName, List<String> relationsName) {
    return researchOutputDAO.save(output, actionName, relationsName);
  }


}
