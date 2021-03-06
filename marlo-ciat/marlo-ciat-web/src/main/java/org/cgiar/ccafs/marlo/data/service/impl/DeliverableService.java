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
package org.cgiar.ccafs.marlo.data.service.impl;


import org.cgiar.ccafs.marlo.data.dao.IDeliverableDAO;
import org.cgiar.ccafs.marlo.data.model.Deliverable;
import org.cgiar.ccafs.marlo.data.service.IDeliverableService;

import java.util.List;

import com.google.inject.Inject;

/**
 * @author Christian Garcia
 */
public class DeliverableService implements IDeliverableService {


  private IDeliverableDAO deliverableDAO;

  // Managers


  @Inject
  public DeliverableService(IDeliverableDAO deliverableDAO) {
    this.deliverableDAO = deliverableDAO;


  }

  @Override
  public boolean deleteDeliverable(long deliverableId) {

    return deliverableDAO.deleteDeliverable(deliverableId);
  }

  @Override
  public boolean existDeliverable(long deliverableID) {

    return deliverableDAO.existDeliverable(deliverableID);
  }

  @Override
  public List<Deliverable> findAll() {

    return deliverableDAO.findAll();

  }

  @Override
  public Deliverable getDeliverableById(long deliverableID) {

    return deliverableDAO.find(deliverableID);
  }

  @Override
  public List<Deliverable> getDeliverablesByUserId(Long userId) {
    return deliverableDAO.getDeliverablesByUserId(userId);
  }

  @Override
  public long saveDeliverable(Deliverable deliverable) {

    return deliverableDAO.save(deliverable);
  }

  @Override
  public long saveDeliverable(Deliverable deliverable, String actionName, List<String> relationsName) {
    return deliverableDAO.save(deliverable, actionName, relationsName);
  }


}
