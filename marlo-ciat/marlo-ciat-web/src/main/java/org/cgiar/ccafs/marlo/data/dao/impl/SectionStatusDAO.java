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

import org.cgiar.ccafs.marlo.data.dao.ISectionStatusDAO;
import org.cgiar.ccafs.marlo.data.model.SectionStatus;

import java.util.List;
import java.util.Map;

import com.google.inject.Inject;

public class SectionStatusDAO implements ISectionStatusDAO {

  private StandardDAO dao;

  @Inject
  public SectionStatusDAO(StandardDAO dao) {
    this.dao = dao;
  }

  @Override
  public boolean deleteSectionStatus(long sectionStatusId) {
    SectionStatus sectionStatus = this.find(sectionStatusId);
    return dao.delete(sectionStatus);
  }

  @Override
  public List<Map<String, Object>> distinctSectionStatus(long programID) {
    String query = "select DISTINCT section_name from section_statuses where research_program_id=" + programID;
    return dao.findCustomQuery(query);
  }

  @Override
  public boolean existSectionStatus(long sectionStatusID) {
    SectionStatus sectionStatus = this.find(sectionStatusID);
    if (sectionStatus == null) {
      return false;
    }
    return true;

  }

  @Override
  public SectionStatus find(long id) {
    return dao.find(SectionStatus.class, id);

  }

  @Override
  public List<SectionStatus> findAll() {
    String query = "from " + SectionStatus.class.getName();
    List<SectionStatus> list = dao.findAll(query);
    if (list.size() > 0) {
      return list;
    }
    return null;

  }

  @Override
  public List<SectionStatus> getSectionStatus(long programId, String sectionName) {
    String query = "from " + SectionStatus.class.getName() + " where section_name='" + sectionName
      + "' and research_program_id=" + programId;
    List<SectionStatus> list = dao.findAll(query);
    if (list.size() > 0) {
      return list;
    }
    return null;
  }

  @Override
  public SectionStatus getSectionStatusByOutcome(long programId, long outcomeId, String sectionName, int year) {
    String query = "from " + SectionStatus.class.getName() + " where section_name='" + sectionName
      + "' and research_program_id=" + programId + " and research_outcome_id=" + outcomeId + " and year=" + year;
    List<SectionStatus> list = dao.findAll(query);
    if (list.size() > 0) {
      return list.get(0);
    }
    return null;
  }

  @Override
  public SectionStatus getSectionStatusByOutput(long programId, long outputId, String sectionName, int year) {
    String query = "from " + SectionStatus.class.getName() + " where section_name='" + sectionName
      + "' and research_program_id=" + programId + " and research_output_id=" + outputId + " and year=" + year;
    List<SectionStatus> list = dao.findAll(query);
    if (list.size() > 0) {
      return list.get(0);
    }
    return null;
  }

  @Override
  public SectionStatus getSectionStatusByProgram(long programId, String sectionName, int year) {
    String query = "from " + SectionStatus.class.getName() + " where section_name='" + sectionName
      + "' and research_program_id=" + programId + " and year=" + year;
    List<SectionStatus> list = dao.findAll(query);
    if (list.size() > 0) {
      return list.get(0);
    }
    return null;
  }

  @Override
  public List<SectionStatus> getSectionStatussByUserId(long userId) {
    String query = "from " + SectionStatus.class.getName() + " where user_id=" + userId;
    return dao.findAll(query);
  }

  @Override
  public long save(SectionStatus sectionStatus) {
    if (sectionStatus.getId() == null) {
      dao.save(sectionStatus);
    } else {
      dao.update(sectionStatus);
    }
    return sectionStatus.getId();
  }


}