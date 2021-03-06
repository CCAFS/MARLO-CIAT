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

import org.cgiar.ccafs.marlo.data.dao.ICredentialsDAO;
import org.cgiar.ccafs.marlo.data.service.AuthenticationManager;

import com.google.inject.Inject;


/**
 * @author Hermes Jiménez - CIAT/CCAFS
 */
public class AuthenticationManagerImp implements AuthenticationManager {


  private ICredentialsDAO credentialsDao;

  @Inject
  public AuthenticationManagerImp(ICredentialsDAO credentialsDao) {
    super();
    this.credentialsDao = credentialsDao;
  }

  @Override
  public boolean veirifyCredentials(String email, String password) {
    return credentialsDao.verifiyCredentials(email, password);
  }

}
