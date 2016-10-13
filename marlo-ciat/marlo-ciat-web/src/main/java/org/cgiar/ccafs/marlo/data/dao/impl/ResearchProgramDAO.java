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

import org.cgiar.ccafs.marlo.data.dao.IResearchProgramDAO;
import org.cgiar.ccafs.marlo.data.model.ResearchProgram;

import java.util.List;

import com.google.inject.Inject;

public class ResearchProgramDAO implements IResearchProgramDAO {

  private StandardDAO dao;

  @Inject
  public ResearchProgramDAO(StandardDAO dao) {
    this.dao = dao;
  }

  @Override
  public boolean deleteCrpProgram(long crpProgramId) {
    ResearchProgram crpProgram = this.find(crpProgramId);
    crpProgram.setActive(false);
    return this.save(crpProgram) > 0;
  }

  @Override
  public boolean existCrpProgram(long crpProgramID) {
    ResearchProgram crpProgram = this.find(crpProgramID);
    if (crpProgram == null) {
      return false;
    }
    return true;

  }

  @Override
  public ResearchProgram find(long id) {
    return dao.find(ResearchProgram.class, id);

  }

  @Override
  public List<ResearchProgram> findAll() {


    String query = "from " + ResearchProgram.class.getName() + " where is_active=1";
    List<ResearchProgram> list = dao.findAll(query);
    if (list.size() > 0) {
      return list;
    }
    return null;

    /*
     * Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
     * List<CrpProgram> programs = new ArrayList<CrpProgram>();
     * String query = "select * from auditlog";
     * List<Map<String, Object>> lst = dao.findCustomQuery(query);
     * for (Map<String, Object> map : lst) {
     * String json = (String) map.get("Entity_json");
     * CrpProgram prog = gson.fromJson(json, CrpProgram.class);
     * programs.add(prog);
     * }
     * return programs;
     */
  }

  @Override
  public List<ResearchProgram> findCrpProgramsByType(long id, int programType) {
    String query =
      "from " + ResearchProgram.class.getName() + " where crp_id=" + id + " and program_type=" + programType
        + " and is_active=1";
    List<ResearchProgram> list = dao.findAll(query);
    if (list.size() > 0) {
      return list;
    }
    return null;
  }

  /*
   * (non-Javadoc)
   * @see org.cgiar.ccafs.marlo.data.dao.IResearchProgramDAO#findProgramsByResearchArea(java.lang.Long)
   */
  @Override
  public List<ResearchProgram> findProgramsByResearchArea(Long researchAreaId) {
    String query =
      "from " + ResearchProgram.class.getName() + " where research_area_id=" + researchAreaId + " and is_active=1";
    List<ResearchProgram> list = dao.findAll(query);
    if (list.size() > 0) {
      return list;
    }
    return null;
  }

  @Override
  public long save(ResearchProgram crpProgram) {
    if (crpProgram.getId() == null) {
      dao.save(crpProgram);
    } else {
      dao.update(crpProgram);
    }
    return crpProgram.getId();
  }

  @Override
  public long save(ResearchProgram crpProgram, String actionName, List<String> relationsName) {
    if (crpProgram.getId() == null) {
      dao.save(crpProgram, actionName, relationsName);
    } else {
      dao.update(crpProgram, actionName, relationsName);
    }
    return crpProgram.getId();
  }


}