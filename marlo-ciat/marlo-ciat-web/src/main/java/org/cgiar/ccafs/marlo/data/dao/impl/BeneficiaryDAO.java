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

import org.cgiar.ccafs.marlo.data.dao.IBeneficiaryDAO;
import org.cgiar.ccafs.marlo.data.model.Beneficiary;

import java.util.List;

import com.google.inject.Inject;

public class BeneficiaryDAO implements IBeneficiaryDAO {

  private StandardDAO dao;

  @Inject
  public BeneficiaryDAO(StandardDAO dao) {
    this.dao = dao;
  }

  @Override
  public boolean deleteBeneficiary(long beneficiaryId) {
    Beneficiary beneficiary = this.find(beneficiaryId);
    return dao.delete(beneficiary);
  }

  @Override
  public boolean existBeneficiary(long beneficiaryID) {
    Beneficiary beneficiary = this.find(beneficiaryID);
    if (beneficiary == null) {
      return false;
    }
    return true;

  }

  @Override
  public Beneficiary find(long id) {
    return dao.find(Beneficiary.class, id);

  }

  @Override
  public List<Beneficiary> findAll() {
    String query = "from " + Beneficiary.class.getName();
    List<Beneficiary> list = dao.findAll(query);
    if (list.size() > 0) {
      return list;
    }
    return null;

  }

  @Override
  public List<Beneficiary> getBeneficiarysByUserId(long userId) {
    String query = "from " + Beneficiary.class.getName() + " where user_id=" + userId;
    return dao.findAll(query);
  }

  @Override
  public long save(Beneficiary beneficiary) {
    if (beneficiary.getId() == null) {
      dao.save(beneficiary);
    } else {
      dao.update(beneficiary);
    }
    return beneficiary.getId();
  }


}