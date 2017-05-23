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


import org.cgiar.ccafs.marlo.data.dao.IDeliverableTypeDAO;
import org.cgiar.ccafs.marlo.data.model.DeliverableType;
import org.cgiar.ccafs.marlo.data.service.IDeliverableTypeService;

import java.util.List;

import com.google.inject.Inject;

/**
 * @author Christian Garcia
 */
public class DeliverableTypeService implements IDeliverableTypeService {


  private IDeliverableTypeDAO deliverableTypeDAO;

  // Managers


  @Inject
  public DeliverableTypeService(IDeliverableTypeDAO deliverableTypeDAO) {
    this.deliverableTypeDAO = deliverableTypeDAO;


  }

  @Override
  public boolean deleteDeliverableType(long deliverableTypeId) {

    return deliverableTypeDAO.deleteDeliverableType(deliverableTypeId);
  }

  @Override
  public boolean existDeliverableType(long deliverableTypeID) {

    return deliverableTypeDAO.existDeliverableType(deliverableTypeID);
  }

  @Override
  public List<DeliverableType> findAll() {

    return deliverableTypeDAO.findAll();

  }

  @Override
  public DeliverableType getDeliverableTypeById(long deliverableTypeID) {

    return deliverableTypeDAO.find(deliverableTypeID);
  }

  @Override
  public List<DeliverableType> getDeliverableTypesByUserId(Long userId) {
    return deliverableTypeDAO.getDeliverableTypesByUserId(userId);
  }

  @Override
  public List<DeliverableType> getSubDeliverableType(Long deliverableId) {
    return deliverableTypeDAO.getSubDeliverableType(deliverableId);
  }

  @Override
  public long saveDeliverableType(DeliverableType deliverableType) {

    return deliverableTypeDAO.save(deliverableType);
  }


}
