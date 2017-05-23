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


import org.cgiar.ccafs.marlo.data.dao.IDeliverableOutputDAO;
import org.cgiar.ccafs.marlo.data.model.DeliverableOutput;
import org.cgiar.ccafs.marlo.data.service.IDeliverableOutputService;

import java.util.List;

import com.google.inject.Inject;

/**
 * @author Christian Garcia
 */
public class DeliverableOutputService implements IDeliverableOutputService {


  private IDeliverableOutputDAO deliverableOutputDAO;

  // Managers


  @Inject
  public DeliverableOutputService(IDeliverableOutputDAO deliverableOutputDAO) {
    this.deliverableOutputDAO = deliverableOutputDAO;


  }

  @Override
  public boolean deleteDeliverableOutput(long deliverableOutputId) {

    return deliverableOutputDAO.deleteDeliverableOutput(deliverableOutputId);
  }

  @Override
  public boolean existDeliverableOutput(long deliverableOutputID) {

    return deliverableOutputDAO.existDeliverableOutput(deliverableOutputID);
  }

  @Override
  public List<DeliverableOutput> findAll() {

    return deliverableOutputDAO.findAll();

  }

  @Override
  public DeliverableOutput getDeliverableOutputById(long deliverableOutputID) {

    return deliverableOutputDAO.find(deliverableOutputID);
  }

  @Override
  public List<DeliverableOutput> getDeliverableOutputsByUserId(Long userId) {
    return deliverableOutputDAO.getDeliverableOutputsByUserId(userId);
  }

  @Override
  public long saveDeliverableOutput(DeliverableOutput deliverableOutput) {

    return deliverableOutputDAO.save(deliverableOutput);
  }

  @Override
  public long saveDeliverableOutput(DeliverableOutput deliverableOutput, String actionName, List<String> relationsName) {
    return deliverableOutputDAO.save(deliverableOutput, actionName, relationsName);
  }


}
