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

import org.cgiar.ccafs.marlo.data.dao.IOutputNextSubUserDAO;
import org.cgiar.ccafs.marlo.data.model.OutputNextSubUser;

import java.util.List;

import com.google.inject.Inject;

public class OutputNextSubUserDAO implements IOutputNextSubUserDAO {

  private StandardDAO dao;

  @Inject
  public OutputNextSubUserDAO(StandardDAO dao) {
    this.dao = dao;
  }

  @Override
  public boolean deleteOutputNextSubUser(long outputNextSubUserId) {
    OutputNextSubUser outputNextSubUser = this.find(outputNextSubUserId);
    return dao.delete(outputNextSubUser);
  }

  @Override
  public boolean existOutputNextSubUser(long outputNextSubUserID) {
    OutputNextSubUser outputNextSubUser = this.find(outputNextSubUserID);
    if (outputNextSubUser == null) {
      return false;
    }
    return true;

  }

  @Override
  public OutputNextSubUser find(long id) {
    return dao.find(OutputNextSubUser.class, id);

  }

  @Override
  public List<OutputNextSubUser> findAll() {
    String query = "from " + OutputNextSubUser.class.getName();
    List<OutputNextSubUser> list = dao.findAll(query);
    if (list.size() > 0) {
      return list;
    }
    return null;

  }

  @Override
  public List<OutputNextSubUser> getOutputNextSubUsersByUserId(long userId) {
    String query = "from " + OutputNextSubUser.class.getName() + " where user_id=" + userId;
    return dao.findAll(query);
  }

  @Override
  public long save(OutputNextSubUser outputNextSubUser) {
    if (outputNextSubUser.getId() == null) {
      dao.save(outputNextSubUser);
    } else {
      dao.update(outputNextSubUser);
    }
    return outputNextSubUser.getId();
  }


}