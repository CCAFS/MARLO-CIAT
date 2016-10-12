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


import org.cgiar.ccafs.marlo.data.dao.IResearchProgramDAO;
import org.cgiar.ccafs.marlo.data.model.ResearchProgram;
import org.cgiar.ccafs.marlo.data.service.IProgramService;

import java.util.List;

import com.google.inject.Inject;

/**
 * @author Christian Garcia
 */
public class ProgramService implements IProgramService {


  private IResearchProgramDAO crpProgramDAO;

  // Managers


  @Inject
  public ProgramService(IResearchProgramDAO crpProgramDAO) {
    this.crpProgramDAO = crpProgramDAO;


  }

  @Override
  public boolean deleteCrpProgram(long crpProgramId) {

    return crpProgramDAO.deleteCrpProgram(crpProgramId);
  }

  @Override
  public boolean existCrpProgram(long crpProgramID) {

    return crpProgramDAO.existCrpProgram(crpProgramID);
  }

  @Override
  public List<ResearchProgram> findAll() {

    return crpProgramDAO.findAll();

  }

  @Override
  public List<ResearchProgram> findCrpProgramsByType(long id, int programType) {
    return crpProgramDAO.findCrpProgramsByType(id, programType);
  }

  @Override
  public ResearchProgram getCrpProgramById(long crpProgramID) {

    return crpProgramDAO.find(crpProgramID);
  }

  @Override
  public long saveCrpProgram(ResearchProgram crpProgram) {

    return crpProgramDAO.save(crpProgram);
  }


  @Override
  public long saveCrpProgram(ResearchProgram crpProgram, String actionName, List<String> relationsName) {

    return crpProgramDAO.save(crpProgram, actionName, relationsName);
  }

}
