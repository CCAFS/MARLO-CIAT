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

import org.cgiar.ccafs.marlo.data.dao.IResearchTopicDAO;
import org.cgiar.ccafs.marlo.data.model.ResearchTopic;

import java.util.List;

import com.google.inject.Inject;

public class ResearchTopicDAO implements IResearchTopicDAO {

  private StandardDAO dao;

  @Inject
  public ResearchTopicDAO(StandardDAO dao) {
    this.dao = dao;
  }

  @Override
  public boolean deleteResearchTopic(long researchTopicId) {
    ResearchTopic researchTopic = this.find(researchTopicId);
    return dao.delete(researchTopic);
  }

  @Override
  public boolean existResearchTopic(long researchTopicID) {
    ResearchTopic researchTopic = this.find(researchTopicID);
    if (researchTopic == null) {
      return false;
    }
    return true;

  }

  @Override
  public ResearchTopic find(long id) {
    return dao.find(ResearchTopic.class, id);

  }

  @Override
  public List<ResearchTopic> findAll() {
    String query = "from " + ResearchTopic.class.getName();
    List<ResearchTopic> list = dao.findAll(query);
    if (list.size() > 0) {
      return list;
    }
    return null;

  }

  @Override
  public List<ResearchTopic> getResearchTopicsByUserId(long userId) {
    String query = "from " + ResearchTopic.class.getName() + " where user_id=" + userId;
    return dao.findAll(query);
  }

  @Override
  public long save(ResearchTopic researchTopic) {
    if (researchTopic.getId() == null) {
      dao.save(researchTopic);
    } else {
      dao.update(researchTopic);
    }
    return researchTopic.getId();
  }


}