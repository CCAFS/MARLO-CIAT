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

import org.cgiar.ccafs.marlo.data.dao.IFundingSourceTypeDAO;
import org.cgiar.ccafs.marlo.data.model.FundingSourceType;

import java.util.List;

import com.google.inject.Inject;

public class FundingSourceTypeDAO implements IFundingSourceTypeDAO {

  private StandardDAO dao;

  @Inject
  public FundingSourceTypeDAO(StandardDAO dao) {
    this.dao = dao;
  }

  @Override
  public boolean deleteFundingSourceType(long fundingSourceTypeId) {
    FundingSourceType fundingSourceType = this.find(fundingSourceTypeId);
    fundingSourceType.setActive(false);
    return this.save(fundingSourceType) > 0;
  }

  @Override
  public boolean existFundingSourceType(long fundingSourceTypeID) {
    FundingSourceType fundingSourceType = this.find(fundingSourceTypeID);
    if (fundingSourceType == null) {
      return false;
    }
    return true;

  }

  @Override
  public FundingSourceType find(long id) {
    return dao.find(FundingSourceType.class, id);

  }

  @Override
  public List<FundingSourceType> findAll() {
    String query = "from " + FundingSourceType.class.getName();
    List<FundingSourceType> list = dao.findAll(query);
    if (list.size() > 0) {
      return list;
    }
    return null;

  }

  @Override
  public List<FundingSourceType> getFundingSourceTypesByUserId(long userId) {
    String query = "from " + FundingSourceType.class.getName() + " where user_id=" + userId;
    return dao.findAll(query);
  }

  @Override
  public long save(FundingSourceType fundingSourceType) {
    if (fundingSourceType.getId() == null) {
      dao.save(fundingSourceType);
    } else {
      dao.update(fundingSourceType);
    }
    return fundingSourceType.getId();
  }


}