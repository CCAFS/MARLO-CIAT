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

import org.cgiar.ccafs.marlo.data.dao.IDeliverableOutputDAO;
import org.cgiar.ccafs.marlo.data.model.DeliverableOutput;

import java.util.List;

import com.google.inject.Inject;

public class DeliverableOutputDAO implements IDeliverableOutputDAO {

  private StandardDAO dao;

  @Inject
  public DeliverableOutputDAO(StandardDAO dao) {
    this.dao = dao;
  }

  @Override
  public boolean deleteDeliverableOutput(long deliverableOutputId) {
    DeliverableOutput deliverableOutput = this.find(deliverableOutputId);
    deliverableOutput.setActive(false);
    return this.save(deliverableOutput) > 0;
  }

  @Override
  public boolean existDeliverableOutput(long deliverableOutputID) {
    DeliverableOutput deliverableOutput = this.find(deliverableOutputID);
    if (deliverableOutput == null) {
      return false;
    }
    return true;

  }

  @Override
  public DeliverableOutput find(long id) {
    return dao.find(DeliverableOutput.class, id);

  }

  @Override
  public List<DeliverableOutput> findAll() {
    String query = "from " + DeliverableOutput.class.getName();
    List<DeliverableOutput> list = dao.findAll(query);
    if (list.size() > 0) {
      return list;
    }
    return null;

  }

  @Override
  public List<DeliverableOutput> getDeliverableOutputsByUserId(long userId) {
    String query = "from " + DeliverableOutput.class.getName() + " where user_id=" + userId;
    return dao.findAll(query);
  }

  @Override
  public long save(DeliverableOutput deliverableOutput) {
    if (deliverableOutput.getId() == null) {
      dao.save(deliverableOutput);
    } else {
      dao.update(deliverableOutput);
    }
    return deliverableOutput.getId();
  }

  @Override
  public long save(DeliverableOutput deliverableOutput, String actionName, List<String> relationsName) {
    if (deliverableOutput.getId() == null) {
      dao.save(deliverableOutput, actionName, relationsName);
    } else {
      dao.update(deliverableOutput, actionName, relationsName);
    }
    return deliverableOutput.getId();
  }


}