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

import org.cgiar.ccafs.marlo.data.dao.INextuserTypeDAO;
import org.cgiar.ccafs.marlo.data.model.NextuserType;

import java.util.List;

import com.google.inject.Inject;

public class NextuserTypeDAO implements INextuserTypeDAO {

  private StandardDAO dao;

  @Inject
  public NextuserTypeDAO(StandardDAO dao) {
    this.dao = dao;
  }

  @Override
  public boolean deleteNextuserType(long nextuserTypeId) {
    NextuserType nextuserType = this.find(nextuserTypeId);
    return dao.delete(nextuserType);
  }

  @Override
  public boolean existNextuserType(long nextuserTypeID) {
    NextuserType nextuserType = this.find(nextuserTypeID);
    if (nextuserType == null) {
      return false;
    }
    return true;

  }

  @Override
  public NextuserType find(long id) {
    return dao.find(NextuserType.class, id);

  }

  @Override
  public List<NextuserType> findAll() {
    String query = "from " + NextuserType.class.getName();
    List<NextuserType> list = dao.findAll(query);
    if (list.size() > 0) {
      return list;
    }
    return null;

  }

  @Override
  public List<NextuserType> getNextuserTypesByUserId(long userId) {
    String query = "from " + NextuserType.class.getName() + " where user_id=" + userId;
    return dao.findAll(query);
  }

  @Override
  public long save(NextuserType nextuserType) {
    if (nextuserType.getId() == null) {
      dao.save(nextuserType);
    } else {
      dao.update(nextuserType);
    }
    return nextuserType.getId();
  }


}