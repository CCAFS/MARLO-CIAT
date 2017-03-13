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

import org.cgiar.ccafs.marlo.data.dao.IDeliverableTypeDAO;
import org.cgiar.ccafs.marlo.data.model.DeliverableType;

import java.util.List;

import com.google.inject.Inject;

public class DeliverableTypeDAO implements IDeliverableTypeDAO {

  private StandardDAO dao;

  @Inject
  public DeliverableTypeDAO(StandardDAO dao) {
    this.dao = dao;
  }

  @Override
  public boolean deleteDeliverableType(long deliverableTypeId) {
    DeliverableType deliverableType = this.find(deliverableTypeId);
    deliverableType.setActive(false);
    return this.save(deliverableType) > 0;
  }

  @Override
  public boolean existDeliverableType(long deliverableTypeID) {
    DeliverableType deliverableType = this.find(deliverableTypeID);
    if (deliverableType == null) {
      return false;
    }
    return true;

  }

  @Override
  public DeliverableType find(long id) {
    return dao.find(DeliverableType.class, id);

  }

  @Override
  public List<DeliverableType> findAll() {
    String query = "from " + DeliverableType.class.getName();
    List<DeliverableType> list = dao.findAll(query);
    if (list.size() > 0) {
      return list;
    }
    return null;

  }

  @Override
  public List<DeliverableType> getDeliverableTypesByUserId(long userId) {
    String query = "from " + DeliverableType.class.getName() + " where user_id=" + userId;
    return dao.findAll(query);
  }

  @Override
  public long save(DeliverableType deliverableType) {
    if (deliverableType.getId() == null) {
      dao.save(deliverableType);
    } else {
      dao.update(deliverableType);
    }
    return deliverableType.getId();
  }


}