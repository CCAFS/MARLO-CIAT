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


import org.cgiar.ccafs.marlo.data.dao.IBeneficiaryTypeDAO;
import org.cgiar.ccafs.marlo.data.model.BeneficiaryType;
import org.cgiar.ccafs.marlo.data.service.IBeneficiaryTypeService;

import java.util.List;

import com.google.inject.Inject;

/**
 * @author Christian Garcia
 */
public class BeneficiaryTypeService implements IBeneficiaryTypeService {


  private IBeneficiaryTypeDAO beneficiaryTypeDAO;

  // Managers


  @Inject
  public BeneficiaryTypeService(IBeneficiaryTypeDAO beneficiaryTypeDAO) {
    this.beneficiaryTypeDAO = beneficiaryTypeDAO;


  }

  @Override
  public boolean deleteBeneficiaryType(long beneficiaryTypeId) {

    return beneficiaryTypeDAO.deleteBeneficiaryType(beneficiaryTypeId);
  }

  @Override
  public boolean existBeneficiaryType(long beneficiaryTypeID) {

    return beneficiaryTypeDAO.existBeneficiaryType(beneficiaryTypeID);
  }

  @Override
  public List<BeneficiaryType> findAll() {

    return beneficiaryTypeDAO.findAll();

  }

  @Override
  public BeneficiaryType getBeneficiaryTypeById(long beneficiaryTypeID) {

    return beneficiaryTypeDAO.find(beneficiaryTypeID);
  }

  @Override
  public List<BeneficiaryType> getBeneficiaryTypesByUserId(Long userId) {
    return beneficiaryTypeDAO.getBeneficiaryTypesByUserId(userId);
  }

  @Override
  public long saveBeneficiaryType(BeneficiaryType beneficiaryType) {

    return beneficiaryTypeDAO.save(beneficiaryType);
  }


}
