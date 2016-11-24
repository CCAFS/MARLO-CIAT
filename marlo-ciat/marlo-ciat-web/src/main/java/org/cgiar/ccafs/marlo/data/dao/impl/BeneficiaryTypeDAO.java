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

import org.cgiar.ccafs.marlo.data.dao.IBeneficiaryTypeDAO;
import org.cgiar.ccafs.marlo.data.model.BeneficiaryType;

import java.util.List;

import com.google.inject.Inject;

public class BeneficiaryTypeDAO implements IBeneficiaryTypeDAO {

  private StandardDAO dao;

  @Inject
  public BeneficiaryTypeDAO(StandardDAO dao) {
    this.dao = dao;
  }

  @Override
  public boolean deleteBeneficiaryType(long beneficiaryTypeId) {
    BeneficiaryType beneficiaryType = this.find(beneficiaryTypeId);
    return dao.delete(beneficiaryType);
  }

  @Override
  public boolean existBeneficiaryType(long beneficiaryTypeID) {
    BeneficiaryType beneficiaryType = this.find(beneficiaryTypeID);
    if (beneficiaryType == null) {
      return false;
    }
    return true;

  }

  @Override
  public BeneficiaryType find(long id) {
    return dao.find(BeneficiaryType.class, id);

  }

  @Override
  public List<BeneficiaryType> findAll() {
    String query = "from " + BeneficiaryType.class.getName();
    List<BeneficiaryType> list = dao.findAll(query);
    if (list.size() > 0) {
      return list;
    }
    return null;

  }

  @Override
  public List<BeneficiaryType> getBeneficiaryTypesByUserId(long userId) {
    String query = "from " + BeneficiaryType.class.getName() + " where user_id=" + userId;
    return dao.findAll(query);
  }

  @Override
  public long save(BeneficiaryType beneficiaryType) {
    if (beneficiaryType.getId() == null) {
      dao.save(beneficiaryType);
    } else {
      dao.update(beneficiaryType);
    }
    return beneficiaryType.getId();
  }


}