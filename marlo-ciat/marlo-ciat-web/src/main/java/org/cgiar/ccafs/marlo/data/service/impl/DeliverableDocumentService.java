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


import org.cgiar.ccafs.marlo.data.dao.IDeliverableDocumentDAO;
import org.cgiar.ccafs.marlo.data.model.DeliverableDocument;
import org.cgiar.ccafs.marlo.data.service.IDeliverableDocumentService;

import java.util.List;

import com.google.inject.Inject;

/**
 * @author Christian Garcia
 */
public class DeliverableDocumentService implements IDeliverableDocumentService {


  private IDeliverableDocumentDAO deliverableDocumentDAO;

  // Managers


  @Inject
  public DeliverableDocumentService(IDeliverableDocumentDAO deliverableDocumentDAO) {
    this.deliverableDocumentDAO = deliverableDocumentDAO;


  }

  @Override
  public boolean deleteDeliverableDocument(long deliverableDocumentId) {

    return deliverableDocumentDAO.deleteDeliverableDocument(deliverableDocumentId);
  }

  @Override
  public boolean existDeliverableDocument(long deliverableDocumentID) {

    return deliverableDocumentDAO.existDeliverableDocument(deliverableDocumentID);
  }

  @Override
  public List<DeliverableDocument> findAll() {

    return deliverableDocumentDAO.findAll();

  }

  @Override
  public DeliverableDocument getDeliverableDocumentById(long deliverableDocumentID) {

    return deliverableDocumentDAO.find(deliverableDocumentID);
  }

  @Override
  public List<DeliverableDocument> getDeliverableDocumentsByUserId(Long userId) {
    return deliverableDocumentDAO.getDeliverableDocumentsByUserId(userId);
  }

  @Override
  public long saveDeliverableDocument(DeliverableDocument deliverableDocument) {

    return deliverableDocumentDAO.save(deliverableDocument);
  }


}
