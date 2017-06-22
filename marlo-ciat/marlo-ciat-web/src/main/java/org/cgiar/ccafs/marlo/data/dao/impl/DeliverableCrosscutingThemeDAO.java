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

import org.cgiar.ccafs.marlo.data.dao.IDeliverableCrosscutingThemeDAO;
import org.cgiar.ccafs.marlo.data.model.DeliverableCrosscutingTheme;

import java.util.List;

import com.google.inject.Inject;

public class DeliverableCrosscutingThemeDAO implements IDeliverableCrosscutingThemeDAO {

  private StandardDAO dao;

  @Inject
  public DeliverableCrosscutingThemeDAO(StandardDAO dao) {
    this.dao = dao;
  }

  @Override
  public boolean deleteDeliverableCrosscutingTheme(long deliverableCrosscutingThemeId) {
    DeliverableCrosscutingTheme deliverableCrosscutingTheme = this.find(deliverableCrosscutingThemeId);
    deliverableCrosscutingTheme.setActive(false);
    return this.save(deliverableCrosscutingTheme) > 0;
  }

  @Override
  public boolean existDeliverableCrosscutingTheme(long deliverableCrosscutingThemeID) {
    DeliverableCrosscutingTheme deliverableCrosscutingTheme = this.find(deliverableCrosscutingThemeID);
    if (deliverableCrosscutingTheme == null) {
      return false;
    }
    return true;

  }

  @Override
  public DeliverableCrosscutingTheme find(long id) {
    return dao.find(DeliverableCrosscutingTheme.class, id);

  }

  @Override
  public List<DeliverableCrosscutingTheme> findAll() {
    String query = "from " + DeliverableCrosscutingTheme.class.getName();
    List<DeliverableCrosscutingTheme> list = dao.findAll(query);
    if (list.size() > 0) {
      return list;
    }
    return null;

  }

  @Override
  public List<DeliverableCrosscutingTheme> getDeliverableCrosscutingThemesByUserId(long userId) {
    String query = "from " + DeliverableCrosscutingTheme.class.getName() + " where user_id=" + userId;
    return dao.findAll(query);
  }

  @Override
  public long save(DeliverableCrosscutingTheme deliverableCrosscutingTheme) {
    if (deliverableCrosscutingTheme.getId() == null) {
      dao.save(deliverableCrosscutingTheme);
    } else {
      dao.update(deliverableCrosscutingTheme);
    }
    return deliverableCrosscutingTheme.getId();
  }

  @Override
  public long save(DeliverableCrosscutingTheme deliverableCrosscutingTheme, String actionName, List<String> relationsName) {
    if (deliverableCrosscutingTheme.getId() == null) {
      dao.save(deliverableCrosscutingTheme, actionName, relationsName);
    } else {
      dao.update(deliverableCrosscutingTheme, actionName, relationsName);
    }
    return deliverableCrosscutingTheme.getId();
  }


}