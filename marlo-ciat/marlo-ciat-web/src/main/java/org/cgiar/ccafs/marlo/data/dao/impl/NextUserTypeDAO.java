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
import org.cgiar.ccafs.marlo.data.model.NextUserType;

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
    NextUserType nextUserType = this.find(nextUserTypeId);
    return dao.delete(nextUserType);
  }

  @Override
  public boolean existNextUserType(long nextUserTypeID) {
    NextUserType nextUserType = this.find(nextUserTypeID);
    if (nextUserType == null) {
      return false;
    }
    return true;

  }

  @Override
  public NextUserType find(long id) {
    return dao.find(NextUserType.class, id);

  }

  @Override
  public List<NextUserType> findAll() {
    String query = "from " + NextUserType.class.getName();
    List<NextUserType> list = dao.findAll(query);
    if (list.size() > 0) {
      return list;
    }
    return null;

  }

  @Override
  public List<NextUserType> getNextUserTypesByUserId(long userId) {
    String query = "from " + NextUserType.class.getName() + " where user_id=" + userId;
    return dao.findAll(query);
  }

  @Override
  public long save(NextUserType nextUserType) {
    if (nextUserType.getId() == null) {
      dao.save(nextUserType);
    } else {
      dao.update(nextUserType);
    }
    return nextUserType.getId();
  }


}