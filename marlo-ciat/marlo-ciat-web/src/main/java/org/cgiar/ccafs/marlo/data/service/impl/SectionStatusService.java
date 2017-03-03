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


import org.cgiar.ccafs.marlo.data.dao.ISectionStatusDAO;
import org.cgiar.ccafs.marlo.data.model.SectionStatus;
import org.cgiar.ccafs.marlo.data.service.ISectionStatusService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;

/**
 * @author Christian Garcia
 */
public class SectionStatusService implements ISectionStatusService {


  private ISectionStatusDAO sectionStatusDAO;

  // Managers


  @Inject
  public SectionStatusService(ISectionStatusDAO sectionStatusDAO) {
    this.sectionStatusDAO = sectionStatusDAO;


  }

  @Override
  public boolean deleteSectionStatus(long sectionStatusId) {

    return sectionStatusDAO.deleteSectionStatus(sectionStatusId);
  }

  @Override
  public List<String> distinctSectionStatus(long programID) {
    List<String> status = new ArrayList<String>();

    List<Map<String, Object>> data = sectionStatusDAO.distinctSectionStatus(programID);
    if (data != null) {
      for (Map<String, Object> map : data) {
        status.add(map.get("section_name").toString());
      }
    }

    return status;
  }

  @Override
  public boolean existSectionStatus(long sectionStatusID) {

    return sectionStatusDAO.existSectionStatus(sectionStatusID);
  }

  @Override
  public List<SectionStatus> findAll() {

    return sectionStatusDAO.findAll();

  }

  @Override
  public List<SectionStatus> getSectionStatus(long programId, String sectionName) {
    return sectionStatusDAO.getSectionStatus(programId, sectionName);
  }

  @Override
  public SectionStatus getSectionStatusById(long sectionStatusID) {

    return sectionStatusDAO.find(sectionStatusID);
  }

  @Override
  public SectionStatus getSectionStatusByOutcome(long programId, long outcomeId, String sectionName, int year) {
    return sectionStatusDAO.getSectionStatusByOutcome(programId, outcomeId, sectionName, year);
  }

  @Override
  public SectionStatus getSectionStatusByOutput(long programId, long outputId, String sectionName, int year) {
    return sectionStatusDAO.getSectionStatusByOutput(programId, outputId, sectionName, year);
  }

  @Override
  public SectionStatus getSectionStatusByProgram(long programId, String sectionName, int year) {
    return sectionStatusDAO.getSectionStatusByProgram(programId, sectionName, year);
  }

  @Override
  public SectionStatus getSectionStatusByProject(long programId, long projectId, String sectionName, int year) {
    return sectionStatusDAO.getSectionStatusByProject(programId, projectId, sectionName, year);
  }

  @Override
  public List<SectionStatus> getSectionStatussByUserId(Long userId) {
    return sectionStatusDAO.getSectionStatussByUserId(userId);
  }

  @Override
  public long saveSectionStatus(SectionStatus sectionStatus) {

    return sectionStatusDAO.save(sectionStatus);
  }


}
