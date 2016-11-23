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

import org.cgiar.ccafs.marlo.data.dao.IResearchOutputDAO;
import org.cgiar.ccafs.marlo.data.model.ResearchOutput;

import java.util.List;

import com.google.inject.Inject;

public class ResearchOutputDAO implements IResearchOutputDAO {

  private StandardDAO dao;

  @Inject
  public ResearchOutputDAO(StandardDAO dao) {
    this.dao = dao;
  }

  @Override
  public boolean deleteResearchOutput(long researchOutputId) {
    ResearchOutput researchOutput = this.find(researchOutputId);
    return dao.delete(researchOutput);
  }

  @Override
  public boolean existResearchOutput(long researchOutputID) {
    ResearchOutput researchOutput = this.find(researchOutputID);
    if (researchOutput == null) {
      return false;
    }
    return true;

  }

  @Override
  public ResearchOutput find(long id) {
    return dao.find(ResearchOutput.class, id);

  }

  @Override
  public List<ResearchOutput> findAll() {
    String query = "from " + ResearchOutput.class.getName();
    List<ResearchOutput> list = dao.findAll(query);
    if (list.size() > 0) {
      return list;
    }
    return null;

  }

  @Override
  public List<ResearchOutput> getResearchOutputsByUserId(long userId) {
    String query = "from " + ResearchOutput.class.getName() + " where user_id=" + userId;
    return dao.findAll(query);
  }

  @Override
  public long save(ResearchOutput researchOutput) {
    if (researchOutput.getId() == null) {
      dao.save(researchOutput);
    } else {
      dao.update(researchOutput);
    }
    return researchOutput.getId();
  }

  @Override
  public long save(ResearchOutput output, String actionName, List<String> relationsName) {
    if (output.getId() == null) {
      dao.save(output, actionName, relationsName);
    } else {
      dao.update(output, actionName, relationsName);
    }
    return output.getId();
  }


}