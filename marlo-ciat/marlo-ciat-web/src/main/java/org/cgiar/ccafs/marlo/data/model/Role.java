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

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.google.gson.annotations.Expose;

/**
 * Modified by @author nmatovu last on Sep 29, 2016
 *
 */
public class Role implements Serializable {

	private static final long serialVersionUID = 5596676652614917686L;
	@Expose
	private Long id;

	private Crp crp;

	@Expose
	private String description;

	@Expose
	private String acronym;

	@Expose
	private Set<UserRole> userRoles = new HashSet<UserRole>(0);

	public Role() {
	}

	public Role(Crp crp, String description, String acronym) {
		this.crp = crp;
		this.description = description;
		this.acronym = acronym;

	}

	public Role(Crp crp, String description, String acronym,
			Set<UserRole> userRoles) {
		this.crp = crp;
		this.description = description;
		this.acronym = acronym;

		this.userRoles = userRoles;
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
		Role other = (Role) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

	public String getAcronym() {
		return this.acronym;
	}

	/**
	 * @return the crp
	 */
	public Crp getcrp() {
		return crp;
	}

	/**
	 * @param crp
	 *            the crp to set
	 */
	public void setcrp(Crp crp) {
		this.crp = crp;
	}

	public String getDescription() {
		return this.description;
	}

	public Long getId() {
		return this.id;
	}

	public Set<UserRole> getUserRoles() {
		return this.userRoles;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public void setAcronym(String acronym) {
		this.acronym = acronym;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setUserRoles(Set<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	@Override
	public String toString() {
		return id.toString();
	}
}
