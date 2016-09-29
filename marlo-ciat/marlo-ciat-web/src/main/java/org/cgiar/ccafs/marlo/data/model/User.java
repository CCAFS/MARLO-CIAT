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

/**
 * 
 */
package org.cgiar.ccafs.marlo.data.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.cgiar.ccafs.marlo.data.IAuditlog;

import com.google.gson.annotations.Expose;

/**
 * Represents the User of the system.
 * 
 * Modified by @author nmatovu last on Sep 29, 2016
 *
 */
public class User implements IAuditlog {
	// TODO: Extend Auditlog
	private static final long serialVersionUID = -5959737574447795130L;
	@Expose
	private Long id;

	@Expose
	private String firstName;

	@Expose
	private String lastName;

	@Expose
	private String username;

	@Expose
	private String email;

	@Expose
	private String password;

	@Expose
	private boolean cgiarUser;

	private boolean autoSave;

	private User createdBy;

	private Date activeSince;

	private User modifiedBy;

	private String modificationJustification;
	private boolean active;
	private Date lastLogin;
	private Set<UserRole> userRoles = new HashSet<UserRole>(0);
	private Set<CrpUser> crpUsers = new HashSet<CrpUser>(0);

	public User() {
	}

	public User(String email, String password, boolean cgiarUser, boolean active) {
		this.email = email;
		this.password = password;
		this.cgiarUser = cgiarUser;
		this.active = active;
	}

	public User(String firstName, String lastName, String username,
			String email, String password, boolean cgiarUser, User createdBy,
			boolean active, Date lastLogin, Set<UserRole> userRoles,
			Set<CrpUser> crpUsers) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.email = email;
		this.password = password;
		this.cgiarUser = cgiarUser;
		this.active = active;
		this.createdBy = createdBy;

		this.lastLogin = lastLogin;
		this.userRoles = userRoles;
		this.crpUsers = crpUsers;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		User other = (User) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

	public Date getActiveSince() {
		return activeSince;
	}

	/**
	 * This method returns the user's full name.
	 * 
	 * @return a String that represents the user's full name. e.g. Héctor Tobón
	 */
	public String getComposedCompleteName() {
		return this.firstName + " " + this.lastName;
	}

	/**
	 * This method returns a composed way to show a User.
	 * 
	 * @return a String that represents a User. e.g. Tobón, Héctor
	 *         <h.f.tobon@cgiar.org>
	 */
	public String getComposedName() {
		if (this.id == null || this.id == -1) {
			return "";
		}
		return this.lastName + ", " + this.firstName + " <" + this.email + ">";
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public Set<CrpUser> getCrpUsers() {
		return this.crpUsers;
	}

	public String getEmail() {
		return this.email;
	}

	public String getFirstName() {
		return this.firstName;
	}

	@Override
	public Long getId() {
		return this.id;
	}

	public Date getLastLogin() {
		return this.lastLogin;
	}

	public String getLastName() {
		return this.lastName;
	}

	@Override
	public String getLogDeatil() {
		StringBuilder sb = new StringBuilder();
		sb.append("Id : ").append(this.getId());
		return sb.toString();
	}

	public String getModificationJustification() {
		return modificationJustification;
	}

	@Override
	public User getModifiedBy() {
		return modifiedBy;
	}

	public String getPassword() {
		return this.password;
	}

	public String getUsername() {
		return this.username;
	}

	public Set<UserRole> getUserRoles() {
		return this.userRoles;
	}

	@Override
	public boolean isActive() {
		return active;
	}

	public boolean isAutoSave() {
		return autoSave;
	}

	public boolean isCgiarUser() {
		return cgiarUser;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setActiveSince(Date activeSince) {
		this.activeSince = activeSince;
	}

	public void setAutoSave(boolean autoSave) {
		this.autoSave = autoSave;
	}

	public void setCgiarUser(boolean cgiarUser) {
		this.cgiarUser = cgiarUser;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public void setCrpUsers(Set<CrpUser> crpUsers) {
		this.crpUsers = crpUsers;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setModificationJustification(String modificationJustification) {
		this.modificationJustification = modificationJustification;
	}

	public void setModifiedBy(User modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setUserRoles(Set<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	@Override
	public String toString() {
		return id.toString();
	}
}
