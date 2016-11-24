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


import org.cgiar.ccafs.marlo.data.dao.IBeneficiaryDAO;
import org.cgiar.ccafs.marlo.data.model.Beneficiary;
import org.cgiar.ccafs.marlo.data.service.IBeneficiaryService;

import java.util.List;

import com.google.inject.Inject;

/**
 * @author Christian Garcia
 */
public class BeneficiaryService implements IBeneficiaryService {


  private IBeneficiaryDAO beneficiaryDAO;

  // Managers


  @Inject
  public BeneficiaryService(IBeneficiaryDAO beneficiaryDAO) {
    this.beneficiaryDAO = beneficiaryDAO;


  }

  @Override
  public boolean deleteBeneficiary(long beneficiaryId) {

    return beneficiaryDAO.deleteBeneficiary(beneficiaryId);
  }

  @Override
  public boolean existBeneficiary(long beneficiaryID) {

    return beneficiaryDAO.existBeneficiary(beneficiaryID);
  }

  @Override
  public List<Beneficiary> findAll() {

    return beneficiaryDAO.findAll();

  }

  @Override
  public Beneficiary getBeneficiaryById(long beneficiaryID) {

    return beneficiaryDAO.find(beneficiaryID);
  }

  @Override
  public List<Beneficiary> getBeneficiarysByUserId(Long userId) {
    return beneficiaryDAO.getBeneficiarysByUserId(userId);
  }

  @Override
  public long saveBeneficiary(Beneficiary beneficiary) {

    return beneficiaryDAO.save(beneficiary);
  }


}
