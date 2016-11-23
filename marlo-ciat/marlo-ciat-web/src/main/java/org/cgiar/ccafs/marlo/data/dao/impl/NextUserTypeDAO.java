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

import org.cgiar.ccafs.marlo.data.dao.INextUserTypeDAO;
import org.cgiar.ccafs.marlo.data.model.OutputNextUser;

import java.util.List;

import com.google.inject.Inject;

public class NextUserTypeDAO implements INextUserTypeDAO {

  private StandardDAO dao;

  @Inject
  public NextUserTypeDAO(StandardDAO dao) {
    this.dao = dao;
  }

  @Override
  public boolean deleteNextUserType(long nextUserTypeId) {
    OutputNextUser nextUserType = this.find(nextUserTypeId);
    return dao.delete(nextUserType);
  }

  @Override
  public boolean existNextUserType(long nextUserTypeID) {
    OutputNextUser nextUserType = this.find(nextUserTypeID);
    if (nextUserType == null) {
      return false;
    }
    return true;

  }

  @Override
  public OutputNextUser find(long id) {
    return dao.find(OutputNextUser.class, id);

  }

  @Override
  public List<OutputNextUser> findAll() {
    String query = "from " + OutputNextUser.class.getName();
    List<OutputNextUser> list = dao.findAll(query);
    if (list.size() > 0) {
      return list;
    }
    return null;

  }

  @Override
  public List<OutputNextUser> getNextUserTypesByUserId(long userId) {
    String query = "from " + OutputNextUser.class.getName() + " where user_id=" + userId;
    return dao.findAll(query);
  }

  @Override
  public long save(OutputNextUser nextUserType) {
    if (nextUserType.getId() == null) {
      dao.save(nextUserType);
    } else {
      dao.update(nextUserType);
    }
    return nextUserType.getId();
  }


}