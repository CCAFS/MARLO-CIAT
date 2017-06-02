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

package org.cgiar.ccafs.marlo.action.capdev;

import org.cgiar.ccafs.marlo.action.BaseAction;
import org.cgiar.ccafs.marlo.config.APConfig;
import org.cgiar.ccafs.marlo.utils.APConstants;

import org.cgiar.ciat.auth.LDAPService;
import org.cgiar.ciat.auth.LDAPUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;

public class ContactPersonAction extends BaseAction {

  private static final long serialVersionUID = 1L;

  private List<Map<String, Object>> users;


  @Inject
  public ContactPersonAction(APConfig config) {
    super(config);
    // TODO Auto-generated constructor stub
  }


  @Override
  public String execute() throws Exception {
    // TODO Auto-generated method stub
    return super.execute();
  }


  public List<Map<String, Object>> getUsers() {
    return users;
  }


  @Override
  public void prepare() throws Exception {
    // TODO Auto-generated method stub
    super.prepare();
  }


  public String searchADUser() throws Exception {
    final Map<String, Object> parameters = this.getParameters();
    final LDAPService service = new LDAPService();
    final String queryParameter = StringUtils.trim(((String[]) parameters.get(APConstants.QUERY_PARAMETER))[0]);
    final List<LDAPUser> users = service.searchUsers(queryParameter);
    this.users = new ArrayList<>();

    for (final LDAPUser user : users) {
      final Map<String, Object> userMap = new HashMap<>();
      userMap.put("firstName", user.getFirstName());
      userMap.put("lastName", user.getLastName());
      userMap.put("email", user.getEmail());
      this.users.add(userMap);

    }

    return SUCCESS;

  }


  public void setUsers(List<Map<String, Object>> users) {
    this.users = users;
  }


}
